package com.android.tennistrackerapp.controller.adapters;

import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.tennistrackerapp.R;
import com.android.tennistrackerapp.model.Match;
import com.android.tennistrackerapp.model.Player;
import com.android.tennistrackerapp.model.database.DBManager;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/*
 * Resources :
 *  - https://developer.android.com/guide/topics/ui/layout/recyclerview
 *  - https://openclassrooms.com/fr/courses/3499366-developpez-une-application-pour-android/3568556-affichez-des-listes-avec-recyclerview
 *  - https://www.youtube.com/watch?v=fxVeFwtIpVc
 */
public class PlayerListAdapter extends RecyclerView.Adapter<PlayerListAdapter.PlayerViewHolder> {


    // --------------------
    // PRIVATE ATTRIBUTES
    // --------------------
    private List<Player> data;
    private OnPlayerClicked listenerCallBack;

    // --------------------
    // STATIC VIEW HOLDER
    // --------------------
    static class PlayerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, TaskPlayerList.Listeners {

        private TextView rank;
        private TextView title;
        private TextView raton;
        private ProgressBar prograss;

        Player data;
        private OnPlayerClicked listener;

        PlayerViewHolder(View cell, OnPlayerClicked onPlayerClicked) {
            super(cell);

            //find cell's view
            this.rank = cell.findViewById(R.id.player_list_cell_rank);
            this.title = cell.findViewById(R.id.player_list_cell_name);
            this.prograss = cell.findViewById(R.id.player_list_cell_progress);
            this.raton = cell.findViewById(R.id.player_list_cell_match_count);

            this.listener = onPlayerClicked;
            cell.setOnClickListener(this);
            new TaskPlayerList(this).execute();
        }

        void initWithData(Player data) {
            this.data = data;

            rank.setText(String.valueOf(data.getRank()));
            title.setText(String.valueOf(data.getName()));
        }

        @Override
        public void onClick(View v) {
            listener.onPlayerSelected(this.data.getId());
        }

        @Override
        public void onPostExecuted(ArrayList<Integer> data) {
            double ratio = (data.get(0)/(double)data.get(1))*100;
            this.prograss.setProgress((int)ratio);
            this.raton.setText(new StringBuilder().append(ratio).append("%").toString());
        }
    }

    // --------------------------
    // CONSTRUCTOR AND OVERRIDES
    // --------------------------
    public PlayerListAdapter(List<Player> dataSet, OnPlayerClicked listener) {
        this.data = dataSet;
        this.listenerCallBack = listener;
    }

    @NonNull
    @Override
    public PlayerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create a new generic cell
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_player_list_cell, parent, false);

        return new PlayerViewHolder(v, listenerCallBack);
    }

    @Override
    public void onBindViewHolder(@NonNull PlayerViewHolder holder, int position) {
        Player data = this.data.get(position);
        holder.initWithData(data);
    }

    @Override
    public int getItemCount() {
        return this.data.size();
    }

    // -----------------------
    // Interface for listener
    // -----------------------
    public interface OnPlayerClicked{
        void onPlayerSelected(int playerId);
    }
}

class TaskPlayerList extends AsyncTask<Void, Void, ArrayList<Integer>> {

    DBManager manager = DBManager.getInstance();
    public interface Listeners {
        void onPostExecuted(ArrayList<Integer> fragment);
    }

    private final WeakReference<PlayerListAdapter.PlayerViewHolder> callback;

    TaskPlayerList(PlayerListAdapter.PlayerViewHolder callback) {
        this.callback = new WeakReference<>(callback);
    }


    @Override
    protected ArrayList<Integer> doInBackground(Void... voids) {
        ArrayList<Integer> response = new ArrayList<>();

        List<Match> nbMatchs = manager.getMatchManager().getAllMatchWith(callback.get().data.getId());
        int nbVictories = 0;
        for (Match m: nbMatchs) {
            if(m.getWinner().getId() == callback.get().data.getId()) nbVictories++;
        }

        response.add(nbVictories);
        response.add(nbMatchs.size());
        return response;
    }

    @Override
    protected void onPostExecute(ArrayList<Integer> array) {
        super.onPostExecute(array);
        this.callback.get().onPostExecuted(array);
    }
}