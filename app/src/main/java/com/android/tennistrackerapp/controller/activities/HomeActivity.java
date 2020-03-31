package com.android.tennistrackerapp.controller.activities;

import android.os.Bundle;
import android.transition.TransitionManager;
import android.view.View;

import com.android.tennistrackerapp.R;
import com.android.tennistrackerapp.controller.fragments.HeaderProfileFragment;
import com.android.tennistrackerapp.controller.fragments.ListHistoryFragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

public class HomeActivity extends AppCompatActivity {

    private HeaderProfileFragment mProfileFragment;
    private ListHistoryFragment mListFragment;

    private boolean mIsListExpanded = false;
    private ConstraintSet originalConstrainSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        this.originalConstrainSet = new ConstraintSet();
        this.originalConstrainSet.clone((ConstraintLayout)findViewById(R.id.main_activity_constrain_layout));
        //FragmentManager
        initFragments();
    }

    @Override
    protected void onStart() {
        super.onStart();


        findViewById(R.id.recycle_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAnimation();
            }
        });
    }

    private void initFragments() {
        // 1- Find fragment if already exist
        this.mProfileFragment = (HeaderProfileFragment) getSupportFragmentManager().findFragmentById(R.id.frame_layout_header_profile);
        this.mListFragment = (ListHistoryFragment) getSupportFragmentManager().findFragmentById(R.id.frame_list);

        // 2- manage and do something if it's not the case
        if(this.mProfileFragment == null) {
            // A. instantiate it
            this.mProfileFragment = new HeaderProfileFragment();
            // B. add it to the FragmentManager
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.frame_layout_header_profile, this.mProfileFragment)
                    .commit();
        }

        if(this.mListFragment == null) {
            // A. instantiate it
            this.mListFragment = new ListHistoryFragment();
            // B. add it to the FragmentManager
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.frame_list, this.mListFragment)
                    .commit();
        }
    }

    private void createAnimation() {
        View frame = findViewById(R.id.frame_list);
        ConstraintLayout layout = findViewById(R.id.main_activity_constrain_layout);


        ConstraintSet constraintOrigin = this.originalConstrainSet;

        ConstraintSet constraintExpanded = new ConstraintSet();
        constraintExpanded.connect(frame.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP);
        constraintExpanded.connect(frame.getId(), ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM);
        constraintExpanded.connect(frame.getId(), ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START);
        constraintExpanded.connect(frame.getId(), ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END);

        TransitionManager.beginDelayedTransition(layout);
        ConstraintSet constraint = this.mIsListExpanded ? constraintOrigin : constraintExpanded;
        constraint.applyTo(layout);
        this.mIsListExpanded = !this.mIsListExpanded;
    }
}
