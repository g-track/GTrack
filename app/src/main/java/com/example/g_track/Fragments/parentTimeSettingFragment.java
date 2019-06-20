package com.example.g_track.Fragments;


import android.graphics.Color;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.example.g_track.R;

import static android.R.layout.simple_spinner_dropdown_item;
import static android.R.layout.simple_spinner_item;


/**
 * A simple {@link Fragment} subclass.
 */
public class parentTimeSettingFragment extends Fragment {
    private Spinner timeSpinner;
    private Switch OffOnAlert_btn;
    private ConstraintLayout alert_time_set_layout;


    public parentTimeSettingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       // return inflater.inflate(R.layout.fragment_parent_time_setting, container, false);

        View view = inflater.inflate(R.layout.fragment_parent_time_setting, container, false);
        initialization(view);
        setSpinner();
        setColorOfSelectedItem();
        setOfOnAlert();
        return view;
    }

    private void setOfOnAlert() {
        OffOnAlert_btn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    alert_time_set_layout.setVisibility(ConstraintLayout.VISIBLE);
                    OffOnAlert_btn.setText("ON");
                }else {
                    alert_time_set_layout.setVisibility(ConstraintLayout.INVISIBLE);
                    OffOnAlert_btn.setText("OFF");
                }
            }
        });
    }

    private void setSpinner() {
        String[] time = {"5 minutes","10 minutes","20 minutes","30 minutes","45 minutes","1 hour"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),simple_spinner_item,time);
        adapter.setDropDownViewResource(simple_spinner_dropdown_item);
        timeSpinner.setAdapter(adapter);
    }

    private void initialization(View view) {
        timeSpinner = view.findViewById(R.id.parent_timeSpinner_id);
        OffOnAlert_btn = view.findViewById(R.id.parent_switch_id);
        alert_time_set_layout = view.findViewById(R.id.parent_time_set_layout_id);
    }

    private void setColorOfSelectedItem(){
        timeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) view).setTextColor(Color.WHITE); //Change selected text color
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

}
