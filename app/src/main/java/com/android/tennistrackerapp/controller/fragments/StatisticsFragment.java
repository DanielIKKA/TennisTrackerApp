package com.android.tennistrackerapp.controller.fragments;

import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.tennistrackerapp.R;
import com.android.tennistrackerapp.model.Match;
import com.android.tennistrackerapp.model.MatchStat;
import com.android.tennistrackerapp.model.database.DBManager;

import java.util.ArrayList;
import java.util.HashMap;

import androidx.fragment.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class StatisticsFragment extends Fragment {

    // ------------------------------
    // ATTRIBUTES
    // ------------------------------
    private DBManager manager = DBManager.getInstance();
    private Match match;
    private ArrayList<MatchStat> statistics;

    private HashMap<String, Pair> allStaMapped;

    // ------------------------------
    // COMPONENTS FOR DESIGN
    // ------------------------------
    private View mainView;

    private TextView date;
    private TextView locationTitle;
    private TextView name1;
    private TextView name2;


    // -----------------------------------
    // CONSTRUCTOR FACTORY AND OVERRIDES
    // -----------------------------------
    public static StatisticsFragment newInstance(Match match) {
        StatisticsFragment fragment = new StatisticsFragment();

        fragment.match = match;

        fragment.statistics = fragment.manager.getMatchStatManager().getAllMatchWithMatchId(match.getId());
        fragment.statistics.get(0).setPlayer(fragment.manager.getPlayerManager().getById(fragment.statistics.get(0).getPlayer().getId()));
        fragment.statistics.get(1).setPlayer(fragment.manager.getPlayerManager().getById(fragment.statistics.get(1).getPlayer().getId()));



        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.mainView = inflater.inflate(R.layout.fragment_statistics, container, false);

        this.findViews();
        this.setupUI();

        return mainView;
    }

    // ---------------------
    // SETUP FUNCTIONS
    // ---------------------
    private void findViews(){
        this.date = mainView.findViewById(R.id.statistics_info_date);
        this.locationTitle = mainView.findViewById(R.id.statistics_info_location);

        this.name1 = mainView.findViewById(R.id.statistics_stat_name1);
        this.name2 = mainView.findViewById(R.id.statistics_stat_name2);
    }

    private void setupUI(){
        LinearLayout layout = mainView.findViewById(R.id.statistics_stat_layout);

        // Info Card
        this.date.setText(match.getDate().toString());
        if(match.getLocation() == null) {
            this.locationTitle.setText("N/A");
        }

        // Statistic
        this.name1.setText(this.statistics.get(0).getPlayer().getName());
        this.name2.setText(this.statistics.get(1).getPlayer().getName());
    }

    private void lineStat(String stat1, String title, String stat2, ViewGroup container){
        this.getLayoutInflater().inflate(R.layout.fragment_stattistics_cell_stat, container);
    }

    // ---------------------
    // Listener
    // ---------------------
}
