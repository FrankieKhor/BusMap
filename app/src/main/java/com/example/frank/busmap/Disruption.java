package com.example.frank.busmap;

import android.content.Context;
import android.graphics.Color;

import com.arsy.maps_library.MapRipple;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by frank on 14/03/2018.
 */

public class Disruption
{
    private LatLng latLng;
    private Context mapContext;
    private GoogleMap mMap;

    public Disruption(LatLng latLng, Context context, GoogleMap mMap)
    {
        this.latLng = latLng;
        this.mapContext = context;
        this.mMap = mMap;
    }


    public void drawDisruption()
    {
        MapRipple mapRipple = new MapRipple(mMap, new LatLng(51.564072184349996, -0.27827641759000005), mapContext);
        mapRipple.withNumberOfRipples(2);
        mapRipple.withDistance(0.1);
        mapRipple.withFillColor(Color.BLUE);
        mapRipple.withTransparency(0.5f);
        mapRipple.withRippleDuration(5000) ;   //12000ms

        if(mapRipple.isAnimationRunning())
        {

        }
        mapRipple.startRippleMapAnimation();
    }

    public void createFakeDelay(){


    }
}
