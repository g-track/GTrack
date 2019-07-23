package com.example.g_track.Fragments;



import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.g_track.Activities.studentHome;

import com.example.g_track.Model.Bus;
import com.example.g_track.Model.Route;
import com.example.g_track.Model.Stop;
import com.example.g_track.Model.Student;
import com.example.g_track.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static com.google.android.gms.maps.model.BitmapDescriptorFactory.fromResource;
import static java.lang.Math.acos;
import static java.lang.Math.cos;
import static java.lang.Math.sin;


/**
 * A simple {@link Fragment} subclass.
 */
public class studentTrackBusFragment extends Fragment implements OnMapReadyCallback {
    private GoogleMap mGoogleMap;
    private MapView mapView;
    private View view;
    private double latitude = 32.20561 ,longitude = 74.19276;
    private double previousLatitude=0,previousLongitude=0,nextLatitude,nextLongitude;
    private long prTime, nextTime;
    private Marker mMarker;
    private DatabaseReference studentRef,routeRef,busRef,stopRef;
    private FirebaseDatabase database;
    private double M_PI = 3.14159;
    private TextView busSpeed,estimatedTime;
    private int stopId, studentTime, checker=0;
    double lat ,lng;
    int[] timeArray = {5, 10,15, 20, 30, 45, 60};
    //MarkerOptions place1, place2;


