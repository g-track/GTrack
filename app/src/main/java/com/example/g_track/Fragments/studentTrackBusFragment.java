package com.example.g_track.Fragments;


import android.Manifest;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.g_track.AlertCheckService;
import com.example.g_track.R;
//import com.example.g_track.SenderMail;
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


    private boolean permissionChecker = false;
    private MapView mapView;
    Marker mCurrent;
    private View view;
    private ProgressDialog progressDialog;
    private double busLatitude = 32.20561, busLongitude = 74.19276;
    private Marker mMarker;
    private String speed = "", time = "";
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
       // SenderMail sm = new SenderMail(getContext(),"sohailm816@gmail.com");
        Intent i = new Intent(getContext(), TrackBusService.class);
        getActivity().startService(i);

       Intent service = new Intent(getContext(), AlertCheckService.class);
       getActivity().startService(service);
        return view;
    }

    public void getLocationPermission() {
        String permissions[] = {Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION};
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                permissionChecker = true;
            } else {
                ActivityCompat.requestPermissions(getActivity(), permissions, 0000);
            }
        } else {
            ActivityCompat.requestPermissions(getActivity(), permissions, 0000);
        }
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

    @Override
    public void onPause() {
        try {
            getActivity().unregisterReceiver(broadcastReceiver);
        } catch (IllegalArgumentException ex) {
        }
        super.onPause();
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
                .icon(fromResource(R.drawable.markerone)));
        mMarker.showInfoWindow();
        mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 16));
        double speed2 = Double.valueOf(speed);

        Log.i("SPEED", "Speed" + speed);
        Log.i("SPEED", "Time" + time);
        if (speed2 <= 0.009) {
            busSpeed.setText("Calculating...");
        } else {
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
        if (!isNetworkAvailable()) {
            AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
            builder1.setMessage("Displaying the default location");
            builder1.setTitle("No Internet");
            builder1.setCancelable(true);

            builder1.setPositiveButton(
                    "Ok",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });

            AlertDialog alert11 = builder1.create();
            alert11.show();

            mMarker = mGoogleMap.addMarker(new MarkerOptions().position(new LatLng(busLatitude, busLongitude))
                    .title("====Bus Location====").visible(true)
                    .snippet("Lat:" + busLatitude + " , Lng:" + busLongitude)
                    .icon(fromResource(R.drawable.markerone)));
            mMarker.showInfoWindow();
        }else {
            mMarker = mGoogleMap.addMarker(new MarkerOptions().position(new LatLng(busLatitude, busLongitude))
                    .title("====Bus Location====").visible(true)
                    .snippet("Lat:" + busLatitude + " , Lng:" + busLongitude)
                    .icon(fromResource(R.drawable.markerone)));
            mMarker.showInfoWindow();
        }

        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(myLocation.latitude, myLocation.longitude), 14.0f));
    }

    public void lngLat() {
    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        permissionChecker = false;

        switch (requestCode) {
            case 0000:
                if(grantResults.length >0){
                    for(int i=0; i< grantResults.length; i++){
                        if(grantResults[i] != PackageManager.PERMISSION_GRANTED){
                            permissionChecker = false;
                            return;
                        }
                    }
                    permissionChecker = true;
                }
        }
    }

}
