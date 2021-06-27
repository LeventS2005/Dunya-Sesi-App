package com.example.dunya_sesi;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ProfileSettingsActivity extends AppCompatActivity {


    Button settingsBackButton;
    EditText emailEditText;
    TextView emailUpdateErrorTextView;
    Button emailUpdateButton;
    EditText passwordEditText;
    EditText confirmPasswordEditText;
    TextView passwordUpdateErrorTextView;
    Button passwordUpdateButton;

    String currentEmail = "";

    SharedPreferences mySharedPref;
    String mySharedPrefFileName = "LOGIN_INFO";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_settings);

        emailEditText = findViewById(R.id.profileUserEmail);
        emailUpdateErrorTextView = findViewById(R.id.profileUserEmailUpdateError);
        emailUpdateButton = findViewById(R.id.updateProfileUserEmailButton);

        passwordEditText = findViewById(R.id.profileUserNewPassword);
        confirmPasswordEditText = findViewById(R.id.profileUserConfirmNewPassword);
        passwordUpdateErrorTextView = findViewById(R.id.userPasswordUpdateError);
        passwordUpdateButton = findViewById(R.id.profileUpdatePasswordButton);
        settingsBackButton = findViewById(R.id.settingsBackButton);

        mySharedPref = getSharedPreferences(mySharedPrefFileName,  Context.MODE_PRIVATE);

        loadProfileInfo(emailEditText);

        settingsBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateBack();
            }
        });


        emailUpdateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateEmail(emailEditText, emailUpdateErrorTextView, emailUpdateButton, mySharedPref.edit());
            }
        });

        passwordUpdateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updatePassword(passwordEditText,confirmPasswordEditText,passwordUpdateErrorTextView,passwordUpdateButton);
            }
        });
    }

    private void navigateBack() {
        // TODO (homework) navigate back from the settings page
        Intent i = new Intent(this.getApplicationContext(), Explore.class);
        startActivity(i);
    }

    private void loadProfileInfo(EditText emailEditText) {
        emailEditText.setText(util.getEmailFromSharePreferences(this));
        currentEmail = emailEditText.getText().toString();
    }

    private void updateEmail(final EditText emailEditText, final TextView emailUpdateErrorTextView, final Button emailUpdateButton, final SharedPreferences.Editor editor) {
        emailUpdateErrorTextView.setText("");

        if(emailEditText.getText().toString().trim().length() == 0) {
            emailUpdateErrorTextView.setText("Please enter a valid email");
            return;
        }

        if(!util.isValidEmail(emailEditText.getText().toString())) {
            emailUpdateErrorTextView.setText("Please enter a valid email");
            return;
        }

        if(!util.isNetworkAvailable((ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE))) {
            emailUpdateErrorTextView.setText("Please connect to the internet!");
            return;
        }

        String response = "";

        emailUpdateButton.setEnabled(false);

        new util.UpdateEmailTask(response, currentEmail, emailEditText.getText().toString()) {
            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                if(result.equals("USER-EXISTS")){
                    emailUpdateErrorTextView.setText(
                            "An Account with this email exists. Please use a different email.");
                } else if (result.equals(emailEditText.getText().toString())) {
                    editor.putString("EMAIL", emailEditText.getText().toString());
                    editor.apply();
                    editor.commit();
                    emailUpdateErrorTextView.setText("Email Updated");
                } else {
                    emailUpdateErrorTextView.setText(
                            "There was a server error. Please try again.");
                }
                emailUpdateButton.setEnabled(true);
            }
        }.execute();
    }


    private void updatePassword(EditText passwordEditText, final EditText confirmPasswordEditText, final TextView passwordUpdateErrorTextView, final Button passwordUpdateButton) {
        passwordUpdateErrorTextView.setText("");

        if(passwordEditText.getText().toString().trim().length() == 0) {
            passwordUpdateErrorTextView.setText("Please enter a valid password");
            return;
        }

        if(passwordEditText.getText().toString().trim().length() < 5) {
            passwordUpdateErrorTextView.setText("Please enter a longer password");
            return;
        }

        if(confirmPasswordEditText.getText().toString().trim().length() == 0) {
            passwordUpdateErrorTextView.setText("Please enter a valid password");
            return;
        }

        if(!confirmPasswordEditText.getText().toString().equals(passwordEditText.getText().toString())) {
            passwordUpdateErrorTextView.setText("Your password and confirmation do not match");
            return;
        }

        if(!util.isNetworkAvailable((ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE))) {
            passwordUpdateErrorTextView.setText("Please connect to the internet!");
            return;
        }

        String response = "";

        passwordUpdateButton.setEnabled(false);

        new util.UpdatePasswordTask(response, currentEmail, confirmPasswordEditText.getText().toString()) {
            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                if (result.equals(confirmPasswordEditText.getText().toString())) {
                    passwordUpdateErrorTextView.setText(
                            "You have a new password. Yay!");
                } else {
                    passwordUpdateErrorTextView.setText(
                            "There was a server error. Please try again.");
                }
                passwordUpdateButton.setEnabled(true);
            }
        }.execute();
    }



}
