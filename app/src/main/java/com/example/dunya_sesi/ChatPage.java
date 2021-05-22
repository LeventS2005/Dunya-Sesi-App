package com.example.dunya_sesi;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class ChatPage extends AppCompatActivity {

    Button sendMessageButton;
    EditText newMessageView;
    RecyclerView messageListView;
    ChatPageAdapter chatPageAdapter;
    ArrayList<ChatMessage> chatMessages = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_page);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        String senderName = getIntent().getStringExtra("SENDER_NAME");
        //title
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null )
        {
            actionBar.setTitle(senderName);
        }

        chatPageAdapter = new ChatPageAdapter(chatMessages);
        sendMessageButton = findViewById(R.id.sendMessageButton);
        newMessageView = findViewById(R.id.newMessageView);
        messageListView = findViewById(R.id.messageListView);

        messageListView.setAdapter(chatPageAdapter);
        // Set layout manager to position the items

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);

        messageListView.setLayoutManager(linearLayoutManager);

        sendMessageButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                sendNewMessage(newMessageView.getText().toString());
            }
        });

    }
    private void sendNewMessage (String newMessage) {
        //sending message impl
        if(newMessage.isEmpty()){
            return;
        }

        chatMessages.add(new ChatMessage(newMessage, "me"));
        chatMessages.add(new ChatMessage(newMessage, "Mohamed"));
        chatPageAdapter.notifyDataSetChanged();
        messageListView.scrollToPosition(chatPageAdapter.getItemCount() - 1);
    }


}