package com.example.g_track;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.preference.PreferenceManager;
import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.example.g_track.Activities.studentHome;
import com.example.g_track.Model.Bus;
import com.example.g_track.Model.Route;
import com.example.g_track.Model.Stop;
import com.example.g_track.Model.Student;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

import static java.lang.Math.acos;
import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class TrackBusService extends Service {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private static final String TIME_DEPATRURE_1 = "13:30";
    private static final String TIME_DEPATRURE_2 = "16:20";


    private DatabaseReference studentRef, routeRef, busRef, stopRef, geoRef;
    private FirebaseDatabase database;
    private double latitude = 32.20561, longitude = 74.19276;
    private double previousLatitude = 0, previousLongitude = 0, nextLatitude, nextLongitude;
    private long prTime, nextTime;
    private int stopId, studentTime,studentTime2,counter = 0;
    private boolean checker, checker2, alertStatus;
    double lat, lng;
    int[] timeArray = {5, 10, 15, 20, 30, 45, 60};
    ArrayList<Double> latList, lngList, timeCheckList;
    ArrayList<Long> timeList;
    private double M_PI = 3.14159;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        Log.i("SERVICE", "Service Started");
        initialization();
        prTime = System.currentTimeMillis();
        getDataFromFirebase();
    }

    private void initialization() {
        database = FirebaseDatabase.getInstance();
        studentRef = database.getReference("student");
        routeRef = database.getReference("route");
        stopRef = database.getReference("stop");
        busRef = database.getReference("bus");
        geoRef = database.getReference("geoFire");
        latList = new ArrayList<Double>();
        lngList = new ArrayList<Double>();
        timeList = new ArrayList<Long>();
        timeCheckList = new ArrayList<Double>();
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        editor = sharedPreferences.edit();
    }


    private void getDataFromFirebase() {
            studentRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    try{
                    for (DataSnapshot studentSnapshot : dataSnapshot.getChildren()) {
                        Student student = studentSnapshot.getValue(Student.class);
                        if (student.getStudentID() == 15137038) {
                            final int routeId = student.getStudentRouteID();
                            stopId = student.getStudentStopID();
                            studentTime = student.getAlertArrivalTime();
                            studentTime2 = student.getAlertDepartureTime();
                            alertStatus = student.isAlertStatus();
                            routeRef.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    for (DataSnapshot routeSnapshot : dataSnapshot.getChildren()) {
                                        Route route = routeSnapshot.getValue(Route.class);
                                        if (route.getRouteID() == routeId) {
                                            final String busId = route.getRouteBusID();
                                            busRef.addValueEventListener(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                    for (DataSnapshot busSnapshot : dataSnapshot.getChildren()) {
                                                        Bus bus = busSnapshot.getValue(Bus.class);
                                                        if (bus.getBusID().equals(busId)) {
                                                            latitude = bus.getBusLatitude();
                                                            longitude = bus.getBusLongitude();
                                                            nextTime = System.currentTimeMillis();
                                                            Log.i("LAT", "lat"+latitude);
                                                            if (previousLatitude == 0 && previousLongitude == 0) {
                                                                previousLatitude = latitude+0.00001;
                                                                previousLongitude = longitude+0.00001;
                                                            }
                                                            updateBusLocationOnMap(latitude, longitude);
                                                        }
                                                    }
                                                }
                                                @Override
                                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                                }
                                            });
                                        }
                                    }
                                }
                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                }
                            });
                        }
                    }
                    }catch (Exception e){
                        Toast.makeText(getApplicationContext(), "Exception "+ e, Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });
    }

    private void updateBusLocationOnMap(double latitude, double longitude) {

        if(counter < 6){
            latList.add(latitude);
            lngList.add(longitude);
            timeList.add(nextTime);
            counter++;
        }else{
            counter = 0;
            for(int i=0; i<= latList.size()-1; i++){
                nextLatitude += latList.get(i);
                nextLongitude += lngList.get(i);
                nextTime += timeList.get(i);
            }

            nextLatitude = nextLatitude / 6;
            nextLongitude = nextLongitude / 6;
            nextTime = nextTime / 6;

            latList.clear();
            lngList.clear();
            timeList.clear();

            double dist = distance_on_geoid(nextLatitude, nextLongitude, previousLatitude, previousLongitude);
            double time_s = ((nextTime - prTime) / 1000.0);
            prTime = nextTime;
            previousLatitude = nextLatitude;
            previousLongitude = nextLongitude;
            nextLongitude = 0;
            nextLatitude = 0;

            double speed_mps = dist / time_s;
            double speed_kph = (speed_mps * 3.6);
            LatLng stopLocation = getStopLatLng(stopId);
            double distance = (distance_on_geoid(latitude, longitude, stopLocation.latitude, stopLocation.longitude));
            double time = (distance / speed_mps);
            String formatted_speed = String.format("%.2f", speed_kph);
            Log.i("HELLO", "Time is " + time);
            String formatted2_time = formatTime(time);


            if(alertStatus){
                checker2 = sharedPreferences.getBoolean("Checker2", true);
                if (checker2) {
                    alertDepartureTime();
                }

                checker = sharedPreferences.getBoolean("Checker", true);
                Log.i("SP", "Value "+ checker);
                if (checker) {
                    for (int c = 0; c < 7; c++) {
                        if (c == studentTime) {
                            if (time <= (timeArray[c] * 60)) {
                                timeCheckList.add(time);
                                if(timeCheckList.size() == 3) {
                                    sendNotification("Bus will be at your stop in " + timeArray[c] + " minutes", "G-Track");
                                    editor.putLong("Time", System.currentTimeMillis());
                                    editor.putBoolean("Checker", false);
                                    editor.commit();
                                    checker = sharedPreferences.getBoolean("Checker", true);
                                    Log.i("SP_AFTER", "Value "+ checker);
                                }
                            }else {
                                timeCheckList.clear();
                            }
                        }
                    }
                }
                long timeStamp = (System.currentTimeMillis() - sharedPreferences.getLong("Time", System.currentTimeMillis()));
                long timeStamp2 = (System.currentTimeMillis() - sharedPreferences.getLong("Time2", System.currentTimeMillis()));
                if(timeStamp >= 300000){
                    editor.putBoolean("Checker", true); //TODO Turn 300000 into 12 hours
                    editor.commit();
                }
                if(timeStamp2 >= 300000){
                    editor.putBoolean("Checker2", true);
                    editor.commit();
                }
            }


            Intent i = new Intent("Service_Data");
            i.putExtra("lat", previousLatitude);
            i.putExtra("lng", previousLongitude);
            i.putExtra("speed", formatted_speed);
            i.putExtra("time", formatted2_time);

            Log.i("BROADCAST", "Sending...");

            sendBroadcast(i);

        }
    }
    public void alertDepartureTime(){
        try {
            Date date = new Date();
            Date date3 = new Date();
            String strDateFormat = "dd-MM-yyyy hh:mm";
            DateFormat dateFormat = new SimpleDateFormat(strDateFormat);
            String formattedDate= dateFormat.format(date);


            String todayDate = "dd-MM-yyyy ";
            DateFormat dateFormat1 = new SimpleDateFormat(todayDate);
            String dateTimeToday = dateFormat1.format(date);
            String dateTimeToday2 = dateFormat1.format(date3);
            dateTimeToday = dateTimeToday + TIME_DEPATRURE_1;
            dateTimeToday2 = dateTimeToday2 + TIME_DEPATRURE_2;

            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm");
            Date date1 = sdf.parse(formattedDate);
            Date date2 = sdf.parse(dateTimeToday);
            Date date4 = sdf.parse(dateTimeToday2);

            long dateMili3 = date4.getTime();
            long dateMili1 = date1.getTime();
            long dateMili2 = date2.getTime();

            for (int c = 0; c < 7; c++) {
                if (c == studentTime2) {
                    if ((dateMili2-dateMili1) <= (timeArray[c]*60)*1000) {
                        sendNotification("Bus will depart in " + timeArray[c] + " minutes", "G-Track");
                        editor.putLong("Time2", System.currentTimeMillis());
                        editor.putBoolean("Checker2", false);
                        editor.commit();
                    }else if((dateMili3-dateMili1) <= (timeArray[c]*60)*1000){
                        sendNotification("Bus will depart in " + timeArray[c] + " minutes", "G-Track");
                        editor.putLong("Time2", System.currentTimeMillis());
                        editor.putBoolean("Checker2", false);
                        editor.commit();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

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

    LatLng getStopLatLng(final int stop_id){
        try {
            stopRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot stopSnapshot : dataSnapshot.getChildren()) {
                        Stop stop = stopSnapshot.getValue(Stop.class);
                        if (stop.getStopID() == stop_id) {
                            lat = stop.getStopLatitude();
                            lng = stop.getStopLongitude();
                        }
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });
        }catch (Exception e){
        }
        LatLng latLng = new LatLng(lat, lng);
        return latLng;
    }

    double distance_on_geoid(double lat1, double lon1, double lat2, double lon2) {
        // Convert degrees to radians
        lat1 = lat1 * M_PI / 180.0;
        lon1 = lon1 * M_PI / 180.0;
        lat2 = lat2 * M_PI / 180.0;
        lon2 = lon2 * M_PI / 180.0;
        // radius of earth in metres
        double r = 6378100;
        // P
        double rho1 = r * cos(lat1);
        double z1 = r * sin(lat1);
        double x1 = rho1 * cos(lon1);
        double y1 = rho1 * sin(lon1);
        // Q
        double rho2 = r * cos(lat2);
        double z2 = r * sin(lat2);
        double x2 = rho2 * cos(lon2);
        double y2 = rho2 * sin(lon2);
        // Dot product
        double dot = (x1 * x2 + y1 * y2 + z1 * z2);
        double cos_theta = dot / (r * r);
        double theta = acos(cos_theta);
        // Distance in Metres
        return r * theta;
    }

    private String formatTime(double seconds){
        String fTime;
        int p1 = (int) (seconds % 60);
        int p2 = (int) (seconds / 60);
        int p3 = p2 % 60;
        p2 = p2 / 60;
        if(p3 == 0 && p2 == 0){
            fTime =  p1 + " s";
        }else if(p2 == 0){
            if(p1 < 10){
                fTime = p3 + ":" + "0" + p1 + " m";
            }else{
                fTime = p3 + ":" + p1 + " m";
            }
        }else{
            if(p1<10 && p3<10){
                fTime = p2 + ":0" + p3 + ":0" + p1 + " h";
            }else if(p1<10){
                fTime = p2 + ":" + p3 + ":0" + p1 + " h";
            }else if(p3<10){
                fTime = p2 + ":0" + p3 + ":" + p1 + " h";
            }else{
                fTime = p2 + ":" + p3 + ":" + p1 + " h";
            }
        }
        return fTime;
    }
}
