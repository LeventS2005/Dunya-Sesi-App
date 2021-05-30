package com.example.dunya_sesi.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.dunya_sesi.R;

public class WorldNetworkFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

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
        return root;
    }
}