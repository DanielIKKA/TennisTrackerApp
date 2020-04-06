package com.android.tennistrackerapp.controller;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.tennistrackerapp.R;
import com.android.tennistrackerapp.model.Match;
import com.android.tennistrackerapp.model.MatchStat;
import com.android.tennistrackerapp.model.Player;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;

/*
 * Resources :
 *  - https://developer.android.com/guide/topics/ui/layout/recyclerview
 *  - https://openclassrooms.com/fr/courses/3499366-developpez-une-application-pour-android/3568556-affichez-des-listes-avec-recyclerview
 *  - https://www.youtube.com/watch?v=fxVeFwtIpVc
 */
public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomViewHolder> {

    private List<Match> data;

    // Provide a reference to the views for each data item (cell)
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    static class CustomViewHolder extends RecyclerView.ViewHolder {

        private ImageView image;
        private TextView title;

        private Match data;

        CustomViewHolder(View cell) {
            super(cell);

            //find cell's view
            this.image = cell.findViewById(R.id.cell_ball_state);
            this.title = cell.findViewById(R.id.cell_title);
        }

        void initWithData(Match data) {
            Player player1 = new Player("Daniel", 42, "");
            Player player2 = new Player("Victor", 2, "");

            String winner = player1.getName();
            String looser = player2.getName();
            String dateToStr = data.getDate();
            String location = data.getLocation().toString();

            this.title.setText(MessageFormat.format("{0} - {1}\n{2} - {3}", winner, looser, location, dateToStr));
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public CustomAdapter(List<Match> dataSet) {
        this.data = dataSet;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create a new generic cell
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_home_cell_history, parent, false);

        CustomViewHolder vh = new CustomViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        Match data = this.data.get(position);
        holder.initWithData(data);
    }

    @Override
    public int getItemCount() {
        return this.data.size();
    }
}
