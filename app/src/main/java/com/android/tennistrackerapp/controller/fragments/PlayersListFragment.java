package com.android.tennistrackerapp.controller.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.tennistrackerapp.R;
import com.android.tennistrackerapp.controller.adapters.PlayerListAdapter;
import com.android.tennistrackerapp.model.Player;
import com.android.tennistrackerapp.model.database.DBManager;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class PlayersListFragment extends Fragment {

    // -------------------------
    // COMPONENTS FOR DESIGN
    // -------------------------
    private RecyclerView rv;

    // ------------------
    // PRIVATE ATTRIBUTE
    // ------------------
    private DBManager manager;
    private ArrayList<Player> data;
    private PlayerListAdapter.OnPlayerClicked listenerCallBack;


    // ------------------------------
    // CONSTRUCTOR AND OVERRIDES
    // ------------------------------
    public PlayersListFragment() {}

    public static PlayersListFragment newInstance(PlayerListAdapter.OnPlayerClicked listener) {
        PlayersListFragment fragment = new PlayersListFragment();
        fragment.listenerCallBack = listener;
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        manager = DBManager.getInstance();

        this.data = (ArrayList<Player>) manager.getPlayerManager().getAll();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_players_list, container, false);

        RecyclerView rv = v.findViewById(R.id.players_list_recycler_view);

        //RecycleView
        //1- Layout Manager will manage displaying on screen, there it will be a vertical list
        rv.setLayoutManager(new LinearLayoutManager(v.getContext(), RecyclerView.VERTICAL, false));

        //2- The Adapter will manage content on each cell, it is a custom Class which extends of Adapter
        rv.setAdapter(new PlayerListAdapter(data, listenerCallBack));
        return v;
    }
}