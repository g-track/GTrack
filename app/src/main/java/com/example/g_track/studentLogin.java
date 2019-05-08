package com.example.g_track;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class studentLogin extends AppCompatActivity {
    private EditText studentId;
    private EditText studentPassword;
    private Button btnStudentLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_login);

        studentId = findViewById(R.id.editText_student_id);
        studentPassword = findViewById(R.id.editText_student_password);
        btnStudentLogin = findViewById(R.id.student_btn_login);

        studentId.addTextChangedListener(loginTextWatcher);
        studentPassword.addTextChangedListener(loginTextWatcher);
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
                    studentPassword.setError("Password Must be of at least 8 characher!");
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
        Toast.makeText(this, "Login Successfull!", Toast.LENGTH_LONG).show();

        Intent studentHomePage = new Intent(getApplicationContext(), studentHome.class);
        startActivity(studentHomePage);
    }
}
