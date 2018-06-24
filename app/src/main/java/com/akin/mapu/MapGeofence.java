package com.akin.mapu;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingRequest;

import static com.google.android.gms.location.Geofence.NEVER_EXPIRE;

/**
 * Created by Mungfali on 14-06-2018.
 */

public class MapGeofence {
  /* private String id;
    private double latitude;
    private double longitude;
    private float radius;
    private long expirationDuration;
    private int transitionType; */
    private int loiteringDelay = 60000;
    double latitude,longitude;

    public void setLatLong(double lati,double longi){
        latitude=lati;
        longitude=longi;
    }

    private Geofence createGeofenceObject() {

        Geofence geofence = new Geofence.Builder().setRequestId("13.012969, 77.630765")
                .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER)
                .setCircularRegion(latitude, longitude, 50)
                .setExpirationDuration( NEVER_EXPIRE)
                .setLoiteringDelay(loiteringDelay).build();
        return geofence;
    }

    public GeofencingRequest getGeofencingRequest() {
        GeofencingRequest.Builder builder = new GeofencingRequest.Builder();
        builder.setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER);
        builder.addGeofence(createGeofenceObject());
        return builder.build();
    }


}
