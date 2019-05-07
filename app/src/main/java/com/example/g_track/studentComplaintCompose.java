package com.example.g_track;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class studentComplaintCompose extends AppCompatActivity {
 
    private Button email_send_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_complaint_compose);
        initialization();
        sendEmail();
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
    }
}
