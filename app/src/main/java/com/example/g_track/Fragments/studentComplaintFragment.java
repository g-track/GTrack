package com.example.g_track.Fragments;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.g_track.Model.Complaint;
import com.example.g_track.R;
import com.example.g_track.Adapters.complaintAdapter;
import com.example.g_track.Activities.studentComplaintCompose;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class studentComplaintFragment extends Fragment {
    public studentComplaintFragment() {
        // Required empty public constructor
    }

    private FloatingActionButton compose_floating_btn;
    private RecyclerView recyclerView;
    private List<Complaint> complaintList;
    DatabaseReference databaseReference;
    private ProgressDialog progressDialog;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_student_complaint, container, false);
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
        databaseReference = FirebaseDatabase.getInstance().getReference("complaint");
        complaintList = new ArrayList<>();
        compose_floating_btn = view.findViewById(R.id.compose_btn_id);
    }

    public void setUpRecyclerView(View view){
        recyclerView = view.findViewById(R.id.recyclerView_id);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
       // recyclerView.setAdapter(new complaintAdapter(subject,desc, time, status, getContext()));
    }


    @Override
    public void onStart() {
        super.onStart();
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                complaintList.clear();
                for(DataSnapshot complaintSnapShot: dataSnapshot.getChildren()){
                    Complaint complaint = complaintSnapShot.getValue(Complaint.class);
                    if(complaint.getComplaintStatus()){
                        complaintList.add(complaint);
                    }

                }
                progressDialog.dismiss();
                if(complaintList.isEmpty()){
                    Toast.makeText(getContext(),  "No Complaints Found", Toast.LENGTH_LONG).show();
                }
                complaintAdapter adapter = new complaintAdapter(complaintList, getContext());
                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}
