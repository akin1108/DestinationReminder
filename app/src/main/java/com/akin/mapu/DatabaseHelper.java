package com.akin.mapu;

/**
 * Created by Mungfali on 21-06-2018.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import java.util.ArrayList;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import java.util.ArrayList;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;


public class DatabaseHelper extends SQLiteOpenHelper {

    private static  final String DATABASE_NAME = "destination.db";
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sqlDestination = "CREATE TABLE destination(NUMBER VARCHAR ,STOP VARCHAR, LATITUDE DUOBLE, LONGITUDE DOUBLE)";
       sqLiteDatabase.execSQL(sqlDestination);
    }


    public void insertquery(String number,String stop,double latitude,double longitude) {
        ContentValues insertvalue=new ContentValues();
        insertvalue.put("NUMBER",number);
        insertvalue.put("STOP",stop);
        insertvalue.put("LATITUDE",latitude);
        insertvalue.put("LONGITUDE",longitude);
        this.getWritableDatabase().insert("destination","",insertvalue);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public  String[] fetchquery()
    {


        ArrayList<String> namelist = new ArrayList<String>();
        String selectQuery = "select DISTINCT NUMBER from destination";
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor  = db.rawQuery(selectQuery, null);
        int size=cursor.getCount();
        if (cursor.moveToFirst()) {
            do {
                namelist.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        cursor.close();

        String[] spinnerdataarray = new String[namelist.size()];
        spinnerdataarray = namelist.toArray(spinnerdataarray);
        return spinnerdataarray;
    }

    public  String[] fetchquery1(String value)
    {
        Log.i("DBHELPER","Entered fetch query 1");
        ArrayList<String> namelist = new ArrayList<String>();
        Log.i("DBHELPER", "select STOP from destination where NUMBER='"+value+"'");
        String selectQuery = "select STOP from destination where NUMBER='"+value+"'" ;

        SQLiteDatabase db=this.getReadableDatabase();
        Log.i("DBHELPER","after getting db ");
        Cursor cursor  = db.rawQuery(selectQuery, null);
        Log.i("DBHELPER","after cursor ");

        if (cursor.moveToFirst()) {
            Log.i("DBHELPER","b4 population ");
            do {
                Log.i("DBHELPER","b4 getstring ");
                namelist.add(cursor.getString(cursor.getColumnIndex("STOP")));
                Log.i("DBHELPER","after getstring ");
            } while (cursor.moveToNext());
            Log.i("DBHELPER","after population ");
        }

        cursor.close();

        String[] spinnerdataarraystop = new String[namelist.size()];
        spinnerdataarraystop = namelist.toArray(spinnerdataarraystop);
        Log.i("DBHELPER","leaving fetch query 1");
        return spinnerdataarraystop;
    }


    double getlatitude(String busnumber ,String stopname){


        Log.i("DBHELPER","select LATITUDE from destination where NUMBER='"+busnumber+"'and STOP='"+stopname+"'");
        String selectQuery = "select LATITUDE from destination where NUMBER='"+busnumber+"'and STOP='"+stopname+"'";
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor  = db.rawQuery(selectQuery, null);
        double latitude=0.0;
        if (cursor.moveToFirst()) {
            Log.i("DBHELPER","Entering if");
            latitude = cursor.getDouble(cursor.getColumnIndex("LATITUDE"));
            Log.i("DBHELPER","Exiting if");
        }
        cursor.close();
        return latitude;
    }

    double getlongitude(String busnumber ,String stopname){
        Log.i("DBHELPER","Entering getlongitude");

        String selectQuery = "select LONGITUDE from destination where NUMBER='"+busnumber+"'and STOP='"+stopname+"'" ;
        Log.i("DBHELPER",selectQuery);
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor  = db.rawQuery(selectQuery, null);
        double longitude=0.0;
        if (cursor.moveToFirst()) {
            Log.i("DBHELPER","Entering if");
            longitude = cursor.getDouble(cursor.getColumnIndex("LONGITUDE"));
            Log.i("DBHELPER","Exiting if");
        }
        cursor.close();

        Log.i("DBHELPER","Leaving getlongitude");
        return longitude;
    }

    public boolean isDataLoaded() {
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select STOP from destination where NUMBER='100D'",null);
        if (cursor.moveToFirst())
        {
            cursor.close();
            return true;
        }
        else
        {
            cursor.close();
            return false;
        }
    }

}
