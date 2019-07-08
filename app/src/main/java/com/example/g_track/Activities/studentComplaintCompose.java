package com.example.g_track.Activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.renderscript.Sampler;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.g_track.Model.Complaint;
import com.example.g_track.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class studentComplaintCompose extends AppCompatActivity {

    private Button emailSendBtn;
    private EditText complaintSubject;
    private EditText complaintDesc;
    private Toolbar compose_toolbar;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private int count;
    Complaint complaint;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_complaint_compose);
        initialization();
        getCurrentTimeUsingDate();
        complaintSubject.addTextChangedListener(subjectTextWatcher);
        complaintSubject.addTextChangedListener(subjectTextWatcher);
        itemCounter();
        sendEmail();
        setUpToolbar();
    }

    private void setUpToolbar() {
        setSupportActionBar(compose_toolbar);
        getSupportActionBar().setTitle("Compose Email");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void sendEmail() {
        emailSendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(complaintSubject.getText().toString().equals("") || complaintDesc.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(), "Please fill all the fields", Toast.LENGTH_LONG).show();

                }else{
                    generateComplaint();
                    finishActivity();
                }

            }
        });
    }

    public static String  getCurrentTimeUsingDate() {
        Date date = new Date();
        String strDateFormat = "dd-MM-yyyy hh:mm:ss aaa";
        DateFormat dateFormat = new SimpleDateFormat(strDateFormat);
        String formattedDate= dateFormat.format(date);
        return formattedDate;
    }

    private void initialization() {
        emailSendBtn = findViewById(R.id.email_send_btn_id);
        compose_toolbar = findViewById(R.id.compose_toolbar_id);
        complaintSubject = findViewById(R.id.edtText_cmp_subject);
        complaintDesc = findViewById(R.id.edtText_cmp_desc);
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("Complaint");
    }
    @SuppressLint("ResourceType")
    public void finishActivity(){
        Intent goToHome = new Intent(getApplicationContext(), studentHome.class);
        goToHome.putExtra("TAG", "CC");
        startActivity(goToHome);
    }

    private void generateComplaint(){
        String subject = complaintSubject.getText().toString();
        String desc = complaintDesc.getText().toString();
        long studentId = 15137029;
        String time = getCurrentTimeUsingDate();
        int status = 1;
        complaint = new Complaint();
        complaint.setComplaintID(count+1);
        complaint.setComplaintSubject(subject);
        complaint.setComplaintDesc(desc);
        complaint.setResolvedStatus(status);
        complaint.setComplaintStatus(true);
        complaint.setComplaintTime(time);
        complaint.setStudentId(studentId);

        databaseReference.push().setValue(complaint);

        clearInputFields();
        //finishActivity();

    }
    private void clearInputFields(){
        complaintSubject.getText().clear();
        complaintDesc.getText().clear();
    }

    public void itemCounter(){

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot complaintSnapShot: dataSnapshot.getChildren()){
                    count++;
                }
                Log.i("Sohail", "onDataChange: "+count);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private TextWatcher subjectTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            try{
                if(complaintSubject.getText().toString().equals("")){
                    complaintSubject.setError("Please enter subject");
                }
                else if(complaintDesc.getText().toString().equals("")){
                    complaintDesc.setError("Please enter Description");
                }
            }catch(Exception e){}
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

}
