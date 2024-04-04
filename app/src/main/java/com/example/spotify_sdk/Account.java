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
    TextView deleteAButton, changeEmailButton, changePasswordButton;
    EditText changeEmailText, changePasswordText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        user = FirebaseAuth.getInstance().getCurrentUser();
        deleteAButton = findViewById(R.id.DeleteAccountButton);
        changeEmailButton = findViewById(R.id.ChangeEmailButton);
        changePasswordButton = findViewById(R.id.changePasswordButton);
        changeEmailText = findViewById(R.id.editEmail);
        changePasswordText = findViewById(R.id.editPassword);
        deleteAButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                user.delete()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(getApplicationContext(), "Delete Successful.", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), Login.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        });
            }
        });
        changeEmailButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                user.updateEmail(changeEmailText.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete( Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(getApplicationContext(), "User email address updated.", Toast.LENGTH_SHORT).show();
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

    }
}