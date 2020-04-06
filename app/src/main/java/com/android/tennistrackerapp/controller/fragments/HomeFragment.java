package com.android.tennistrackerapp.controller.fragments;

import android.os.Bundle;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.android.tennistrackerapp.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.fragment.app.Fragment;

public class HomeFragment extends Fragment implements View.OnClickListener {

    // ------------------------------
    // COMPONENTS FOR DESIGN
    // ------------------------------
    private View mainView;
    private boolean mIsListExpanded = false;
    private ConstraintSet originalConstrainSet;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.mainView = inflater.inflate(R.layout.fragment_home, container, false);

        //configure
        configureFragments();
        configureLayout();

        return mainView;
    }

    @Override
    public void onStart() {
        super.onStart();
        Button btn = mainView.findViewById(R.id.recycle_btn);

        btn.setOnClickListener(this);
    }


    // ---------------------
    // SETUP FUNCTIONS
    // ---------------------
    private void configureFragments() {
        // 1- Find fragment if already exist

        HeaderProfileFragment profileFragment = (HeaderProfileFragment) getChildFragmentManager().findFragmentById(R.id.frame_layout_header_profile);
        ListHistoryFragment listFragment = (ListHistoryFragment) getChildFragmentManager().findFragmentById(R.id.frame_list);

        // 2- manage and do something if it's not the case
        if(profileFragment == null) {
            // A. instantiate it
            profileFragment = new HeaderProfileFragment();
            // B. add it to the FragmentManager
            getChildFragmentManager().beginTransaction()
                    .add(R.id.frame_layout_header_profile, profileFragment)
                    .commit();
        }

        if(listFragment == null) {
            // A. instantiate it
            listFragment = new ListHistoryFragment();
            // B. add it to the FragmentManager
            getChildFragmentManager().beginTransaction()
                    .add(R.id.frame_list, listFragment)
                    .commit();
        }
    }

    private void configureLayout() {
        // Configuration for layout animation
        this.originalConstrainSet = new ConstraintSet();
        this.originalConstrainSet.clone((ConstraintLayout) mainView.findViewById(R.id.fragment_home_wrapper));
    }

    // ---------------------
    // ANIMATIONS
    // ---------------------
    private void expandListAnimation() {
        View frame = this.mainView.findViewById(R.id.frame_list);
        ConstraintLayout layout = this.mainView.findViewById(R.id.fragment_home_wrapper);

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

    // ---------------------
    // Listener
    // ---------------------
    @Override
    public void onClick(View v) {
        expandListAnimation();
    }
}
