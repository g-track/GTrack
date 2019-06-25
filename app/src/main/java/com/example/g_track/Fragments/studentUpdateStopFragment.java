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

import com.example.g_track.Model.Stop;
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
    private DatabaseReference myRef;
    public studentUpdateStopFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_student_update_stop, container, false);
        initialization(view);
        setStopList();
        setColorOfSelectedItem();
        return view;
    }

    private void setStopList() {
        final ArrayList<String> stopList = new ArrayList<>();
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot stopSnapshot : dataSnapshot.getChildren()){
                    Stop stop = stopSnapshot.getValue(Stop.class);
                    String  stopName = stop.getStopName();
                    //stopList.add(stopName);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

          stopList.add("Stop 1");
          stopList.add("Stop 2");


        //String[] stopList = {"stop 1","stop 2","stop 3","stop 4","stop 5","stop 6","stop 7"};
       /* ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_dropdown_item,stopList);
        spinnerStopLsit.setAdapter(adapter);

*/

        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(getContext(),  android.R.layout.simple_spinner_dropdown_item, stopList);
        adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);

        spinnerStopLsit.setAdapter(adapter);
       // String selectedItem = spinnerStopLsit.getSelectedItem().toString();
       // Log.i("Sohail",selectedItem);
    }

    private void initialization(View view) {
        spinnerStopLsit = view.findViewById(R.id.spinnerStopList_id);
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Stop");
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
