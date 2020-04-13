package com.android.tennistrackerapp.controller.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.MenuItem;

import com.android.tennistrackerapp.R;
import com.android.tennistrackerapp.controller.adapters.PlayerListAdapter;
import com.android.tennistrackerapp.controller.fragments.HomeFragment;
import com.android.tennistrackerapp.controller.fragments.ManageProfileFragment;
import com.android.tennistrackerapp.controller.fragments.PlayersListFragment;
import com.android.tennistrackerapp.model.Player;
import com.android.tennistrackerapp.model.ToastAssistant;
import com.android.tennistrackerapp.model.database.DBManager;
import com.google.android.material.navigation.NavigationView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, PlayerListAdapter.OnPlayerClicked {

    // ---------------------
    // CONSTANTS
    // ---------------------
    private static final String CURRENT_ID = String.valueOf(R.string.comTennisTrackerCURRENT_ID);
    private static final String PREFERENCE_MANAGER = String.valueOf(R.string.comTennisTrackerPREF_MANAGER);

    // ---------------------
    // DESIGN COMPONENTS
    // ---------------------
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    // ---------------------
    // ATTRIBUTE
    // ---------------------
    private DBManager dbManager;
    private int currentPlayerId;

    //FOR FRAGMENTS
    // 1 - Declare fragment handled by Navigation Drawer
    private Fragment fragmentHome;
    private Fragment fragmentManageNewProfile;
    private Fragment fragmentManageUpdateProfile;
    private Fragment fragmentPlayersList;
    private Fragment fragmentSettings;

    //FOR DATA
    // 2 - Identify each fragment with a number
    private static final int FRAGMENT_HOME = 0;
    private static final int FRAGMENT_MANAGE_NEW_PROFILE = 1;
    private static final int FRAGMENT_MANAGE_UPDATE_PROFILE = 11;
    private static final int FRAGMENT_SETTINGS = 2;
    private static final int FRAGMENT_PLAYERS_LIST = 4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        checkSavedState(savedInstanceState);
        ToastAssistant.initToastAssistant(this);


        //Configure Drawer setup Views
        this.configureToolBar();
        this.configureDrawerLayout();
        this.configureNavigationView();

        //FragmentManager
        showFirstFragment();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putInt(CURRENT_ID, this.currentPlayerId);
        outPersistentState.putInt(CURRENT_ID, this.currentPlayerId);
        saveSharedPref();
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        checkSavedState(savedInstanceState);
    }

    @Override
    public void onBackPressed() {
        // 5 - Handle back click to close menu
        if (this.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            this.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    // ---------------------
    // CONFIGURATION
    // ---------------------
    //Bundle InstanceState Configuration
    private void checkSavedState(Bundle bundle) {
        DBManager.Init(this);
        this.dbManager = DBManager.getInstance();
        // Check whether we're recreating a previously destroyed instance
        if (bundle != null) {
            this.currentPlayerId = bundle.getInt(CURRENT_ID);
            if(dbManager.getPlayerManager().getById(currentPlayerId) == null) {
                getIdWithSharedPref();
            }
        } else {
            //We have to find one (first of the list)
            List<Player> list = this.dbManager.getPlayerManager().getAll();
            this.currentPlayerId = list.get(0).getId();
        }

        justComingFromDeleteOrUpdate();

        saveSharedPref();
    }

    private void saveSharedPref() {
        SharedPreferences prefs = getSharedPreferences(PREFERENCE_MANAGER, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(CURRENT_ID, this.currentPlayerId);
        editor.apply();
    }

    private void getIdWithSharedPref() {
        SharedPreferences prefs = getSharedPreferences(PREFERENCE_MANAGER, Context.MODE_PRIVATE);
        this.currentPlayerId = prefs.getInt(CURRENT_ID, -1);
    }

    //Coming from a delete player
    private void justComingFromDeleteOrUpdate() {
        boolean isFromDeleteOrUpdate = getIntent().getBooleanExtra(getResources().getString(R.string.FROM_DELETE_OR_UPDATE), false);

        if(isFromDeleteOrUpdate) {
            getIdWithSharedPref();
        }
    }

    // Fragments setup
    private void showFirstFragment(){
        Fragment visibleFragment = getSupportFragmentManager().findFragmentById(R.id.main_frame_manager);
        if (visibleFragment == null){
            // 1.1 - Show News Fragment
            this.showFragment(FRAGMENT_HOME);
            // 1.2 - Mark as selected the menu item corresponding to NewsFragment
            this.navigationView.getMenu().getItem(FRAGMENT_HOME).setChecked(true);
        }
    }

    // Toolbar setup
    private void configureToolBar(){
        this.toolbar = findViewById(R.id.tool_bar);
    }

    // Drawer Layout setup
    private void configureDrawerLayout(){
        this.drawerLayout = findViewById(R.id.activity_main_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    // NavigationView setup
    private void configureNavigationView(){
        this.navigationView = findViewById(R.id.activity_main_nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    // -------------------------------------
    // Navigation Listener Implementation
    // -------------------------------------
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();

        switch (id){
            case R.id.menu_item_home :
                showFragment(FRAGMENT_HOME);
                break;
            case R.id.menu_item_new_player:
                showFragment(FRAGMENT_MANAGE_NEW_PROFILE);
                break;
            case R.id.menu_item_settings:
                showFragment(FRAGMENT_SETTINGS);
                break;
            case R.id.menu_item_player_list:
                showFragment(FRAGMENT_PLAYERS_LIST);
                break;
            default:
                break;
        }

        this.drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }

    private void showFragment(int fragmentIdentifier){
        switch (fragmentIdentifier){
            case FRAGMENT_HOME :
                this.showHomeFragment();
                break;
            case FRAGMENT_MANAGE_NEW_PROFILE:
                this.showManageProfileFragmentForNew();
                break;
            case FRAGMENT_SETTINGS:
                this.showSettingsFragment();
                break;
            case FRAGMENT_PLAYERS_LIST:
                this.showPayersListFragment();
                break;
            default:
                break;
        }
    }

    private void showPayersListFragment() {
        if (this.fragmentPlayersList == null) this.fragmentPlayersList = PlayersListFragment.newInstance(this);
        this.startTransactionFragment(this.fragmentPlayersList);
    }

    private void showHomeFragment(){
        if (this.fragmentHome == null) this.fragmentHome = HomeFragment.newInstance();
        this.startTransactionFragment(this.fragmentHome);
    }

    private void showManageProfileFragmentForNew(){
        if (this.fragmentManageNewProfile == null) {
            this.fragmentManageNewProfile = ManageProfileFragment
                    .newInstance(ManageProfileFragment.ProfileViewState.NEW_PROFILE, null);
        }
        this.startTransactionFragment(this.fragmentManageNewProfile);
    }

    private void showManageProfileFragmentForUpdate(int id){
        if (this.fragmentManageUpdateProfile == null) {
            this.fragmentManageUpdateProfile = ManageProfileFragment
                    .newInstance(ManageProfileFragment.ProfileViewState.UPDATE_PROFILE, id);
        }
        this.startTransactionFragment(this.fragmentManageUpdateProfile);
    }

    private void showSettingsFragment(){
//        if (this.fragmentSettings == null) this.fragmentSettings = SettingFragment.newInstance();
//        this.startTransactionFragment(this.fragmentSettings);
    }

    // 3 - Generic method that will replace and show a fragment inside the MainActivity Frame Layout
    private void startTransactionFragment(Fragment fragment){
        if (!fragment.isVisible()){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main_frame_manager, fragment).commit();
        }
    }

    // -------------------------------------
    // onPlayerSelected Listener Implementation
    // -------------------------------------
    @Override
    public void onPlayerSelected(int playerId) {
        if(this.fragmentManageUpdateProfile != null) {
            //this.getSupportFragmentManager().beginTransaction().remove(this.fragmentManageUpdateProfile).commit();
            this.fragmentManageUpdateProfile = null;
        }
        showManageProfileFragmentForUpdate(playerId);
    }
}
