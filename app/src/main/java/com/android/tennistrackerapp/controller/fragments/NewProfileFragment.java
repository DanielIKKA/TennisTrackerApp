package com.android.tennistrackerapp.controller.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.android.tennistrackerapp.R;
import com.android.tennistrackerapp.model.Player;
import com.android.tennistrackerapp.model.database.DBManager;

import androidx.fragment.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewProfileFragment extends Fragment implements View.OnClickListener {

    // --------------
    // DESIGN ELEM
    // --------------
    private View mainView;
    private Button btnCreate;

    // ---------------------
    // PRIVATES ATTRIBUTES
    // ---------------------
    private DBManager manager = DBManager.getInstance();

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

        //design elements
        this.btnCreate = mainView.findViewById(R.id.new_profile_btn);

        return this.mainView;
    }


    // ----------------------------
    // LISTENER IMPLEMENTATION
    // ----------------------------
    @Override
    public void onClick(View v) {
        //1 get information from UI
        EditText nameEditText = mainView.findViewById(R.id.new_profile_field_name);
        EditText rank = mainView.findViewById(R.id.new_profile_field_rank);
        EditText age = mainView.findViewById(R.id.new_profile_field_age);

        //Create a player
        Player newPlayer = new Player(nameEditText.getText().toString(),Integer.parseInt(rank.getText().toString()), Integer.parseInt(age.getText().toString()), "");

        //Put it within db
        manager.getPlayerManager().createOne(newPlayer);
    }
}
