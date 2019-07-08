package com.example.g_track.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.g_track.Model.Complaint;
import com.example.g_track.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class studentComplaintCompose extends AppCompatActivity {

    private Button emailSendBtn;
    private EditText complaintSubject;
    private EditText complaintDesc;
    private Toolbar compose_toolbar;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_complaint_compose);
        initialization();
        complaintSubject.addTextChangedListener(subjectTextWatcher);
        complaintSubject.addTextChangedListener(subjectTextWatcher);
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
                if(complaintSubject.getText().toString().equals("") && complaintDesc.getText().toString().equals("")){
                }else{
                    generateComplaint();
                }
            }
        });
    }

    private void initialization() {
        emailSendBtn = findViewById(R.id.email_send_btn_id);
        compose_toolbar = findViewById(R.id.compose_toolbar_id);
        complaintSubject = findViewById(R.id.edtText_cmp_subject);
        complaintDesc = findViewById(R.id.edtText_cmp_desc);
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("Complaint");
    }

    private void generateComplaint(){
        String subject = complaintSubject.getText().toString();
        String desc = complaintDesc.getText().toString();
        long studentId = 15137029;
        long time = 12343211;
        int status = 1;
        boolean dataStatus = true;
        int complaintId = 2;

        Complaint complaint = new Complaint();
        complaint.setComplaintID(complaintId);
        complaint.setComplaintSubject(subject);
        complaint.setComplaintDesc(desc);
        complaint.setResolvedStatus(status);
        complaint.setComplaintStatus(dataStatus);
        complaint.setComplaintTime(time);
        complaint.setStudentId(studentId);

        databaseReference.push().setValue(complaint);
        clearInputFields();

    }
    private void clearInputFields(){
        complaintSubject.getText().clear();
        complaintDesc.getText().clear();
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
                if(complaintDesc.getText().toString().equals("")){
                    complaintDesc.setError("Please enter Description");
                }
            }catch(Exception e){}
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // todo: goto back activity from here

                super.onBackPressed();
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
