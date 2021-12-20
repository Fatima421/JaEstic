package com.grup2.jaestic_user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.grup2.jaestic_user.Models.User;

public class RegisterActivity extends AppCompatActivity {
    // Global properties
    private FirebaseAuth mFirebaseAuth;
    private GoogleSignInClient mGoogleSignInClient;
    private static final String TAG = "SignInActivity";
    private static final int RC_SIGN_IN = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Properties
        Button signUpGoogleBtn = findViewById(R.id.signUpGoogleBtn);
        Button signUpBtn = findViewById(R.id.registerBtn);
        EditText nameText = findViewById(R.id.nameTxt);
        EditText emailText = findViewById(R.id.emailTxt);

        EditText pwdText = findViewById(R.id.pwdTxt);
        ProgressBar progressBar = findViewById(R.id.progress_bar);

        // Initialize Firebase Auth
        mFirebaseAuth = FirebaseAuth.getInstance();

        // If Sign Up button is clicked
        signUpBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // Save every fields text
                String name = nameText.getText().toString().trim();
                String email = emailText.getText().toString().trim();
                String password = pwdText.getText().toString().trim();

                // Check if fields are empty
                if (email.isEmpty()) {
                    emailText.setError("email is required");
                    emailText.requestFocus();
                }

                if (password.isEmpty()) {
                    pwdText.setError("password is required");
                    pwdText.requestFocus();
                }

                // If fields are empty create a user
                if (!email.isEmpty() && !password.isEmpty()) {
                    mFirebaseAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Log.d(TAG, "createUserWithEmail:success");
                                        startActivity(new Intent(RegisterActivity.this, NavigationActivity.class));
                                        //updateUI(user);

                                        User users = new User(email, name);
                                        //recyclerView.setVisibility(View.INVISIBLE);

                                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                                        DatabaseReference regRef = database.getReference("Users");
                                        regRef.push().setValue(users);

                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                        Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();
                                        //updateUI(null);
                                    }
                                }
                            });
                }

            }
        });

        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id_manual))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(RegisterActivity.this, gso);



        // If Sign up with google button is clicked
        signUpGoogleBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                signIn();
            }
        });
    }

    // Sign in
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    // Checks if google sign in was succesful or not
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
            }
        }
    }

    // Asks for google credentials
    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mFirebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mFirebaseAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            updateUI(null);
                        }
                    }
                });
    }

    // Moves you to the next screen
    private void updateUI(FirebaseUser user) {
        Intent goToMainScreen = new Intent(this, NavigationActivity.class);
        startActivity(goToMainScreen);
        RegisterActivity.this.finish();
    }
}