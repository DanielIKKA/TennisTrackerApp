package com.android.tennistrackerapp.controller.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
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
import com.android.tennistrackerapp.controller.activities.MainActivity;
import com.android.tennistrackerapp.model.Player;
import com.android.tennistrackerapp.model.BitmapSaver;
import com.android.tennistrackerapp.model.ToastAssistant;
import com.android.tennistrackerapp.model.database.DBManager;

import java.util.ArrayList;
import java.util.Objects;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.INPUT_METHOD_SERVICE;

/**
 * A simple {@link Fragment} subclass.
 */
public class ManageProfileFragment extends Fragment implements View.OnClickListener, TextWatcher {

    // ---------------------
    // CONSTANTS
    // ---------------------
    private static final String CURRENT_ID = String.valueOf(R.string.comTennisTrackerCURRENT_ID);
    private static final String PREFERENCE_MANAGER = String.valueOf(R.string.comTennisTrackerPREF_MANAGER);
    private static final int REQUEST_IMAGE_CAPTURE = 1321;

    // --------------
    // DESIGN ELEM
    // --------------
    private View mainView;
    private Button btnAction;
    private Button btnDelete;
    private Button btnCurrent;
    private ImageView image;
    private TextView title;
    private ArrayList<EditText> fields = new ArrayList<>();

    private Bitmap profileImage;
    // ---------------------
    // PRIVATES ATTRIBUTES
    // ---------------------
    private DBManager manager = DBManager.getInstance();
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
    public static ManageProfileFragment newInstance(ManageProfileFragment.ProfileViewState state, int id) {
        ManageProfileFragment fragment = new ManageProfileFragment();
        fragment.state = state;

        if (state.equals(ProfileViewState.UPDATE_PROFILE)) {
            fragment.player = fragment.manager.getPlayerManager().getById(id);
        }
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(this.state == ProfileViewState.UPDATE_PROFILE) {
            this.profileImage = BitmapSaver.byteToBitmap(player.getPicture());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        this.mainView =  inflater.inflate(R.layout.fragment_manage_profile, container, false);

        //common design elements
        this.btnAction = mainView.findViewById(R.id.manage_profile_btn_action);
        this.btnDelete = mainView.findViewById(R.id.manage_profile_btn_delete);
        this.btnCurrent = mainView.findViewById(R.id.manage_profile_btn_current);
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
        this.btnCurrent.setOnClickListener(this);
        this.image.setOnClickListener(this);
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
            this.btnCurrent.setVisibility(View.INVISIBLE);
        } else if (state.equals(ProfileViewState.UPDATE_PROFILE)) {
            this.title.setText(context.getResources().getText(R.string.manage_profile_title_update));
            this.btnAction.setText(context.getResources().getText(R.string.manage_profile_btn_action_update_profile));
            this.setBtnState(BtnState.ENABLE);
            this.btnDelete.setVisibility(View.VISIBLE);
            this.btnCurrent.setVisibility(View.VISIBLE);

            if(profileImage != null) {
                this.image.setImageBitmap(profileImage);
            }

            fields.get(0).setText(player.getName());
            fields.get(1).setText(String.valueOf(player.getRank()));
            fields.get(2).setText(String.valueOf(player.getAge()));
        }
    }

    // ------------------
    // PRIVATE METHODS
    // ------------------
    private void cleanFields() {
        for (EditText field: fields) {
            field.setText("");
        }
        this.profileImage = null;
        this.image.setImageBitmap(null);
        this.image.setBackground(Objects.requireNonNull(getContext()).getDrawable(R.drawable.default_profile));
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
                this.btnCurrent.setEnabled(true);
                break;
            case DISABLE:
                btnAction.setAlpha((float) 0.5);
                btnCurrent.setAlpha((float) 0.5);
                this.btnAction.setEnabled(false);
                this.btnCurrent.setEnabled(false);
                break;
        }
    }

    private void newPlayer() {
        String name = fields.get(0).getText().toString();
        int rank = Integer.parseInt(fields.get(1).getText().toString());
        int age = Integer.parseInt(fields.get(2).getText().toString());
        byte[] picture = new byte[]{};

        if(this.profileImage != null) {
            picture = BitmapSaver.bitmapToBytes(this.profileImage, 100);
        }

        // 1- Create a player
        Player newPlayer = new Player(name, rank, age, picture);
        // 2- Put it within db
        manager.getPlayerManager().createOne(newPlayer);

        ToastAssistant.generalInstance.displayShortToast("Player Created");
        // 3- Clean all fields
        cleanFields();
    }

    private void updatePlayer() {
        String name = fields.get(0).getText().toString();
        int rank = Integer.parseInt(fields.get(1).getText().toString());
        int age = Integer.parseInt(fields.get(2).getText().toString());
        byte[] picture = new byte[]{};

        if(this.profileImage != null) {
            picture = BitmapSaver.bitmapToBytes(this.profileImage, 100);
        }

        // 1- Create a player
        player.setAge(age);
        player.setName(name);
        player.setRank(rank);
        player.setPicture(picture);
        // 2- Put it within db
        manager.getPlayerManager().update(player);

        ToastAssistant.generalInstance.displayShortToast("Player Updated");
    }

    private void delete() {
        manager.getPlayerManager().deleteOneById(player.getId());

        //check if's the current players and update sharedPref if need be
        Context context = Objects.requireNonNull(getContext());
        SharedPreferences sharedPref = context.getSharedPreferences(PREFERENCE_MANAGER, Context.MODE_PRIVATE);
        Integer idShared = sharedPref.getInt(CURRENT_ID, -1);

        if(idShared.equals(player.getId())) {
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putInt(CURRENT_ID, manager.getPlayerManager().getAll().get(0).getId());
            editor.apply();
        }

        Intent i = new Intent(this.getContext(), MainActivity.class);
        i.putExtra(context.getResources().getString(R.string.FROM_DELETE_OR_UPDATE), true);
        startActivity(i);
    }

    private void setCurrentPlayer() {
        //check if's the current players and update sharedPref if need be
        Context context = Objects.requireNonNull(getContext());
        SharedPreferences sharedPref = context.getSharedPreferences(PREFERENCE_MANAGER, Context.MODE_PRIVATE);
        Integer idShared = sharedPref.getInt(CURRENT_ID, -1);

        if(!idShared.equals(player.getId())) {
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putInt(CURRENT_ID, player.getId());
            editor.apply();
        }

        Intent i = new Intent(this.getContext(), MainActivity.class);
        i.putExtra(context.getResources().getString(R.string.FROM_DELETE_OR_UPDATE), true);
        startActivity(i);
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
            } else if(v.equals(this.btnDelete)) {
                delete();
            } else if(v.equals(this.btnCurrent)) {
                setCurrentPlayer();
            } else if(v.equals(mainView.findViewById(R.id.manage_profile_layout))) {
                hideSoftKeyBoard();
            } else if(v.equals(this.image)) {
                dispatchTakePictureIntent();
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

    // ----------
    // PHOTO
    // ----------
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(Objects.requireNonNull(getContext()).getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            this.profileImage = (Bitmap) extras.get("data");
            this.image.setImageBitmap(this.profileImage);
        }
    }
}
