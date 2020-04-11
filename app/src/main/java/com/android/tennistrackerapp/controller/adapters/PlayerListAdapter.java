package com.android.tennistrackerapp.controller.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.tennistrackerapp.R;
import com.android.tennistrackerapp.model.Player;

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

    // --------------------
    // STATIC VIEW HOLDER
    // --------------------
    static class PlayerViewHolder extends RecyclerView.ViewHolder {

        private TextView rank;
        private TextView title;
        private TextView ration;
        private ProgressBar prograss;

        private Player data;

        PlayerViewHolder(View cell) {
            super(cell);

            //find cell's view
            this.rank = cell.findViewById(R.id.player_list_cell_rank);
            this.title = cell.findViewById(R.id.player_list_cell_name);
            this.prograss = cell.findViewById(R.id.player_list_cell_progress);
            this.ration = cell.findViewById(R.id.player_list_cell_match_count);

            // TODO: asynctasck for progress bar
        }

        void initWithData(Player data) {
            this.data = data;

            rank.setText(String.valueOf(data.getRank()));
            title.setText(String.valueOf(data.getName()));
        }
    }

    // --------------------------
    // CONSTRUCTOR AND OVERRIDES
    // --------------------------
    public PlayerListAdapter(List<Player> dataSet) {
        this.data = dataSet;
    }

    @NonNull
    @Override
    public PlayerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create a new generic cell
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_player_list_cell, parent, false);

        return new PlayerViewHolder(v);
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
}
