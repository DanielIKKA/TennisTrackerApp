package com.android.tennistrackerapp.controller.fragments;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.tennistrackerapp.R;
import com.android.tennistrackerapp.model.BitmapSaver;
import com.android.tennistrackerapp.model.Player;
import com.android.tennistrackerapp.model.database.DBManager;

import java.util.Objects;

import androidx.fragment.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HeaderProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HeaderProfileFragment extends Fragment {

    private static final String CURRENT_ID = String.valueOf(R.string.comTennisTrackerCURRENT_ID);

    // ---------------------
    // DESIGN ELEMENTS
    // ---------------------
    private View mainView;
    TextView name;
    TextView rank;
    TextView age;
    ImageView image;

    // ---------------------
    // PRIVATE ATTRIBUTES
    // ---------------------
    private Player currentPlayer = new Player();
    private DBManager manager = DBManager.getInstance();

    // ---------------------
    // CONSTRUCTOR & FACTORY
    // ---------------------
    public static HeaderProfileFragment newInstance(int currentPlayerId) {
        HeaderProfileFragment fragment = new HeaderProfileFragment();
        Bundle args = new Bundle();
        args.putInt(CURRENT_ID, currentPlayerId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            this.currentPlayer.setId(getArguments().getInt(CURRENT_ID));
            this.currentPlayer = manager.getPlayerManager().getById(currentPlayer.getId());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.mainView =  inflater.inflate(R.layout.fragment_home_header_profile, container, false);

        findViewByIds();
        updateUI();

        return mainView;
    }

    // ---------------------
    // SETUP FUNCTIONS
    // ---------------------
    private void findViewByIds() {
        this.name = this.mainView.findViewById(R.id.header_name_player);
        this.rank = this.mainView.findViewById(R.id.header_rank);
        this.age = this.mainView.findViewById(R.id.header_age);
        this.image = this.mainView.findViewById(R.id.header_profile_image);
    }
    private void updateUI() {
        String rankStr = new StringBuilder()
                .append(Objects.requireNonNull(getActivity()).getResources().getString(R.string.profile_card_rank)).append(" ")
                .append(this.currentPlayer.getRank())
                .toString();

        String ageStr = new StringBuilder()
                .append(this.currentPlayer.getAge()).append(" ")
                .append(Objects.requireNonNull(getActivity()).getResources().getString(R.string.profile_card_age))
                .toString();

        Bitmap profil = (this.currentPlayer.getPicture().length == 0) ? null : BitmapSaver.byteToBitmap(this.currentPlayer.getPicture());

        if(profil == null) {
            this.image.setBackground(getContext().getDrawable(R.drawable.default_profile));
        }
        this.image.setImageBitmap(profil);
        this.name.setText(this.currentPlayer.getName());
        this.rank.setText(rankStr);
        this.age.setText(ageStr);
    }
}
