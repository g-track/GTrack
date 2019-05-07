package com.example.g_track;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

public class studentHome extends AppCompatActivity {
    private Toolbar toolbar;
    private DrawerLayout main_drawer;
    private NavigationView main_navigationView;
    private ActionBarDrawerToggle main_actionBarToggle;
    private BottomNavigationView main_student_bottomNavigation;
    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialization();
        setUpToolbar();
        actionOnClickingMainItems();
        actionOnClickingBottomMenu();
    }

    private void actionOnClickingBottomMenu() {
        main_student_bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment fragment;
                int id = menuItem.getItemId();
                switch (id){
                    case R.id.trackBus_id:
                        fragment = new studentTrackBusFragment();
                        loadFragment(fragment);
                        actionBar.setTitle("Track Bus");
                        return true;
                    case R.id.complaint_id:
                        fragment = new studentComplaintFragment();
                        loadFragment(fragment);
                        actionBar.setTitle("Complaint");
                        return true;
                    case R.id.setting_id:
                        fragment = new studentTimeSettingFragment();
                        loadFragment(fragment);
                        actionBar.setTitle("Time Setting");
                        return true;
                }
                return false;
            }
        });
    }

    private void setUpToolbar() {
        toolbar = findViewById(R.id.main_toolbar);
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
                    case R.id.myProfile_id:
                        //Toast.makeText(studentHome.this, "My Profile is Clicked.", Toast.LENGTH_SHORT).show();
                        fragment = new studentProfileFragment();
                        loadFragment(fragment);
                        main_drawer.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.updateStop_id:
                        Toast.makeText(studentHome.this, "Update Stop Location is Clicked.", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.viewRoute_id:
                        Toast.makeText(studentHome.this, "View Route is Clicked.", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.viewBusDetails_id:
                        Toast.makeText(studentHome.this, "View Bus Details is Clicked.", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.logOut_id:
                        Toast.makeText(studentHome.this, "Log Out is Clicked.", Toast.LENGTH_SHORT).show();
                        break;
                     default:
                         return true;
                }
                return true;
            }
        });
    }

    private void initialization() {
        main_drawer = findViewById(R.id.drawer_id);
        main_navigationView = findViewById(R.id.navigationView_id);
        main_student_bottomNavigation = findViewById(R.id.bottomNavigation_id);
    }

    private void loadFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.framLayout_id,fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
