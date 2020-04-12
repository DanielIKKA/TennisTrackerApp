package com.android.tennistrackerapp.controller.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.tennistrackerapp.R;
import com.android.tennistrackerapp.controller.adapters.CustomAdapter;
import com.android.tennistrackerapp.model.Match;
import com.android.tennistrackerapp.model.Player;
import com.android.tennistrackerapp.model.database.DBManager;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ListHistoryFragment extends Fragment {

    private static final String CURRENT_ID = String.valueOf(R.string.comTennisTrackerCURRENT_ID);

    private DBManager manager;
    ArrayList<Match> data;

    // ---------------------
    // CONSTRUCTOR & FACTORY
    // ---------------------
    public static ListHistoryFragment newInstance(int currentPlayerId) {
        ListHistoryFragment fragment = new ListHistoryFragment();
        Bundle args = new Bundle();
        args.putInt(CURRENT_ID, currentPlayerId);
        fragment.setArguments(args);
        return fragment;
    }

    public ListHistoryFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.manager = DBManager.getInstance();

        if (getArguments() != null) {
            // 1- find all matches where currentPlayer played
            this.data = (ArrayList<Match>) manager.getMatchManager().getAllMatchWith(getArguments().getInt(CURRENT_ID));

            // 2- find players because player data is just represent by id
            int currentId = getArguments().getInt(CURRENT_ID);
            Player currentPlayer = manager.getPlayerManager().getById(currentId);
            for (Match match : this.data) {
                // 3- divided by 2 requests
                if(match.getWinner().getId().equals(currentId)) {
                    match.setWinner(currentPlayer);
                    match.setLooser(manager.getPlayerManager().getById(match.getLooser().getId()));
                } else {
                    match.setWinner(manager.getPlayerManager().getById(match.getWinner().getId()));
                    match.setLooser(currentPlayer);
                }
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_home_list_history, container, false);

        RecyclerView rv = v.findViewById(R.id.recyclerView_main);

        //RecycleView
        //1- Layout Manager will manage displaying on screen, there it will be a vertical list
       rv.setLayoutManager(new LinearLayoutManager(v.getContext(), RecyclerView.VERTICAL, false));

       //2- The Adapter will manage content on each cell, it is a custom Class which extends of Adapter
        rv.setAdapter(new CustomAdapter(data));

        return v;
    }
}
