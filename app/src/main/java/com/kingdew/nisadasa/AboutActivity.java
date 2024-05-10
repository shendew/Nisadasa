package com.kingdew.nisadasa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
    }

    public void Visit(View view) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://thedevsl.com/"));
        startActivity(browserIntent);
    }
}