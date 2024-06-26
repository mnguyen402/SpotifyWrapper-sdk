package com.example.spotify_sdk;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class ChristmasWrappedActivity extends AppCompatActivity {
    private TextView topSongTextView, topArtistTextView;
    private Button saveBtn1, saveBtn4, saveBtn3, saveBtn2;
    FirebaseFirestore db;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_christmas_wrapped);
        topSongTextView = findViewById(R.id.topSongTextView);
        topArtistTextView = findViewById(R.id.topArtistTextView);
        topArtistTextView.setText(getIntent().getStringExtra("topArtist"));
        topSongTextView.setText(getIntent().getStringExtra("topSong"));
        saveBtn1 = findViewById(R.id.ChristmasBtn1);
        saveBtn2 = findViewById(R.id.ChrismasBtn2);
        saveBtn3 = findViewById(R.id.ChrismasBtn3);
        saveBtn4 = findViewById(R.id.ChrismasBtn4);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        saveBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, Object> user = new HashMap<>();
                user.put("topArtist", topArtistTextView.getText().toString());
                user.put("topSong", topSongTextView.getText().toString());
                user.put("Genre", "");

                db.collection("users")
                        .document(mAuth.getUid())
                        .collection("save")
                        .document("saveslot1")
                        .set(user)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(ChristmasWrappedActivity.this, "Save Complete.", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(ChristmasWrappedActivity.this, e.getMessage(),
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
                }
            });
        saveBtn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, Object> user = new HashMap<>();
                user.put("topArtist", topArtistTextView.getText().toString());
                user.put("topSong", topSongTextView.getText().toString());
                user.put("Genre", "");

                db.collection("users")
                        .document(mAuth.getUid())
                        .collection("save")
                        .document("saveslot4")
                        .set(user)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(ChristmasWrappedActivity.this, "Save Complete.", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(ChristmasWrappedActivity.this, e.getMessage(),
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
        saveBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, Object> user = new HashMap<>();
                user.put("topArtist", topArtistTextView.getText().toString());
                user.put("topSong", topSongTextView.getText().toString());
                user.put("Genre", "");

                db.collection("users")
                        .document(mAuth.getUid())
                        .collection("save")
                        .document("saveslot2")
                        .set(user)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(ChristmasWrappedActivity.this, "Save Complete.", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(ChristmasWrappedActivity.this, e.getMessage(),
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
        saveBtn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, Object> user = new HashMap<>();
                user.put("topArtist", topArtistTextView.getText().toString());
                user.put("topSong", topSongTextView.getText().toString());
                user.put("Genre", "");

                db.collection("users")
                        .document(mAuth.getUid())
                        .collection("save")
                        .document("saveslot3")
                        .set(user)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(ChristmasWrappedActivity.this, "Save Complete.", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(ChristmasWrappedActivity.this, e.getMessage(),
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

    }
}
