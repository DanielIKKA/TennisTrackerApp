package com.android.tennistrackerapp.controller.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.tennistrackerapp.R;
import com.android.tennistrackerapp.controller.CustomAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ListHistoryFragment extends Fragment {

    private AppCompatButton mBtnMore;

    /**
     * It's require to have a public empty constructor
     */
    public ListHistoryFragment() {}

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment HeaderProfilFragment.
     */
    public static ListHistoryFragment newInstance() {
        ListHistoryFragment fragment = new ListHistoryFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {}
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
        rv.setAdapter(new CustomAdapter(new String[]{
                "Roger\nLieu - 07/07/2019",
                "Nadal\nLieu - 07/07/2019",
                "Roger\nLieu - 07/07/2019",
                "Nadal\nLieu - 07/07/2019",
                "Roger\nLieu - 07/07/2019",
                "Nadal\nLieu - 07/07/2019"}));

        return v;
    }

    public AppCompatButton getBtnMore() { return mBtnMore; }
}
