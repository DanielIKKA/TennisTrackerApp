package com.android.tennistrackerapp.controller.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.tennistrackerapp.R;
import com.android.tennistrackerapp.model.BitmapSaver;
import com.android.tennistrackerapp.model.Match;
import com.android.tennistrackerapp.model.MatchStat;
import com.android.tennistrackerapp.model.Player;
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

    HashMap<String, Object> statMapped1;
    HashMap<String, Object> statMapped2;

    // ------------------------------
    // COMPONENTS FOR DESIGN
    // ------------------------------
    private View mainView;

    private TextView date;
    private TextView locationTitle;
    private TextView name1;
    private TextView name2;
    private ImageView profile1;
    private ImageView profile2;


    // -----------------------------------
    // CONSTRUCTOR FACTORY AND OVERRIDES
    // -----------------------------------
    public static StatisticsFragment newInstance(Match match) {
        StatisticsFragment fragment = new StatisticsFragment();

        // TODO: AsyncTask within onCreateView
        fragment.match = match;

        fragment.statistics = fragment.manager.getMatchStatManager().getAllMatchWithMatchId(match.getId());
        fragment.statistics.get(0).setPlayer(fragment.manager.getPlayerManager().getById(fragment.statistics.get(0).getPlayer().getId()));
        fragment.statistics.get(1).setPlayer(fragment.manager.getPlayerManager().getById(fragment.statistics.get(1).getPlayer().getId()));

        fragment.statMapped1 = MatchStat.getNewDictionary(fragment.statistics.get(0));
        fragment.statMapped2 = MatchStat.getNewDictionary(fragment.statistics.get(1));


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

        this.profile1 = mainView.findViewById(R.id.statistics_stat_image1);
        this.profile2 = mainView.findViewById(R.id.statistics_stat_image2);
    }

    private void setupUI(){
        LinearLayout layout = mainView.findViewById(R.id.statistics_stat_layout);

        // Info Card
        this.date.setText(match.getDate().toString());
        if(match.getLocation() == null) {
            this.locationTitle.setText("N/A");
        }

        //Images
        Player p1 = this.statistics.get(0).getPlayer();
        Player p2 = this.statistics.get(1).getPlayer();

        if(p1.getPicture().length != 0) {
            this.profile1.setImageBitmap(BitmapSaver.byteToBitmap(p1.getPicture()));
        }
        if(p2.getPicture().length != 0) {
            this.profile2.setImageBitmap(BitmapSaver.byteToBitmap(p2.getPicture()));
        }

        //names
        this.name1.setText(p1.getName());
        this.name2.setText(p2.getName());

        // Statistics
        for(String key : this.statMapped1.keySet()) {
            String valuePlayer1 = String.valueOf(this.statMapped1.get(key));
            String valuePlayer2 =String.valueOf(this.statMapped2.get(key));
            layout.addView(lineStat(valuePlayer1, key, valuePlayer2, layout));
        }
    }

    private View lineStat(String stat1, String title, String stat2, ViewGroup container) {
        View v = this.getLayoutInflater().inflate(R.layout.fragment_stattistics_cell_stat, container, false);

        TextView player1 = v.findViewById(R.id.statistics_cell_player1);
        TextView player2 = v.findViewById(R.id.statistics_cell_player2);
        TextView titleCat = v.findViewById(R.id.statistics_cell_title);

        titleCat.setText(title);

        if(title.equals(MatchStat.KEY_PERCENT_FIRST_SERVICE) || title.equals(MatchStat.KEY_PERCENT_SECOND_SERVICE)) {
            player1.setText((stat1.equals("null")) ? "N/A" : stat1+"%");
            player2.setText((stat2.equals("null")) ? "N/A" : stat2+"%");
            return v;
        }
        player1.setText((stat1.equals("null")) ? "N/A" : stat1);
        player2.setText((stat2.equals("null")) ? "N/A" : stat2);
        return v;
    }

    // ---------------------
    // Listener
    // ---------------------
}
