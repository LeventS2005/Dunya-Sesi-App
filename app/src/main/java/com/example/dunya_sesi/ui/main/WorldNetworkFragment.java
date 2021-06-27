package com.example.dunya_sesi.ui.main;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dunya_sesi.R;
import com.example.dunya_sesi.util;

import java.util.ArrayList;

public class WorldNetworkFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    private ArrayList<WorldSearchUserResult> searchUserResults = new ArrayList<>();

    public static WorldNetworkFragment newInstance(int index) {
        WorldNetworkFragment fragment = new WorldNetworkFragment();
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
        View root = inflater.inflate(R.layout.fragment_world_network, container, false);

        EditText searchUserEditText = root.findViewById(R.id.searchWorldUserEditText);


        RecyclerView worldNetworkList = root.findViewById(R.id.worldNetworkList);
        final WorldSearchUserResultAdapter worldSearchUserResultAdapter = new WorldSearchUserResultAdapter(searchUserResults, this.getActivity());

        worldNetworkList.setAdapter(worldSearchUserResultAdapter);
        // Set layout manager to position the items
        worldNetworkList.setLayoutManager(new LinearLayoutManager(getActivity()));

        searchUserEditText.addTextChangedListener(new TextWatcher() {
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
                searchForUser(editable.toString(), worldSearchUserResultAdapter);
            }
        });

        return root;
    }

    private void searchForUser(String query, final WorldSearchUserResultAdapter worldSearchUserResultAdapter){
        if (query.trim().length() == 0){
            return;
        }

        String response = "";

        new util.SearchUserProfileTask(response, query) {
            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                searchUserResults.clear();
                searchUserResults.addAll(util.searchResultToUserList(result));
                worldSearchUserResultAdapter.notifyDataSetChanged();
            }
        }.execute();
    }
}