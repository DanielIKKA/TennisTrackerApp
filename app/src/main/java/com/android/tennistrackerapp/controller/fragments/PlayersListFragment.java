package com.android.tennistrackerapp.controller.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.tennistrackerapp.R;
import com.android.tennistrackerapp.model.Player;
import com.android.tennistrackerapp.model.database.DBManager;

import java.util.ArrayList;
import java.util.Objects;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class PlayersListFragment extends Fragment {

    // -------------------------
    // COMPONENTS FOR DESIGN
    // -------------------------
    private ListView lv;

    // ------------------
    // PRIVATE ATTRIBUTE
    // ------------------
    private DBManager manager;
    private ArrayList<String> itemsList = new ArrayList<>();
    private ArrayAdapter<String> adapter;

    // ------------------------------
    // CONSTRUCTOR AND OVERRIDES
    // ------------------------------
    public PlayersListFragment() {}

    public static PlayersListFragment newInstance() { return new PlayersListFragment(); }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        manager = DBManager.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mainView = inflater.inflate(R.layout.fragment_players_list, container, false);

        //elements design
        lv= mainView.findViewById(R.id.players_list_list_view);

        adapter = new ArrayAdapter<>(Objects.requireNonNull(getContext()), android.R.layout.simple_dropdown_item_1line, itemsList);
        fillList();

        this.lv.setAdapter(adapter);

        return mainView;
    }

    // ---------------
    // CONFIGURE
    // ---------------
    private void fillList() {
        ArrayList<Player> players = (ArrayList<Player>) manager.getPlayerManager().getAll();

        for (Player player : players) {
            this.itemsList.add(player.getName());
        }

        this.adapter.notifyDataSetChanged();
    }
}
