package com.example.g_track.Fragments;


import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ahmadrosid.lib.drawroutemap.DrawMarker;
import com.ahmadrosid.lib.drawroutemap.DrawRouteMaps;
import com.example.g_track.Model.Location;
import com.example.g_track.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.google.android.gms.maps.CameraUpdateFactory.newLatLng;


/**
 * A simple {@link Fragment} subclass.
 */
public class studentTrackBusFragment extends Fragment implements OnMapReadyCallback {
    private DatabaseReference myRef;
    private FirebaseDatabase database;
    private  GoogleMap mGoogleMap;
    private MapView mapView;
    private  View view;
    private String latitude;
    private String longitude;
    private Location BusLocation;
    double lt,lg;

    ///
    MarkerOptions place1, place2;
    ///

    public studentTrackBusFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       view = inflater.inflate(R.layout.fragment_student_track_bus, container, false);
       initialization(view);
       addLocationToDatabase();
       getDataFromFirebase();


        return  view;
    }

    private void addLocationToDatabase() {
        BusLocation = new Location(123,32,74);
        myRef.child("Location").setValue(BusLocation);
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
        myRef = database.getReference("Bus Location");
    }

    private void getDataFromFirebase() {
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //latitude =  dataSnapshot.child("latitude").getValue().toString();
                //longitude = dataSnapshot.child("longitude").getValue().toString();
                //Toast.makeText(getContext(), "latitude: " + latitude + " longitude: " + longitude, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
       // MapsInitializer.initialize(getContext());

       // System.out.println("My Name is M.Sohail "+latitude);
//        Log.i("latitude :" , latitude);
       // lt = Double.parseDouble(latitude);
       // lg = Double.parseDouble(longitude);

        mGoogleMap = googleMap;
        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        LatLng myLocation = new LatLng(32,	73);
        mGoogleMap.addMarker(new MarkerOptions().position(myLocation).title("SE Lab").icon(BitmapDescriptorFactory.fromResource(R.drawable.busiconmap)));
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(myLocation.latitude,myLocation.longitude),12.0f));

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
}
