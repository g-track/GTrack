package com.example.g_track;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class complaintAdapter extends RecyclerView.Adapter<complaintAdapter.complaintViewHolder> {

     String[] data;
     String[] desc;
     String[] time;
     String[] status;
     Context mContext;

    public complaintAdapter(String[] data, String[] desc, String[] time, String[] status, Context mContext) {
        this.data = data;
        this.desc = desc;
        this.time = time;
        this.status = status;
        this.mContext = mContext;
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
        String Desc= desc[i];
        String Time = time[i];
        String Status = status[i];

        complaintViewHolder.complaintSubject.setText(Subject);
        complaintViewHolder.complaintDesc.setText(Desc);
        complaintViewHolder.complaintStatus.setText(Status);
        complaintViewHolder.complaintTime.setText(Time);

        complaintViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 Intent intent_go_to_complaintDetail = new Intent(mContext,studentComplaintDetails.class);
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
        public TextView complaintTime;
        public TextView complaintStatus;
        public complaintViewHolder(@NonNull View itemView) {
            super(itemView);
            complaintSubject = itemView.findViewById(R.id.tv_complaint_subject);
            complaintDesc = itemView.findViewById(R.id.tv_complaint_desc);
            complaintStatus = itemView.findViewById(R.id.tv_complaint_status);
            complaintTime = itemView.findViewById(R.id.tv_complaint_time);
            complaintImg = itemView.findViewById(R.id.img_complaint_icon);
        }
    }
}
