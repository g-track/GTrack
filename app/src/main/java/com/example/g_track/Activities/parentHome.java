package com.example.g_track.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.g_track.Fragments.parentProfileFragment;
import com.example.g_track.Fragments.parentTimeSettingFragment;
import com.example.g_track.Fragments.parentTrackBusFragment;
import com.example.g_track.Model.User;
import com.example.g_track.R;

public class parentHome extends AppCompatActivity {
    private Toolbar toolbar;
    private DrawerLayout main_drawer;
    private NavigationView main_navigationView;
    private ActionBarDrawerToggle main_actionBarToggle;
    private BottomNavigationView main_parent_bottomNavigation;
    private ActionBar actionBar;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_home);

        if (savedInstanceState == null) {
            Fragment fragment = new parentTrackBusFragment();
            loadFragment(fragment);
        }

        initialization();
        setUpToolbar();
        actionOnClickingMainItems();
        actionOnClickingBottomMenu();
    }


    private void actionOnClickingBottomMenu() {
        main_parent_bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment fragment;
                int id = menuItem.getItemId();
                switch (id){
                    case R.id.parent_trackBus_id:
                        fragment = new parentTrackBusFragment();
                        loadFragment(fragment);
                        actionBar.setTitle("Track Bus");
                        return true;
                    case R.id.parent_setting_id:
                        fragment = new parentTimeSettingFragment();
                        loadFragment(fragment);
                        actionBar.setTitle("Time Setting");
                        return true;
                }
                return false;
            }
        });
    }

    private void setUpToolbar() {
        toolbar = findViewById(R.id.parent_main_toolbar);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        actionBar.setTitle("G-Track");
        main_actionBarToggle = new ActionBarDrawerToggle(this,main_drawer,toolbar,R.string.open,R.string.close);
        main_drawer.addDrawerListener(main_actionBarToggle);
        main_actionBarToggle.syncState();
        ///
    }

    private void actionOnClickingMainItems() {
        main_navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment fragment;
                int id = menuItem.getItemId();
                switch (id){
                    case R.id.parent_myProfile_id:
                        fragment = new parentProfileFragment();
                        loadFragment(fragment);
                        actionBar.setTitle("My Profile");
                        main_drawer.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.parent_viewBusDetails_id:
                        fragment = new parentViewBusDetails();
                        loadFragment(fragment);
                        actionBar.setTitle("G-Track");
                        main_drawer.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.parent_logOut_id:
                        Toast.makeText(parentHome.this, "Log Out is Clicked.", Toast.LENGTH_SHORT).show();
                        User user = new User(parentHome.this);
                        user.removeUser();
                        Intent intent = new Intent(getApplicationContext(),parentLogin.class);
                        startActivity(intent);
                        finish();
                        break;
                    default:
                        return true;
                }
                return true;
            }
        });
    }

    private void initialization() {
        main_drawer = findViewById(R.id.parent_drawer);
        main_navigationView = findViewById(R.id.parent_navigationView_id);
        main_parent_bottomNavigation = findViewById(R.id.parent_bottomNavigation_id);
    }

    private void loadFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.parent_framLayout_id,fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
