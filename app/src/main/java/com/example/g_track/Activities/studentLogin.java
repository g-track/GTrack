package com.example.g_track.Activities;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.g_track.R;
import com.google.firebase.auth.FirebaseAuth;

public class studentLogin extends AppCompatActivity {
    private EditText studentId;
    private EditText studentPassword;
    private Button btnStudentLogin;
    private FirebaseAuth mAuth;
    String email,password;

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
       // mAuth = FirebaseAuth.getInstance();
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
       // Toast.makeText(this, "Login Successfull!", Toast.LENGTH_LONG).show();
        email = studentId.getText().toString();
        password = studentPassword.getText().toString();

     /*   mAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                           // Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();

                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                           // Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(getApplicationContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }

                        // ...
                    }
                });*/

        Intent studentHomePage = new Intent(getApplicationContext(), studentHome.class);
        startActivity(studentHomePage);
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
        finish();
    }
}
