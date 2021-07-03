package com.example.dunya_sesi.ui.main;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dunya_sesi.ChatPage;
import com.example.dunya_sesi.Explore;
import com.example.dunya_sesi.R;
import com.example.dunya_sesi.ViewProfile;
import com.example.dunya_sesi.util;

import java.io.InputStream;
import java.util.ArrayList;

public class WorldSearchUserResultAdapter extends
        RecyclerView.Adapter<WorldSearchUserResultAdapter.ViewHolder> {

    Activity activity;
    ArrayList<WorldSearchUserResult> searchResults;
    String defaultProfileImageLink = "https://i.pinimg.com/originals/1a/41/ec/1a41ec3f12b4d5165d46168bd952117d.gif";

    WorldSearchUserResultAdapter(ArrayList<WorldSearchUserResult> newSearchResults, Activity activity) {
        this.searchResults = newSearchResults;
        this.activity = activity;
    }

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public ImageView profileImage;
        public TextView username;
        public TextView caption;
        public Button viewProfileButton;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            profileImage = (ImageView) itemView.findViewById(R.id.profileImage);
            username = (TextView) itemView.findViewById(R.id.username);
            caption = (TextView) itemView.findViewById(R.id.caption);
            viewProfileButton = (Button) itemView.findViewById(R.id.viewProfileButton);
        }
    }

    @NonNull
    @Override
    public WorldSearchUserResultAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View resultView = inflater.inflate(R.layout.world_search_user_result, parent, false);

        // Return a new holder instance
        WorldSearchUserResultAdapter.ViewHolder viewHolder = new WorldSearchUserResultAdapter.ViewHolder(resultView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull WorldSearchUserResultAdapter.ViewHolder holder, int position) {
        final WorldSearchUserResult searchResult = searchResults.get(position);
        holder.username.setText(searchResult.username);
        holder.caption.setText(searchResult.caption);
//download profile image from int and upl to image view
        if (searchResult.profile_photo_url.equals("default")) {
            new WorldSearchUserResultAdapter.DownloadImageTask(holder.profileImage)
                    .execute(defaultProfileImageLink);
        } else {
            new WorldSearchUserResultAdapter.DownloadImageTask(holder.profileImage)
                    .execute(searchResult.profile_photo_url);
        }

            holder.viewProfileButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(!util.isNetworkAvailable((ConnectivityManager)
                            activity.getSystemService(Context.CONNECTIVITY_SERVICE))) {
                        Toast toast = Toast.makeText(activity, "Please connect to the internet.", Toast.LENGTH_LONG);
                        toast.show();
                        return;
                    }
                    if(util.getEmailFromSharePreferences(activity).equals(searchResult.email)) {
                        Intent i = new Intent(activity, Explore.class);
                        i.putExtra("frgToLoad", 2);
                        activity.startActivity(i);
                        activity.finish();
                    } else {
                        Intent i = new Intent(activity, ViewProfile.class);
                        i.putExtra("profileEmail", searchResult.email);
                        i.putExtra("profileId", searchResult.userId);
                        activity.startActivity(i);
                    }
                }
            });

    }

    @Override
    public int getItemCount() {
        return searchResults.size();
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
}
