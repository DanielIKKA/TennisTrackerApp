package com.android.tennistrackerapp.controller.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.tennistrackerapp.R;

import androidx.fragment.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewProfileFragment extends Fragment {

    // --------------
    // DESIGN ELEM
    // --------------
    private View mainView;

    // --------------
    // CONSTRUCTOR
    // --------------
    public NewProfileFragment() {}

    public static NewProfileFragment newInstance() {
        return new NewProfileFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        this.mainView =  inflater.inflate(R.layout.fragment_new_profile, container, false);

        return this.mainView;
    }
}
