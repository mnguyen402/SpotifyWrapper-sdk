package com.example.spotify_sdk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class SpotifyWrapperActivity extends AppCompatActivity {
    private TextView topSongTextView, topArtistTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spotify_wrapper);
        topSongTextView = findViewById(R.id.topSongTextView);
        topArtistTextView = findViewById(R.id.topArtistTextView);
        topArtistTextView.setText(getIntent().getStringExtra("topArtist"));
        topSongTextView.setText(getIntent().getStringExtra("topSong"));

    }
}