package com.android.tennistrackerapp.controller.activities;

import android.os.Bundle;
import android.transition.TransitionManager;
import android.view.MenuItem;
import android.view.View;

import com.android.tennistrackerapp.R;
import com.android.tennistrackerapp.controller.fragments.HeaderProfileFragment;
import com.android.tennistrackerapp.controller.fragments.ListHistoryFragment;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private boolean mIsListExpanded = false;
    private ConstraintSet originalConstrainSet;

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        // Configuration for layout animation
        this.originalConstrainSet = new ConstraintSet();
        this.originalConstrainSet.clone((ConstraintLayout)findViewById(R.id.main_activity_constrain_layout));

        //Configure Views
        this.configureToolBar();
        this.configureDrawerLayout();
        this.configureNavigationView();

        //FragmentManager
        initFragments();
    }

    @Override
    protected void onStart() {
        super.onStart();

        findViewById(R.id.recycle_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expandListAnimation();
            }
        });
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

    // Fragments configuration
    private void initFragments() {
        // 1- Find fragment if already exist
        HeaderProfileFragment profileFragment = (HeaderProfileFragment) getSupportFragmentManager().findFragmentById(R.id.frame_layout_header_profile);
        ListHistoryFragment listFragment = (ListHistoryFragment) getSupportFragmentManager().findFragmentById(R.id.frame_list);

        // 2- manage and do something if it's not the case
        if(profileFragment == null) {
            // A. instantiate it
            profileFragment = new HeaderProfileFragment();
            // B. add it to the FragmentManager
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.frame_layout_header_profile, profileFragment)
                    .commit();
        }

        if(listFragment == null) {
            // A. instantiate it
            listFragment = new ListHistoryFragment();
            // B. add it to the FragmentManager
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.frame_list, listFragment)
                    .commit();
        }
    }

    // 1 - Configure Toolbar
    private void configureToolBar(){
        this.toolbar = findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
    }

    // 2 - Configure Drawer Layout
    private void configureDrawerLayout(){
        this.drawerLayout = findViewById(R.id.activity_main_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    // 3 - Configure NavigationView
    private void configureNavigationView(){
        this.navigationView = findViewById(R.id.activity_main_nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    // ---------------------
    // ANIMATIONS
    // ---------------------
    private void expandListAnimation() {
        View frame = findViewById(R.id.frame_list);
        ConstraintLayout layout = findViewById(R.id.main_activity_constrain_layout);

        ConstraintSet constraintOrigin = this.originalConstrainSet;

        ConstraintSet constraintExpanded = new ConstraintSet();
        constraintExpanded.connect(frame.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP);
        constraintExpanded.connect(frame.getId(), ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM);
        constraintExpanded.connect(frame.getId(), ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START);
        constraintExpanded.connect(frame.getId(), ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END);

        TransitionManager.beginDelayedTransition(layout);
        ConstraintSet constraint = this.mIsListExpanded ? constraintOrigin : constraintExpanded;
        constraint.applyTo(layout);
        this.mIsListExpanded = !this.mIsListExpanded;
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
