package com.example.g_track.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.g_track.Model.Student;
import com.example.g_track.Model.User;
import com.example.g_track.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class studentLogin extends AppCompatActivity {
    private EditText studentId;
    private EditText studentPassword;
    private Button btnStudentLogin;
    private String password;
    private String Id;
    private DatabaseReference studentRef;
    private FirebaseDatabase database;
    private boolean checkRegister;
    private ProgressDialog progressDialog;
    protected static final SharedPreferences settings = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_login);
        initilization();
        studentId.addTextChangedListener(loginTextWatcher);
        studentPassword.addTextChangedListener(loginTextWatcher);
    }

    private void initilization() {
        studentId = findViewById(R.id.editText_student_id);
        studentPassword = findViewById(R.id.editText_student_password);
        btnStudentLogin = findViewById(R.id.student_btn_login);
        database = FirebaseDatabase.getInstance();
        studentRef = database.getReference("student");
    }

    private TextWatcher loginTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            try{
                if(studentId.length() < 8){
                    studentId.setError("ID must be of 8 character!");
                }else if(studentPassword.getText().length()<8){
                    studentPassword.setError("Password Must be of at least 8 character!");
                }else{
                    btnStudentLogin.setEnabled(true);
                }
            }catch(Exception e){}
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };


    public void studentLogin(View view){
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();
        checkRegister = false;
        Id = studentId.getText().toString();
        password = studentPassword.getText().toString();
        studentRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               for (DataSnapshot studentSnapshot : dataSnapshot.getChildren()){
                   Student student = studentSnapshot.getValue(Student.class);
                   if (student.getStudentID() == Integer.valueOf(Id) ){
                       checkRegister = true;
                       if (password.equals(student.getStudentPassword())){
                           Intent studentHomePage = new Intent(getApplicationContext(), studentHome.class);
                           studentHomePage.putExtra("userType","Student");
                           startActivity(studentHomePage);
                           User user = new User(studentLogin.this);
                           user.setUserId(Id);
                           user.setUserType("Student");
                           Toast.makeText(studentLogin.this, "Your are Login Successfully", Toast.LENGTH_SHORT).show();
                       }
                       else {
                           Toast.makeText(studentLogin.this, "Your Password Is Wrong.Please try Again", Toast.LENGTH_SHORT).show();
                       }
                   }
                }
               if (checkRegister==false){
                   Toast.makeText(studentLogin.this, "Your are not Register.Please register her/him before Login", Toast.LENGTH_SHORT).show();
               }
               studentId.setText("");
               studentPassword.setText("");
               progressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    @Override
    public void onBackPressed() {
        finishAffinity();
        finish();
    }
}
