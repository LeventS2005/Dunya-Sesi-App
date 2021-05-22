package com.example.dunya_sesi;

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

import com.example.dunya_sesi.ui.main.ChatItem;

import java.io.InputStream;
import java.util.ArrayList;

public class ChatPageAdapter extends
        RecyclerView.Adapter<ChatPageAdapter.ViewHolder> {

    ArrayList<ChatMessage> messages;

    ChatPageAdapter(ArrayList<ChatMessage> newMessages) {
        this.messages = newMessages;
    }

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView messageText;
        public TextView messageSender;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            messageSender = (TextView) itemView.findViewById(R.id.messageSender);
            messageText = (TextView) itemView.findViewById(R.id.messageText);
        }
    }

    @Override
    public int getItemViewType(int position) {
        // Just as an example, return 0 or 2 depending on position
        // Note that unlike in ListView adapters, types don't have to be contiguous
        return this.messages.get(position).sender.equals("me") ? 0 : 1;
    }

    @NonNull
    @Override
    public ChatPageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View sentMessageView = viewType == 0 ? inflater.inflate(R.layout.sent_message, parent, false) : inflater.inflate(R.layout.received_message, parent, false);
//receivedMessage

        // Return a new holder instance
        ChatPageAdapter.ViewHolder viewHolder = new ChatPageAdapter.ViewHolder(sentMessageView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ChatPageAdapter.ViewHolder holder, int position) {
      holder.messageText.setText(this.messages.get(position).message);
      holder.messageSender.setText(this.messages.get(position).sender);
    }

    @Override
    public int getItemCount() { return messages.size();
    }


}

