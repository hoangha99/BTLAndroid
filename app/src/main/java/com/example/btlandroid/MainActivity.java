package com.example.btlandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.btlandroid.Activity.Info;
import com.example.btlandroid.Fragments.FragmentsAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private FragmentsAdapter bottom;
    private ViewPager viewPager;
    private BottomNavigationView bottomNavi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.info:
                Intent intent = new Intent(this, Info.class);
                startActivity(intent);
                break;
            case R.id.exit:
                System.exit(0);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void init() {
        viewPager = findViewById(R.id.viewPager);
        bottomNavi = findViewById(R.id.botnavi);

        bottom = new FragmentsAdapter(getSupportFragmentManager(), FragmentsAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(bottom);
        bottomNavi.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home: {
                        viewPager.setCurrentItem(0);
                        break;
                    }
                    case R.id.alarm: {
                        viewPager.setCurrentItem(1);
                        break;
                    }
                    case R.id.weather: {
                        viewPager.setCurrentItem(2);
                        break;
                    }
                }
                return true;
            }
        });
    }
}