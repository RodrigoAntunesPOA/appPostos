package com.example.rodrigoantunes.abasteceaqui.localizacao;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;

/**
 * Created by RodrigoAntunes on 24/06/17.
 */

public class Geolocalizacao  {

    private Context context;
    private GoogleMap mMap;
    private FusedLocationProviderClient mFusedLocationClient;
    private LocationCallback locationCallback;
    public static Location ultLocation;
    private LatLng lastPosicao;
    private final int MY_REQUEST_CODE = 99;


    public Geolocalizacao( Context ctx) {

        context=ctx;

        mFusedLocationClient =
                LocationServices.getFusedLocationProviderClient(ctx);

        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                for (Location location : locationResult.getLocations()) {
                    Log.e("localizacao", String.valueOf(location.getLatitude()));
                    ultLocation = location;
                }
            };
        };

    }

    public void getLastLocation() {
        mFusedLocationClient.getLastLocation()
                .addOnSuccessListener((Activity)  context, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        Log.e("localizacao", String.valueOf(location.getLatitude()));
                        ultLocation = location;
                    }
                });
    }

    public double calculaDistancia(double lat1, double long1, double lat2, double long2) {

        double resultado;

        resultado= 6371* Math.acos(Math.cos(Math.PI*(90-lat2)/180)*Math.cos((90-lat1)* Math.PI/180)+Math.sin((90-lat2)*Math.PI/180)*Math.sin((90-lat1)* Math.PI/180)*Math.cos((long1-long2)*Math.PI/180));

        return resultado;

    }


}
