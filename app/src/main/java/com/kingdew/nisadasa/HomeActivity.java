package com.kingdew.nisadasa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.transition.TransitionManager;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kingdew.nisadasa.adapters.homeAdapter;
import com.kingdew.nisadasa.models.Posts;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class HomeActivity extends AppCompatActivity {


    ArrayList<Posts> posts;
    homeAdapter adapter;
    RecyclerView mainRec;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ImageView openDraw;
    SwipeRefreshLayout swipeLay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mainRec=findViewById(R.id.mainrec);
        drawerLayout=findViewById(R.id.drawelay);
        navigationView=findViewById(R.id.navi_view);
        openDraw=findViewById(R.id.opendrawe);
        swipeLay=findViewById(R.id.swipLay);

        posts=new ArrayList<>();
        mainRec.setLayoutManager(new LinearLayoutManager(this));

        loadData();

        openDraw.setOnClickListener(view -> {
            //TransitionManager.beginDelayedTransition((ViewGroup) navigationView.getParent());
            drawerLayout.openDrawer(GravityCompat.START);
        });
        mainRec.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
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


    }

    private void loadData() {
        FirebaseDatabase.getInstance().getReference("Posts").orderByKey().limitToFirst(10).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot item: snapshot.getChildren()){
                    posts.add(item.getValue(Posts.class));
                }
                Collections.reverse(posts);
                adapter=new homeAdapter(HomeActivity.this,posts);
                mainRec.setAdapter(adapter);
                if (swipeLay.isRefreshing()){
                    swipeLay.setRefreshing(false);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}