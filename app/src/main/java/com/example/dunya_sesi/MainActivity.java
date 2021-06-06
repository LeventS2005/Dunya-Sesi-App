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
import android.widget.Toast;

import java.io.UnsupportedEncodingException;

// this is login page
public class MainActivity extends AppCompatActivity {

    Button loginButton;
    Button registerButton;
    Button forgotPasswordButton;
    EditText loginEmailEditText;
    EditText loginPasswordEditText;
    TextView errorTextView;
    SharedPreferences mySharedPref;
    String mySharedPrefFileName = "LOGIN_INFO";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        registerButton = findViewById(R.id.registerButton);
        loginButton = findViewById(R.id.loginButton);
        forgotPasswordButton = findViewById(R.id.forgotPasswordButton);
        loginEmailEditText = findViewById(R.id.loginEmailEditText);
        loginPasswordEditText = findViewById(R.id.loginPasswordEditText);
        errorTextView = findViewById(R.id.loginErrorTextView);

        mySharedPref = getSharedPreferences(mySharedPrefFileName,  Context.MODE_PRIVATE);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (loginEmailEditText.getText().toString().trim().length() != 0 && loginPasswordEditText.getText().toString().trim().length() != 0) {
                    errorTextView.setText("");
                    try {
                        createNewLoggedInUser(loginEmailEditText.getText().toString(), loginPasswordEditText.getText().toString(), mySharedPref.edit());
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                } else {
                    errorTextView.setText("");
                    errorTextView.setText("Missing Field!");
                }
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateToRegisterPage();
            }
        });

        forgotPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateToForgotPasswordPage();
            }
        });


    }



    @Override
    protected void onResume() {
        super.onResume();

        if (isUserLoggedIn(mySharedPref)) {
            final Intent i = new Intent(this, Explore.class);
            startActivity(i);
            finish();
        }

    }

    private void createNewLoggedInUser (final String email, String password, final SharedPreferences.Editor editor) throws UnsupportedEncodingException {

        final String response = "";

        if(!util.isNetworkAvailable((ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE))) {
            errorTextView.setText("Please connect to the internet!");
            return;
        }

        if(!util.isValidEmail(email)) {
            errorTextView.setText("Please enter a valid email!");
            return;
        }

        loginButton.setEnabled(false);

        new util.LoginTask(response, email, password) {
            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                if(response.equals("USER-FOUND")){
                    editor.putString("USERNAME", email);
                    editor.commit();
                    final Intent i = new Intent(getApplicationContext(), Explore.class);
                    startActivity(i);
                    finish();
                } else {
                    errorTextView.setText("Account not found. Please check the email or password entered!");
                }
                loginButton.setEnabled(true);
            }
        }.execute();


    }

    private boolean isUserLoggedIn (SharedPreferences sharedPreferences) {

        if(!sharedPreferences.contains("USERNAME")) {
            return false;
        }

        if (sharedPreferences.getString("USERNAME", "").equals("")) {
            return false;
        } else {
            return true;
        }
    }

    private void navigateToRegisterPage () {

        final Intent i = new Intent(getApplicationContext(), RegisterActivity.class);
        startActivity(i);
        finish();

    }

    private void navigateToForgotPasswordPage () {

        final Intent i = new Intent(getApplicationContext(), ForgotPasswordActivity.class);
        startActivity(i);
        finish();

    }


}
