package com.example.g_track.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.g_track.Model.Parent;
import com.example.g_track.Model.Student;
import com.example.g_track.Model.User;
import com.example.g_track.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class changePassword extends AppCompatActivity {
    private EditText oldPassEdt,newPassEdt,confirmPassEdt;
    private Button ressetPassBtn;
    private DatabaseReference studentRef, parentRef;
    private FirebaseDatabase database;
    private String studentKey, parentKey;
    private String userType;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        initialization();
        userType = getIntent().getStringExtra("userType");
        ressetPassBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userType.equals("Student")){
                    changeStudentPassword();
                }else if (userType.equals("Parent")){
                    changeParentPassword();
                }
            }
        });

    }

    private void changeParentPassword() {
        final String oldPass = oldPassEdt.getText().toString();
        final String newPass = newPassEdt.getText().toString();
        final String confirmPass = confirmPassEdt.getText().toString();

        User user = new User(changePassword.this);
        final String name = user.getUserId();

        parentRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot parentSnapshot : dataSnapshot.getChildren()){
                    Parent parent = parentSnapshot.getValue(Parent.class);
                    if (parent.getParentName().equals(name)){
                        parentKey = parentSnapshot.getKey();
                        if (oldPass.equals(parent.getParentPassword())){
                            if ((newPass.equals(confirmPass))&&(!newPass.isEmpty())){
                                parentRef.child(parentKey).child("parentPassword").setValue(newPass);
                                Intent intent = new Intent(changePassword.this,parentLogin.class);
                                intent.putExtra("userType","Parent");
                                startActivity(intent);
                                Toast.makeText(changePassword.this, "your password is reset Successfully", Toast.LENGTH_SHORT).show();

                            }else {
                                Toast.makeText(changePassword.this, "Confirm Password does not matched with new Password.Try again", Toast.LENGTH_SHORT).show();
                            }

                        }else {
                            Toast.makeText(changePassword.this, "Old password is Wrong, Please try again.", Toast.LENGTH_SHORT).show();
                        }

                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        oldPassEdt.setText("");
        newPassEdt.setText("");
        confirmPassEdt.setText("");
    }


    private void changeStudentPassword() {
        final String oldPass = oldPassEdt.getText().toString();
        final String newPass = newPassEdt.getText().toString();
        final String confirmPass = confirmPassEdt.getText().toString();

        User user = new User(changePassword.this);
        final String id = user.getUserId();

        studentRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot studentSnapshot : dataSnapshot.getChildren()){
                    Student student = studentSnapshot.getValue(Student.class);
                    if (student.getStudentID()== Integer.valueOf(id)){
                        studentKey = studentSnapshot.getKey();
                        if (oldPass.equals(student.getStudentPassword())){
                            if ((newPass.equals(confirmPass))&&(!newPass.isEmpty())){
                                studentRef.child(studentKey).child("studentPassword").setValue(newPass);
                                Intent intent = new Intent(changePassword.this,studentLogin.class);
                                startActivity(intent);
                                finish();
                                Toast.makeText(changePassword.this, "your password is reset Successfully", Toast.LENGTH_SHORT).show();

                            }else {
                                Toast.makeText(changePassword.this, "Confirm Password does not matched with new Password.Try again", Toast.LENGTH_SHORT).show();
                            }

                        }else {
                            Toast.makeText(changePassword.this, "Old password is Wrong, Please try again.", Toast.LENGTH_SHORT).show();
                        }

                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        oldPassEdt.setText("");
        newPassEdt.setText("");
        confirmPassEdt.setText("");

    }

    private void initialization() {
        oldPassEdt = findViewById(R.id.oldpass_id);
        newPassEdt = findViewById(R.id.newpass_id);
        confirmPassEdt = findViewById(R.id.confirmpass_id);
        ressetPassBtn = findViewById(R.id.resetpass_id);
        database = FirebaseDatabase.getInstance();
        studentRef = database.getReference("student");
        parentRef = database.getReference("parent");
    }
}
