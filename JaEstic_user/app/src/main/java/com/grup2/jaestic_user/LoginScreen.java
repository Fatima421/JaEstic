package com.grup2.jaestic_user;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.Calendar;

public class LoginScreen extends AppCompatActivity {
    // Global properties
    private FirebaseAuth mFirebaseAuth;
    private GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);

        // Creating an intent to be able to go to RegisterScreen
        Intent goToRegisterScreen = new Intent(this, RegisterScreen.class);
        Intent goToMainScreen = new Intent(this, MainScreen.class);


        // Properties
        Button signInGoogleBtn = findViewById(R.id.signInGoogleBtn);
        Button signInFacebookBtn = findViewById(R.id.signInFacebookBtn);
        Button signInBtn = findViewById(R.id.LoginBtn);
        EditText emailText = findViewById(R.id.txtMail);
        EditText pwdText = findViewById(R.id.txtPassword);
        TextView registerTextView = findViewById(R.id.txtregister2);
        TextView rememberPasswordTextView = findViewById(R.id.txtRememberPassword);


        // Initialize Firebase Auth
        mFirebaseAuth = FirebaseAuth.getInstance();
        createRequest();

        // If Sign In with Google button is clicked
        signInGoogleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();

            }
        });

        // If Sign In with email button is clicked
        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailText.getText().toString();
                String password = pwdText.getText().toString();

                if (!email.equals("") && !password.equals("")) {
                    mFirebaseAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(LoginScreen.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Toast.makeText(LoginScreen.this, getString(R.string.authSuccess),
                                                Toast.LENGTH_LONG).show();

                                        startActivity(goToMainScreen);
                                        finish();
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Toast.makeText(LoginScreen.this, getString(R.string.authFail),
                                                Toast.LENGTH_LONG).show();
                                    }
                                }
                    });
                }
            }
        });
        // If RememberPassword TextView is clicked
        rememberPasswordTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFirebaseAuth.sendPasswordResetEmail(emailText.toString())
                    .addOnCompleteListener(LoginScreen.this, new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(LoginScreen.this, "Reset link sent to your email",
                                        Toast.LENGTH_LONG).show();

                                startActivity(goToMainScreen);
                                finish();
                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(LoginScreen.this, "Unable to send reset mail",
                                        Toast.LENGTH_LONG).show();
                            }
                        }
                });
            }
        });

        // If register TextView is clicked
        registerTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(goToRegisterScreen);
            }
        });
    }

    private void createRequest() {
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id_manual))
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options especified by gso
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, 1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                //Log.w(TAG, "Google sign in failed", e);
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mFirebaseAuth.signInWithCredential(credential)
                .addOnSuccessListener(this, authResult -> {
                    startActivity(new Intent(LoginScreen.this, PreLoginScreen.class));
                    finish();
                });
        // .addOnFailureListener(this, e -> Toast.makeText(SignInActivity.this, "Authentication failed.",
        //         Toast.LENGTH_SHORT).show());
    }
}