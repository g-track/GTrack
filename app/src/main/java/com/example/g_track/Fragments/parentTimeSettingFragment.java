package com.example.g_track.Fragments;


import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.example.g_track.Model.Parent;
import com.example.g_track.Model.User;
import com.example.g_track.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.R.layout.simple_spinner_dropdown_item;
import static android.R.layout.simple_spinner_item;


/**
 * A simple {@link Fragment} subclass.
 */
public class parentTimeSettingFragment extends Fragment {
    private Spinner timeSpinner;
    private Switch OffOnAlert_btn;
    private ConstraintLayout alert_time_set_layout;
    DatabaseReference parentDatabase;
    Parent parentData, parent;
    boolean status;
    String parentKey;
    private ProgressDialog progressDialog;
    String[] time = {"5 minutes","10 minutes","15 minutes","20 minutes","30 minutes", "45 minutes", "1 hour"};


    public parentTimeSettingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       // return inflater.inflate(R.layout.fragment_parent_time_setting, container, false);

        View view = inflater.inflate(R.layout.fragment_parent_time_setting, container, false);
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading.....");
        initialization(view);
        setToogleButton();
        setSpinner();
        setColorOfSelectedItem();
        setOfOnAlert();
        return view;
    }


    private void setToogleButton(){
        progressDialog.show();
        final User user = new User(getContext());
        parentDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot parentSnapShot: dataSnapshot.getChildren()){
                    parentData = parentSnapShot.getValue(Parent.class);
                    if(parentData.getChildStudentID() == Integer.valueOf(user.getUserId())){
                        parent = parentData;
                        status = parent.isAlertStatus();
                        //studentKey = studentSnapShot.getKey();
                        if(status){
                            alert_time_set_layout.setVisibility(ConstraintLayout.VISIBLE);
                            OffOnAlert_btn.setText("ON");
                            OffOnAlert_btn.setChecked(true);
                        }else {
                            alert_time_set_layout.setVisibility(ConstraintLayout.INVISIBLE);
                            OffOnAlert_btn.setText("OFF");
                            OffOnAlert_btn.setChecked(false);
                        }
                        Log.i("ALERT STATUS", "onDataChange: "+ parent.isAlertStatus());

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        Log.i("ALERT STATUS OUT", "onDataChange: "+ status);
    }



    private void setOfOnAlert() {
        final User user = new User(getContext());
        OffOnAlert_btn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, final boolean isChecked) {
                parentDatabase.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot parentSnapShot: dataSnapshot.getChildren()){
                            parentData = parentSnapShot.getValue(Parent.class);
                            if(parentData.getChildStudentID() == Integer.valueOf(user.getUserId())){
                                parent = parentData;
                                parentKey = parentSnapShot.getKey();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                if(isChecked){
                    alert_time_set_layout.setVisibility(ConstraintLayout.VISIBLE);
                    OffOnAlert_btn.setText("ON");
                    if(parentKey != null){
                        parentDatabase.child(parentKey).child("alertStatus").setValue(true);
                    }

                }else {
                    alert_time_set_layout.setVisibility(ConstraintLayout.INVISIBLE);
                    OffOnAlert_btn.setText("OFF");
                    if(parentKey != null){
                        parentDatabase.child(parentKey).child("alertStatus").setValue(false);
                    }
                }
            }
        });
        progressDialog.dismiss();
    }

    private void setSpinner() {
        progressDialog.show();
        final User user = new User(getContext());
        parentDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot parentSnapShot: dataSnapshot.getChildren()){
                    parentData = parentSnapShot.getValue(Parent.class);
                    if(parentData.getChildStudentID() == Integer.valueOf(user.getUserId())){
                        parent = parentData;
                    }
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),simple_spinner_item,time);
                adapter.setDropDownViewResource(simple_spinner_dropdown_item);
                timeSpinner.setAdapter(adapter);
                Log.i("STUDNET", String.valueOf(parent.getArrivalTime()));
                timeSpinner.setSelection(parent.getArrivalTime());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        progressDialog.dismiss();

    }

    private void initialization(View view) {
        parentDatabase = FirebaseDatabase.getInstance().getReference("parent");
        parent = new Parent();
        parentData = new Parent();
        timeSpinner = view.findViewById(R.id.parent_timeSpinner_id);
        OffOnAlert_btn = view.findViewById(R.id.parent_switch_id);
        alert_time_set_layout = view.findViewById(R.id.parent_time_set_layout_id);
    }

    private void setArrivalTime(final int pos){
        progressDialog.show();
        final User user = new User(getContext());
        parentDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot parentSnapShot: dataSnapshot.getChildren()){
                    parentData = parentSnapShot.getValue(Parent.class);
                    if(parentData.getChildStudentID() == Integer.valueOf(user.getUserId())){
                        parent = parentData;
                        parentKey = parentSnapShot.getKey();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        if(parentKey != null){
            parentDatabase.child(parentKey).child("arrivalTime").setValue(pos);
        }
        progressDialog.dismiss();
    }

    private void setColorOfSelectedItem(){
        timeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                setArrivalTime(position);
                ((TextView) view).setTextColor(Color.WHITE); //Change selected text color
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

}
