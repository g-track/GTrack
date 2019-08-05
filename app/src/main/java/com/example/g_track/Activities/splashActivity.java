package com.example.g_track.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.g_track.Model.User;
import com.example.g_track.R;

public class splashActivity extends AppCompatActivity {
    private String status;
    private ConstraintLayout constraintLayout;
    private Button student,parent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        constraintLayout = findViewById(R.id.userTypeLayout);

        User user = new User(splashActivity.this);
        if (user.getUserId() != null) {
            if (user.getUserType().equals("Student")) {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(splashActivity.this, studentHome.class);
                        startActivity(intent);
                        finish();
                    }
                }, 1500);

            } else if (user.getUserType().equals("Parent")) {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(splashActivity.this, parentHome.class);
                        startActivity(intent);
                        finish();
                    }
                }, 1500);
            } else {
                constraintLayout.setVisibility(View.VISIBLE);
            }
        }


            student = findViewById(R.id.student_btn_id);
            parent = findViewById(R.id.parent_btn_id);

            student.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent1 = new Intent(splashActivity.this, studentLogin.class);
                    startActivity(intent1);
                    finish();
                }
            });

            parent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent2 = new Intent(splashActivity.this, parentLogin.class);
                    startActivity(intent2);
                    finish();
                }
            });



    }
}
