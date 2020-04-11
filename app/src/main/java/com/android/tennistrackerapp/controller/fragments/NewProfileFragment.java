package com.android.tennistrackerapp.controller.fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.android.tennistrackerapp.R;
import com.android.tennistrackerapp.model.Player;
import com.android.tennistrackerapp.model.database.DBManager;

import java.util.ArrayList;
import java.util.Objects;

import androidx.fragment.app.Fragment;

import static android.content.Context.INPUT_METHOD_SERVICE;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewProfileFragment extends Fragment implements View.OnClickListener, TextWatcher {

    // --------------
    // DESIGN ELEM
    // --------------
    private View mainView;
    private Button btnCreate;

    enum BtnState {
        ENABLE, DISABLE
    }

    // ---------------------
    // PRIVATES ATTRIBUTES
    // ---------------------
    private DBManager manager = DBManager.getInstance();
    private ArrayList<EditText> fields = new ArrayList<>();

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
        fields.add((EditText) mainView.findViewById(R.id.new_profile_field_name));
        fields.add((EditText) mainView.findViewById(R.id.new_profile_field_rank));
        fields.add((EditText) mainView.findViewById(R.id.new_profile_field_age));

        //set listeners
        this.btnCreate.setOnClickListener(this);
        this.mainView.findViewById(R.id.new_profile_layout).setOnClickListener(this);

        for (EditText field : fields) {
            field.addTextChangedListener(this);
        }

        return this.mainView;
    }


    // ------------------
    // PRIVATE METHODS
    // ------------------
    private void cleanFields() {
        for (EditText field: fields) {
            field.getText().clear();
        }
    }

    private void hideSoftKeyBoard() {
        InputMethodManager imm = (InputMethodManager) Objects.requireNonNull(getContext()).getSystemService(INPUT_METHOD_SERVICE);

        if(Objects.requireNonNull(imm).isAcceptingText()) { // verify if the soft keyboard is open
            imm.hideSoftInputFromWindow(mainView.getWindowToken(), 0);
        }
    }

    private boolean isCompleteForm() {
        for (EditText field: fields) {
            if(field.getText().length() == 0) {
                return false;
            }
        }
        return true;
    }

    private void setBtnState(BtnState state) {
        switch (state) {
            case ENABLE:
                this.btnCreate.setEnabled(true);
                break;
            case DISABLE:
                this.btnCreate.setEnabled(false);
                break;
        }
    }

    // ----------------------------
    // LISTENER IMPLEMENTATION
    // ----------------------------
    @Override
    public void onClick(View v) {
        if(v.equals(mainView.findViewById(R.id.new_profile_layout))) {
            hideSoftKeyBoard();
            return;
        }

        // 1- check if field are not empty
        if(!isCompleteForm()) {
            return;
        }

        // 2- Create a player
        Player newPlayer = new Player(fields.get(0).getText().toString(),Integer.parseInt(fields.get(1).getText().toString()),
                Integer.parseInt(fields.get(2).getText().toString()), "");

        // 3- Put it within db
        manager.getPlayerManager().createOne(newPlayer);

        // 4- Clean all fields
        cleanFields();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {}

    @Override
    public void afterTextChanged(Editable s) {
        if (isCompleteForm()) {
            setBtnState(BtnState.ENABLE);
        } else {
            setBtnState(BtnState.DISABLE);
        }
    }
}
