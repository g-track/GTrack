package com.example.g_track.Activities;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.g_track.Model.Bus;
import com.example.g_track.Model.Driver;
import com.example.g_track.Model.Parent;
import com.example.g_track.Model.Route;
import com.example.g_track.Model.Student;
import com.example.g_track.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
/**
 * A simple {@link Fragment} subclass.
 */
public class parentViewBusDetails extends Fragment {
    private FirebaseDatabase database;
    private DatabaseReference parentRef,studentRef,routeRef,busRef,driverRef;
    private String routeName,driverName;
    private TextView busNoText,driverText,routeText, driverPhone;
    String busNo;

    public parentViewBusDetails() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_parent_view_bus_details, container, false);
        initialization(view);
        getDataFromFirebase();
        return view;
    }

    private void getDataFromFirebase() {
        parentRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot parentSnapshot : dataSnapshot.getChildren()){
                    Parent parent = parentSnapshot.getValue(Parent.class);
                    if (parent.getParentID()==51){
                        final int childId = parent.getChildStudentID();
                        studentRef.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for (DataSnapshot studentSnapshot : dataSnapshot.getChildren()){
                                    Student student = studentSnapshot.getValue(Student.class);
                                    if (student.getStudentID()==childId){
                                        final int routeId = student.getStudentRouteID();
                                        routeRef.addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                for (DataSnapshot routeSnapshot : dataSnapshot.getChildren()){
                                                    Route route = routeSnapshot.getValue(Route.class);
                                                    if (route.getRouteID()==routeId){
                                                        routeName = route.getRouteName();
                                                        routeText.setText(routeName);
                                                        final String busId = route.getRouteBusID();
                                                        busRef.addValueEventListener(new ValueEventListener() {
                                                            @Override
                                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                                for (DataSnapshot busSnapshot : dataSnapshot.getChildren()){
                                                                    Bus bus = busSnapshot.getValue(Bus.class);
                                                                    if (bus.getBusID().equals(busId)){
                                                                        final int driverId = bus.getBusDriverID();
                                                                        busNo = bus.getBusID();
                                                                        busNoText.setText(busNo);
                                                                        driverRef.addValueEventListener(new ValueEventListener() {
                                                                            @Override
                                                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                                                for (DataSnapshot driverSnapshot : dataSnapshot.getChildren()){
                                                                                    Driver driver = driverSnapshot.getValue(Driver.class);
                                                                                    if (driver.getDriverID()==driverId){
                                                                                        driverName = driver.getDriverName();
                                                                                        driverText.setText(driverName);
                                                                                        driverPhone.setText(driver.getDriverPhone());
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
        busNoText = view.findViewById(R.id.parent_bus_no_id);
        driverText = view.findViewById(R.id.parent_driver_name_id);
        routeText = view.findViewById(R.id.parent_route_name_id);
        driverPhone = view.findViewById(R.id.parent_driver_phone_name_id);
        database = FirebaseDatabase.getInstance();
        parentRef = database.getReference("Parent");
        studentRef = database.getReference("student");
        routeRef = database.getReference("route");
        busRef = database.getReference("bus");
        driverRef = database.getReference("driver");
    }

}
