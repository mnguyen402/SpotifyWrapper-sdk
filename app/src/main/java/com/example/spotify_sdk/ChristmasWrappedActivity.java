package com.example.spotify_sdk;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ChristmasWrappedActivity extends AppCompatActivity {
    private TextView topSongTextView, topArtistTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_christmas_wrapped);
        topSongTextView = findViewById(R.id.topSongTextView);
        topArtistTextView = findViewById(R.id.topArtistTextView);
        topArtistTextView.setText(getIntent().getStringExtra("topArtist"));
        topSongTextView.setText(getIntent().getStringExtra("topSong"));
    }
}
