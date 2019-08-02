
package com.example.g_track.Fragments;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.BitmapFactory;
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
import com.example.g_track.Model.Location;
import com.example.g_track.Model.Parent;
import com.example.g_track.Model.Route;
import com.example.g_track.Model.Stop;
import com.example.g_track.Model.Student;
import com.example.g_track.R;
import com.example.g_track.TrackBusService;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;

import static com.google.android.gms.maps.model.BitmapDescriptorFactory.fromBitmap;
import static com.google.android.gms.maps.model.BitmapDescriptorFactory.fromResource;
import static java.lang.Math.acos;
import static java.lang.Math.cos;
import static java.lang.Math.sin;

/**
 * A simple {@link Fragment} subclass.
 */

public class parentTrackBusFragment extends Fragment implements OnMapReadyCallback {
    private GoogleMap mGoogleMap;
    private MapView mapView;
    private View view;
    private double busLatitude = 32.2049 ,busLongitude = 74.1924;
    private Marker mMarker;
    private TextView busSpeed,estimatedTime;
    private String speed, time;

    private BroadcastReceiver broadcastReceiver;

    public parentTrackBusFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_parent_track_bus, container, false);
        initialization(view);
        Intent i = new Intent(getContext(), TrackBusService.class);
        getActivity().startService(i);
        return  view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (broadcastReceiver == null) {
            broadcastReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    busLatitude = (double) intent.getExtras().get("lat");
                    busLongitude = (double) intent.getExtras().get("lng");
                    speed = (String) intent.getExtras().get("speed");
                    time = (String) intent.getExtras().get("time");

                    showLocation(busLatitude, busLongitude);
                }
            };
        }
        getActivity().registerReceiver(broadcastReceiver, new IntentFilter("Service_Data"));
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mapView = view.findViewById(R.id.map);
        if (mapView!=null){
            mapView.onCreate(null);
            mapView.onResume();
            mapView.getMapAsync(this);
        }
    }

    private void initialization(View view) {
        busSpeed = view.findViewById(R.id.parent_busSpeed_id);
        estimatedTime = view.findViewById(R.id.parent_estimatedTime_id);
    }

    private void showLocation(double latitude, double longitude) {
        if (null != mMarker) {
            mMarker.remove();
        }
        mMarker = mGoogleMap.addMarker(new MarkerOptions().position(
                new LatLng(latitude, longitude))
                .title("====Bus Location====").visible(true)
                .snippet("Lat:" + latitude + " , Lng:" + longitude)
                .icon(fromResource(R.drawable.markerone)));
        mMarker.showInfoWindow();
        mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 18));
        busSpeed.setText(speed + " Kph");
        double speed2 = Double.valueOf(speed);
        if (speed2 <= 0.009) {
            estimatedTime.setText("Undefined");
        } else {
            estimatedTime.setText(time);
        }

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        LatLng busLocation = new LatLng(busLatitude, busLongitude);
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(busLocation.latitude, busLocation.longitude), 10.0f));
    }


}

