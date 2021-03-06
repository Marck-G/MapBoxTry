package com.dev.marck.prom.mapboxtry;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.dev.marck.prom.mapboxtry.MapResources.DBConectionObject;
import com.dev.marck.prom.mapboxtry.MapResources.Places;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.IconFactory;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;

public class MainActivity extends AppCompatActivity {
    private static final int MAP_TIME = 700;
//    mapa de mapbox
    private MapView mapView;
    private ConstraintLayout btn_hasi;
    private ConstraintLayout btn_atera;
    private ConstraintLayout btn_reiniciar;
    private ConstraintLayout btn_continuar;
    private TextView title;
    private ConstraintLayout pointViewHub;
    private boolean isPointView = false;
    private ImageView btn_back;
    private  DBConectionObject db;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        Mapbox.getInstance( this, getString( R.string.key ) );
        setContentView( R.layout.activity_main );
        getSupportActionBar().hide();
        mapView = findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
//        posicionamiento de camara
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
                            .setIcon( IconFactory.getInstance( MainActivity.this ).fromResource( R.mipmap.ic_marker_x ) ));
                        mapboxMap.addMarker( new MarkerOptions().position( Places.ST_CRUZ )
                                .setIcon( IconFactory.getInstance( MainActivity.this ).fromResource(  R.mipmap.ic_marker_x  )));
                        mapboxMap.addMarker( new MarkerOptions().position( Places.ZERAMIKA )
                                .setIcon( IconFactory.getInstance( MainActivity.this ).fromResource(  R.mipmap.ic_marker_x  )));
                        mapboxMap.addMarker( new MarkerOptions().position( Places.JAUREGIA )
                                .setIcon( IconFactory.getInstance( MainActivity.this ).fromResource( R.mipmap.ic_marker_x  )));
                        mapboxMap.addMarker( new MarkerOptions().position( Places.DORRETXEA )
                                .setIcon( IconFactory.getInstance( MainActivity.this ).fromResource( R.mipmap.ic_marker_v  )));
                        mapboxMap.addMarker( new MarkerOptions().position( Places.PARKE )
                                .setIcon( IconFactory.getInstance( MainActivity.this ).fromResource(  R.mipmap.ic_marker_x )));

                    }
                }
        );


        title = findViewById( R.id.main_title );
        pointViewHub = findViewById( R.id.point_view_controllers );
        btn_hasi = findViewById( R.id.btn_jaraitu );
        btn_atera = findViewById( R.id.btn_atera );
        btn_back = findViewById( R.id.btn_back );
        btn_continuar = findViewById( R.id.btn_continuar );
        btn_reiniciar = findViewById( R.id.btn_reiniciar );
        pointViewHub.setVisibility( View.INVISIBLE );

        TextView next = findViewById( R.id.nextPoint );
        TextView previous = findViewById( R.id.previousPoint );

        next.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                nextPoint();
            }
        } );

        previous.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                previousPoint();
            }
        } );

//        DATABASE
        db = new DBConectionObject( this, "app",null, 1 );

        btn_back.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                onBackPressed();
            }
        } );

        btn_reiniciar.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                reiniciar();
            }
        } );

        btn_continuar.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                continuar();
            }
        } );

        btn_atera.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                finish();
            }
        } );

        btn_hasi.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                setOnePointView( true );
                LatLng last = db.getLastPoint();
                if ( last != null )
                    viewPoint(last );
                else
                    Log.e( "Error location", "No encontrado ultimo punto" );
            }
        } );
        restoreCamera();
    }
//    establecemos la posicion por defecto de la camara
    private void restoreCamera(){
        title.setText( "Llodio" );
        mapView.getMapAsync( new OnMapReadyCallback() {
            @Override
            public void onMapReady( @NonNull MapboxMap mapboxMap ) {
                CameraPosition position = new CameraPosition.Builder()
                        .target( Places.LLODIO )
                        .zoom( 14 )
                        .tilt( 0 )
                        .bearing( 0 )
                        .build();
                mapboxMap.animateCamera( CameraUpdateFactory.newCameraPosition( position ), MAP_TIME );
            }
        } );
    }

    private void reiniciar(){
//        TODO: crear una nueva partida, se inicia desde cero
//        Intent --> primera actividad
        Intent i = new Intent( MainActivity.this, SecondActivity.class );
        startActivity( i );
    }

    private void continuar(){
//        TODO: continua desde el ultimo punto
//        intent --> ActivityManager.init( int id )
    }

    private void nextPoint(){
        setOnePointView( true );
        int actual = Places.getId( db.getLastPoint() );
        db.updateLastPoint( actual++ );
        viewPoint( Places.getPlace( actual++ ) );

    }
    private void previousPoint(){
        setOnePointView( true );
        int actual = Places.getId( db.getLastPoint() );
        db.updateLastPoint( actual-- );
        viewPoint( Places.getPlace( actual-- ) );

    }

    private void setOnePointView( boolean set ){
        if ( set ){
            btn_hasi.setVisibility( View.INVISIBLE );
            btn_atera.setVisibility( View.INVISIBLE );
            new Handler().postDelayed( new Runnable() {
                @Override
                public void run() {
                    pointViewHub.setVisibility( View.VISIBLE );
                    pointViewHub.animate().setDuration( 300 ).translationY( -10 ).start();
                }
            }, MAP_TIME );

        } else {
            pointViewHub.setVisibility( View.INVISIBLE );
            new Handler().postDelayed( new Runnable() {
                @Override
                public void run() {
                    btn_hasi.setVisibility( View.VISIBLE );
                    btn_atera.setVisibility( View.VISIBLE );

                }
            }, 500 );

        }
        isPointView = set;
    }

//    hace zoom sobre un punto especifico
    private void viewPoint( LatLng pos ){
        String name = Places.getName( pos );
        title.setText( name );
        mapView.getMapAsync( new OnMapReadyCallback() {
            @Override
            public void onMapReady( @NonNull MapboxMap mapboxMap ) {
                CameraPosition position = new CameraPosition.Builder()
                        .target( pos )
                        .zoom( 19 )
                        .build();
                mapboxMap.animateCamera( CameraUpdateFactory.newCameraPosition( position ),500 );
            }
        } );
    }

    @Override
    public void onBackPressed() {
        if( isPointView ){
            restoreCamera();
            setOnePointView( false );
        }else{
            finish();
        }
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

