package com.android.tennistrackerapp.controller.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.tennistrackerapp.R;
import com.android.tennistrackerapp.model.Player;
import com.android.tennistrackerapp.model.database.DBManager;

import java.util.Objects;

import androidx.fragment.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HeaderProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HeaderProfileFragment extends Fragment {

    private static final String CURRENT_ID = String.valueOf(R.string.comTennisTrackerCURRENT_ID);

    // ---------------------
    // DESIGN ELEMENTS
    // ---------------------
    private View mainView;

    private Player currentPlayer = new Player();
    private DBManager manager;

    // ---------------------
    // CONSTRUCTOR & FACTORY
    // ---------------------
    public static HeaderProfileFragment newInstance(int currentPlayerId) {
        HeaderProfileFragment fragment = new HeaderProfileFragment();
        Bundle args = new Bundle();
        args.putInt(CURRENT_ID, currentPlayerId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.manager = DBManager.getInstance();

        if (getArguments() != null) {
            this.currentPlayer.setId(getArguments().getInt(CURRENT_ID));
            this.currentPlayer = manager.getPlayerManager().getById(currentPlayer.getId());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.mainView =  inflater.inflate(R.layout.fragment_home_header_profile, container, false);
        updateUI();
        Log.d("PROFILE_CARD_OnCreateView", String.valueOf(currentPlayer.getId()));
        return mainView;
    }

    // ---------------------
    // SETUP FUNCTIONS
    // ---------------------
    private void updateUI() {
        TextView name = this.mainView.findViewById(R.id.header_name_player);
        TextView rank = this.mainView.findViewById(R.id.header_rank);
        TextView age = this.mainView.findViewById(R.id.header_age);

        String rankStr = new StringBuilder()
                .append(Objects.requireNonNull(getActivity()).getResources().getString(R.string.profile_card_rank)).append(" ")
                .append(this.currentPlayer.getRank())
                .toString();

        String ageStr = new StringBuilder()
                .append(this.currentPlayer.getAge()).append(" ")
                .append(Objects.requireNonNull(getActivity()).getResources().getString(R.string.profile_card_age))
                .toString();

        name.setText(this.currentPlayer.getName());
        rank.setText(rankStr);
        age.setText(ageStr);
    }
}
