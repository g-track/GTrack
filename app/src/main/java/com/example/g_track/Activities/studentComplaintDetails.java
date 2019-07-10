package com.example.g_track.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.g_track.Model.Complaint;
import com.example.g_track.Model.Student;
import com.example.g_track.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class studentComplaintDetails extends AppCompatActivity {
    private Toolbar mToolbar;
    private TextView complaintSubject;
    private TextView complaintDesc;
    private TextView resolvedStatus;
    private TextView complaintTime;
    private TextView studentName;
    private ImageView statusImg;

    private Complaint complaintExtra;
    private Student student;
    private Complaint complaintData, complaint;
    private Student studentData;
    DatabaseReference databaseReference;
    DatabaseReference complaintReference;


    private ImageView delete_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_complaint_details);
        initialization();
        setUpToolbar();
        deleteComplaint();

        complaintExtra = getIntent().getExtras().getParcelable("Complaint");

    }

    private void initialization() {
        mToolbar = findViewById(R.id.toolbar_complaintDetail_id);
        databaseReference = FirebaseDatabase.getInstance().getReference("student");
        complaintReference = FirebaseDatabase.getInstance().getReference("complaint");
        student = new Student();
        complaintSubject = findViewById(R.id.tv_cmp_subject);
        complaintDesc = findViewById(R.id.tv_cmp_details);
        resolvedStatus = findViewById(R.id.tv_cmp_status);
        complaintTime = findViewById(R.id.tv_cmp_time);
        studentName = findViewById(R.id.tv_cmp_userName);
        delete_btn = findViewById(R.id.img_cmp_delete);
        statusImg = findViewById(R.id.img_cmp_status);
    }

    private void deleteComplaint() {
        delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                complaintReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot complaintSnapShot: dataSnapshot.getChildren()){
                            complaintData = complaintSnapShot.getValue(Complaint.class);
                            if(complaintData.getComplaintID() == complaintExtra.getComplaintID()){
                                complaint = complaintData;
                                String complaintKey = complaintSnapShot.getKey();
                                complaintReference.child(complaintKey).child("complaintStatus").setValue(false);
                            }
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                Intent complaintFragment = new Intent(getApplicationContext(), studentHome.class);
                complaintFragment.putExtra("TAG", "CC");
                startActivity(complaintFragment);
                //Toast.makeText(studentComplaintDetails.this, "Delete Icon is Clicked.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setDetails(){
        complaintSubject.setText(complaintExtra.getComplaintSubject());
        complaintDesc.setText(complaintExtra.getComplaintDesc());
        complaintTime.setText(String.valueOf(complaintExtra.getComplaintTime()));
        if(complaintExtra.getResolvedStatus() == 1){
            resolvedStatus.setText("Pending");
            resolvedStatus.setTextColor(getResources().getColor(R.color.pendingColor));
            statusImg.setImageResource(R.drawable.pending);

        }else if(complaintExtra.getResolvedStatus() == 2){
            resolvedStatus.setText("In Process");
            resolvedStatus.setTextColor(getResources().getColor(R.color.processColor));
            statusImg.setImageResource(R.drawable.processing);
        }else if(complaintExtra.getResolvedStatus() == 3){
            resolvedStatus.setText("Resolved");
            resolvedStatus.setTextColor(getResources().getColor(R.color.doneColor));
            statusImg.setImageResource(R.drawable.resolved);
        }else{
            resolvedStatus.setText("Invalid");
            resolvedStatus.setTextColor(getResources().getColor(R.color.invalidColor));
            statusImg.setImageResource(R.drawable.invalid);
        }
        studentName.setText(student.getStudentName());
    }

    private void setUpToolbar() {
        setSupportActionBar(mToolbar);
        if (getSupportActionBar()!=null){
            getSupportActionBar().setTitle("Complaint Details");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot studentSnapShot: dataSnapshot.getChildren()){
                    studentData = studentSnapShot.getValue(Student.class);
                    if(studentData.getStudentID() == complaintExtra.getStudentId()){
                        student = studentData;
                    }

                }

                setDetails();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    @Override
    public void onBackPressed() {
        if(getFragmentManager().getBackStackEntryCount() == 0) {
            super.onBackPressed();
        }
        else {
            getFragmentManager().popBackStack();
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // todo: goto back activity from here

                /*Intent intent = new Intent(studentComplaintDetails.this, studentHome.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
                return true;*/
                super.onBackPressed();
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