    public studentTrackBusFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_student_track_bus, container, false);
        initialization(view);
        getDataFromFirebase();

        return  view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mapView = view.findViewById(R.id.studentMap_id);
        if (mapView!=null){
            mapView.onCreate(null);
            mapView.onResume();
            mapView.getMapAsync(this);
        }
    }

    private void initialization(View view) {
        database = FirebaseDatabase.getInstance();
        studentRef = database.getReference("student");
        routeRef = database.getReference("route");
        stopRef = database.getReference("stop");
        busRef = database.getReference("bus");
        busSpeed = view.findViewById(R.id.parent_busSpeed_id);
        estimatedTime = view.findViewById(R.id.parent_estimatedTime_id);
    }

    private void updateBusLocationOnMap(double latitude, double longitude) {
        if (null != mMarker) {
            mMarker.remove();
        }
        mMarker = mGoogleMap.addMarker(new MarkerOptions().position(
                new LatLng(latitude, longitude))
                .title("====Bus Location====").visible(true)
                .snippet("Lat:" + latitude + " , Lng:" + longitude)
                .icon(fromResource(R.drawable.markertwo)));
        mMarker.showInfoWindow();
        mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 18));

        double dist = distance_on_geoid(latitude, longitude, previousLatitude, previousLongitude);

        double time_s = ((nextTime - prTime) / 1000.0);


        double speed_mps = dist / time_s;
        double speed_kph = (speed_mps * 3.6);


        LatLng stopLocation = getStopLatLng(stopId);
        double distance = (distance_on_geoid(latitude, longitude, stopLocation.latitude, stopLocation.longitude));
        double time = (distance / speed_mps);
        String formatted_speed = String.format("%.2f", speed_kph);
        Log.i("Time", "Time is " + time);
        String formatted2_time = formatTime(time);
        busSpeed.setText(formatted_speed + " Kph");
        if (speed_kph <= 0.0009) {
            estimatedTime.setText("Undefined");
        } else {
            estimatedTime.setText(formatted2_time);
        }

        prTime = nextTime;
        previousLatitude = latitude;
        previousLongitude = longitude;

        if (checker == 0) {
            for (int i = 0; i < 7; i++) {
                if (i == studentTime) {
                    if (time <= (timeArray[i] * 60)) {
                        sendNotification("Bus will be at your stop in " + timeArray[i] + " minutes");
                        checker++;
                    }
                }
            }
        }
    }


    @Override
    public void onStart() {
        super.onStart();
    }

    private void getDataFromFirebase() {

        try {
            studentRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot studentSnapshot : dataSnapshot.getChildren()) {
                        Student student = studentSnapshot.getValue(Student.class);
                        if (student.getStudentID() == 15137038) {
                            final int routeId = student.getStudentRouteID();
                            stopId = student.getStudentStopID();
                            studentTime = student.getAlertArrivalTime();
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
                                                            if (previousLatitude == 0 && previousLongitude == 0) {
                                                                previousLatitude = latitude;
                                                                previousLongitude = longitude;
                                                                prTime = System.currentTimeMillis();
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
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }catch (Exception e){
            Toast.makeText(getContext(), "Error: " + e, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
       // MapsInitializer.initialize(getContext());
      // Log.i("Latitudddddddddddd", "Latitude:"+latitude+" Longitude:"+longitude);
        mGoogleMap = googleMap;
        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        LatLng myLocation = new LatLng(latitude,longitude);

        /*mGoogleMap.addMarker(new MarkerOptions().position(myLocation).title("SE Lab").icon(fromResource(R.drawable.markertwo)));
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(myLocation.latitude,myLocation.longitude),18.0f));*/

        /*mGoogleMap.addMarker(new MarkerOptions().position(myLocation).title("SE Lab").icon(fromResource(R.drawable.markertwo)));
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(myLocation.latitude,myLocation.longitude),18.0f));*/

   /*    //int x=10;
        mGoogleMap = googleMap;
       *//* LatLng origin = new LatLng(-7.788969, 110.338382);
        LatLng destination = new LatLng(-7.881200, 110.449709);*//*
        LatLng origin = new LatLng(-7.788969, 110.338382);
        LatLng destination = new LatLng(-7.781200, 110.349709);
        DrawRouteMaps.getInstance(getContext())
                .draw(origin, destination, mGoogleMap);
        DrawMarker.getInstance(getContext()).draw(mGoogleMap, origin, R.drawable.car_icon, "Origin Location");
        DrawMarker.getInstance(getContext()).draw(mGoogleMap, destination, R.drawable.car_icon, "Destination Location");

        LatLngBounds bounds = new LatLngBounds.Builder()
                .include(origin)
                .include(destination).build();
        Point displaySize = new Point();
       getActivity().getWindowManager().getDefaultDisplay().getSize(displaySize);
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, displaySize.x, 250, 30));*/
    }

    public void lngLat(){

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
            Toast.makeText(getContext(), "Error: " + e, Toast.LENGTH_LONG).show();
        }
            LatLng latLng = new LatLng(lat, lng);
            return latLng;
    }

    private void sendNotification(String notificationDetails) {
        // Create an explicit content Intent that starts the main Activity.
        Intent notificationIntent = new Intent(getContext(), studentHome.class);

        // Construct a task stack.
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(getContext());

        // Add the main Activity to the task stack as the parent.
        stackBuilder.addParentStack(studentHome.class);

        // Push the content Intent onto the stack.
        stackBuilder.addNextIntent(notificationIntent);

        // Get a PendingIntent containing the entire back stack.
        PendingIntent notificationPendingIntent =
                stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        // Get a notification builder that's compatible with platform versions >= 4
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getContext());

        // Define the notification settings.
        builder.setSmallIcon(R.mipmap.ic_launcher)
                // In a real app, you may want to use a library like Volley
                // to decode the Bitmap.
                .setLargeIcon(BitmapFactory.decodeResource(getResources(),
                        R.mipmap.ic_launcher))
                .setColor(getResources().getColor(R.color.pendingColor))
                .setContentTitle("Bus is Arriving")
                .setContentText(notificationDetails)
                .setContentIntent(notificationPendingIntent);

        // Dismiss notification once the user touches it.
        builder.setAutoCancel(true);

        // Get an instance of the Notification manager
        NotificationManager mNotificationManager =
                (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);

        // Issue the notification
        mNotificationManager.notify(0, builder.build());
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
