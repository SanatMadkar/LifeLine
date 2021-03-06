package com.example.lifeline;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class homeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setTitle("My Profile");

        BottomNavigationView bottomNav=findViewById(R.id.bottom_nav);
        bottomNav.setOnNavigationItemSelectedListener(navListner);
        Menu menu =bottomNav.getMenu();
        MenuItem menuItem=menu.getItem(0);
        menuItem.setChecked(true);

        findViewById(R.id.homeLogout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                finish();
                startActivity(new Intent(homeActivity.this, LoginActivity.class));
            }
        });

        findViewById(R.id.homeRewards).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(homeActivity.this, RewardsActivity.class));
            }
        });

        findViewById(R.id.homeHistory).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(homeActivity.this, HistoryActivity.class));
            }
        });

        findViewById(R.id.homeEdit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(homeActivity.this, EditInfoActivity.class));
            }
        });


    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListner=new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()){
                case R.id.navigation_profile:
                    // do nothing
                    break;
                case R.id.navigation_appointment:
                    BookingActivity.step = 0;
                    startActivity(new Intent(homeActivity.this, BookingActivity.class));
                    break;
                case R.id.navigation_events:
                    startActivity(new Intent(homeActivity.this,UserEventMain.class));
                    break;
                case R.id.navigation_leaderboard:
                    startActivity(new Intent(homeActivity.this,LeaderboardActivity.class));
                    break;

            }
            return true;
        }
    };
}
