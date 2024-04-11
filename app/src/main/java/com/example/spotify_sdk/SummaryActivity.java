package com.example.spotify_sdk;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class SummaryActivity extends AppCompatActivity {
    Button saveslot1, saveslot2, saveslot3, saveslot4;
    FirebaseFirestore db;
    FirebaseAuth mAuth;
    FirebaseUser user;
    TextView topArtist, topSong, Genre;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);
        saveslot1 = findViewById(R.id.saveSlot1);
        saveslot2 = findViewById(R.id.saveSlot2);
        saveslot3 = findViewById(R.id.saveSlot3);
        saveslot4 = findViewById(R.id.saveSlot4);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        user = mAuth.getCurrentUser();
        topArtist = findViewById(R.id.topArtistTextView);
        topSong = findViewById(R.id.topSongTextView);
        Genre = findViewById(R.id.topGenreTextView);
        TextView summary = findViewById(R.id.SummaryTitle);

        Button backBth = findViewById(R.id.backBtn);
        backBth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent to navigate to the Login activity
                Intent intent = new Intent(SummaryActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        saveslot1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CollectionReference ColRef = db.collection("users")
                        .document(mAuth.getUid().toString())
                        .collection("save");
                ColRef.document("saveslot1").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                topArtist.setText(document.get("topArtist").toString());
                                topSong.setText(document.get("topSong").toString());
                                Genre.setText(document.get("Genre").toString());
                            } else {
                                Toast.makeText(SummaryActivity.this, "Save Slot is empty", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(SummaryActivity.this, task.getException().getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        saveslot2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CollectionReference ColRef = db.collection("users")
                        .document(mAuth.getUid().toString())
                        .collection("save");
                ColRef.document("saveslot2").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                topArtist.setText(document.get("topArtist").toString());
                                topSong.setText(document.get("topSong").toString());
                                Genre.setText(document.get("Genre").toString());
                            } else {
                                Toast.makeText(SummaryActivity.this, "Save Slot is empty", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(SummaryActivity.this, task.getException().getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        saveslot3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CollectionReference ColRef = db.collection("users")
                        .document(mAuth.getUid().toString())
                        .collection("save");
                ColRef.document("saveslot3").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                topArtist.setText(document.get("topArtist").toString());
                                topSong.setText(document.get("topSong").toString());
                                Genre.setText(document.get("Genre").toString());
                            } else {
                                Toast.makeText(SummaryActivity.this, "Save Slot is empty", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(SummaryActivity.this, task.getException().getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        saveslot4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CollectionReference ColRef = db.collection("users")
                        .document(mAuth.getUid().toString())
                        .collection("save");
                ColRef.document("saveslot4").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                topArtist.setText(document.get("topArtist").toString());
                                topSong.setText(document.get("topSong").toString());
                                Genre.setText(document.get("Genre").toString());
                            } else {
                                Toast.makeText(SummaryActivity.this, "Save Slot is empty", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(SummaryActivity.this, task.getException().getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}