
package com.example.g_track.Fragments;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.g_track.R;
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
    public void onPause() {
        try {
            getActivity().unregisterReceiver(broadcastReceiver);
        } catch (IllegalArgumentException ex) {
        }
        super.onPause();

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
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(busLocation.latitude, busLocation.longitude), 10.0f));
    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


}

