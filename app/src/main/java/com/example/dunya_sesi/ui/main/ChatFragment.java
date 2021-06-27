package com.example.dunya_sesi.ui.main;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dunya_sesi.R;

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
        insertRandomChats();
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_chat, container, false);


        chatListView = root.findViewById(R.id.chatList);

        chatListAdapter = new ChatListAdapter(chatItems);

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

        return root;
    }

    private void insertRandomChats () {
        int i = 0;

        while (i < 5) {
            chatItems.add(new ChatItem(i, "https://i.redd.it/fi48haz3f5i21.jpg", "Sender " + i, "Last Message"));
            i = i + 1;
        }
    }
}