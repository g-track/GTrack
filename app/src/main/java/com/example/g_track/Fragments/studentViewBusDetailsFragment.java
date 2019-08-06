package com.example.g_track.Fragments;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.g_track.Model.Bus;
import com.example.g_track.Model.Driver;
import com.example.g_track.Model.Route;
import com.example.g_track.Model.Student;
import com.example.g_track.Model.User;
import com.example.g_track.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class studentViewBusDetailsFragment extends Fragment {
    private FirebaseDatabase database;
    private DatabaseReference studentRef,routeRef,busRef,driverRef;
    private TextView busNo,busDriverName,busRoutName, driverPhone;
    private ProgressDialog progressDialog;

    public studentViewBusDetailsFragment() {
        // Required empty public constructor

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_student_view_bus_details, container, false);
        initialization(view);
        getDataFromDatabase();
        return view;
    }

    private void getDataFromDatabase() {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        final User user = new User(getContext());
        studentRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot studentSnapshot : dataSnapshot.getChildren()){
                    Student student = studentSnapshot.getValue(Student.class);
                    if (student.getStudentID()==Integer.valueOf(user.getUserId())){
                        final int routeId = student.getStudentRouteID();
                        routeRef.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for (DataSnapshot routeSnapshot : dataSnapshot.getChildren()){
                                    Route route = routeSnapshot.getValue(Route.class);
                                    if (route.getRouteID()==routeId){
                                       String routeName = route.getRouteName();
                                        busRoutName.setText(routeName);
                                        final String busId = route.getRouteBusID();
                                        busRef.addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                for (DataSnapshot busSnapshot : dataSnapshot.getChildren()){
                                                    Bus bus = busSnapshot.getValue(Bus.class);
                                                    if (bus.getBusID().equals(busId)){
                                                        final int driverId = bus.getBusDriverID();
                                                        String busId = bus.getBusID();
                                                        busNo.setText(String.valueOf(busId));;
                                                        //Log.i("Sohail","ROUTE NAME:"+routeName+" , BUS NO:"+busNo);
                                                        driverRef.addValueEventListener(new ValueEventListener() {
                                                            @Override
                                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                                for (DataSnapshot driverSnapshot : dataSnapshot.getChildren()){
                                                                    Driver driver = driverSnapshot.getValue(Driver.class);
                                                                    if (driver.getDriverID()==driverId){
                                                                       String driverName = driver.getDriverName();
                                                                        busDriverName.setText(driverName);
                                                                        driverPhone.setText(driver.getDriverPhone());
                                                                    }
                                                                }
                                                                progressDialog.dismiss();
                                                            }

                                                            @Override
                                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                                            }
                                                        });
                                                    }
                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                            }
                                        });
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void initialization(View view) {
        database = FirebaseDatabase.getInstance();
        studentRef = database.getReference("student");
        routeRef = database.getReference("route");
        busRef = database.getReference("bus");
        driverRef = database.getReference("driver");
        busNo = view.findViewById(R.id.bus_no_id);
        driverPhone = view.findViewById(R.id.student_driver_phone_id);
        busDriverName = view.findViewById(R.id.driver_name_id);
        busRoutName = view.findViewById(R.id.rout_name_id);
    }
}
