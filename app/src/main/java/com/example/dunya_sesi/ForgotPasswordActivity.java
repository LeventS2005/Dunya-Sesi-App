package com.example.dunya_sesi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
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

        String response = "";

errorTextView.setText("");

    //todo: work for today
if (email.length() < 5) {
            errorTextView.setText("Email is too short!");
    //end the function bc the user didn't enter a valid email
    return;

        }

        if(!util.isValidEmail(email)) {
        errorTextView.setText("Please enter a valid email");
            return;
        }

        // check internet connection
        if(!util.isNetworkAvailable((ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE))) {
            errorTextView.setText("Please connect to the internet!");
            return;
        }

        forgotPasswordButton.setEnabled(false);
        new util.ForgotPasswordTask(response, email) {
            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                if(response.equals("USER NOT FOUND")){
                    errorTextView.setText("Account not found.");
                } else {
                    errorTextView.setText("Email Sent!");
                }
                forgotPasswordButton.setEnabled(true);
            }
        }.execute();
    }
}
