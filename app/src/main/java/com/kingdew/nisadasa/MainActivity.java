package com.kingdew.nisadasa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.bumptech.glide.Glide;

import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor sharedEditor ;
    public static final String PassString = "Check_App_Status";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getPreferences(Context.MODE_PRIVATE);
        sharedEditor = sharedPreferences.edit();




// handler for load first splash animations
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if(CheckAppIsOpenFirstTimeOrNot()){
                    startActivity(new Intent(MainActivity.this,LoginActivity.class));
                }
                else {
                    startActivity(new Intent(MainActivity.this,HomeActivity.class));

                }
            }
        },500);
    }


    //check shared preference for first launch
    public boolean CheckAppIsOpenFirstTimeOrNot(){

        if(sharedPreferences.getBoolean(PassString,true)){
            sharedEditor.putBoolean(PassString,false);
            sharedEditor.commit();
            sharedEditor.apply();
            // If App open for first time then return true.
            return true;
        }else {
            // If App open second time or already opened previously then return false.
            return false;
        }
    }
}