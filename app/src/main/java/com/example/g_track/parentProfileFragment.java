package com.example.g_track;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class parentProfileFragment extends Fragment {
    private CircleImageView parentProfileImage;
    private Button btnParentUpdateProfile;


    public parentProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_parent_profile, container, false);
        initialization(view);
        return view;
    }


    private void initialization(View view) {
        parentProfileImage = view.findViewById(R.id.parent_profile_image);
        btnParentUpdateProfile = view.findViewById(R.id.btn_parent_update_info);
    }

}
