package com.grup2.jaestic_user;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class PreLoginScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.prelogin_screen);
        Intent redirect = new Intent(this, LoginScreen.class);
        Intent redirect2 = new Intent(this, RegisterScreen.class);

        Button btnsignin = findViewById(R.id.btnsignin);
        Button btnsignup = findViewById(R.id.btnsignup);

        btnsignin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(redirect);
            }
        });

        btnsignup.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(redirect2);
            }
        });
    }
}