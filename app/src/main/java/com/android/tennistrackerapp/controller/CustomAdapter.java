package com.android.tennistrackerapp.controller;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.tennistrackerapp.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/*
 * Resources :
 *  - https://developer.android.com/guide/topics/ui/layout/recyclerview
 *  - https://openclassrooms.com/fr/courses/3499366-developpez-une-application-pour-android/3568556-affichez-des-listes-avec-recyclerview
 *  - https://www.youtube.com/watch?v=fxVeFwtIpVc
 */
public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomViewHolder> {

    private String[] mDataset;

    // Provide a reference to the views for each data item (cell)
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    static class CustomViewHolder extends RecyclerView.ViewHolder {

        private ImageView image;
        private TextView title;

        CustomViewHolder(View cell) {
            super(cell);

            //find cell's view
            this.image = cell.findViewById(R.id.cell_ball_state);
            this.title = cell.findViewById(R.id.cell_title);
        }

        void initWithData(String data) {
            this.title.setText(data);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public CustomAdapter(String[] dataSet) {
        this.mDataset = dataSet;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create a new generic cell
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cell_history, parent, false);

        CustomViewHolder vh = new CustomViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        String data = mDataset[position];
        holder.initWithData(data);
    }

    @Override
    public int getItemCount() {
        return mDataset.length;
    }
}
