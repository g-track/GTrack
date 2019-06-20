package com.example.g_track.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.g_track.R;

public class parentForgetPassword extends AppCompatActivity {
    private EditText editTextcode;
    private Button btnNext;
    private TextView textLine;
    private EditText newPassword;
    private EditText confirmPassword;
    private Button btnResetPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_forget_password);

        editTextcode = findViewById(R.id.parent_fP_code);
        btnNext = findViewById(R.id.btn_parent_next);
        textLine = findViewById(R.id.textView8);
        newPassword = findViewById(R.id.parent_newPassword);
        confirmPassword = findViewById(R.id.parent_confirmPassword);
        btnResetPassword = findViewById(R.id.btn_parent_resetPassword);


        editTextcode.addTextChangedListener(forgetPasswordCode);

    }

    private TextWatcher forgetPasswordCode = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            try{
                if(editTextcode.length() < 4){
                    editTextcode.setError("Code must be of 4 digits!");
                }else{
                    btnNext.setEnabled(true);
                }
            }catch(Exception e){}
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    public void showPasswordField(View view){
        editTextcode.setVisibility(View.INVISIBLE);
        btnNext.setVisibility(View.INVISIBLE);
        textLine.setVisibility(View.INVISIBLE);

        newPassword.setVisibility(View.VISIBLE);
        confirmPassword.setVisibility(View.VISIBLE);
        btnResetPassword.setVisibility(View.VISIBLE);

    }
}
