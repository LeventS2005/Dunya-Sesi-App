package com.example.dunya_sesi;

import android.app.Activity;
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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dunya_sesi.ui.main.ChatItem;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;

public class ChatPageAdapter extends
        RecyclerView.Adapter<ChatPageAdapter.ViewHolder> {

    ArrayList<ChatMessage> messages;
    Activity activity;


    ChatPageAdapter(ArrayList<ChatMessage> newMessages, Activity activity) {
        this.messages = newMessages;
        this.activity = activity;
    }

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView messageTimeStamp;
        public TextView messageId;
        public TextView senderId;
        public TextView recieverId;
        public TextView messageText;
        public TextView messageSender;

        public Button cancelDeletionButton;
        public Button deleteMessageButton;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            messageSender = (TextView) itemView.findViewById(R.id.messageSender);
            messageText = (TextView) itemView.findViewById(R.id.messageText);
            messageId = (TextView) itemView.findViewById(R.id.messageId);
            recieverId = itemView.findViewById(R.id.recieverId);
            senderId = itemView.findViewById(R.id.senderId);
            messageTimeStamp = itemView.findViewById(R.id.messageTimeStamp);
        }
    }

    @Override
    public int getItemViewType(int position) {
        // Just as an example, return 0 or 2 depending on position
        // Note that unlike in ListView adapters, types don't have to be contiguous
        return this.messages.get(position).sender_id.equals(util.getUserIdFromSharePreferences(activity)) ? 0 : 1;
    }

    @NonNull
    @Override
    public ChatPageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, final int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        final View sentMessageView = viewType == 0 ? inflater.inflate(R.layout.sent_message, parent, false) : inflater.inflate(R.layout.received_message, parent, false);
//receivedMessage

        sentMessageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                if (viewType == 0) {

                    final LinearLayout deleteMessageButtonContainer =
                            (LinearLayout) sentMessageView.findViewById(R.id.deleteMessageButtonContainer);
                    deleteMessageButtonContainer.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

                    //when cancel deletion b is clicked
                    Button cancelDeletion = (Button) sentMessageView.findViewById(R.id.cancelDeletion);
                    Button deleteMessageButton = (Button) sentMessageView.findViewById(R.id.deleteMessage);

                    cancelDeletion.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            deleteMessageButtonContainer.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0));

                        }
                    });

                    deleteMessageButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            if(!util.isNetworkAvailable((ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE))) {
                                Toast.makeText(activity.getApplicationContext(),"Could delete message. Please connect to the internet!", Toast.LENGTH_SHORT).show();
                                return;
                            }

                            TextView messaageIdView = (TextView) sentMessageView.findViewById(R.id.messageId);
                            TextView senderIdView = (TextView) sentMessageView.findViewById(R.id.senderId);
                            TextView recieverIdView = (TextView) sentMessageView.findViewById(R.id.recieverId);
                            TextView messaageTimeStampView = (TextView) sentMessageView.findViewById(R.id.messageTimeStamp);
                            int messagePosition = Integer.valueOf(messaageIdView.getText().toString());


                            String response = "";

                            new util.DeleteMessageTask(response, util.getUserIdFromSharePreferences(activity), recieverIdView.getText().toString(), messaageTimeStampView.getText().toString()) {
                                @Override
                                protected void onPostExecute(String result) {
                                    super.onPostExecute(result);
                                    if(result.equals("DELETED")) {
                                        messages.remove(messagePosition);
                                        notifyDataSetChanged();
                                    }
                                }
                            }.execute();

                        }
                    });
                }

                return false;
            }
        });


        // Return a new holder instance
        ChatPageAdapter.ViewHolder viewHolder = new ChatPageAdapter.ViewHolder(sentMessageView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ChatPageAdapter.ViewHolder holder, int position) {
      holder.messageText.setText(this.messages.get(position).message);
      holder.messageSender.setText(this.messages.get(position).sender_id.equals(util.getUserIdFromSharePreferences(activity)) ? "Me" : "My Friend");
      holder.messageId.setText(position + "");
      holder.messageTimeStamp.setText(this.messages.get(position).time_contact);
      holder.senderId.setText(this.messages.get(position).sender_id);
      holder.recieverId.setText(this.messages.get(position).reciever_id);
    }

    @Override
    public int getItemCount() { return messages.size();
    }


}

