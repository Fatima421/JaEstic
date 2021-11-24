package com.grup2.jaestic_user;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class PreLoginScreen extends AppCompatActivity {

    // Global properties
    private FirebaseAuth mAuth;
    Intent goToRegisterScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.prelogin_screen);

        // Properties
        Button signUpBtn = findViewById(R.id.signUpBtn);
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Creating an intent to be able to go to another screen
        goToRegisterScreen = new Intent(this, RegisterScreen.class);

        // If signUp button is clicked changed to register screen
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(goToRegisterScreen);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            startActivity(goToRegisterScreen);
        }
    }
}