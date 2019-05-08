package com.example.g_track;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class studentUpdateStopFragment extends Fragment {


    private Spinner spinnerStopLsit;
    public studentUpdateStopFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_student_update_stop, container, false);
        initialization(view);
        setStopList();
        setColorOfSelectedItem();
        return view;
    }

    private void setStopList() {
        String[] stopList = {"stop 1","stop 2","stop 3","stop 4","stop 5","stop 6","stop 7"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_dropdown_item,stopList);
        spinnerStopLsit.setAdapter(adapter);
    }

    private void initialization(View view) {
        spinnerStopLsit = view.findViewById(R.id.spinnerStopList_id);
    }

    private void setColorOfSelectedItem(){
        spinnerStopLsit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
