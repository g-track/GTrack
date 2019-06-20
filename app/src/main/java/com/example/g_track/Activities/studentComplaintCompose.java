package com.example.g_track.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.g_track.R;

public class studentComplaintCompose extends AppCompatActivity {
 
    private Button email_send_btn;
    private Toolbar compose_toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_complaint_compose);
        initialization();
        sendEmail();
        setUpToolbar();
    }

    private void setUpToolbar() {
        setSupportActionBar(compose_toolbar);
        getSupportActionBar().setTitle("Compose Email");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void sendEmail() {
        email_send_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(studentComplaintCompose.this, "Your Email is sent to Transport Manager:", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initialization() {
        email_send_btn = findViewById(R.id.email_send_btn_id);
        compose_toolbar = findViewById(R.id.compose_toolbar_id);
    }
}
