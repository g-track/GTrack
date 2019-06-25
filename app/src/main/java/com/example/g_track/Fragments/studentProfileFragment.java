package com.example.g_track.Fragments;


import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.g_track.Model.Route;
import com.example.g_track.Model.Stop;
import com.example.g_track.Model.Student;
import com.example.g_track.R;
import com.example.g_track.Activities.parentForgetPassword;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class studentProfileFragment extends Fragment {
    private CircleImageView studentProfileImage;
    private ImageView btnEditPhone;
    private TextView phoneText;
    private EditText popupEditTextPhone;
    private AlertDialog.Builder updatePhone;
    private AlertDialog dialogPopup;
    private Button btnUpdatePhone;
    private TextView updatePassword;
    private TextView studentName,studentId,studentFatherName,studentFatherCNIC,studentRouteName,studentStopName;
    private FirebaseDatabase database;
    private DatabaseReference studentRef,routeRef,stopRef;

    public studentProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_student_profile, container, false);

        initialization(view);
        btnEditPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editPhone(v);
            }
        });
        updatePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatePassword(v);
            }
        });
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        studentRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot studentSnapshot : dataSnapshot.getChildren()){
                    final Student student = studentSnapshot.getValue(Student.class);
                    if (student.getStudentID()==15137038){
                        studentName.setText(student.getStudentName());
                        studentId.setText(String.valueOf(student.getStudentID()));
                        studentFatherName.setText(student.getFatherName());
                        studentFatherCNIC.setText(student.getFatherCNIC());
                        phoneText.setText(student.getStudentPhoneNo());
                        final int stopId = student.getStudentStopID();
                        routeRef.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for (DataSnapshot routeSnapshot : dataSnapshot.getChildren()){
                                    Route route = routeSnapshot.getValue(Route.class);
                                    if (route.getRouteID()==student.getStudentRouteID()){
                                        studentRouteName.setText(route.getRouteName());
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                        stopRef.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for (DataSnapshot stopSnapshot : dataSnapshot.getChildren()){
                                    Stop stop = stopSnapshot.getValue(Stop.class);
                                    if (stop.getStopID()==stopId){
                                        studentStopName.setText(stop.getStopName());
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
        studentProfileImage = view.findViewById(R.id.student_profile_image);
        btnEditPhone = view.findViewById(R.id.btn_student_phone_edit);
        phoneText = view.findViewById(R.id.student_phoneNo_textView);
        updatePassword = view.findViewById(R.id.student_updatePassword_textView);
        studentId = view.findViewById(R.id.student_id_textView);
        studentName = view.findViewById(R.id.student_login_Text);
        studentFatherName = view.findViewById(R.id.student_father_textView);
        studentFatherCNIC = view.findViewById(R.id.student_fatherCnic_textView);
        studentRouteName = view.findViewById(R.id.student_route_textView);
        studentStopName = view.findViewById(R.id.student_stop_textView);
        database = FirebaseDatabase.getInstance();
        studentRef = database.getReference("Student");
        routeRef = database.getReference("Route");
        stopRef = database.getReference("Stop");
    }

    public void updatePhone(View view){
        phoneText.setText(popupEditTextPhone.getText());
        dialogPopup.cancel();
    }

    public void updatePassword(View view){
        Intent updatePassword = new Intent(this.getContext(), parentForgetPassword.class);
        startActivity(updatePassword);
    }

    public void editPhone(View view){
        try{
            updatePhone = new AlertDialog.Builder(getContext());
            final View popupView = getLayoutInflater().inflate(R.layout.popup_edit_phone_parent, null);
            popupEditTextPhone = popupView.findViewById(R.id.editText_parent_edit_phone);
            popupEditTextPhone.setText(phoneText.getText());
            btnUpdatePhone = popupView.findViewById(R.id.btn_popup_parent_edit_phone);
            popupEditTextPhone.addTextChangedListener(updatePhoneNo);
            btnUpdatePhone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updatePhone(popupView);
                }
            });
            updatePhone.setView(popupView);
            dialogPopup = updatePhone.create();
            dialogPopup.show();
        }catch(Exception e){}
    }

    private TextWatcher updatePhoneNo = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            try{
                if(popupEditTextPhone.length() < 11){
                    popupEditTextPhone.setError("Phone Number must be of 11 digits!");
                }else{
                    btnUpdatePhone.setEnabled(true);
                }

            }catch(Exception e){}
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
}
