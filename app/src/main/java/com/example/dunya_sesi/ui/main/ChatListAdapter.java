package com.example.dunya_sesi.ui.main;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dunya_sesi.ChatPage;
import com.example.dunya_sesi.R;

import java.io.InputStream;
import java.security.AccessController;
import java.util.ArrayList;

public class ChatListAdapter extends
        RecyclerView.Adapter<ChatListAdapter.ViewHolder> {

    ArrayList<ChatItem> chats;

    ChatListAdapter(ArrayList<ChatItem> newChats) {
        this.chats = newChats;
    }

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public ImageView senderProfileImage;
        public TextView senderName;
        public TextView lastMessage;
        public Button openChatButton;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            senderProfileImage = (ImageView) itemView.findViewById(R.id.senderProfileImage);
            senderName = (TextView) itemView.findViewById(R.id.senderName);
            lastMessage = (TextView) itemView.findViewById(R.id.lastMessage);
            openChatButton = (Button) itemView.findViewById(R.id.openChatButton);
        }
    }

    @NonNull
    @Override
    public ChatListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View chatView = inflater.inflate(R.layout.chat_item, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(chatView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ChatListAdapter.ViewHolder holder, int position) {
        final ChatItem chatItem = chats.get(position);
        holder.senderName.setText(chatItem.senderName);
        holder.lastMessage.setText(chatItem.lastMessage);
//download profile image from int and upl to image view
        new ChatListAdapter.DownloadImageTask(holder.senderProfileImage)
                .execute(chatItem.senderProfileImageUrl);

        holder.openChatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), ChatPage.class);
                i.putExtra("SENDER_NAME", chatItem.senderName);
                view.getContext().startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return chats.size();
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
