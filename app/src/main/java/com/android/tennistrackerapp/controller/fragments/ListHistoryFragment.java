package com.android.tennistrackerapp.controller.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.tennistrackerapp.R;
import com.android.tennistrackerapp.controller.CustomAdapter;
import com.android.tennistrackerapp.model.Match;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ListHistoryFragment extends Fragment {

    /**
     * It's require to have a public empty constructor
     */
    public ListHistoryFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_home_list_history, container, false);

        RecyclerView rv = v.findViewById(R.id.recyclerView_main);

        //RecycleView
        //1- Layout Manager will manage displaying on screen, there it will be a vertical list
       rv.setLayoutManager(new LinearLayoutManager(v.getContext(), RecyclerView.VERTICAL, false));

       //2- The Adapter will manage content on each cell, it is a custom Class which extends of Adapter
        ArrayList data = new ArrayList<Match>();
        data.add(new Match());
        data.add(new Match());
        data.add(new Match());
        rv.setAdapter(new CustomAdapter(data));

        return v;
    }
}
