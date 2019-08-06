package com.example.g_track;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.example.g_track.Activities.MyHelper;
import com.example.g_track.Activities.studentHome;
import com.example.g_track.Model.Alert;
import com.example.g_track.Model.Student;
import com.example.g_track.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AlertCheckService extends Service {
    private DatabaseReference alertRef, studentRef;
    private FirebaseDatabase  database;
    private User user;
    private Alert alert = new Alert();
    private int studentRouteId = -999;
    private MyHelper myHelper;
    private ArrayList<Integer> arrayList;
    private boolean check = true;
    private SQLiteDatabase sqLiteDatabase;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        Log.i("ALERT SERVICE", "Started");
        initialization();
        getStudentFromFirebase();


    }
    private void initialization() {
        user = new User(getApplicationContext());
        myHelper = new MyHelper(getApplicationContext());
        sqLiteDatabase = myHelper.getWritableDatabase();
        database = FirebaseDatabase.getInstance();
        studentRef = database.getReference("student");
        alertRef = database.getReference("alert");
        arrayList = new ArrayList<Integer>();
        arrayList = myHelper.allAlertId();
    }


    private void getStudentFromFirebase(){
        studentRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot studentSnapshot : dataSnapshot.getChildren()) {
                    Student student = studentSnapshot.getValue(Student.class);
                    Log.i("ALERT SERVICE", "User "+ user.getUserId());
                    if(!user.getUserId().equals("")){
                        if (student.getStudentID() == Integer.valueOf(user.getUserId())) {
                            studentRouteId = student.getStudentRouteID();
                            getAlertFromFirebase();
                            Log.i("ALERT SERVICE", "Getting Student");
                        }
                    }else{
                        stopSelf();
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private void getAlertFromFirebase(){

        alertRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot alertSnapshot : dataSnapshot.getChildren()) {
                    Alert alertSnap = alertSnapshot.getValue(Alert.class);
                    Log.i("ALERT SERVICE", "Before Alert");
                    if(studentRouteId != -999){
                        Log.i("ALERT SERVICE", "After Alert "+ alertSnap.getAlertId());
                        if (alertSnap.getRouteID() == studentRouteId) {
                            Log.i("ALERT SERVICE", "After All "+ + alertSnap.getRouteID());
                            if(!arrayList.isEmpty()){
                                check = true;

                                for(int one: arrayList){
                                    Log.i("ALERT SERVICE", "Before is  " + one);
                                    if(one == alertSnap.getAlertId()){
                                        Log.i("ALERT SERVICE", "False No Alert " + one);
                                        check = false;
                                    }
                                }
                                if(check){
                                    Log.i("ALERT SERVICE", "Sending Notification");
                                    sendNotification(alertSnap.getAlertDescription(), alertSnap.getAlertHeader());
                                    myHelper.addToDB(alertSnap.getAlertId());

                                }

                            }else{
                                myHelper.addToDB(alertSnap.getAlertId());
                                Log.i("ALERT SERVICE", "Sending Notification below");
                                sendNotification(alertSnap.getAlertDescription(), alertSnap.getAlertHeader());
                            }

                            //sendNotification(alertSnap.getAlertDescription(), alertSnap.getAlertHeader());
                        }
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private void sendNotification(String notificationDetails, String notificationTitle) {
        // Create an explicit content Intent that starts the main Activity.
        Intent notificationIntent = new Intent(getApplicationContext(), studentHome.class);
        // Construct a task stack.
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(getApplicationContext());
        // Add the main Activity to the task stack as the parent.
        stackBuilder.addParentStack(studentHome.class);
        // Push the content Intent onto the stack.
        stackBuilder.addNextIntent(notificationIntent);
        // Get a PendingIntent containing the entire back stack.
        PendingIntent notificationPendingIntent =
                stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        // Get a notification builder that's compatible with platform versions >= 4
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext());
        // Define the notification settings.
        builder.setSmallIcon(R.mipmap.schoolbus)
                // In a real app, you may want to use a library like Volley
                // to decode the Bitmap.
                .setLargeIcon(BitmapFactory.decodeResource(getResources(),
                        R.mipmap.schoolbus))
                .setColor(getResources().getColor(R.color.colorWhite))
                .setContentTitle(notificationTitle)
                .setContentText(notificationDetails)
                .setContentIntent(notificationPendingIntent);
        // Dismiss notification once the user touches it.
        builder.setAutoCancel(true);
        // Get an instance of the Notification manager
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        // Issue the notification
        mNotificationManager.notify(0, builder.build());
    }
}
