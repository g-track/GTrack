
package com.example.g_track.Fragments;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.g_track.Model.Bus;
import com.example.g_track.Model.Location;
import com.example.g_track.Model.Parent;
import com.example.g_track.Model.Route;
import com.example.g_track.Model.Student;
import com.example.g_track.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
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
/**
 * A simple {@link Fragment} subclass.
 */

public class parentTrackBusFragment extends Fragment implements OnMapReadyCallback {
    private GoogleMap mGoogleMap;
    private FirebaseDatabase database;
    private DatabaseReference parentRef, studentRef, routeRef, busRef;
    private double latitude = 32.2500, longitude = 74.1667;
    private Marker mMarker;
    private ProgressDialog progressDialog;

    public parentTrackBusFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_parent_track_bus, container, false);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        // setParentData();
        initialization();
        getDataFromFirebaseForBusLocation();
        return view;
    }

    private void getDataFromFirebaseForBusLocation() {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading....");
        progressDialog.show();
        parentRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot parentSnapshot : dataSnapshot.getChildren()) {
                    Parent parent = parentSnapshot.getValue(Parent.class);
                    if (parent.getParentID() == 51) {
                        final int childId = parent.getChildStudentID();
                        studentRef.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for (DataSnapshot studentSnapshot : dataSnapshot.getChildren()) {
                                    Student student = studentSnapshot.getValue(Student.class);
                                    if (student.getStudentID() == childId) {
                                        final int routeId = student.getStudentRouteID();
                                        routeRef.addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                for (DataSnapshot routeSnapshot : dataSnapshot.getChildren()) {
                                                    Route route = routeSnapshot.getValue(Route.class);
                                                    if (route.getRouteID()==routeId){
                                                    final String busId = route.getRouteBusID();
                                                    busRef.addValueEventListener(new ValueEventListener() {
                                                        @Override
                                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                            for (DataSnapshot busSnapshot : dataSnapshot.getChildren()) {
                                                                Bus bus = busSnapshot.getValue(Bus.class);
                                                                if (bus.getBusID() == busId) {
                                                                    latitude = bus.getBusLatitude();
                                                                    longitude = bus.getBusLongitude();
                                                                    updateBusLocationOnMap(latitude,longitude);

                                                                }
                                                            }
                                                        }

                                                        @Override
                                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                                        }
                                                    });
                                                    }
                                                }
                                                progressDialog.dismiss();
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


    }

    private void updateBusLocationOnMap(double latitude, double longitude) {
        if (null != mMarker) {
            mMarker.remove();
        }
        mMarker = mGoogleMap.addMarker(new MarkerOptions().position(
                new LatLng(latitude, longitude))
                .title("====Bus Location====").visible(true)
                .snippet("Lat:"+latitude+" , Lng:"+longitude)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.markerone)));
        mMarker.showInfoWindow();
        mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude,longitude),18));
    }

    private void initialization() {
        database = FirebaseDatabase.getInstance();
        parentRef = database.getReference("Parent");
        studentRef = database.getReference("student");
        routeRef = database.getReference("route");
        busRef = database.getReference("bus");
    }

    private void setParentData() {
      /*  DatabaseReference reference = FirebaseDatabase.getInstance().getReference("bus");
        Bus bus = new Bus();
        bus.setBusID(32);
        bus.setBusDriverID(12);
        bus.setBusStatus(true);
        bus.setBusLatitude(32.5678);
        bus.setBusLongitude(72.456789);

        reference.push().setValue(bus);*/
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        LatLng busLocation = new LatLng(latitude, longitude);
        mGoogleMap.addMarker(new MarkerOptions().position(busLocation).title("Gift University").icon(BitmapDescriptorFactory.fromResource(R.drawable.markerone)));
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(busLocation.latitude, busLocation.longitude), 10.0f));
    }

}

