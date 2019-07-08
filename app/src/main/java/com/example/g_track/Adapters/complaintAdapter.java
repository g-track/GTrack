package com.example.g_track.Adapters;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.g_track.Activities.studentComplaintDetails;
import com.example.g_track.Model.Complaint;
import com.example.g_track.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class complaintAdapter extends RecyclerView.Adapter<complaintAdapter.complaintViewHolder> {

    List<Complaint> complaints;
    private Context mContext;

    private Complaint complaintExtra = new Complaint();

     /*
     1 == Pending
     2 == In Process
     3 == Done
     4 == Invalid
     */


    public complaintAdapter(List<Complaint> complaints, Context context) {
        this.complaints = complaints;
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
        final Complaint complaint = complaints.get(i);

        String letter;
        Log.i("SUBJECT", complaint.getComplaintSubject().toString());
        complaintViewHolder.complaintSubject.setText(complaint.getComplaintSubject());
        complaintViewHolder.complaintDesc.setText(complaint.getComplaintDesc());
        letter = complaint.getComplaintSubject();
        complaintViewHolder.complaintImg.setText(String.valueOf(letter.charAt(0)));
        if(complaint.getResolvedStatus() == 1){
            complaintViewHolder.complaintStatus.setText("Pending");
            complaintViewHolder.complaintStatus.setBackgroundResource(R.drawable.pendinground);
            complaintViewHolder.complaintStatus.setTextColor(ContextCompat.getColor(mContext, R.color.pendingColor));
            complaintViewHolder.complaintImg.setTextColor(ContextCompat.getColor(mContext, R.color.pendingColor));
        }else if(complaint.getResolvedStatus() == 2){
            complaintViewHolder.complaintStatus.setText("In Process");
            complaintViewHolder.complaintStatus.setBackgroundResource(R.drawable.inprocessround);
            complaintViewHolder.complaintStatus.setTextColor(ContextCompat.getColor(mContext, R.color.processColor));
            complaintViewHolder.complaintImg.setTextColor(ContextCompat.getColor(mContext, R.color.processColor));
        }else if(complaint.getResolvedStatus() == 3){
            complaintViewHolder.complaintStatus.setText("Resolved");
            complaintViewHolder.complaintStatus.setBackgroundResource(R.drawable.resolvedround);
            complaintViewHolder.complaintStatus.setTextColor(ContextCompat.getColor(mContext, R.color.doneColor));
            complaintViewHolder.complaintImg.setTextColor(ContextCompat.getColor(mContext, R.color.doneColor));
        }else if(complaint.getResolvedStatus() == 4){
            complaintViewHolder.complaintStatus.setText("Invalid");
            complaintViewHolder.complaintStatus.setBackgroundResource(R.drawable.invalidround);
            complaintViewHolder.complaintStatus.setTextColor(ContextCompat.getColor(mContext, R.color.invalidColor));
            complaintViewHolder.complaintImg.setTextColor(ContextCompat.getColor(mContext, R.color.invalidColor));
        }else{

        }

        complaintViewHolder.complaintTime.setText(String.valueOf(getTimeDiff(complaint.getComplaintTime())));
        complaintViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_go_to_complaintDetail = new Intent(mContext, studentComplaintDetails.class);

                intent_go_to_complaintDetail.putExtra("Complaint", complaint);

                mContext.startActivity(intent_go_to_complaintDetail);
                // Toast.makeText(mContext, "item is clicked.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return complaints.size();
    }

    public class complaintViewHolder extends RecyclerView.ViewHolder{
        public TextView complaintSubject;
        public TextView complaintDesc;
        public TextView complaintImg;
        public TextView complaintTime;
        public TextView complaintStatus;
        public complaintViewHolder(@NonNull View itemView) {
            super(itemView);
            complaintSubject = itemView.findViewById(R.id.tv_complaint_subject);
            complaintDesc = itemView.findViewById(R.id.tv_complaint_desc);
            complaintStatus = itemView.findViewById(R.id.tv_complaint_status);
            complaintTime = itemView.findViewById(R.id.tv_complaint_time);
            complaintImg = itemView.findViewById(R.id.tv_cmp_icon);
        }
    }

    public String getTimeDiff(String date){
        String time = "a moment ago";

        try
        {
            SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss aaa");
            Date past = format.parse(date);
            Date now = new Date();
            long seconds=TimeUnit.MILLISECONDS.toSeconds(now.getTime() - past.getTime());
            long minutes=TimeUnit.MILLISECONDS.toMinutes(now.getTime() - past.getTime());
            long hours=TimeUnit.MILLISECONDS.toHours(now.getTime() - past.getTime());
            long days=TimeUnit.MILLISECONDS.toDays(now.getTime() - past.getTime());

            if(seconds<60)
            {
                time = seconds + " sec";
                System.out.println(seconds+" seconds ago");
            }
            else if(minutes<60)
            {
                time = minutes + " min";
                System.out.println(minutes+" minutes ago");
            }
            else if(hours<24)
            {
                time = hours + " hr";
                System.out.println(hours+" hours ago");
            }
            else
            {
                if(days < 2){
                    time = "Yesterday";
                }
                else if(days >= 2){
                    time = date.substring(0, 10);
                }else {
                    time = "invalid";
                }
                System.out.println(days+" days ago");
            }

        }
        catch (Exception j){
            j.printStackTrace();
        }


        return time;
    }
}
