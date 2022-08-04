package com.example.imageswitcher;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    int a = 0;

    public void switchImage(View view){
        ImageView image = findViewById(R.id.imageView2);
        if(a == 0) {
            image.setImageResource(R.drawable.m);
            a = 1;
        }
        else {
            image.setImageResource(R.drawable.c);
            a = 0;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
