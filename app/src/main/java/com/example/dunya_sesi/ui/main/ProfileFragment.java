package com.example.dunya_sesi.ui.main;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.dunya_sesi.Explore;
import com.example.dunya_sesi.MainActivity;
import com.example.dunya_sesi.ProfileSettingsActivity;
import com.example.dunya_sesi.R;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import static android.content.ContentValues.TAG;
import static android.os.FileUtils.copy;

/**
 * A placeholder fragment containing a simple view.
 */
public class ProfileFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    private Button logoutButton;
    SharedPreferences mySharedPref;
    String mySharedPrefFileName = "LOGIN_INFO";

    public static ProfileFragment newInstance(int index) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_profile, container, false);
        final ImageView profileImage = root.findViewById(R.id.userProfileImage);
        logoutButton = root.findViewById(R.id.logoutButton);
        mySharedPref = getContext().getSharedPreferences(mySharedPrefFileName,  Context.MODE_PRIVATE);

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logoutUser(mySharedPref.edit());
            }
        });

        new DownloadImageTask(profileImage)
                .execute("https://i.redd.it/fi48haz3f5i21.jpg");

        final Button settingsButton = root.findViewById(R.id.settingsButton);
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateToSettingsActivity();
            }
        });
        return root;
    }

    private void navigateToSettingsActivity() {
        // TODO(homework) using intent to navigate to settings activity
        Intent i = new Intent(this.getActivity(), ProfileSettingsActivity.class);
        startActivity(i);
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }

    private void logoutUser(SharedPreferences.Editor editor) {
        logoutButton.setEnabled(false);
        editor.clear();
        editor.commit();
        editor.apply();
        if (!mySharedPref.contains("USERNAME")) {
            final Intent i = new Intent(getContext().getApplicationContext(), MainActivity.class);
            startActivity(i);
            getActivity().finish();
        }
    }
}