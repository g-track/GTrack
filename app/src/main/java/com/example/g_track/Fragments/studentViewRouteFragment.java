package com.example.g_track.Fragments;


import android.app.ProgressDialog;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ahmadrosid.lib.drawroutemap.DrawMarker;
import com.ahmadrosid.lib.drawroutemap.DrawRouteMaps;
import com.example.g_track.Model.Route;
import com.example.g_track.Model.Stop;
import com.example.g_track.Model.Student;
import com.example.g_track.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
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
    LatLng myLocation = new LatLng(32.2019,74.1924);
    private FirebaseDatabase database;
    private DatabaseReference studentRef,routeRef,stopRef;
    private ProgressDialog progressDialog;
    private GoogleMap mMap;

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
        setLocationOfBusesOnMap();
        return view;
    }

    private void initialization() {
        database = FirebaseDatabase.getInstance();
        studentRef = database.getReference("student");
        routeRef = database.getReference("route");
        stopRef = database.getReference("stop");
    }

    private void setLocationOfBusesOnMap() {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        studentRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot studentSnapshot : dataSnapshot.getChildren()) {
                    Student student = studentSnapshot.getValue(Student.class);
                    if (student.getStudentID() == 15137038) {
                        final int routeId = student.getStudentRouteID();

                        stopRef.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for (DataSnapshot stopSnapshot : dataSnapshot.getChildren()) {
                                    Stop stop = stopSnapshot.getValue(Stop.class);
                                    if (stop.getStopRouteID() ==routeId) {
                                        LatLng latLng = new LatLng(stop.getStopLatitude(), stop.getStopLongitude());
                                        options.position(latLng);
                                        options.title(stop.getStopName()).icon(fromResource(R.drawable.markertwo)).visible(true);
                                        // options.snippet("someDesc");
                                        mMap.addMarker(options);
                                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(stop.getStopLatitude(), stop.getStopLongitude()), 8.0f));
                                        progressDialog.dismiss();
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

    @Override
    public void onMapReady(GoogleMap googleMap) {

      /*  mGoogleMap = googleMap;
        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mGoogleMap.addMarker(new MarkerOptions().position(myLocation));
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(myLocation.latitude,myLocation.longitude),10.0f));

        setLocationOfBusesOnMap();*/

        mMap = googleMap;
        LatLng origin = new LatLng(31.788969, 73.338382);
        LatLng destination = new LatLng(33.781200, 75.349709);
        DrawRouteMaps.getInstance(getContext())
                .draw(origin, destination, mMap);
        DrawMarker.getInstance(getContext()).draw(mMap, origin, R.drawable.markertwo, "Origin Location");
        DrawMarker.getInstance(getContext()).draw(mMap, destination, R.drawable.markertwo, "Destination Location");

        LatLngBounds bounds = new LatLngBounds.Builder()
                .include(origin)
                .include(destination).build();
        Point displaySize = new Point();
        getActivity().getWindowManager().getDefaultDisplay().getSize(displaySize);
        mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, displaySize.x, 250, 30));

    }
}
