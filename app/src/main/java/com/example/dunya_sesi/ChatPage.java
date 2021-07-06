package com.example.dunya_sesi;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.dunya_sesi.ui.main.ChatItem;
import com.example.dunya_sesi.ui.main.WorldSearchUserResult;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class ChatPage extends AppCompatActivity {

    Button sendMessageButton;
    EditText newMessageView;
    RecyclerView messageListView;
    ChatPageAdapter chatPageAdapter;
    ArrayList<ChatMessage> chatMessages = new ArrayList<>();

    String senderId;
    boolean messagesLoaded = false;
    boolean sendingMessage = false;

    boolean fetchingMessagesFromChatMate = false;
    Handler recieveMessagesHandler = new Handler();
    Runnable recieveMessagesRunnable;
    int recieveMessagedelay = 3000;
    Timer recieveMessagestimer;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_page);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        String senderName = getIntent().getStringExtra("SENDER_NAME");
        senderId = getIntent().getStringExtra("SENDER_ID");
        String myId = util.getUserIdFromSharePreferences(this);
        //title
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(senderName);
        }

        chatPageAdapter = new ChatPageAdapter(chatMessages, this);
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
                sendNewMessage(sendMessageButton, myId, senderId, newMessageView.getText().toString());
            }
        });

        loadMessages(senderId);

        messageListView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (recyclerView.canScrollVertically(1) || chatMessages.size() == 5) {
                    if (chatMessages.size() > 4) {
                        loadPreviousMessages(chatMessages.get(0).time_contact, senderId);
                    }
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Create the Handler object (on the main thread by default)
        recieveMessagesHandler = new Handler();
        // Define the code block to be executed
        recieveMessagesRunnable = new Runnable() {
            @Override
            public void run() {
                // Do something here on the main thread
                if(!messagesLoaded) {
                    recieveMessagesHandler.postDelayed(this, recieveMessagedelay);
                    return;
                }

                if(sendingMessage) {
                    recieveMessagesHandler.postDelayed(this, recieveMessagedelay);
                    return;
                }

                if(fetchingMessagesFromChatMate) {
                    recieveMessagesHandler.postDelayed(this, recieveMessagedelay);
                    return;
                }


                if(chatMessages.size() == 0) {
                    getRecievedMessages("1980-07-05 14:18:57", senderId);
                    recieveMessagesHandler.postDelayed(this, recieveMessagedelay);
                } else {
                    getRecievedMessages(chatMessages.get(0).time_contact, senderId);
                    recieveMessagesHandler.postDelayed(this, recieveMessagedelay);
                }
            }
        };
        // Run the above code block on the main thread after 2 seconds
        recieveMessagesRunnable.run();
    }

    @Override
    protected void onPause() {
        super.onPause();
        recieveMessagesHandler.removeCallbacks(recieveMessagesRunnable);
    }

    private void sendNewMessage(Button sendMessageButton, String myId, String profileId, String newMessage) {

        sendingMessage = true;

        if (fetchingMessagesFromChatMate) {
            return;
        }

        //sending message impl
        if (newMessage.isEmpty()) {
            return;
        }

        if (!util.isNetworkAvailable((ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE))) {
            Toast.makeText(getApplicationContext(), "Could not send message. Please connect to the internet!", Toast.LENGTH_SHORT).show();
            return;
        }

        sendMessageButton.setEnabled(false);

        /*
        chatMessages.add(new ChatMessage(newMessage, "me"));
        chatMessages.add(new ChatMessage(newMessage, "Mohamed")); */
        String response = "";

        new util.SendMessageTask(response, myId, profileId, newMessage) {
            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);

                if (result.length() > 0) {
                    chatMessages.add(new ChatMessage(myId, profileId, newMessage, result));
                    chatPageAdapter.notifyDataSetChanged();
                    messageListView.scrollToPosition(chatPageAdapter.getItemCount() - 1);
                    newMessageView.setText("");
                }
                sendMessageButton.setEnabled(true);
                sendingMessage = false;
            }
        }.execute();


    }

    // Loading for the first time.
    private void loadMessages(String profileId) {

        if (!util.isNetworkAvailable((ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE))) {
            Toast.makeText(getApplicationContext(), "Could load chat. Please connect to the internet!", Toast.LENGTH_SHORT).show();
            return;
        }

        String response = "";

        new util.GetAllMessagesTask(response, util.getUserIdFromSharePreferences(this), profileId) {
            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);

                ArrayList<ChatMessage> chatMessageArrayList = util.messageResultToChatMessageList(result);
                chatMessages.clear();

                for (int i = chatMessageArrayList.size() - 1; i >= 0; i--) {
                    chatMessages.add(chatMessageArrayList.get(i));
                }

                chatPageAdapter.notifyDataSetChanged();
                messageListView.scrollToPosition(chatPageAdapter.getItemCount() - 1);
                messagesLoaded = true;
            }
        }.execute();
    }

    private void loadPreviousMessages(String lastTimeStamp, String profileId) {

        if (fetchingMessagesFromChatMate) {
            return;
        }

        if (!util.isNetworkAvailable((ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE))) {
            Toast.makeText(getApplicationContext(), "Could load more chat. Please connect to the internet!", Toast.LENGTH_SHORT).show();
            return;
        }

        String response = "";

        new util.GetAllMessagesTask(response, util.getUserIdFromSharePreferences(this), profileId, lastTimeStamp) {
            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);

                ArrayList<ChatMessage> chatMessageArrayList = util.messageResultToChatMessageList(result);

                for (ChatMessage chatMessage : chatMessageArrayList) {
                    for (ChatMessage chatMessage1 : chatMessages) {
                        if (chatMessage.time_contact.equals(chatMessage1.time_contact)) {
                            return;
                        }
                    }
                }

                for (int i = 0; i < chatMessageArrayList.size(); i++) {
                    chatMessages.add(0, chatMessageArrayList.get(i));
                }

                chatPageAdapter.notifyDataSetChanged();
            }
        }.execute();
    }


    private void getRecievedMessages(String lastTimeStamp, String profileId) {

        if (!util.isNetworkAvailable((ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE))) {
            return;
        }

        fetchingMessagesFromChatMate = true;

        String response = "";

        new util.GetRecievedMessagesTask(response, util.getUserIdFromSharePreferences(this), profileId, lastTimeStamp) {
            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);

                ArrayList<ChatMessage> chatMessageArrayList = util.messageResultToChatMessageList(result);
                ArrayList<ChatMessage> finalChatMessageList = new ArrayList<>();

                for (ChatMessage chatMessage : chatMessageArrayList) {
                    boolean messageFound = false;
                    for (ChatMessage chatMessage1 : chatMessages) {
                        if (chatMessage.time_contact.equals(chatMessage1.time_contact)) {
                            messageFound = true;
                        }
                    }
                    if(!messageFound) {
                        finalChatMessageList.add(chatMessage);
                    }
                }

                if(finalChatMessageList.size() == 0) {
                    System.out.println("Found nothing");
                    fetchingMessagesFromChatMate = false;
                    return;
                }

                for (int i = finalChatMessageList.size() - 1; i >= 0; i--) {
                    chatMessages.add(finalChatMessageList.get(i));
                }

                chatPageAdapter.notifyDataSetChanged();
                fetchingMessagesFromChatMate = false;

                if(scrollToBottom(messageListView)) {
                    messageListView.scrollToPosition(chatPageAdapter.getItemCount() - 1);
                } else {
                    Toast.makeText(getApplicationContext(), "Scroll to see new message", Toast.LENGTH_LONG).show();
                }

                Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                // Vibrate for 500 milliseconds
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
                } else {
                    //deprecated in API 26
                    v.vibrate(500);
                }
            }
        }.execute();
    }


    public static boolean scrollToBottom(RecyclerView recyclerView) {
        if (recyclerView == null) return false;
        if (recyclerView.computeVerticalScrollExtent() + recyclerView.computeVerticalScrollOffset()
                >= recyclerView.computeVerticalScrollRange())
            return true;
        return false;
    }


}