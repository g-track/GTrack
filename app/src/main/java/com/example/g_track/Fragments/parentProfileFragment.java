package com.example.g_track.Fragments;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.g_track.Activities.changePassword;
import com.example.g_track.Model.Parent;
import com.example.g_track.Model.User;
import com.example.g_track.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class parentProfileFragment extends Fragment {
    private CircleImageView parentProfileImage;
    private ImageView btnEditPhone;
    private TextView phoneText;
    private EditText popupEditTextPhone;
    private AlertDialog.Builder updatePhone;
    private AlertDialog dialogPopup;
    private Button btnUpdatePhone;
    private TextView updatePassword,parentName,parentCNIC,parentPhoneNo,parentChildID;
    private FirebaseDatabase database;
    private DatabaseReference parentRef;
    private String parentKey;
    private ProgressBar progressBar;
    private ProgressDialog progressDialog;
    public parentProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_parent_profile, container, false);
        //progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        initialization(view);
        updatePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatePassword(v);
            }
        });
        viewParentDetails();
        return view;
    }

    private void viewParentDetails() {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        final User user = new User(getContext());
        parentRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot parentSnapshot : dataSnapshot.getChildren()){
                    Parent parent = parentSnapshot.getValue(Parent.class);
                    if (parent.getChildStudentID()==Integer.valueOf(user.getUserId())){
                        parentKey = parentSnapshot.getKey();
                        parentName.setText(parent.getParentName());
                        parentCNIC.setText(parent.getParentCNIC());
                        phoneText.setText(parent.getParentPhoneNo());
                        parentChildID.setText(String.valueOf(parent.getChildStudentID()));
                        progressDialog.dismiss();
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void updatePassword(View view){
        Intent updatePassword = new Intent(this.getContext(), changePassword.class);
        updatePassword.putExtra("userType","Parent");
        startActivity(updatePassword);
    }


    private void initialization(View view) {
        parentProfileImage = view.findViewById(R.id.parent_profile_image);
        phoneText = view.findViewById(R.id.parent_phoneNo_textView);
        updatePassword = view.findViewById(R.id.parent_updatePassword_textView);
        parentName = view.findViewById(R.id.parent_login_Text);
        parentCNIC = view.findViewById(R.id.parent_cnicNo_textView);
        parentChildID = view.findViewById(R.id.parent_childid_textView);
        database = FirebaseDatabase.getInstance();
        parentRef = database.getReference("parent");
        //progressBar = view.findViewById(R.id.parent_profile_spin_kit);
    }

    private TextWatcher updatePhoneNo = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            try{
                if(popupEditTextPhone.length() < 11){
                    popupEditTextPhone.setError("Phone Number must be of 11 digits!");
                }else{
                    btnUpdatePhone.setEnabled(true);
                }

            }catch(Exception e){}
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
}
