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

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    // TODO: Rename and change types of parameters


    private RecyclerView mRv;
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
    // TODO: Rename and change types and number of parameters
    public static ListHistoryFragment newInstance() {
        ListHistoryFragment fragment = new ListHistoryFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            //mParam1 = getArguments().getString(ARG_PARAM1);
            //mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_list_history, container, false);

        this.mRv = v.findViewById(R.id.recyclerView_main);

        //RecycleView
        //1- Layout Manager will manage displaying on screen, there it will be a vertical list
       this.mRv.setLayoutManager(new LinearLayoutManager(v.getContext(), RecyclerView.VERTICAL, false));
        //2- The Adapter will manage content on each cell, it is a custom Class which extends of Adapter
        this.mRv.setAdapter(new CustomAdapter(new String[]{
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
