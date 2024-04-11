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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class SummaryActivity extends AppCompatActivity {
    TextView saveslot1, saveslot2, saveslot3, saveslot4;
    FirebaseFirestore db;
    FirebaseAuth mAuth;
    FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);
        saveslot1 = findViewById(R.id.saveslot1);
        saveslot2 = findViewById(R.id.saveslot2);
        saveslot3 = findViewById(R.id.saveslot3);
        saveslot4 = findViewById(R.id.saveslot4);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        user = mAuth.getCurrentUser();

        Button backBth = findViewById(R.id.backBtn);
        backBth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent to navigate to the Login activity
                Intent intent = new Intent(SummaryActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        DocumentReference docRef = db.collection("users").document("saveslot1");
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        saveslot1.setText(document.get("TopArtist").toString() + "\n" + document.get("TopSong").toString() + "\n" + document.get("TopGenres").toString());
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
}