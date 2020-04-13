package com.android.tennistrackerapp.controller.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.android.tennistrackerapp.R;
import com.android.tennistrackerapp.model.Match;
import com.android.tennistrackerapp.model.MatchStat;
import com.android.tennistrackerapp.model.Player;
import com.android.tennistrackerapp.model.database.DBManager;

import androidx.fragment.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewMatchFragment extends Fragment {

    // ---------------------
    // DESIGN ELEMENTS
    // ---------------------
    private View mainView;
    private EditText game1_winner;
    private EditText game2_winner;
    private EditText game3_winner;
    private EditText game1_looser;
    private EditText game2_looser;
    private EditText game3_looser;

    // ---------------------
    // PRIVATE ATTRIBUTES
    // ---------------------
    private DBManager manager = DBManager.getInstance();
    private Player winner;
    private Player looser;
    private Match newMatch;
    private MatchStat newStat;

    // ---------------------
    // CONSTRUCTOR & FACTORY
    // ---------------------
    public NewMatchFragment() {}

    public static NewMatchFragment newInstance() {
        return new NewMatchFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.mainView = inflater.inflate(R.layout.fragment_new_match, container, false);



        return mainView;
    }

    // ---------------------
    // SETUP
    // ---------------------
    private void
}
