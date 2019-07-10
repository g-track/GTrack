/*package com.example.g_track;

import android.graphics.Point;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.ahmadrosid.lib.drawroutemap.DrawMarker;
import com.ahmadrosid.lib.drawroutemap.DrawRouteMaps;
import com.cs.googlemaproute.DrawRoute;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

public class basicMapDemo extends FragmentActivity implements OnMapReadyCallback, DrawRoute.onDrawRoute {

    GoogleMap mMap;*/
   // String mapKey =getResources().getString(R.string.google_maps_key);
    /*@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_map_demo);

        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }
*/
    /*@Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;*/
       /* LatLng origin = new LatLng(-7.788969, 110.338382);
        LatLng destination = new LatLng(-7.781200, 110.349709);
        DrawRouteMaps.getInstance(this)
                .draw(origin, destination, mMap);
        DrawMarker.getInstance(this).draw(mMap, origin, R.drawable.map_arrow_icon, "Origin Location");
        DrawMarker.getInstance(this).draw(mMap, destination, R.drawable.map_arrow_icon, "Destination Location");

        LatLngBounds bounds = new LatLngBounds.Builder()
                .include(origin)
                .include(destination).build();
        Point displaySize = new Point();
        getWindowManager().getDefaultDisplay().getSize(displaySize);
        mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, displaySize.x, 250, 30));*/

        /*DrawRoute.getInstance(this,basicMapDemo.this).setFromLatLong(24.905954,67.0803505)
                .setToLatLong(24.9053485,67.079119).setGmapAndKey("AIzaSyC6fc7HCZqS-Oergw8jiD8tQSMS1-0BykU",mMap).setColorHash("#00ff00").run();
        DrawRoute.getInstance(this,basicMapDemo.this).run();
    }

    @Override
    public void afterDraw(String result) {
        Log.d("My response",""+result);

    }
}
*/