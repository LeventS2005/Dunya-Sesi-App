package com.example.dunya_sesi.ui.main;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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
import com.example.dunya_sesi.util;

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

    String oldUsername = "";
    String oldCaption = "";

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
        final EditText usernameEditText = root.findViewById(R.id.userName);
        final TextView usernameErrorTextView = root.findViewById(R.id.userNameUpdateError);
        final Button updateUsernameButton = root.findViewById(R.id.updateUsernameButton);
        final EditText captionEditText = root.findViewById(R.id.userCaption);
        final TextView captionErrorTextView = root.findViewById(R.id.userCaptionUpdateError);
        final Button updateCaptionButton = root.findViewById(R.id.updateCaptionButton);
        logoutButton = root.findViewById(R.id.logoutButton);
        mySharedPref = getContext().getSharedPreferences(mySharedPrefFileName,  Context.MODE_PRIVATE);

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logoutUser(mySharedPref.edit());
            }
        });

        loadUserProfile(usernameEditText, captionEditText, profileImage, util.getEmailFromSharePreferences(getActivity()));

        updateUsernameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateUsername(util.getEmailFromSharePreferences(getActivity()), usernameEditText.getText().toString(), usernameErrorTextView, usernameEditText);
            }
        });

        updateCaptionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateCaption(util.getEmailFromSharePreferences(getActivity()), captionEditText.getText().toString(), captionErrorTextView, captionEditText);
            }
        });

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

    private void loadUserProfile(final EditText username, final EditText caption, ImageView profileImage, String email) {
        String response = "";

        if (!util.isNetworkAvailable((ConnectivityManager)
                getActivity().getSystemService(Context.CONNECTIVITY_SERVICE))) {
           // username.setText("Please connect to the internet to load your information");
           // Load information from shared preferences instead
            return;
        }

        new DownloadImageTask(profileImage)
                .execute("https://i.redd.it/fi48haz3f5i21.jpg");

        new util.GetUserProfileTask(response, email) {
            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                ProfileInfo profileInfo = new ProfileInfo(result);
                username.setText(profileInfo.username);
                caption.setText(profileInfo.caption);
                oldUsername = profileInfo.username;
                oldCaption = profileInfo.caption;
            }
        }.execute();

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


    private void updateCaption (String email, String newCaption, final TextView captionErrorTextView, EditText captionField) {

        if (!util.isNetworkAvailable((ConnectivityManager)
                getActivity().getSystemService(Context.CONNECTIVITY_SERVICE))) {
            captionErrorTextView.setText("Please connect to the internet to perform task");
            return;
        }

        if (newCaption.trim().length() == 0) {
            captionErrorTextView.setText("Username cannot be empty. Please try again!");
            captionField.setText(oldCaption);
            return;
        }

        if (newCaption.trim().equals(oldCaption)) {
            captionErrorTextView.setText("Caption updated!");
            return;
        }

        String response = "";

        new util.UpdateCaptionTask(response, email, newCaption) {
            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                if(result.trim().length() == 0) {
                    captionErrorTextView.setText("Could no update caption. Server error!");
                } else {
                    captionErrorTextView.setText("Caption Updated");
                    oldCaption = result;
                }
            }
        }.execute();

    }


    private void updateUsername (String email, String newUsername, final TextView usernameErrorTextView, EditText usernameField) {

        if (!util.isNetworkAvailable((ConnectivityManager)
                getActivity().getSystemService(Context.CONNECTIVITY_SERVICE))) {
            usernameErrorTextView.setText("Please connect to the internet to perform task");
            return;
        }

        if (newUsername.trim().length() == 0) {
            usernameErrorTextView.setText("Username cannot be empty. Please try again!");
            usernameField.setText(oldUsername);
            return;
        }

        if (newUsername.trim().equals(oldUsername)) {
            usernameErrorTextView.setText("Username updated!");
            return;
        }

        String response = "";

        new util.UpdateUsernameTask(response, email, newUsername) {
            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                if(result.trim().length() == 0) {
                    usernameErrorTextView.setText("Could no update username. Server error!");
                } else {
                    usernameErrorTextView.setText("Username Updated");
                    oldUsername = result;
                }
            }
        }.execute();

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