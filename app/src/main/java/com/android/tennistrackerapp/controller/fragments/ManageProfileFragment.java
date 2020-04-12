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

import androidx.annotation.Nullable;
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
    private Player player;

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

    public static ManageProfileFragment newInstance(ManageProfileFragment.ProfileViewState state, @Nullable Player player) {
        ManageProfileFragment fragment = new ManageProfileFragment();
        fragment.state = state;

        if (state.equals(ProfileViewState.UPDATE_PROFILE)) {
            fragment.player = player;
        }

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
        this.btnDelete.setOnClickListener(this);
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
            this.setBtnState(BtnState.DISABLE);
            this.btnDelete.setVisibility(View.INVISIBLE);
        } else if (state.equals(ProfileViewState.UPDATE_PROFILE)) {
            this.title.setText(context.getResources().getText(R.string.manage_profile_title_update));
            this.btnAction.setText(context.getResources().getText(R.string.manage_profile_btn_action_update_profile));
            this.btnDelete.setVisibility(View.VISIBLE);

            fields.get(0).setText(player.getName());
            fields.get(1).setText(player.getRank());
            fields.get(2).setText(player.getAge());
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
                btnAction.setAlpha(1);
                this.btnAction.setEnabled(true);
                break;
            case DISABLE:
                btnAction.setAlpha((float) 0.5);
                this.btnAction.setEnabled(false);
                break;
        }
    }

    private void newPlayer() {
        String name = fields.get(0).getText().toString();
        int rank = Integer.parseInt(fields.get(1).getText().toString());
        int age = Integer.parseInt(fields.get(2).getText().toString());

        // 1- Create a player
        Player newPlayer = new Player(name, rank, age,"");
        // 2- Put it within db
        manager.getPlayerManager().createOne(newPlayer);

        // 3- Clean all fields
        cleanFields();
    }

    private void updatePlayer() {
        String name = fields.get(0).getText().toString();
        int rank = Integer.parseInt(fields.get(1).getText().toString());
        int age = Integer.parseInt(fields.get(2).getText().toString());

        // 1- Create a player
        Player newPlayer = new Player(name, rank, age,"");
        // 2- Put it within db
        manager.getPlayerManager().createOne(newPlayer);

        // 3- Clean all fields
        cleanFields();
    }

    // ----------------------------
    // LISTENER IMPLEMENTATION
    // ----------------------------
    @Override
    public void onClick(View v) {
            if(v.equals(this.btnAction)) {
                // 1- check if field are not empty
                if (!isCompleteForm()) return;
                // 2- check state if Update or New
                if (state.equals(ProfileViewState.NEW_PROFILE)) {
                    newPlayer();
                } else {
                    updatePlayer();
                }
                //this.state.equals(ProfileViewState.NEW_PROFILE) ?
            } else if(v.equals(this.btnDelete)) {

            } else if(v.equals(mainView.findViewById(R.id.manage_profile_layout))) {
                hideSoftKeyBoard();
            }
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
