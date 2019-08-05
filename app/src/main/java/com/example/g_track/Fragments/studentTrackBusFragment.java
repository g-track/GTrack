package com.example.g_track.Fragments;


import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.g_track.R;
import com.example.g_track.SenderMail;
import com.example.g_track.TrackBusService;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import static com.google.android.gms.maps.model.BitmapDescriptorFactory.fromResource;


/**
 * A simple {@link Fragment} subclass.
 */
public class studentTrackBusFragment extends Fragment implements OnMapReadyCallback {
    private GoogleMap mGoogleMap;
    private MapView mapView;
    private View view;
    private ProgressDialog progressDialog;
    private double busLatitude = 32.20561, busLongitude = 74.19276;
    private Marker mMarker;
    private String speed="", time="";
    private TextView busSpeed, estimatedTime;

    private BroadcastReceiver broadcastReceiver;


    public studentTrackBusFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_student_track_bus, container, false);
        initialization(view);

        SenderMail sm = new SenderMail(getContext(),"sohailm816@gmail.com");
        Intent i = new Intent(getContext(), TrackBusService.class);
        getActivity().startService(i);


        return view;
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
        mapView = view.findViewById(R.id.studentMap_id);
        if (mapView != null) {
            mapView.onCreate(null);
            mapView.onResume();
            mapView.getMapAsync(this);
        }
    }

    private void initialization(View view) {
        busSpeed = view.findViewById(R.id.student_busSpeed_id);
        estimatedTime = view.findViewById(R.id.student_estimatedTime_id);
    }

    private void showLocation(double latitude, double longitude) {
        if (null != mMarker) {
            mMarker.remove();
        }
        mMarker = mGoogleMap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude))
                .title("====Bus Location====").visible(true)
                .snippet("Lat:" + latitude + " , Lng:" + longitude)
                .icon(fromResource(R.drawable.markertwo)));
        mMarker.showInfoWindow();
        mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 18));
        double speed2 = Double.valueOf(speed);

        Log.i("SPEED", "Speed"+ speed);
        Log.i("SPEED", "Time"+ time);
        if(speed2 <= 0.009){
            busSpeed.setText("Calculating...");
        }else{
            busSpeed.setText(speed + " Kph");
        }


        if (speed2 <= 0.009) {
            estimatedTime.setText("Estimating...");
        } else {
            estimatedTime.setText(time);
        }


    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        LatLng myLocation = new LatLng(busLatitude, busLongitude);
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(myLocation.latitude, myLocation.longitude), 18.0f));
    }

    public void lngLat() {
    }
}

    /*
    Circle circle = mGoogleMap.addCircle(new CircleOptions()
            .center(new LatLng(latitude, longitude))
            .radius(100)
            .strokeColor(Color.RED)
            .fillColor(0x220000FF));
    GeoQuery geoQuery = geoFire.queryAtLocation(new GeoLocation(dangerArea.latitude, dangerArea.longitude), 0.1f);
        geoQuery.addGeoQueryEventListener(new GeoQueryEventListener() {
@Override
public void onKeyEntered(String key, GeoLocation location) {
        Toast.makeText(getContext(), "You Entered!", Toast.LENGTH_SHORT).show();
        }

@Override
public void onKeyExited(String key) {
        Toast.makeText(getContext(), "You Exited!", Toast.LENGTH_SHORT).show();
        }

@Override
public void onKeyMoved(String key, GeoLocation location) {

        }

@Override
public void onGeoQueryReady() {

        }

@Override
public void onGeoQueryError(DatabaseError error) {
        Toast.makeText(getContext(), "Error"+ error, Toast.LENGTH_SHORT).show();
        }
        });*/
