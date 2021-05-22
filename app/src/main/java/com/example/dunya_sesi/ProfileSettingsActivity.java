package com.example.dunya_sesi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ProfileSettingsActivity extends AppCompatActivity {


    Button settingsBackButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_settings);

        settingsBackButton = findViewById(R.id.settingsBackButton);

        settingsBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateBack();
            }
        });
    }

    private void navigateBack() {
        // TODO (homework) navigate back from the settings page
        Intent i = new Intent(this.getApplicationContext(), Explore.class);
        startActivity(i);
    }




}
