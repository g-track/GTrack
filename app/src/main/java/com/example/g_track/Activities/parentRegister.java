package com.example.g_track.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.g_track.Activities.parentLogin;
import com.example.g_track.Model.Parent;
import com.example.g_track.Model.Student;
import com.example.g_track.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class parentRegister extends AppCompatActivity {
    private EditText parentName;
    private EditText parentPassword;
    private EditText childId;
    private EditText parentCnic;
    private EditText parentPhone;
    private Button btnRegister;
    private DatabaseReference parentRef,studentRef;
    private FirebaseDatabase database;
    private boolean checkStudent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_register);
        initialization();

    }

    private void initialization() {
        parentName = findViewById(R.id.editText_parent_name);
        parentPassword = findViewById(R.id.editText_parent_passwordR);
        childId = findViewById(R.id.editText_child_id);
        parentCnic = findViewById(R.id.editText_parent_cnic);
        parentPhone = findViewById(R.id.editText_parent_phone);
        btnRegister = findViewById(R.id.btn_signUp);

        parentName.addTextChangedListener(parentRegister);
        parentPassword.addTextChangedListener(parentRegister);
        childId.addTextChangedListener(parentRegister);
        parentCnic.addTextChangedListener(parentRegister);
        parentPhone.addTextChangedListener(parentRegister);

        database = FirebaseDatabase.getInstance();
        parentRef = database.getReference("parent");
        studentRef = database.getReference("student");


    }

    private TextWatcher parentRegister = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            try{
                if(parentName.getText().length() < 3){
                    parentName.setError("Username must be of at least 3 characters!");
                }else if(parentPassword.getText().length() < 8){
                    parentPassword.setError("Password must be of at least 8 characters!");
                }else if(childId.getText().length() < 8){
                    childId.setError("Child's id must be of 8 characters!");
                }else if(parentCnic.getText().length() < 13){
                    parentCnic.setError("CNIC must be of 13 characters!");
                }else if(parentPhone.getText().length()< 11){
                    parentPhone.setError("Phone number must be of 11 characters!");
                }else{
                    btnRegister.setEnabled(true);
                }
            }catch (Exception e){}

        }

        @Override
        public void afterTextChanged(Editable s) {
            try{
                if(parentName.getText().length() < 3){
                    parentName.setError("Username must be of at least 3 characters!");
                }else if(parentPassword.getText().length() < 8){
                    parentPassword.setError("Password must be of at least 8 characters!");
                }else if(childId.getText().length() < 8){
                    childId.setError("Child's id must be of 8 characters!");
                }else if(parentCnic.getText().length() < 13){
                    parentCnic.setError("CNIC must be of 13 characters!");
                }else if(parentPhone.getText().length()< 11){
                    parentPhone.setError("Phone number must be of 11 characters!");
                }else{
                    btnRegister.setEnabled(true);
                }
            }catch (Exception e){}
        }
    };


    public void registerParent(View view){
        checkStudent = false;
        final Parent parent = new Parent();
        final String id = childId.getText().toString();
        final String name = parentName.getText().toString();
        final String cnic = parentCnic.getText().toString();
        final String phone = parentPhone.getText().toString();
        final String password = parentPassword.getText().toString();

        studentRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot studentSnapshot : dataSnapshot.getChildren()){
                    Student student = studentSnapshot.getValue(Student.class);
                    if (student.getStudentID()==Integer.valueOf(id)){
                        checkStudent = true;
                        if (student.getFatherCNIC().equals(cnic)){
                            parent.setChildStudentID(Integer.valueOf(id));
                            parent.setParentPhoneNo(phone);
                            parent.setParentCNIC(cnic);
                            parent.setParentName(name);
                            parent.setParentPassword(password);
                            parentRef.push().setValue(parent);
                            Intent intent = new Intent(getApplicationContext(),parentLogin.class);
                            startActivity(intent);
                            Toast.makeText(parentRegister.this, "Your are register successfully.Now you need to login", Toast.LENGTH_SHORT).show();

                        }else {
                            Toast.makeText(parentRegister.this, "Sorry you can't be Register Because your CNIC is Not valid.", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                if (checkStudent == false){
                    Toast.makeText(parentRegister.this, "student does not exists with such Id", Toast.LENGTH_SHORT).show();
                }

                childId.setText("");
                parentName.setText("");
                parentCnic.setText("");
                parentPhone.setText("");
                parentPassword.setText("");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void loginActivity(View view){
        Intent parentLogin = new Intent(getApplicationContext(), parentLogin.class);
        startActivity(parentLogin);
    }
}
