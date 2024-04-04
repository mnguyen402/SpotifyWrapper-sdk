package com.example.spotify_sdk;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Account extends AppCompatActivity {
    FirebaseUser user;
    TextView deleteAButton, changePasswordButton, backButton;
    EditText changePasswordText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        user = FirebaseAuth.getInstance().getCurrentUser();
        deleteAButton = findViewById(R.id.DeleteAccountButton);
        changePasswordButton = findViewById(R.id.changePasswordButton);
        changePasswordText = findViewById(R.id.editPassword);
        backButton = findViewById(R.id.backButtonAccount);
        deleteAButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                user.delete()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(getApplicationContext(), "Delete Successful.", Toast.LENGTH_SHORT).show();
                                    FirebaseAuth.getInstance().signOut();
                                    Intent intent = new Intent(getApplicationContext(), Login.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        });
            }
        });
        changePasswordButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                user.updatePassword(changePasswordText.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(getApplicationContext(), "User password address updated.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}