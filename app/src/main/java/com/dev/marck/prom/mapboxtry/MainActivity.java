package com.dev.marck.prom.mapboxtry;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;

public class MainActivity extends AppCompatActivity {
//    mapa de mapbox
    private MapView mapView;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        Mapbox.getInstance( this, getString( R.string.key ) );
        setContentView( R.layout.activity_main );
        getSupportActionBar().hide();
        mapView = findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
//        posicionamiento de camara
        CameraPosition init = new CameraPosition.Builder()
                .target( new LatLng( 43.1410371,-2.9620557 ) )
                .zoom( 16)
                .tilt( 58.0 )
                .bearing( 40 )
                .build();
        MarkerOptions p = new MarkerOptions().position( new LatLng( 43.1410378,-2.9620560 ) );
        mapView.getMapAsync(
                new OnMapReadyCallback() {
                    @Override
                    public void onMapReady( @NonNull MapboxMap mapboxMap ) {
//                        Cargamos el estilo del mapa
                        mapboxMap.setStyle( new Style.Builder().fromUrl( getString( R.string.map_style_url ) ) );
                        mapboxMap.animateCamera( CameraUpdateFactory.newCameraPosition(init), 300 );
                        mapboxMap.addMarker( p );
                    }
                }
        );
    }

    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

}

