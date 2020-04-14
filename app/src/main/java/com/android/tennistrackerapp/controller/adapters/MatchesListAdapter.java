package com.android.tennistrackerapp.controller.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.tennistrackerapp.R;
import com.android.tennistrackerapp.model.Match;
import com.android.tennistrackerapp.model.Player;

import java.text.MessageFormat;
import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/*
 * Resources :
 *  - https://developer.android.com/guide/topics/ui/layout/recyclerview
 *  - https://openclassrooms.com/fr/courses/3499366-developpez-une-application-pour-android/3568556-affichez-des-listes-avec-recyclerview
 *  - https://www.youtube.com/watch?v=fxVeFwtIpVc
 */
public class MatchesListAdapter extends RecyclerView.Adapter<MatchesListAdapter.CustomViewHolder> {

    private List<Match> data;
    private OnMatchClickedListener listener;

    // Provide a reference to the views for each data item (cell)
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    static class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private static final String CURRENT_ID = String.valueOf(R.string.comTennisTrackerCURRENT_ID);
        private static final String PREFERENCE_MANAGER = String.valueOf(R.string.comTennisTrackerPREF_MANAGER);

        private ImageView image;
        private TextView title;

        private Match data;
        private int currentId;
        private OnMatchClickedListener listener;

        CustomViewHolder(View cell, OnMatchClickedListener callBack) {
            super(cell);

            //find cell's view
            this.image = cell.findViewById(R.id.cell_ball_state);
            this.title = cell.findViewById(R.id.cell_title);

            this.listener = callBack;

            cell.setOnClickListener(this);

            getSharedPref(cell.getContext());
        }

        void initWithData(Match data) {
            this.data = data;
            Player winner = data.getWinner();
            Player looser = data.getLooser();

            Date dateToStr = data.getDate();
            String location = data.getLocation() == null ? "N/A" : data.getLocation().toString();

            this.title.setText(MessageFormat.format("{0} - {1}\n{2} - {3}", winner.getName(), looser.getName(), location, dateToStr));
            if ((winner.getId() == (this.currentId))) {
                this.image.setImageResource(R.drawable.ball_win);
            } else {
                this.image.setImageResource(R.drawable.ball_lose);
            }
        }

        private void getSharedPref(Context context) {
            SharedPreferences sharedPref = context.getSharedPreferences(PREFERENCE_MANAGER, Context.MODE_PRIVATE);
            this.currentId = sharedPref.getInt(CURRENT_ID, -1);
        }

        @Override
        public void onClick(View v) {
            listener.onMatchSelected(this.data);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MatchesListAdapter(List<Match> dataSet, OnMatchClickedListener callBack) {
        this.data = dataSet;
        this.listener = callBack;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create a new generic cell
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_home_cell_history, parent, false);

        return new CustomViewHolder(v, listener);
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

    // ----------------------
    public interface OnMatchClickedListener {
        public void onMatchSelected(Match match);
    }
}
