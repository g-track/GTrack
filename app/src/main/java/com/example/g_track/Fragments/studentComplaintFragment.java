package com.example.g_track.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.g_track.R;
import com.example.g_track.Adapters.complaintAdapter;
import com.example.g_track.Activities.studentComplaintCompose;


/**
 * A simple {@link Fragment} subclass.
 */
public class studentComplaintFragment extends Fragment {
    public studentComplaintFragment() {
        // Required empty public constructor
    }

    private FloatingActionButton compose_floating_btn;
    private RecyclerView recyclerView;
    String[] subject = {"Complaint 1","Complaint 2","Complaint 3","Complaint 4","Complaint 5","Complaint 6",
             "Complaint 7","Complaint 8","Complaint 9","Complaint 10"};
    String[] desc = {"The cards are drawn to the screen with a default elevation, which causes the system to draw a shadow underneath them.",
            "The cards are drawn to the screen with a default elevation, which causes the system to draw a shadow underneath them.",
            "The cards are drawn to the screen with a default elevation, which causes the system to draw a shadow underneath them.",
            "The cards are drawn to the screen with a default elevation, which causes the system to draw a shadow underneath them.",
            "The cards are drawn to the screen with a default elevation, which causes the system to draw a shadow underneath them.",
            "The cards are drawn to the screen with a default elevation, which causes the system to draw a shadow underneath them.",
            "The cards are drawn to the screen with a default elevation, which causes the system to draw a shadow underneath them.",
            "The cards are drawn to the screen with a default elevation, which causes the system to draw a shadow underneath them.",
            "The cards are drawn to the screen with a default elevation, which causes the system to draw a shadow underneath them.",
            "The cards are drawn to the screen with a default elevation, which causes the system to draw a shadow underneath them."};
    String[] time = {"2h","3h","1-1-2019","2-4-2019","1h","5h",
            "10h","5h","7h","25h"};
    String[] status = {"Pending","Aknowledged","Processing","Done","Invalid","Pending",
            "In Process","Pending","Done", "Done"};
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_student_complaint, container, false);
        initialization(view);
        setUpRecyclerView(view);
        goToComposePage();
        return view;
    }

    private void goToComposePage() {
        compose_floating_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_go_to_compose = new Intent(getContext(), studentComplaintCompose.class);
                startActivity(intent_go_to_compose);
            }
        });
    }

    private void initialization(View view) {
        compose_floating_btn = view.findViewById(R.id.compose_btn_id);
    }

    public void setUpRecyclerView(View view){
        recyclerView = view.findViewById(R.id.recyclerView_id);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new complaintAdapter(subject,desc, time, status, getContext()));
    }

}
