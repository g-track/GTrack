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

public class parentLogin extends AppCompatActivity {
    private EditText parentUsername;
    private EditText parentPassword;
    private Button btnParentLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_login);

        parentUsername = findViewById(R.id.editText_parent_username);
        parentPassword = findViewById(R.id.editText_parent_password);
        btnParentLogin = findViewById(R.id.parent_btn_login);

        parentUsername.addTextChangedListener(loginTextWatcher);
        parentPassword.addTextChangedListener(loginTextWatcher);
    }



    private TextWatcher loginTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            try{
                if(parentUsername.length() < 3){
                    parentUsername.setError("Username must be of 3 character!");
                }else if(parentPassword.getText().length()<8){
                    parentPassword.setError("Password Must be of 8 characher!");
                }else{
                    btnParentLogin.setEnabled(true);
                }
            }catch(Exception e){}
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };


    public void parentLogin(View view){
        Toast.makeText(this, "Login Successfull!", Toast.LENGTH_LONG).show();

        Intent parentHomePage = new Intent(getApplicationContext(), parentHome.class);
        startActivity(parentHomePage);
    }
}
