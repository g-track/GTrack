package com.example.g_track.Adapters;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.g_track.Activities.studentComplaintDetails;
import com.example.g_track.R;

public class complaintAdapter extends RecyclerView.Adapter<complaintAdapter.complaintViewHolder> {

     String[] data;
     Context mContext;

    public complaintAdapter(String[] data,Context context) {
        this.data = data;
        this.mContext = context;
    }

    @NonNull
    @Override
    public complaintViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.complaint_item,viewGroup,false);
        complaintViewHolder complaintViewHolder = new complaintViewHolder(view);
        return complaintViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull complaintViewHolder complaintViewHolder, int i) {
        String Subject = data[i];
        complaintViewHolder.complaintSubject.setText(Subject);
        complaintViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 Intent intent_go_to_complaintDetail = new Intent(mContext, studentComplaintDetails.class);
                 mContext.startActivity(intent_go_to_complaintDetail);
               // Toast.makeText(mContext, "item is clicked.", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return data.length;
    }

    public class complaintViewHolder extends RecyclerView.ViewHolder{
        public TextView complaintSubject;
        public TextView complaintDesc;
        public ImageView complaintImg;
        public complaintViewHolder(@NonNull View itemView) {
            super(itemView);
            complaintSubject = itemView.findViewById(R.id.complain_subject_txt_id);
            complaintDesc = itemView.findViewById(R.id.complaint_desc_txt_id);
            complaintImg = itemView.findViewById(R.id.complaint_icon_img);
        }
    }
}
