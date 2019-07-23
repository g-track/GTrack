package com.example.g_track.Fragments;


import android.app.ProgressDialog;
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
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

//import com.example.g_track.Model.Root;
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
    private DatabaseReference studentRef,stopRef, routeRef;
    private String studentKey;
    private String studentStopName;
    private TextView routeNameShow;
    private int updatedStopId;
    private int mPosition;
    private int routeId;
    private ProgressDialog progressDialog;
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

       // fetchingRoot();
        setStopFromFirebaseToSpinner();
        setRouteName();
        setColorOfSelectedItem();
        return view;
    }

    public void setRouteName(){
        routeRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(final DataSnapshot routeSnapshot: dataSnapshot.getChildren()){
                    Route route = routeSnapshot.getValue(Route.class);
                    if(route.getRouteID() == routeId){
                        routeNameShow.setText(route.getRouteName().toString());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void setStopFromFirebaseToSpinner() {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading...");
        progressDialog.show();
       stopList = new ArrayList<>();
        studentRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (final DataSnapshot studentSnapshot : dataSnapshot.getChildren()){
                    Student student = studentSnapshot.getValue(Student.class);
                    if (student.getStudentID()==15137038){
                       studentKey = studentSnapshot.getKey();
                        routeId = student.getStudentRouteID();
                        final int stopId = student.getStudentStopID();

                        stopRef.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                stopList.clear();
                                for(DataSnapshot stopSnapshot : dataSnapshot.getChildren()){
                                    Stop stop = stopSnapshot.getValue(Stop.class);
                                    if (stop.getStopRouteID()==routeId) {
                                        String stopName = stop.getStopName();
                                        stopList.add(stopName);
                                    }
                                    if(stop.getStopID()==stopId){
                                        studentStopName = stop.getStopName();
                                    }
                                }
                                int index = 0;
                                for (String stop_name : stopList){

                                    if (stop_name == studentStopName){
                                        mPosition = index;
                                        break;
                                    }
                                    index++;
                                }

                                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),  android.R.layout.simple_spinner_dropdown_item, stopList);
                                adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
                                spinnerStopLsit.setAdapter(adapter);
                                spinnerStopLsit.setSelection(mPosition);
                                spinnerStopLsit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {
                                        stopRef.addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                final String nameStop = stopList.get(position);
                                                for (DataSnapshot stopSnapshot : dataSnapshot.getChildren()){
                                                    Stop stop = stopSnapshot.getValue(Stop.class);
                                                    if (stop.getStopName().equals(nameStop)){
                                                        updatedStopId = stop.getStopID();
                                                        studentRef.child(studentKey).child("studentStopID").setValue(updatedStopId);
                                                    }
                                                }
                                                progressDialog.dismiss();
                                            }
                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                            }
                                        });
                                        studentRef.child(studentKey).child("studentStopID").setValue(updatedStopId);
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
        routeNameShow = view.findViewById(R.id.textView12);
        studentRef = database.getReference("student");
        stopRef = database.getReference("stop");
        routeRef = database.getReference("route");
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
