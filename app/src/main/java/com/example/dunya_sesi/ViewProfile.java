package com.example.dunya_sesi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dunya_sesi.ui.main.ProfileFragment;
import com.example.dunya_sesi.ui.main.ProfileInfo;

import java.io.InputStream;

public class ViewProfile extends AppCompatActivity {

    TextView username;
    TextView caption;
    ImageView profileImage;
    Button backButton;
    Button sendMessageButton;
    Button addFriendButton;
    Button removeFriendButton;
    Button removeFriendRequestButton;
    Button rejectFriendRequestButton;

    private int profileId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);

        profileImage = findViewById(R.id.viewProfileImage);
        username = findViewById(R.id.viewProfileUsername);
        caption = findViewById(R.id.viewProfileCaption);
        backButton = findViewById(R.id.backButton);
        sendMessageButton = findViewById(R.id.chatMessageButton);
        addFriendButton = findViewById(R.id.addFriendButton);
        removeFriendButton = findViewById(R.id.removeFriendButton);
        removeFriendRequestButton = findViewById(R.id.removeFriendRequestButton);
        rejectFriendRequestButton = findViewById(R.id.rejectFriendRequestButton);

        String profileEmail = "";
        Intent intent = getIntent();

        // Get the extras (if there are any)
        Bundle extras = intent.getExtras();
        if (extras != null) {
            if (extras.containsKey("profileEmail")) {
                profileEmail = getIntent().getExtras().getString("profileEmail");
            }
            if (extras.containsKey("profileId")) {
                profileId = getIntent().getExtras().getInt("profileId");
            }
        }

        loadUserProfile(username, caption, profileImage, profileEmail);

        canAddFriend(addFriendButton, sendMessageButton, util.getUserIdFromSharePreferences(this));

        canRemoveFriend(removeFriendButton, util.getUserIdFromSharePreferences(this));

        canRemoveFriendRequest(removeFriendRequestButton, util.getUserIdFromSharePreferences(this));

        canRejectFriendRequest(rejectFriendRequestButton, util.getUserIdFromSharePreferences(this));

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void loadUserProfile(final TextView username, final TextView caption, ImageView profileImage, String email) {
            String response = "";

            if (!util.isNetworkAvailable((ConnectivityManager)
                    getSystemService(Context.CONNECTIVITY_SERVICE))) {
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


    private void canAddFriend(final Button addFriendButton, final Button sendMessageButton, String myId) {
        addFriendButton.setEnabled(false);
        addFriendButton.setVisibility(View.INVISIBLE);

        String response = "";

        new util.GetFriendshipStatusTask(response, myId, String.valueOf(profileId)) {
            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                if(result.equals("NONE")) {
                    addFriendButton.setEnabled(true);
                    addFriendButton.setVisibility(View.VISIBLE);
                    sendMessageButton.setEnabled(false);
                    sendMessageButton.setVisibility(View.INVISIBLE);
                }
            }
        }.execute();
    }

    private void canRemoveFriend(final Button removeFriendButton, String myId) {
        removeFriendButton.setEnabled(true);
        removeFriendButton.setVisibility(View.VISIBLE);

        String response = "";

        new util.GetFriendshipStatusTask(response, myId, String.valueOf(profileId)) {
            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                if(result.equals("NONE") || result.equals("REQUESTED")) {
                    removeFriendButton.setEnabled(false);
                    removeFriendButton.setVisibility(View.INVISIBLE);
                }
            }
        }.execute();
    }

    private void canRemoveFriendRequest(final Button removeFriendRequestButton, String myId) {
        removeFriendRequestButton.setEnabled(false);
        removeFriendRequestButton.setVisibility(View.INVISIBLE);

        String response = "";

        new util.GetSentFriendRequestTask(response, myId, String.valueOf(profileId)) {
            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                if(result.equals("REQUESTED")) {
                    removeFriendRequestButton.setEnabled(false);
                    removeFriendRequestButton.setVisibility(View.INVISIBLE);
                }
            }
        }.execute();
    }

    private void canRejectFriendRequest(final Button rejectFriendRequestButton, String myId) {
        rejectFriendRequestButton.setEnabled(false);
        rejectFriendRequestButton.setVisibility(View.INVISIBLE);

        String response = "";

        new util.GetRecievedFriendRequestTask(response, myId, String.valueOf(profileId)) {
            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                if(result.equals("REQUESTED")) {
                    rejectFriendRequestButton.setEnabled(false);
                    rejectFriendRequestButton.setVisibility(View.INVISIBLE);
                }
            }
        }.execute();
    }
}