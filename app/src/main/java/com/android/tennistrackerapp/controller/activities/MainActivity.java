package com.android.tennistrackerapp.controller.activities;

import android.os.Bundle;
import android.view.MenuItem;

import com.android.tennistrackerapp.R;
import com.android.tennistrackerapp.controller.fragments.HomeFragment;
import com.android.tennistrackerapp.model.DatabaseManager;
import com.android.tennistrackerapp.model.Player;
import com.google.android.material.navigation.NavigationView;

import java.util.concurrent.Executor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    // ---------------------
    // DESIGN COMPONENTS
    // ---------------------
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    // ---------------------
    // ATTRIBUTE
    // ---------------------
    private DatabaseManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        //Configure Drawer setup Views
        this.configureToolBar();
        this.configureDrawerLayout();
        this.configureNavigationView();

        //FragmentManager
        configFrameLayout();

        //Database
        this.dbManager = DatabaseManager.getInstance(this);
        
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

    // Fragments setup
    private void configFrameLayout() {

        // 1- Find fragment if already exist
        HomeFragment homeFragment = (HomeFragment) getSupportFragmentManager().findFragmentById(R.id.main_frame_manager);

        // 2- manage and do something if it's not the case
        if(homeFragment == null) {
            // A. instantiate it
            homeFragment = HomeFragment.newInstance();
            // B. add it to the FragmentManager
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.main_frame_manager, homeFragment)
                    .commit();
        }
    }

    // Toolbar setup
    private void configureToolBar(){
        this.toolbar = findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
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

    // ---------------------
    // Navigation Listener Implementation
    // ---------------------

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();

        switch (id){
            case R.id.activity_main_drawer_news :
                break;
            case R.id.activity_main_drawer_profile:
                break;
            case R.id.activity_main_drawer_settings:
                break;
            default:
                break;
        }

        this.drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }
}
