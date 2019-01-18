package com.dev.marck.prom.mapboxtry;

import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dev.marck.prom.mapboxtry.MapResources.Places;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.IconFactory;
import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdate;
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
                .target( Places.LLODIO)
                .zoom( 14 )
                .tilt( 0 )
                .bearing( 0 )
                .build();
        MarkerOptions p = new MarkerOptions().position( Places.IKASTOLA ).setIcon( IconFactory.getInstance( MainActivity.this ).fromResource( R.mipmap.ic_marker_x  ));
        MarkerOptions eliza = new MarkerOptions().position( Places.ELIZA ).setIcon( IconFactory.getInstance( MainActivity.this ).fromResource( R.mipmap.ic_marker_x  ));
        mapView.getMapAsync(
                new OnMapReadyCallback() {
                    @Override
                    public void onMapReady( @NonNull MapboxMap mapboxMap ) {
//                        Cargamos el estilo del mapa
                        mapboxMap.setStyle( new Style.Builder().fromUrl( getString( R.string.map_style_url ) ) );
                        mapboxMap.getUiSettings().setZoomGesturesEnabled(false);
                        mapboxMap.getUiSettings().setLogoEnabled( false );
                        mapboxMap.getUiSettings().setScrollGesturesEnabled( false );
                        mapboxMap.getUiSettings().setCompassEnabled( false );
                        mapboxMap.getUiSettings().setRotateGesturesEnabled( false );
                        mapboxMap.getGesturesManager().removeMoveGestureListener();
                        mapboxMap.getUiSettings().setAttributionEnabled( false );
                        mapboxMap.addMarker( p );
                        mapboxMap.addMarker( eliza );
                        mapboxMap.addMarker( new MarkerOptions().position( Places.TREN )
                            .setIcon( IconFactory.getInstance( MainActivity.this ).fromResource( R.mipmap.ic_marker_x ) )).setTitle( "Tren Geltokia" );
                        mapboxMap.addMarker( new MarkerOptions().position( Places.ST_CRUZ )
                                .setIcon( IconFactory.getInstance( MainActivity.this ).fromResource(  R.mipmap.ic_marker_x  ))).setTitle( "Santa Cruz" );
                        mapboxMap.addMarker( new MarkerOptions().position( Places.ZERAMIKA )
                                .setIcon( IconFactory.getInstance( MainActivity.this ).fromResource(  R.mipmap.ic_marker_x  )));
                        mapboxMap.addMarker( new MarkerOptions().position( Places.JAUREGIA )
                                .setIcon( IconFactory.getInstance( MainActivity.this ).fromResource( R.mipmap.ic_marker_x  )));
                        mapboxMap.addMarker( new MarkerOptions().position( Places.DORRETXEA )
                                .setIcon( IconFactory.getInstance( MainActivity.this ).fromResource( R.mipmap.ic_marker_v  )));
                        mapboxMap.addMarker( new MarkerOptions().position( Places.PARKE )
                                .setIcon( IconFactory.getInstance( MainActivity.this ).fromResource(  R.mipmap.ic_marker_x )));
                        mapboxMap.animateCamera( CameraUpdateFactory.newCameraPosition(init), 300 );

                    }
                }
        );

        ConstraintLayout lay = findViewById( R.id.btn_jaraitu );
        ConstraintLayout atera = findViewById( R.id.btn_atera );
        atera.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                finish();
            }
        } );
        lay.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                lay.setVisibility( View.INVISIBLE );
                atera.setVisibility( View.INVISIBLE );
                viewPoint( Places.IKASTOLA );
            }
        } );
    }

//    quita los botones y la interfaz principal y hace zoom sobre un
    private void viewPoint( LatLng pos ){
        mapView.getMapAsync( new OnMapReadyCallback() {
            @Override
            public void onMapReady( @NonNull MapboxMap mapboxMap ) {
                CameraPosition position = new CameraPosition.Builder()
                        .target( pos )
                        .zoom( 16 )
                        .build();
                mapboxMap.animateCamera( CameraUpdateFactory.newCameraPosition( position ),500 );
            }
        } );
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

