package com.example.g_track;


import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class studentProfileFragment extends Fragment {
    private CircleImageView studentProfileImage;
    private ImageView btnEditPhone;
    private TextView phoneText;
    private EditText popupEditTextPhone;
    private AlertDialog.Builder updatePhone;
    private AlertDialog dialogPopup;
    private Button btnUpdatePhone;


    public studentProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_student_profile, container, false);

        initialization(view);
        btnEditPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editPhone(v);
            }
        });

        return view;
    }

    private void initialization(View view) {
        studentProfileImage = view.findViewById(R.id.student_profile_image);
        btnEditPhone = view.findViewById(R.id.btn_student_phone_edit);
        phoneText = view.findViewById(R.id.student_phoneNo_textView);

    }

    public void updatePhone(View view){

        phoneText.setText(popupEditTextPhone.getText());
        dialogPopup.cancel();


    }

    public void editPhone(View view){
        try{
            updatePhone = new AlertDialog.Builder(getContext());
            final View popupView = getLayoutInflater().inflate(R.layout.popup_edit_phone_parent, null);

            popupEditTextPhone = popupView.findViewById(R.id.editText_parent_edit_phone);
            popupEditTextPhone.setText(phoneText.getText());
            btnUpdatePhone = popupView.findViewById(R.id.btn_popup_parent_edit_phone);
            popupEditTextPhone.addTextChangedListener(updatePhoneNo);

            btnUpdatePhone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updatePhone(popupView);
                }
            });

            updatePhone.setView(popupView);
            dialogPopup = updatePhone.create();
            dialogPopup.show();
        }catch(Exception e){}

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
