package com.android.tennistrackerapp.controller.fragments;

import android.os.AsyncTask;
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

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class StatisticsFragment extends Fragment implements TaskManagerStat.Listeners {

    // ------------------------------
    // ATTRIBUTES
    // ------------------------------
    DBManager manager = DBManager.getInstance();
    Match match;
    ArrayList<MatchStat> statistics;

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
    private ImageView imageView;
    private TextView resultName1;
    private TextView resultName2;

    private TextView box1_1;
    private TextView box2_1;
    private TextView box3_1;
    private TextView box1_2;
    private TextView box2_2;
    private TextView box3_2;



    // -----------------------------------
    // CONSTRUCTOR FACTORY AND OVERRIDES
    // -----------------------------------
    public static StatisticsFragment newInstance(Match match) {
        StatisticsFragment fragment = new StatisticsFragment();

        // TODO: AsyncTask within onCreateView
        fragment.match = match;
//        fragment.match = fragment.manager.getMatchManager().getById(match.getId());
//
//        fragment.statistics = fragment.manager.getMatchStatManager().getAllMatchWithMatchId(match.getId());
//        fragment.statistics.get(0).setPlayer(fragment.manager.getPlayerManager().getById(fragment.statistics.get(0).getPlayer().getId()));
//        fragment.statistics.get(1).setPlayer(fragment.manager.getPlayerManager().getById(fragment.statistics.get(1).getPlayer().getId()));
//
//        fragment.statMapped1 = MatchStat.getNewDictionary(fragment.statistics.get(0));
//        fragment.statMapped2 = MatchStat.getNewDictionary(fragment.statistics.get(1));
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new TaskManagerStat(this, match, this).execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.mainView = inflater.inflate(R.layout.fragment_statistics, container, false);

        this.findViews();

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

        this.resultName1 = mainView.findViewById(R.id.statistics_result_player1);
        this.resultName2 = mainView.findViewById(R.id.statistics_result_player2);

        this.box1_1 =mainView.findViewById(R.id.statistics_result_game1_1);
        this.box2_1 =mainView.findViewById(R.id.statistics_result_game2_1);
        this.box3_1 =mainView.findViewById(R.id.statistics_result_game3_1);

        this.box1_2 =mainView.findViewById(R.id.statistics_result_game1_2);
        this.box2_2 =mainView.findViewById(R.id.statistics_result_game2_2);
        this.box3_2 =mainView.findViewById(R.id.statistics_result_game3_2);

        this.imageView = mainView.findViewById(R.id.statistics_image);
    }

    private void setupUI(){
        LinearLayout layout = mainView.findViewById(R.id.statistics_stat_layout);

        // Info Card
        this.date.setText(match.getDate().toString());
        if(match.getLocation() == null) {
            this.locationTitle.setText("N/A");
        }

        //Results
        this.box1_1.setText((statistics.get(0).getGames_set1() == null) ? String.valueOf(0) : String.valueOf(statistics.get(0).getGames_set1()));
        this.box2_1.setText((statistics.get(0).getGames_set2() == null) ? String.valueOf(0) : String.valueOf(statistics.get(0).getGames_set2()));

        this.box1_2.setText((statistics.get(1).getGames_set1() == null) ? String.valueOf(0) : String.valueOf(statistics.get(1).getGames_set1()));
        this.box2_2.setText((statistics.get(1).getGames_set2() == null) ? String.valueOf(0) : String.valueOf(statistics.get(1).getGames_set2()));

        if((statistics.get(0).getGames_set3() == null && statistics.get(1).getGames_set3() == null)) {
            this.box3_1.setVisibility(View.INVISIBLE);
            this.box3_2.setVisibility(View.INVISIBLE);
        } else {
            if(statistics.get(0).getGames_set3() == 0 && statistics.get(1).getGames_set3() == 0) {
                this.box3_1.setVisibility(View.INVISIBLE);
                this.box3_2.setVisibility(View.INVISIBLE);
            } else {
                this.box3_1.setText(String.valueOf(statistics.get(0).getGames_set3()));
                this.box3_2.setText(String.valueOf(statistics.get(1).getGames_set3()));
            }

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

        if(match.getImage() != null) {
            imageView.setImageBitmap(BitmapSaver.byteToBitmap(match.getImage()));
        }

        //names
        this.name1.setText(p1.getName());
        this.name2.setText(p2.getName());
        this.resultName1.setText(p1.getName());
        this.resultName2.setText(p2.getName());

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

    @Override
    public void onPostExecuted(StatisticsFragment fragment) {
        this.match = fragment.match;
        this.statMapped1 = fragment.statMapped1;
        this.statMapped2 = fragment.statMapped2;
        this.statistics =  fragment.statistics;
        this.setupUI();
    }

    // ---------------------
    // Listener
    // ---------------------
}

class TaskManagerStat extends AsyncTask<Void, Void, StatisticsFragment> {

    private Match match;
    private StatisticsFragment fragment;
    public interface Listeners {
        void onPostExecuted(StatisticsFragment fragment);
    }

    private final WeakReference<Listeners>callback;

    TaskManagerStat(StatisticsFragment f, Match match, Listeners callback) {
        this.match = match;
        this.fragment = f;
        this.callback = new WeakReference<>(callback);
    }


    @Override
    protected StatisticsFragment doInBackground(Void... voids) {
        fragment.match = fragment.manager.getMatchManager().getById(match.getId());

        fragment.statistics = fragment.manager.getMatchStatManager().getAllMatchWithMatchId(match.getId());
        fragment.statistics.get(0).setPlayer(fragment.manager.getPlayerManager().getById(fragment.statistics.get(0).getPlayer().getId()));
        fragment.statistics.get(1).setPlayer(fragment.manager.getPlayerManager().getById(fragment.statistics.get(1).getPlayer().getId()));

        fragment.statMapped1 = MatchStat.getNewDictionary(fragment.statistics.get(0));
        fragment.statMapped2 = MatchStat.getNewDictionary(fragment.statistics.get(1));

        return fragment;
    }

    @Override
    protected void onPostExecute(StatisticsFragment fragment) {
        super.onPostExecute(fragment);
        this.callback.get().onPostExecuted(fragment);
    }
}