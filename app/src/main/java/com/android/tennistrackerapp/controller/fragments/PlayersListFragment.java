package com.android.tennistrackerapp.controller.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.tennistrackerapp.R;
import com.android.tennistrackerapp.model.database.DBManager;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PlayersListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PlayersListFragment extends Fragment {

    // ------------------
    // PRIVATE ATTRIBUTE
    // ------------------
    DBManager manager;

    // -------------
    // CONSTRUCTORS
    // -------------
    public PlayersListFragment() {}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        manager = DBManager.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_players_list, container, false);
    }
}
