package com.android.tennistrackerapp.controller.activities;

import android.os.Bundle;

import com.android.tennistrackerapp.R;
import com.android.tennistrackerapp.controller.CustomAdapter;
import com.android.tennistrackerapp.controller.fragments.HeaderProfileFragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class HomeActivity extends AppCompatActivity {

    private HeaderProfileFragment mProfileFragment;
    private RecyclerView mRv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //FragmentManager
        initProfileFragment();

        mRv = findViewById(R.id.recyclerView_main);
        //RecycleView
        //1- Layout Manager will manage displaying on screen, there it will be a vertical list
        mRv.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        //2- The Adapter will manage content on each cell, it is a custom Class which extends of Adapter
        mRv.setAdapter(new CustomAdapter(new String[]{
                "Roger\nLieu - 07/07/2019",
                "Nadal\nLieu - 07/07/2019",
                "Roger\nLieu - 07/07/2019",
                "Nadal\nLieu - 07/07/2019"}));
    }

    private void initProfileFragment() {
        // 1- Find fragment if already exist
        this.mProfileFragment = (HeaderProfileFragment) getSupportFragmentManager().findFragmentById(R.id.frame_layout_header_profile);

        // 2- manage and do something if it's not the case
        if(this.mProfileFragment == null) {
            // A. instantiate it
            this.mProfileFragment = new HeaderProfileFragment();
            // B. add it to the FragmentManager
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.frame_layout_header_profile, this.mProfileFragment)
                    .commit();
        }
    }
}
