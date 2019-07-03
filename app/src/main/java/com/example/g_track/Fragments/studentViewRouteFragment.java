package com.example.g_track.Fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.g_track.Model.Route;
import com.example.g_track.Model.Stop;
import com.example.g_track.Model.Student;
import com.example.g_track.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static com.google.android.gms.maps.model.BitmapDescriptorFactory.fromResource;


/**
 * A simple {@link Fragment} subclass.
 */
public class studentViewRouteFragment extends Fragment implements OnMapReadyCallback {
    private GoogleMap mGoogleMap;
    private ArrayList<LatLng> latlngs = new ArrayList<>();
    private MarkerOptions options = new MarkerOptions();
    LatLng myLocation = new LatLng(32.2500,74.1667);
    private FirebaseDatabase database;
    private DatabaseReference studentRef,routeRef,stopRef;

    public studentViewRouteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_student_view_route, container, false);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        initialization();

        return view;
    }

    private void initialization() {
        database = FirebaseDatabase.getInstance();
        studentRef = database.getReference("Student");
        routeRef = database.getReference("Route");
        stopRef = database.getReference("Stop");
    }

    private void setLocationOfBusesOnMap() {

        studentRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot studentSnapshot : dataSnapshot.getChildren()) {
                    Student student = studentSnapshot.getValue(Student.class);
                    if (student.getStudentID() == 15137029) {
                        final int routeId = student.getStudentRouteID();

                        stopRef.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for (DataSnapshot stopSnapshot : dataSnapshot.getChildren()) {
                                    Stop stop = stopSnapshot.getValue(Stop.class);
                                    if (stop.getStopRouteID() ==routeId) {
                                        LatLng latLng = new LatLng(stop.getStopLatitude(), stop.getStopLongitude());
                                        options.position(latLng);
                                        options.title(stop.getStopName()).visible(true);
                                        // options.snippet("someDesc");
                                        mGoogleMap.addMarker(options);
                                        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(stop.getStopLatitude(), stop.getStopLongitude()), 8.0f));
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

       /* latlngs.add(new LatLng(12.334343, 33.43434));
        latlngs.add(new LatLng(12.8990,33.7896));
        latlngs.add(new LatLng(12.0090,33.0096));
        for (LatLng point : latlngs) {
            options.position(point);
            options.title("someTitle").visible(true);
           // options.snippet("someDesc");
            mGoogleMap.addMarker(options);
            mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(point.latitude,point.longitude),8.0f));*/

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mGoogleMap = googleMap;
        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mGoogleMap.addMarker(new MarkerOptions().position(myLocation).title("GIFT UNIVERSITY").icon(fromResource(R.drawable.busiconmap)));
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(myLocation.latitude,myLocation.longitude),12.0f));

        setLocationOfBusesOnMap();

    }
}
