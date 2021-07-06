package com.example.dunya_sesi;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.dunya_sesi.ui.main.ProfileInfo;
import com.example.dunya_sesi.ui.main.WorldSearchUserResult;
import com.example.dunya_sesi.ui.main.WorldSearchUserResultAdapter;

import java.util.ArrayList;

public class FriendRequests extends AppCompatActivity {

    Button backButton;
    RecyclerView friendRequestList;
    ArrayList<ProfileInfo> requestProfiles = new ArrayList<ProfileInfo>();
    FriendRequestAdapter friendRequestAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_requests);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null )
        {
            actionBar.setTitle("Friend Requests");
        }

        backButton = findViewById(R.id.backButton);
        friendRequestList = findViewById(R.id.friendRequestList);

        friendRequestAdapter = new FriendRequestAdapter(requestProfiles, this);

        friendRequestList.setAdapter(friendRequestAdapter);
        // Set layout manager to position the items
        friendRequestList.setLayoutManager(new LinearLayoutManager(this));

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        String response = "";

        new util.GetFriendRequestsTask(response, util.getUserIdFromSharePreferences(this)) {
            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                if(result.length() > 0) {
                    requestProfiles.clear();
                    for (WorldSearchUserResult searchUserResult : util.searchResultToUserList(result)) {
                        requestProfiles.add(new ProfileInfo(searchUserResult.userId, searchUserResult.username, searchUserResult.caption, searchUserResult.profile_photo_url, searchUserResult.email));
                    }
                    friendRequestAdapter.notifyDataSetChanged();
                }
            }
        }.execute();
    }
}