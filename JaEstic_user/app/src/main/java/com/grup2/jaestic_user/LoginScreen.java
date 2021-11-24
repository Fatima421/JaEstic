package com.grup2.jaestic_user;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.SpannableString;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class LoginScreen extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);
        Intent redirect = new Intent(this, LoginScreen.class);

        EditText txtMail = findViewById(R.id.txtMail);
        EditText txtPassword = findViewById(R.id.txtPassword);
        Button btnlogin = findViewById(R.id.btnlogin);

        btnlogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (txtMail.getText().toString().equals("sergio") && txtPassword.getText().toString().equals("sergio")) {
                    startActivity(redirect);
                }
            }
        });
    }
}