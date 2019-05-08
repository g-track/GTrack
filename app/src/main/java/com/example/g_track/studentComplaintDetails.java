package com.example.g_track;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class studentComplaintDetails extends AppCompatActivity {
    private Toolbar mToolbar;
    private ImageView delete_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_complaint_details);
        initialization();
        setUpToolbar();
        deleteComplaint();
    }

    private void initialization() {
        mToolbar = findViewById(R.id.toolbar_complaintDetail_id);
        delete_btn = findViewById(R.id.delete__icon_id);
    }

    private void deleteComplaint() {
        delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(studentComplaintDetails.this, "Delete Icon is Clicked.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setUpToolbar() {
        setSupportActionBar(mToolbar);
        if (getSupportActionBar()!=null){
            getSupportActionBar().setTitle("Complaint Details");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }
}
