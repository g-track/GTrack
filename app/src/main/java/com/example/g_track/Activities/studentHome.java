package com.example.g_track.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.g_track.Fragments.studentComplaintFragment;
import com.example.g_track.Fragments.studentProfileFragment;
import com.example.g_track.Fragments.studentTimeSettingFragment;
import com.example.g_track.Fragments.studentTrackBusFragment;
import com.example.g_track.Fragments.studentUpdateStopFragment;
import com.example.g_track.Fragments.studentViewBusDetailsFragment;
import com.example.g_track.Fragments.studentViewRouteFragment;
import com.example.g_track.Model.Student;
import com.example.g_track.Model.User;
import com.example.g_track.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class studentHome extends AppCompatActivity {
    private Toolbar toolbar;
    private DrawerLayout main_drawer;
    private NavigationView main_navigationView;
    private ActionBarDrawerToggle main_actionBarToggle;
    private BottomNavigationView main_student_bottomNavigation;
    private ActionBar actionBar;
    private  String extraTag = "";
    private DatabaseReference studentRef;
    private FirebaseDatabase database;
    private TextView studentName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        extraTag = getIntent().getStringExtra("TAG");

        if (savedInstanceState == null) {
            if(extraTag == null){
                Fragment fragment = new studentTrackBusFragment();
                loadFragment(fragment);
            }else{
                if(extraTag.equals("CC")){
                    Fragment fragment = new studentComplaintFragment();
                    loadFragment(fragment);
                }
            }

        }

        initialization();
        setUpToolbar();
        actionOnClickingMainItems();
        actionOnClickingBottomMenu();

        SharedPreferences prefs = getSharedPreferences("LogIn", Context.MODE_PRIVATE);
        String name = prefs.getString("username", "");
       // Log.i("Sohail", "onCreate: LogIn "+name);

        setStudentNameOnProfile();
    }

    private void setStudentNameOnProfile() {
        final User user = new User(getApplicationContext());
        studentRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot studentSnapshot : dataSnapshot.getChildren()){
                    Student student = studentSnapshot.getValue(Student.class);
                    if (student.getStudentID()==Integer.valueOf(user.getUserId())){
                        String name = student.getStudentName();
                        studentName.setText(name);

                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

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
                        actionBar.setTitle("My Profile");
                       // main_student_bottomNavigation.setVisibility(View.INVISIBLE);
                        break;
                    case R.id.updateStop_id:
                       // Toast.makeText(studentHome.this, "Update Stop Location is Clicked.", Toast.LENGTH_SHORT).show();
                        fragment = new studentUpdateStopFragment();
                        loadFragment(fragment);
                        main_drawer.closeDrawer(GravityCompat.START);
                        actionBar.setTitle("G-Track");
                        break;
                    case R.id.viewRoute_id:
                        //Toast.makeText(studentHome.this, "View Route is Clicked.", Toast.LENGTH_SHORT).show();
                        fragment = new studentViewRouteFragment();
                        loadFragment(fragment);
                        main_drawer.closeDrawer(GravityCompat.START);
                        actionBar.setTitle("Route Details");
                        break;
                    case R.id.viewBusDetails_id:
                       // Toast.makeText(studentHome.this, "View Bus Details is Clicked.", Toast.LENGTH_SHORT).show();
                        fragment = new studentViewBusDetailsFragment();
                        loadFragment(fragment);
                        main_drawer.closeDrawer(GravityCompat.START);
                        actionBar.setTitle("G-Track");
                       // main_student_bottomNavigation.setVisibility(View.INVISIBLE);
                        break;
                    case R.id.logOut_id:
                        Toast.makeText(studentHome.this, "Now Your are LogOut.", Toast.LENGTH_SHORT).show();
                        User user = new User(studentHome.this);
                        user.removeUser();
                        Intent intent = new Intent(getApplicationContext(),studentLogin.class);
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
        main_drawer = findViewById(R.id.drawer_id);
        main_navigationView = findViewById(R.id.navigationView_id);
        main_student_bottomNavigation = findViewById(R.id.bottomNavigation_id);
        View headerView = main_navigationView.getHeaderView(0);
        studentName = headerView.findViewById(R.id.textView);
        database = FirebaseDatabase.getInstance();
        studentRef = database.getReference("student");
    }

    private void loadFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.framLayout_id,fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
        finish();
    }
}
