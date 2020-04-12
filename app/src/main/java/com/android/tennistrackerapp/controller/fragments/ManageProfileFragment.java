package com.android.tennistrackerapp.controller.fragments;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

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
public class ManageProfileFragment extends Fragment implements View.OnClickListener, TextWatcher {

    // --------------
    // DESIGN ELEM
    // --------------
    private View mainView;
    private Button btnAction;
    private Button btnDelete;
    private ImageView image;
    private TextView title;

    // ---------------------
    // PRIVATES ATTRIBUTES
    // ---------------------
    private DBManager manager = DBManager.getInstance();
    private ArrayList<EditText> fields = new ArrayList<>();
    private ProfileViewState state;

    private enum BtnState {
        ENABLE, DISABLE
    }

    public static enum ProfileViewState {
        NEW_PROFILE, UPDATE_PROFILE
    }

    // --------------
    // CONSTRUCTOR
    // --------------
    public ManageProfileFragment() {}

    public static ManageProfileFragment newInstance(ManageProfileFragment.ProfileViewState state) {
        ManageProfileFragment fragment = new ManageProfileFragment();
        fragment.state = state;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        this.mainView =  inflater.inflate(R.layout.fragment_manage_profile, container, false);

        //common design elements
        this.btnAction = mainView.findViewById(R.id.manage_profile_btn_action);
        this.btnDelete = mainView.findViewById(R.id.manage_profile_btn_delete);
        this.image = mainView.findViewById(R.id.manage_profile_image);
        this.title = mainView.findViewById(R.id.manage_profile_title);
        fields.add((EditText) mainView.findViewById(R.id.manage_profile_field_name));
        fields.add((EditText) mainView.findViewById(R.id.manage_profile_field_rank));
        fields.add((EditText) mainView.findViewById(R.id.manage_profile_field_age));

        //configure depend on state
        setupUI();

        //set listeners
        this.btnAction.setOnClickListener(this);
        this.mainView.findViewById(R.id.manage_profile_layout).setOnClickListener(this);

        for (EditText field : fields) {
            field.addTextChangedListener(this);
        }

        return this.mainView;
    }

    // ------------------
    // CONFIGURE
    // ------------------
    private void setupUI() {
        Context context = Objects.requireNonNull(getContext());

        if(state.equals(ProfileViewState.NEW_PROFILE)) {
            this.title.setText(context.getResources().getText(R.string.manage_profile_title_new));
            this.btnAction.setText(context.getResources().getText(R.string.manage_profile_btn_action_new_profile));
            this.btnDelete.setVisibility(View.INVISIBLE);
        } else if (state.equals(ProfileViewState.UPDATE_PROFILE)) {
            this.title.setText(context.getResources().getText(R.string.manage_profile_title_update));
            this.btnAction.setText(context.getResources().getText(R.string.manage_profile_btn_action_update_profile));
            this.btnDelete.setVisibility(View.VISIBLE);
        }
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
                this.btnAction.setEnabled(true);
                break;
            case DISABLE:
                this.btnAction.setEnabled(false);
                break;
        }
    }

    // ----------------------------
    // LISTENER IMPLEMENTATION
    // ----------------------------
    @Override
    public void onClick(View v) {
        if(v.equals(mainView.findViewById(R.id.manage_profile_layout))) {
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
