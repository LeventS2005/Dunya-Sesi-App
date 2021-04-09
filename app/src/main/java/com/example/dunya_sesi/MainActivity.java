package com.example.dunya_sesi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

// first change
public class MainActivity extends AppCompatActivity {

    int number = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}