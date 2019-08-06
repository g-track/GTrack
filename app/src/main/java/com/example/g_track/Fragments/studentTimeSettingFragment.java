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

import com.example.g_track.Model.Student;
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
public class studentTimeSettingFragment extends Fragment {
    private Spinner timeSpinner;
    private Spinner departureTimeSpinner;
    private Switch OffOnAlert_btn;
    private ConstraintLayout alert_time_set_layout;
    DatabaseReference databaseReference;
    Student studentData, student;
    private String studentKey;
    boolean status;
    String[] time = {"5 minutes", "10 minutes", "15 minutes", "20 minutes", "30 minutes", "45 minutes", "1 hour"};


    public studentTimeSettingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_student_time_setting, container, false);

        initialization(view);
        setToogleButton();
        setSpinner();
        setColorOfSelectedItem();
        setOfOnAlert();

        return view;
    }


    private void setToogleButton() {


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ProgressDialog progressDialog;
                progressDialog = new ProgressDialog(getContext());
                progressDialog.setMessage("Loading...");
                progressDialog.show();
                User user = new User(getContext());

                for (DataSnapshot studentSnapShot : dataSnapshot.getChildren()) {
                    studentData = studentSnapShot.getValue(Student.class);
                    if (studentData.getStudentID() == Integer.valueOf(user.getUserId())) {
                        student = studentData;
                        status = student.isAlertStatus();
                        //studentKey = studentSnapShot.getKey();
                        if (status) {
                            alert_time_set_layout.setVisibility(ConstraintLayout.VISIBLE);
                            OffOnAlert_btn.setText("ON");
                            OffOnAlert_btn.setChecked(true);
                        } else {
                            alert_time_set_layout.setVisibility(ConstraintLayout.INVISIBLE);
                            OffOnAlert_btn.setText("OFF");
                            OffOnAlert_btn.setChecked(false);
                        }
                        Log.i("ALERT STATUS", "onDataChange: " + student.isAlertStatus());

                    }
                }
                progressDialog.dismiss();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });
        Log.i("ALERT STATUS OUT", "onDataChange: " + status);

    }


    private void setOfOnAlert() {
        ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        final User user = new User(getContext());
        OffOnAlert_btn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, final boolean isChecked) {
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot studentSnapShot : dataSnapshot.getChildren()) {
                            studentData = studentSnapShot.getValue(Student.class);
                            if (studentData.getStudentID() == Integer.valueOf(user.getUserId())) {
                                student = studentData;
                                studentKey = studentSnapShot.getKey();
                            }
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                if (isChecked) {
                    alert_time_set_layout.setVisibility(ConstraintLayout.VISIBLE);
                    OffOnAlert_btn.setText("ON");
                    if (studentKey != null) {
                        databaseReference.child(studentKey).child("alertStatus").setValue(true);
                    }

                } else {
                    alert_time_set_layout.setVisibility(ConstraintLayout.INVISIBLE);
                    OffOnAlert_btn.setText("OFF");
                    if (studentKey != null) {
                        databaseReference.child(studentKey).child("alertStatus").setValue(false);
                    }
                }
            }
        });
        progressDialog.dismiss();
    }

    private void setSpinner() {
        ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        final User user = new User(getContext());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot studentSnapShot : dataSnapshot.getChildren()) {
                    studentData = studentSnapShot.getValue(Student.class);
                    if (studentData.getStudentID() == Integer.valueOf(user.getUserId())){
                        student = studentData;
                    }
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), simple_spinner_item, time);
                adapter.setDropDownViewResource(simple_spinner_dropdown_item);
                timeSpinner.setAdapter(adapter);
                timeSpinner.setSelection(student.getAlertArrivalTime());
                departureTimeSpinner.setAdapter(adapter);
                departureTimeSpinner.setSelection(student.getAlertDepartureTime());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        progressDialog.dismiss();
    }

    private void initialization(View view) {
        timeSpinner = view.findViewById(R.id.student_timeSpinner_1);
        OffOnAlert_btn = view.findViewById(R.id.switch_id);
        studentData = new Student();
        student = new Student();
        alert_time_set_layout = view.findViewById(R.id.layout_spinner_1);
        departureTimeSpinner = view.findViewById(R.id.student_timeSpinner_2);
        databaseReference = FirebaseDatabase.getInstance().getReference("student");
    }

    private void setArrivalTime(final int pos) {
        ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        final User user = new User(getContext());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot studentSnapShot : dataSnapshot.getChildren()) {
                    studentData = studentSnapShot.getValue(Student.class);
                    if (studentData.getStudentID() == Integer.valueOf(user.getUserId())) {
                        student = studentData;
                        studentKey = studentSnapShot.getKey();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        if (studentKey != null) {
            databaseReference.child(studentKey).child("alertArrivalTime").setValue(pos);
        }
        progressDialog.dismiss();
    }

    private void setDepartureTime(final int pos) {
        ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        final User user = new User(getContext());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot studentSnapShot : dataSnapshot.getChildren()) {
                    studentData = studentSnapShot.getValue(Student.class);
                    if (studentData.getStudentID() == Integer.valueOf(user.getUserId())) {
                        student = studentData;
                        studentKey = studentSnapShot.getKey();
                        Log.i("STUDENT", studentKey);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        if (studentKey != null) {
            databaseReference.child(studentKey).child("alertDepartureTime").setValue(pos);
        }
        progressDialog.dismiss();

    }

    private void setColorOfSelectedItem() {
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

        departureTimeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                setDepartureTime(position);
                ((TextView) view).setTextColor(Color.WHITE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
