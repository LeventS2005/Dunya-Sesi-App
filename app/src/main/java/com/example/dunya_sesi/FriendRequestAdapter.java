package com.example.dunya_sesi;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dunya_sesi.ui.main.ProfileInfo;
import com.example.dunya_sesi.ui.main.WorldSearchUserResult;
import com.example.dunya_sesi.ui.main.WorldSearchUserResultAdapter;

import java.io.InputStream;
import java.util.ArrayList;

public class FriendRequestAdapter extends
        RecyclerView.Adapter<FriendRequestAdapter.ViewHolder> {

    Activity activity;
    ArrayList<ProfileInfo> requestProfiles;
    String defaultProfileImageLink = "https://i.pinimg.com/originals/1a/41/ec/1a41ec3f12b4d5165d46168bd952117d.gif";

    FriendRequestAdapter(ArrayList<ProfileInfo> requestProfiles, Activity activity) {
        this.requestProfiles = requestProfiles;
        this.activity = activity;
    }

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView username;
        public Button viewProfileButton;
        public Button acceptRequestButton;
        public Button rejectRequestButton;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            username = (TextView) itemView.findViewById(R.id.username);
            viewProfileButton = (Button) itemView.findViewById(R.id.requestViewProfileButton);
            acceptRequestButton = itemView.findViewById(R.id.acceptRequestButton);
            rejectRequestButton = itemView.findViewById(R.id.rejectRequestButton);
        }
    }

    @NonNull
    @Override
    public FriendRequestAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View resultView = inflater.inflate(R.layout.friend_request_item, parent, false);

        // Return a new holder instance
        FriendRequestAdapter.ViewHolder viewHolder = new FriendRequestAdapter.ViewHolder(resultView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FriendRequestAdapter.ViewHolder holder, int position) {
        final ProfileInfo searchResult = requestProfiles.get(position);
        holder.username.setText(searchResult.username);
        holder.viewProfileButton.setVisibility(View.INVISIBLE);

        holder.acceptRequestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 acceptFriendRequest(util.getUserIdFromSharePreferences(activity), String.valueOf(searchResult.userId),holder.acceptRequestButton, holder.rejectRequestButton, holder.viewProfileButton);
            }
        });

        holder.rejectRequestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rejectFriendRequest(util.getUserIdFromSharePreferences(activity), String.valueOf(searchResult.userId),holder.acceptRequestButton, holder.rejectRequestButton, holder.viewProfileButton);
            }
        });

        holder.viewProfileButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                if(!util.isNetworkAvailable((ConnectivityManager)
                        activity.getSystemService(Context.CONNECTIVITY_SERVICE))) {
                    Toast toast = Toast.makeText(activity, "Please connect to the internet.", Toast.LENGTH_LONG);
                    toast.show();
                    return;
                }
                requestProfiles.removeIf(profile -> profile.userId == searchResult.userId);
                notifyDataSetChanged();
                    Intent i = new Intent(activity, ViewProfile.class);
                    i.putExtra("profileEmail", searchResult.email);
                    i.putExtra("profileId", searchResult.userId);
                    activity.startActivity(i);

            }
        });
    }

    @Override
    public int getItemCount() {
        return requestProfiles.size();
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

    private void acceptFriendRequest(String myId, String profileId, final Button acceptRequestButton, final Button rejectRequestButton, final Button viewProfileButton) {
        acceptRequestButton.setEnabled(false);
        rejectRequestButton.setEnabled(false);
        viewProfileButton.setEnabled(false);

        String response = "";

        new util.AcceptFriendRequestTask(response, myId, profileId) {
            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                if(result.equals("FRIENDS")) {
                    viewProfileButton.setVisibility(View.VISIBLE);
                    viewProfileButton.setEnabled(true);
                    acceptRequestButton.setVisibility(View.INVISIBLE);
                    rejectRequestButton.setVisibility(View.INVISIBLE);
                }
                acceptRequestButton.setEnabled(true);
                rejectRequestButton.setEnabled(true);
            }
        }.execute();
    }

    private void rejectFriendRequest (String myId, final String profileId, final Button acceptRequestButton, final Button rejectRequestButton, final Button viewProfileButton) {
        acceptRequestButton.setEnabled(false);
        rejectRequestButton.setEnabled(false);
        viewProfileButton.setEnabled(false);

        String response = "";

        new util.RemoveFriendTask(response, myId, String.valueOf(profileId)) {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                if(result.equals("DELETED")) {
                    requestProfiles.removeIf(profile -> profile.userId == Integer.parseInt(profileId));
                    notifyDataSetChanged();
                }
                acceptRequestButton.setEnabled(true);
                rejectRequestButton.setEnabled(true);
            }
        }.execute();
    }
}
