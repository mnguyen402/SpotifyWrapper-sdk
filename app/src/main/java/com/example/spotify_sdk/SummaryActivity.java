package com.example.spotify_sdk;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

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