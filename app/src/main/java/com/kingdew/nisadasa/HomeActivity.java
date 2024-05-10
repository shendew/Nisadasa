package com.kingdew.nisadasa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.android.play.core.review.ReviewException;
import com.google.android.play.core.review.ReviewInfo;
import com.google.android.play.core.review.ReviewManager;
import com.google.android.play.core.review.ReviewManagerFactory;
import com.google.android.play.core.review.model.ReviewErrorCode;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.rvadapter.AdmobNativeAdAdapter;
import com.kingdew.nisadasa.adapters.DAOadapter;
import com.kingdew.nisadasa.adapters.cateAdapter;
import com.kingdew.nisadasa.adapters.homeAdapter;
import com.kingdew.nisadasa.adapters.homeNAdapter;
import com.kingdew.nisadasa.models.Category;
import com.kingdew.nisadasa.models.Posts;

import java.util.ArrayList;
import java.util.Collections;

public class HomeActivity extends AppCompatActivity {


    ArrayList<Posts> posts;
    homeNAdapter adapter;
    RecyclerView mainRec,catrec;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ImageView openDraw,searchBtn,more;
    SwipeRefreshLayout swipeLay;
    String key= null;
    DAOadapter daOadapter;
    boolean isLoading=false;
    EditText search_edittext;
    LinearLayout searchView;
    cateAdapter CateAdapter;
    ArrayList<Category> cates;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mainRec=findViewById(R.id.mainrec);
        drawerLayout=findViewById(R.id.drawelay);
        navigationView=findViewById(R.id.navi_view);
        openDraw=findViewById(R.id.opendrawe);
        swipeLay=findViewById(R.id.swipLay);
        searchBtn=findViewById(R.id.imageButton);
        search_edittext=findViewById(R.id.search_edittext);
        catrec=findViewById(R.id.catrec);
        searchView=findViewById(R.id.search_view);
        more=findViewById(R.id.more);
        cates=new ArrayList<>();
        daOadapter=new DAOadapter();

        drawerLayout =findViewById(R.id.drawelay);
        navigationView=findViewById(R.id.navi_view);

        navigationView.bringToFront();
        drawerLayout.closeDrawer(GravityCompat.START);
//        home_menu.setOnClickListener(view -> {
//            drawerLayout.openDrawer(GravityCompat.START);
//        });


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id=item.getItemId();
                drawerLayout.closeDrawer(GravityCompat.START);
                if (id == R.id.allQ) {
                    startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                } else if (id == R.id.rate) {
                    ReviewManager manager = ReviewManagerFactory.create(getApplicationContext());
                    Task<ReviewInfo> request = manager.requestReviewFlow();
                    request.addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            // We can get the ReviewInfo object
                            ReviewInfo reviewInfo = task.getResult();
                        } else {
                            Toast.makeText(HomeActivity.this, "Not logged in to google playstore", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else if (id == R.id.contact) {
                    startActivity(new Intent(getApplicationContext(), AboutActivity.class));
                } else if (id == R.id.about) {
                    startActivity(new Intent(getApplicationContext(), AboutActivity.class));
                }
                return true;
            }
        });

        posts=new ArrayList<>();
        mainRec.setLayoutManager(new LinearLayoutManager(this));
        mainRec.setHasFixedSize(true);
        adapter=new homeNAdapter(HomeActivity.this,posts);


//        AdmobNativeAdAdapter admobNativeAdAdapter= AdmobNativeAdAdapter.Builder.Companion.with(
//                        "ca-app-pub-3940256099942544/2247696110",//Create a native ad id from admob console
//                        adapter,//The adapter you would normally set to your recyClerView
//                        "medium"//Set it with "small","medium" or "custom"
//                )
//                .adItemIterval(3)//native ad repeating interval in the recyclerview
//                .build();

        mainRec.setAdapter(adapter);


        catrec.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        CateAdapter=new cateAdapter(getApplicationContext(),cates);
        catrec.setAdapter(CateAdapter);

        loadData();


        openDraw.setOnClickListener(view -> {
            //TransitionManager.beginDelayedTransition((ViewGroup) navigationView.getParent());
            drawerLayout.openDrawer(GravityCompat.START);
        });

        swipeLay.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (swipeLay.isRefreshing()){
                    posts.clear();
                    loadData();
                }
            }
        });
        searchBtn.setOnClickListener(view -> {
            Animation fade_in = AnimationUtils.loadAnimation(this, R.anim.fade_in);
            Animation fade_out = AnimationUtils.loadAnimation(this, R.anim.fade_out);
            if (search_edittext.getVisibility() == View.GONE)
            {
                search_edittext.setAnimation(fade_in);
                search_edittext.setVisibility(View.VISIBLE);
                searchBtn.setImageDrawable(getDrawable(R.drawable.baseline_close_24));
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)catrec.getLayoutParams();
                params.addRule(RelativeLayout.BELOW, R.id.search_view);

                //searchView.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, 60));
            }else
            {
                search_edittext.setAnimation(fade_out);

                search_edittext.setVisibility(View.GONE);
                searchBtn.setImageDrawable(getDrawable(R.drawable.baseline_search_24));
            }

        });

        more.setOnClickListener(view -> {
            startActivity(new Intent(HomeActivity.this,AddActivity.class));
        });

        search_edittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
// on text change listner process
                adapter.search(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    private void loadData() {
        swipeLay.setRefreshing(true);

        FirebaseDatabase.getInstance().getReference("Category").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot item: snapshot.getChildren()){
                    cates.add(item.getValue(Category.class));

                }
                CateAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        daOadapter.get().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot item: snapshot.getChildren()){
                    posts.add(item.getValue(Posts.class));
                }

                Collections.reverse(posts);

                adapter.notifyDataSetChanged();

                if (swipeLay.isRefreshing()){
                    swipeLay.setRefreshing(false);
                }
                isLoading=false;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

//    private void endlessData() {
//        FirebaseDatabase.getInstance().getReference("Posts").orderByKey().limitToFirst(3).startAfter(key).addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//                for (DataSnapshot item: snapshot.getChildren()){
//                    posts.add(item.getValue(Posts.class));
//                    key=item.getKey();
//                }
//
//                Collections.reverse(posts);
//                adapter=new homeAdapter(HomeActivity.this,posts);
//                mainRec.setAdapter(adapter);
//                adapter.notifyDataSetChanged();
//                if (swipeLay.isRefreshing()){
//                    swipeLay.setRefreshing(false);
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//
//    }
}