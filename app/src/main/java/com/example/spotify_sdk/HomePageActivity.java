package com.example.spotify_sdk;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomePageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        // "Let's Muse" button and set its click listener
        Button btnLetsMuse = findViewById(R.id.btnLetsMuse);
        btnLetsMuse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent to navigate to the Login activity
                Intent intent = new Intent(HomePageActivity.this, Login.class);
                startActivity(intent);
            }
        });

    }
}

