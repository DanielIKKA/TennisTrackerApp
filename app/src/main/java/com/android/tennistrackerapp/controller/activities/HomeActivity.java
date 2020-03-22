package com.android.tennistrackerapp.controller.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.android.tennistrackerapp.R;
import com.android.tennistrackerapp.controller.fragments.HeaderProfileFragment;

public class HomeActivity extends AppCompatActivity {

    private Fragment mProfileFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //FragmentManager
        initProfileFragment();
    }

    private void initProfileFragment() {
        // 1- Find fragment if already exist
        this.mProfileFragment = getSupportFragmentManager().findFragmentById(R.id.frame_layout_header_profile);

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
