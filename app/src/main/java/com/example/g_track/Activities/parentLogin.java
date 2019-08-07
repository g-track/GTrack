package com.example.g_track.Activities;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.g_track.Model.Parent;
import com.example.g_track.Model.User;
import com.example.g_track.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;

import static android.Manifest.permission.SEND_SMS;

public class parentLogin extends AppCompatActivity {
    private EditText parentUsername;
    private EditText parentPassword;
    private Button btnParentLogin;
    private DatabaseReference parentRef;
    private FirebaseDatabase database;
    private boolean checkParent;
    private BroadcastReceiver sendStatusReceiver,deliveredStatusReceiver;

    private static final int REQUEST_SMS =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_login);

        parentUsername = findViewById(R.id.editText_parent_username);
        parentPassword = findViewById(R.id.editText_parent_password);
        btnParentLogin = findViewById(R.id.parent_btn_login);
        database = FirebaseDatabase.getInstance();
        parentRef = database.getReference("parent");

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
                    parentPassword.setError("Password Must be of 8 character!");
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
        checkParent = false;
        final String childId = parentUsername.getText().toString();
        final String password = parentPassword.getText().toString();

        parentRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot parentSnapshot : dataSnapshot.getChildren()){
                    Parent parent = parentSnapshot.getValue(Parent.class);
                    String id = String.valueOf(parent.getChildStudentID());
                    if (id.equals(childId)){
                        checkParent = true;
                        if (parent.getParentPassword().equals(password)){
                            Intent intent = new Intent(getApplicationContext(),parentHome.class);
                            intent.putExtra("userType","Parent");
                            startActivity(intent);
                            User user = new User(parentLogin.this);
                            user.setUserId(id);
                            user.setUserType("Parent");
                            Toast.makeText(parentLogin.this, "You are login Successfully.", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(parentLogin.this, "Password Wrong. Please try Again.", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                if (checkParent==false){
                    Toast.makeText(parentLogin.this, "Your are not register. Please register him/her before login", Toast.LENGTH_SHORT).show();
                }

                parentUsername.setText("");
                parentPassword.setText("");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void forgetPassword(View view) throws Exception {
        Toast.makeText(this, "Forget Password!", Toast.LENGTH_LONG).show();
        Random random = new Random();
        String id = String.format("%04d", random.nextInt(10000));
        Intent parentForgetPass = new Intent(getApplicationContext(), parentForgetPassword.class);
        parentForgetPass.putExtra("code",id);
        //Toast.makeText(this, id, Toast.LENGTH_SHORT).show();
        startActivity(parentForgetPass);
        finish();
       // getSMSPermission()
        // checkForSmsPermission();

       /* if (checkPermission(SEND_SMS)){

        }else {
            ActivityCompat.requestPermissions(this,new String[]{SEND_SMS},REQUEST_SMS);
        }
*/
       // SendMySMS();
/*
        MailSender sender = new MailSender( "15137027@gift.edu.pk",   "shahid3535");
        sender.sendMail("" + "OPT CODE",
                 "Sohail Jhakhar",
                "sohailm816@gmail.com" + "", "WeMeal Team",
                 "sohailm816@gmail.com"); //email list send comma separated string like ("a@gmail.com,b@dahs.com")*/
    }

    public void register(View view){
        Intent intent = new Intent(parentLogin.this,parentRegister.class);
        startActivity(intent);
        finish();

    }



    public boolean checkPermission(String permission) {
        int check = ContextCompat.checkSelfPermission(this,permission);
        return (check==PackageManager.PERMISSION_GRANTED);
    }



    private void SendMySMS() {
        if (checkPermission(SEND_SMS)) {
            String phone = "03427565599";
            String message = "3001 ";

            SmsManager sms = SmsManager.getDefault();
            PendingIntent sentIntent = PendingIntent.getBroadcast(this, 0, new Intent("SMS_SEND"), 0);
            PendingIntent deliveredIntent = PendingIntent.getBroadcast(this, 0, new Intent("SMS_DELIVERED"), 0);
            sms.sendTextMessage(phone, null, message,sentIntent, deliveredIntent);
            Toast.makeText(this, "Message is send Successfully.", Toast.LENGTH_SHORT).show();
        }else {
           // ActivityCompat.requestPermissions(this,new String[]{SEND_SMS}, REQUEST_SMS);
            Toast.makeText(this, "Permission is Denied", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_SMS:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Permission is granted, Now you can access SMS.", Toast.LENGTH_SHORT).show();
                    SendMySMS();
                } else {
                    Toast.makeText(this, "Permission is Denied, and You cannot access SMS.", Toast.LENGTH_SHORT).show();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        requestPermissions(new String[]{SEND_SMS},
                                REQUEST_SMS);
                    }
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    protected void onResume() {
        sendStatusReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String s ="Unknown Error";
                switch (getResultCode()){
                    case RESULT_OK:
                        s = "Message is sent Successfully";
                        break;
                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                        s = "Generic Failure Error.";
                        break;
                     case SmsManager.RESULT_ERROR_NO_SERVICE:
                         s = "Error : No Services Available.";
                         break;
                    case SmsManager.RESULT_ERROR_NULL_PDU:
                        s = "Error : Null PDU.";
                        break;
                    case SmsManager.RESULT_ERROR_RADIO_OFF:
                        s = "Error : Radio is off.";
                        break;
                     default:
                         break;
            }
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
        }

        };
        deliveredStatusReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String s = "Message is not delivered.";
                switch (getResultCode()){
                    case RESULT_OK:
                        s = "Message is sent Successfully.";
                        break;
                    case RESULT_CANCELED:
                        break;
                }
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
            }
        };
        registerReceiver(sendStatusReceiver,new IntentFilter("SMS_SENT"));
        registerReceiver(deliveredStatusReceiver,new IntentFilter("SMS_DELIVERED"));
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(sendStatusReceiver);
        unregisterReceiver(deliveredStatusReceiver);
    }
}
