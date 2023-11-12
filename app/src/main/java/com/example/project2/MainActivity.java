package com.example.project2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {
    TextView theShops;
    TextView at;
    TextView uscVillage;
    float v = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void toLoginPage(View view){
        Log.i("MainActivity.java", "toLoginPage init");
        Intent intent = new Intent(this, LoginActivity.class);
//        Intent intent = new Intent(this, RemindersActivity.class);
        startActivity(intent);
        Log.i("toLoginPage()", "intent complete");
    }

    public void toHomeActivity(View view){
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }
}