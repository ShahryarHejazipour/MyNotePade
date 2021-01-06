package com.shahryar960103.mynotepade;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavDirections;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.shahryar960103.mynotepade.config.MyApplication;
import com.shahryar960103.mynotepade.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    @Override
    public void onBackPressed() {
        

        /*Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);*/

        super.onBackPressed();
    }
}