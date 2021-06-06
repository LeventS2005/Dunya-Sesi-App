package com.example.dunya_sesi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ForgotPasswordActivity extends AppCompatActivity {

    Button loginButton;
    Button forgotPasswordButton;
    EditText emailEditText;
    TextView errorTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        loginButton = findViewById(R.id.loginButton);
        forgotPasswordButton = findViewById(R.id.forgotPasswordSendEmailButton);

        emailEditText = findViewById(R.id.forgotPasswordEmailEditText);
        errorTextView = findViewById(R.id.forgotPasswordErrorTextView);

        forgotPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendEmail(emailEditText.getText().toString().trim());
            }
        });


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateToLoginPage();
            }
        });
    }

    private void navigateToLoginPage () {

        final Intent i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);
        finish();

    }

    private void sendEmail(String email){

        // todo: work for today

    }
}
