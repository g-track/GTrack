package com.example.g_track;


import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
public class parentProfileFragment extends Fragment {
    private CircleImageView parentProfileImage;
    private ImageView btnEditPhone;
    private TextView phoneText;



    public parentProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_parent_profile, container, false);
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
        parentProfileImage = view.findViewById(R.id.parent_profile_image);
        btnEditPhone = view.findViewById(R.id.btn_parent_phone_edit);
        phoneText = view.findViewById(R.id.parent_phone_textView);
    }

    public void updatePhone(View view){
        Button btnUpdatePhone = view.findViewById(R.id.btn_popup_parent_edit_phone);

    }

    public void editPhone(View view){
        try{
            AlertDialog.Builder updatePhone = new AlertDialog.Builder(getContext());
            final View popupView = getLayoutInflater().inflate(R.layout.popup_edit_phone_parent, null);

            EditText phoneNo = popupView.findViewById(R.id.editText_parent_edit_phone);
            phoneNo.setText(phoneText.getText());
            Button btnUpdatePhone = popupView.findViewById(R.id.btn_popup_parent_edit_phone);

            btnUpdatePhone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updatePhone(popupView);
                }
            });

            updatePhone.setView(popupView);
            AlertDialog dialogPopup = updatePhone.create();
            dialogPopup.show();
        }catch(Exception e){}

    }

}
