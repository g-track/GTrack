package com.example.g_track.Fragments;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.g_track.Model.Route;
import com.example.g_track.Model.Stop;
import com.example.g_track.Model.Student;
import com.example.g_track.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class studentUpdateStopFragment extends Fragment {

    private Spinner spinnerStopLsit;
    private FirebaseDatabase database;
    private DatabaseReference studentRef,stopRef;
    private String selectedItem;
    private String studentKey;
    private int updatedStopId;
    private ArrayList<String> stopList;
    public studentUpdateStopFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_student_update_stop, container, false);
        initialization(view);
       // setStopFromFirebaseToSpinner();
        setStopList();
        setColorOfSelectedItem();
        return view;
    }

    /*private void setStopFromFirebaseToSpinner() {
        stopList = new ArrayList<>();
        studentRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                stopList.clear();
                for (final DataSnapshot studentSnapshot : dataSnapshot.getChildren()){
                    Student student = studentSnapshot.getValue(Student.class);
                    if (student.getStudentID()==15137029){
                        studentKey = studentSnapshot.getKey();
                        Log.i("Sohail",studentKey);
                        final int routeId = student.getStudentRouteID();
                        final int stopId = student.getStudentStopID();
                        stopRef.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for(DataSnapshot stopSnapshot : dataSnapshot.getChildren()){
                                    Stop stop = stopSnapshot.getValue(Stop.class);
                                    if (stop.getStopRouteID()==routeId) {
                                        String stopName = stop.getStopName();
                                        stopList.add(stopName);
                                        Log.i("Tag", "setStopFromFirebaseToSpinner: "+stopList.get(0));
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
*/

    private void setStopList() {
        final ArrayList<String> stopList = new ArrayList<>();
        studentRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                stopList.clear();
                for (final DataSnapshot studentSnapshot : dataSnapshot.getChildren()){
                    Student student = studentSnapshot.getValue(Student.class);
                    if (student.getStudentID()==15137029){
                       studentKey = studentSnapshot.getKey();
                        Log.i("Sohail",studentKey);
                        final int routeId = student.getStudentRouteID();
                        //final int stopId = student.getStudentStopID();
                        stopRef.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for(DataSnapshot stopSnapshot : dataSnapshot.getChildren()){
                                    Stop stop = stopSnapshot.getValue(Stop.class);
                                    if (stop.getStopRouteID()==routeId) {
                                        String stopName = stop.getStopName();
                                        stopList.add(stopName);
                                    }
                                }
                                ArrayAdapter<String> adapter =
                                        new ArrayAdapter<String>(getContext(),  android.R.layout.simple_spinner_dropdown_item, stopList);
                                adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
                                spinnerStopLsit.setAdapter(adapter);
                                //spinnerStopLsit.setSelection(0);
                                spinnerStopLsit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        selectedItem = spinnerStopLsit.getSelectedItem().toString();
                                        Log.i("Sohail",selectedItem);
                                        stopRef.addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                for (DataSnapshot stopSnapshot : dataSnapshot.getChildren()){
                                                    Stop stop = stopSnapshot.getValue(Stop.class);
                                                    if (stop.getStopName()==selectedItem){
                                                        updatedStopId = stop.getStopID();
                                                        Log.i("Sohail","value "+updatedStopId);
                                                        studentRef.child(studentKey).child("studentStopID").setValue(updatedStopId);
                                                        Log.i("Sohail", "onItemSelected: " +updatedStopId);
                                                    }
                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                            }
                                        });


                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {

                                    }
                                });
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
        spinnerStopLsit = view.findViewById(R.id.spinnerStopList_id);
        database = FirebaseDatabase.getInstance();
        studentRef = database.getReference("Student");
        stopRef = database.getReference("Stop");
    }

    private void setColorOfSelectedItem(){
        spinnerStopLsit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) view).setTextColor(Color.WHITE); //Change selected text color
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
