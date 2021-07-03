package com.example.dunya_sesi;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;

import com.example.dunya_sesi.ui.main.ProfileInfo;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dunya_sesi.ui.main.SectionsPagerAdapter;

public class Explore extends AppCompatActivity {

    SharedPreferences mySharedPref;
    String mySharedPrefFileName = "LOGIN_INFO";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        mySharedPref = getSharedPreferences(mySharedPrefFileName,  Context.MODE_PRIVATE);

        int intentFragment = 0;
        Intent intent = getIntent();

// Get the extras (if there are any)
        Bundle extras = intent.getExtras();
        if (extras != null) {
            if (extras.containsKey("frgToLoad")) {
               intentFragment = getIntent().getExtras().getInt("frgToLoad");
            }
        }

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        if(intentFragment == 2) {
            viewPager.setCurrentItem(2);
        }

        loadUserProfile(util.getEmailFromSharePreferences(this), mySharedPref.edit());
    }

    private void loadUserProfile(String email, final SharedPreferences.Editor editor) {
        String response = "";

        if (!util.isNetworkAvailable((ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE))) {
            // username.setText("Please connect to the internet to load your information");
            // Load information from shared preferences instead
            return;
        }

        new util.GetUserProfileTask(response, email) {
            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                ProfileInfo profileInfo = new ProfileInfo(result);
                editor.putString("USER_ID", String.valueOf(profileInfo.userId));
                editor.commit();
            }
        }.execute();
    }

}