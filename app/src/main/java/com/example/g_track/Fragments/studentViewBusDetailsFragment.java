package com.example.g_track.Fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.g_track.Model.Bus;
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
    private DatabaseReference myRef;

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
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Bus bus = snapshot.getValue(Bus.class);
                   // Toast.makeText(getContext(), "busID" +  bus.getBusID(), Toast.LENGTH_SHORT).show();

                    if(bus.getBusID()==12) {
                        Log.i("Muhammad Sohail :", "busID:" + bus.getBusID() + ", busDriver:" + bus.getBusDriver() + ", busStatus:" + bus.isBusStatus());
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
        myRef = database.getReference("bus");

    }


}
