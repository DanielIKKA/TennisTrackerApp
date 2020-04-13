package com.android.tennistrackerapp.controller.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.tennistrackerapp.R;
import com.android.tennistrackerapp.model.Match;
import com.android.tennistrackerapp.model.MatchStat;
import com.android.tennistrackerapp.model.database.DBManager;

import androidx.fragment.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class StatisticsFragment extends Fragment {

    // ------------------------------
    // COMPONENTS FOR DESIGN
    // ------------------------------
    private View mainView;

    // ------------------------------
    // ATTRIBUTES
    // ------------------------------
    private DBManager manager = DBManager.getInstance();
    private Match match;
    private MatchStat statistic;


    // -----------------------------------
    // CONSTRUCTOR FACTORY AND OVERRIDES
    // -----------------------------------
    public static StatisticsFragment newInstance(Match match) {
        StatisticsFragment fragment = new StatisticsFragment();

        fragment.match = match;

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
    private void findViews(){}

    private void setupUI(){}
    // ---------------------
    // Listener
    // ---------------------
}
