package com.dev.marck.prom.mapboxtry.MapResources;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.mapbox.mapboxsdk.geometry.LatLng;

public class DBConectionObject extends SQLiteOpenHelper {
    public static final String LAST_POINT_TABLE = "last_point";
    public static final String ACTIVITIES_DATA_TABLE = "act_data";

    public DBConectionObject( Context context, String name, SQLiteDatabase.CursorFactory factory, int version ) {
        super( context, name, factory, version );
    }

    @Override
    public void onCreate( SQLiteDatabase db ) {
        String createLastPointSQL = " CREATE TABLE " + LAST_POINT_TABLE +
                "( id  INTEGER, nombre TEXT )";
        String createActSQL = "CREATE TABLE " + ACTIVITIES_DATA_TABLE +
                "( id INTEGER PRIMARY KEY, intents INTEGER, done NUMERIC, time TEXT )";
        String insertFirstPointSQL = "INSERT INTO " + LAST_POINT_TABLE + " VALUES( 0, '"
                + Places.getName( Places.getPlace( 0 ) ) + "')";
        db.execSQL( createLastPointSQL );
        db.execSQL( createActSQL );
        db.execSQL( insertFirstPointSQL );
    }

    @Override
    public void onUpgrade( SQLiteDatabase db, int oldVersion, int newVersion ) {
        db.execSQL( "DROP TABLE " + ACTIVITIES_DATA_TABLE );
        db.execSQL( "DROP TABLE " + LAST_POINT_TABLE );
        onCreate( db );
    }

    public LatLng getLastPoint(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery( "SELECT * FROM " + LAST_POINT_TABLE, null );
        int id = -1;
        if( cursor.moveToFirst() ){
            id = cursor.getInt( 0 );
            return Places.getPlace( id+1 );
        } else{
            return null;
        }
    }

    public boolean updateLastPoint( int id ){
        String name = Places.getName( Places.getPlace( id ) );
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL( "DELETE FROM " + LAST_POINT_TABLE );
        ContentValues val = new ContentValues();
        val.put( "id", id );
        val.put( "nombre", name );
        long result = db.insert( LAST_POINT_TABLE, null, val );
        return result != 0;
    }
}
