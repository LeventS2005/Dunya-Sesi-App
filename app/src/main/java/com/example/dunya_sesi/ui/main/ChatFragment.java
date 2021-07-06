package com.example.dunya_sesi.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dunya_sesi.FriendRequests;
import com.example.dunya_sesi.R;
import com.example.dunya_sesi.util;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class ChatFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";


    ArrayList<ChatItem> fullChatItems = new ArrayList<>();
    ArrayList<ChatItem> chatItems = new ArrayList<>();

    ChatListAdapter chatListAdapter;
    RecyclerView chatListView;
    EditText searchChat;
    TextView chatListLoadingStatus;


    public static ChatFragment newInstance(int index) {
        ChatFragment fragment = new ChatFragment();
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
        View root = inflater.inflate(R.layout.fragment_chat, container, false);


        chatListView = root.findViewById(R.id.chatList);

        chatListAdapter = new ChatListAdapter(chatItems, getActivity());

        chatListLoadingStatus = root.findViewById(R.id.chatListLoadingStatus);

        chatListView.setAdapter(chatListAdapter);
        // Set layout manager to position the items
        chatListView.setLayoutManager(new LinearLayoutManager(getActivity()));

        searchChat = root.findViewById(R.id.searchChat);

        // if the user is typing something try and search for it
        searchChat.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
               // before text is changed
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
               // aftwhen text is being changed
            }

            @Override
            public void afterTextChanged(Editable editable) {
              // after text is changed
               // searchForChats(editable.toString());
            }
        });

        loadChats();

        return root;
    }

    private void loadChats () {
        chatListLoadingStatus.setVisibility(View.VISIBLE);
        chatListLoadingStatus.setText("Loading your chats :)");
        String response = "";

        new util.GetFriendListTask(response, util.getUserIdFromSharePreferences(getActivity())) {
            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);

                if (util.searchResultToUserList(result).size() == 0) {
                    chatListLoadingStatus.setText("You have no chats, please add friends :)");
                } else {
                    chatItems.clear();
                    for (WorldSearchUserResult worldSearchUserResult : util.searchResultToUserList(result)) {
                        chatItems.add(new ChatItem(worldSearchUserResult.userId, "https://i.redd.it/fi48haz3f5i21.jpg", worldSearchUserResult.username, worldSearchUserResult.caption, worldSearchUserResult.email));
                    }
                    chatListLoadingStatus.setText("Awesome, enjoy talking :)");
                }
                chatListLoadingStatus.setVisibility(View.INVISIBLE);
                chatListAdapter.notifyDataSetChanged();
            }
        }.execute();
    }
}