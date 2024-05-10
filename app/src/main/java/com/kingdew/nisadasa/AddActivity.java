package com.kingdew.nisadasa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class AddActivity extends AppCompatActivity {

    EditText name,body,image;
    Button add;

    int postCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        add=findViewById(R.id.button);
        name=findViewById(R.id.name);
        body=findViewById(R.id.body);
        image=findViewById(R.id.image);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Posts");

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                postCount=Integer.parseInt(String.valueOf(snapshot.getChildrenCount()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        add.setOnClickListener(view ->
        {

            HashMap<String,String> map=new HashMap<>();
            map.put("name",name.getText().toString());
            map.put("desc",body.getText().toString());
            map.put("image",image.getText().toString());
            map.put("hearts","");
            map.put("page",String.valueOf(postCount/20));
            map.put("date",String.valueOf(System.currentTimeMillis()));
            myRef.push().setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(AddActivity.this, "Added", Toast.LENGTH_SHORT).show();
                        name.setText("");
                        body.setText("");
                        image.setText("");
                    }else{
                        Toast.makeText(AddActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        });
    }
}