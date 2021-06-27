package com.example.dunya_sesi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

// This is the registration page
public class RegisterActivity extends AppCompatActivity {

    Button registerButton;
    Button loginButton;
    Button forgotPasswordButton;

    EditText emailEditText;
    EditText usernameEditText;
    EditText passwordEditText;
    EditText confirmPasswordEditText;
    TextView registerErrorTextView;

    SharedPreferences mySharedPref;
    String mySharedPrefFileName = "LOGIN_INFO";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        registerButton = findViewById(R.id.registerButton);
        loginButton = findViewById(R.id.loginButton);
        forgotPasswordButton = findViewById(R.id.forgotPasswordButton);

        emailEditText = findViewById(R.id.registerEmailEditText);
        usernameEditText = findViewById(R.id.registerUsernameEditText);
        passwordEditText = findViewById(R.id.registerPasswordEditText);
        confirmPasswordEditText = findViewById(R.id.registerConfirmPasswordEditText);
        registerErrorTextView = findViewById(R.id.registerErrorTextView);

        mySharedPref = getSharedPreferences(mySharedPrefFileName,  Context.MODE_PRIVATE);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser(emailEditText.getText().toString().trim(),
                        usernameEditText.getText().toString().trim(),
                        passwordEditText.getText().toString().trim(),
                        confirmPasswordEditText.getText().toString().trim(),
                        mySharedPref.edit());
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateToLoginPage();
            }
        });

        forgotPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               navigateToForgotPasswordPage();
            }
        });

    }

    private void navigateToLoginPage () {

        final Intent i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);
        finish();

    }

    private void navigateToForgotPasswordPage () {

        final Intent i = new Intent(getApplicationContext(), ForgotPasswordActivity.class);
        startActivity(i);
        finish();

    }

    private void registerUser(final String email, final String username, String password, String confirmPassword, final SharedPreferences.Editor editor) {
        registerErrorTextView.setText("");
        final String response = "";
        if (email.length() < 5) {
            registerErrorTextView.setText("Please Enter Valid Email!");
            return;
        }

        if (!util.isValidEmail(email)) {
            registerErrorTextView.setText("Please Enter Valid Email!");
            return;
        }

        if (username.length() < 3) {
            registerErrorTextView.setText("Username too short");
            return;
        }
        if (password.length() < 5) {
            registerErrorTextView.setText("Password too short");
            return;
        }
        if(!password.equals(confirmPassword)) {
            registerErrorTextView.setText("Password and its confirmation do not match!");
            return;
        }

        if(!util.isNetworkAvailable((ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE))) {
            registerErrorTextView.setText("Please connect to the internet!");
            return;
        }

        registerButton.setEnabled(false);

        new util.RegisterTask(response, email, password, username) {
            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                if(response.equals("USER-EXISTS")){
                    registerErrorTextView.setText(
                            "An Account with this email exists. Please use a different email.");
                } else if (response.equals("CREATED")) {
                    editor.putString("USERNAME", username);
                    editor.putString("CAPTION", util.default_caption_new_user);
                    editor.putString("EMAIL", email);
                    editor.commit();
                    final Intent i = new Intent(getApplicationContext(), Explore.class);
                    startActivity(i);
                    finish();
                } else {
                    registerErrorTextView.setText(
                            "There was a server error. Please try again.");
                }
                registerButton.setEnabled(true);
            }
        }.execute();
    }
}
