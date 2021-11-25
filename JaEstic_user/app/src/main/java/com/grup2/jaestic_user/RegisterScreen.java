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

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.FirebaseDatabase;
import com.grup2.jaestic_user.Models.User;

public class RegisterScreen extends AppCompatActivity {
    // Global properties
    private FirebaseAuth mFirebaseAuth;
    private GoogleSignInClient mGoogleSignInClient;
    private static final String TAG = "SignInActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_screen);

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
                    //emailText.setCompoundDrawablesWithIntrinsicBounds(0,0, R.drawable.error,0);
                    //emailText.setCompoundDrawablePadding(5);
                }

                if (password.isEmpty()) {
                    pwdText.setError("password is required");
                    pwdText.requestFocus();
                    //pwdText.setCompoundDrawablesWithIntrinsicBounds(0,0, R.drawable.error,0);
                    //pwdText.setCompoundDrawablePadding(5);
                }

       //         progressBar.setVisibility(View.VISIBLE);

                if (!email.isEmpty() && !password.isEmpty()) {
                    mFirebaseAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(RegisterScreen.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Log.d(TAG, "createUserWithEmail:success");
                                        FirebaseUser user = mFirebaseAuth.getCurrentUser();
                                        startActivity(new Intent(RegisterScreen.this, MainActivity.class));
                                        //updateUI(user);
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                        Toast.makeText(RegisterScreen.this, "Authentication failed.",
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

        mGoogleSignInClient = GoogleSignIn.getClient(RegisterScreen.this, gso);



        // If Sign up with google button is clicked
        signUpGoogleBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                signIn();
            }
        });
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
                Log.w(TAG, "Google sign in failed", e);
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mFirebaseAuth.signInWithCredential(credential)
                .addOnSuccessListener(this, authResult -> {
                    startActivity(new Intent(RegisterScreen.this, MainActivity.class));
                    finish();
                })
               .addOnFailureListener(this, e -> Toast.makeText(RegisterScreen.this, "Authentication failed.",
                       Toast.LENGTH_SHORT).show());
    }

    // Get User Name
    private String getUserName() {
        FirebaseUser user = mFirebaseAuth.getCurrentUser();
        if (user != null) {
            return user.getDisplayName();
        }
        return "anonymous";
    }
}