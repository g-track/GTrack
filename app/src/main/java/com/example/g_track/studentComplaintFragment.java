package com.example.g_track;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


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
             "Complaint 7","Complaint 8","Complaint 9","Complaint 10","Complaint 11","Complaint 12","Complaint 12"};
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
                Intent intent_go_to_compose = new Intent(getContext(),studentComplaintCompose.class);
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
        recyclerView.setAdapter(new complaintAdapter(subject,getContext()));
    }

}
