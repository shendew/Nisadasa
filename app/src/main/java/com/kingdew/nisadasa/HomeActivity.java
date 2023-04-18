package com.kingdew.nisadasa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kingdew.nisadasa.adapters.homeAdapter;
import com.kingdew.nisadasa.models.Posts;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {


    ArrayList<Posts> posts;
    homeAdapter adapter;
    RecyclerView mainRec;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mainRec=findViewById(R.id.mainrec);

        posts=new ArrayList<>();
        mainRec.setLayoutManager(new LinearLayoutManager(this));


        FirebaseDatabase.getInstance().getReference("Posts").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot item: snapshot.getChildren()){
                    posts.add(item.getValue(Posts.class));
                }


                Toast.makeText(HomeActivity.this, ""+posts.size(), Toast.LENGTH_SHORT).show();
                adapter=new homeAdapter(HomeActivity.this,posts);
                mainRec.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}