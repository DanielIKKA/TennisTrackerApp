package com.android.tennistrackerapp.controller.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.android.tennistrackerapp.R;
import com.android.tennistrackerapp.model.BitmapSaver;
import com.android.tennistrackerapp.model.Match;
import com.android.tennistrackerapp.model.MatchStat;
import com.android.tennistrackerapp.model.Player;
import com.android.tennistrackerapp.model.ToastAssistant;
import com.android.tennistrackerapp.model.database.DBManager;

import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewMatchFragment extends Fragment
        implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    private static final int REQUEST_IMAGE_CAPTURE = 4242;
    // ---------------------
    // DESIGN ELEMENTS
    // ---------------------
    private View mainView;
    private EditText game1_winner;
    private EditText game2_winner;
    private EditText game3_winner;
    private EditText game1_looser;
    private EditText game2_looser;
    private EditText game3_looser;

    private Spinner spinnerWinner;
    private Spinner spinnerLooser;

    private Button btnCreate;

    private ImageView image;
    // ---------------------
    // PRIVATE ATTRIBUTES
    // ---------------------
    private DBManager manager = DBManager.getInstance();
    private Player winner;
    private Player looser;
    private Match newMatch;

    ArrayList<String> allNames = new ArrayList<>();
    ArrayList<Player> allPlayers = new ArrayList<>();
    private Bitmap imageSaved;

    // ---------------------
    // CONSTRUCTOR & FACTORY
    // ---------------------
    public NewMatchFragment() {}

    public static NewMatchFragment newInstance() {
        return new NewMatchFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //TODO: AsyncTask for request all players
        allPlayers = (ArrayList<Player>) manager.getPlayerManager().getAll();

        for (Player player: allPlayers) {
            allNames.add(player.getName());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.mainView = inflater.inflate(R.layout.fragment_new_match, container, false);

        findViews();
        setupUI();
        setupSpinners();

        btnCreate.setOnClickListener(this);
        image.setOnClickListener(this);

        return mainView;
    }


    // ---------------------
    // SETUP
    // ---------------------
    private void findViews() {
        game1_winner = mainView.findViewById(R.id.new_match_winner_game_1);
        game2_winner = mainView.findViewById(R.id.new_match_winner_game_2);
        game3_winner = mainView.findViewById(R.id.new_match_winner_game_3);

        game1_looser = mainView.findViewById(R.id.new_match_looser_game_1);
        game2_looser = mainView.findViewById(R.id.new_match_looser_game_2);
        game3_looser = mainView.findViewById(R.id.new_match_looser_game_3);

        spinnerWinner = mainView.findViewById(R.id.new_match_winner);
        spinnerLooser = mainView.findViewById(R.id.new_match_looser);

        btnCreate = mainView.findViewById(R.id.new_match_btn_create);

        image = mainView.findViewById(R.id.new_match_image);
    }
    private void setupUI() {}

    private void setupSpinners() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item ,this.allNames);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinnerWinner.setAdapter(adapter);
        spinnerLooser.setAdapter(adapter);

        spinnerWinner.setOnItemSelectedListener(this);
        spinnerLooser.setOnItemSelectedListener(this);
    }


    // ---------------------
    // LISTENER
    // ---------------------
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(parent.equals(spinnerWinner)) {
            winner = allPlayers.get(position);
        } else if(parent.equals(spinnerLooser)) {
            looser = allPlayers.get(position);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        winner = null;
    }

    @Override
    public void onClick(View v) {
        if(v.equals(image)) {
            dispatchTakePictureIntent();
        }else if(v.equals(btnCreate)){
            create();
            ToastAssistant.generalInstance.displayShortToast("new match created");
        }
    }

    private void create() {
        newMatch = new Match(winner, looser, new Date(), null);
        Integer g1_winner = Integer.parseInt(game1_winner.getText().toString());
        Integer g2_winner = Integer.parseInt(game2_winner.getText().toString());
        Integer g3_winner = Integer.parseInt(game3_winner.getText().toString());

        Integer g1_looser = Integer.parseInt(game1_looser.getText().toString());
        Integer g2_looser = Integer.parseInt(game2_looser.getText().toString());
        Integer g3_looser = Integer.parseInt(game3_looser.getText().toString());

        MatchStat statWinner = new MatchStat(newMatch, newMatch.getWinner());
        MatchStat statLooser = new MatchStat(newMatch, newMatch.getLooser());
        statWinner.setGames_set1(g1_winner);
        statWinner.setGames_set2(g2_winner);
        statLooser.setGames_set1(g1_looser);
        statLooser.setGames_set2(g2_looser);
        if(!g1_looser.equals(0) && !g1_winner.equals(0)) {
            statWinner.setGames_set3(g3_winner);
            statLooser.setGames_set3(g3_looser);
        }

        byte[] picture = new byte[]{};

        if(this.imageSaved != null) {
            picture = BitmapSaver.bitmapToBytes(this.imageSaved, 100);
            newMatch.setImage(picture);
        }

        manager.getMatchManager().createOne(newMatch);
        manager.getMatchStatManager().createOne(statWinner);
        manager.getMatchStatManager().createOne(statLooser);
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
            this.imageSaved = (Bitmap) extras.get("data");
            this.image.setImageBitmap(this.imageSaved);
        }
    }
}
