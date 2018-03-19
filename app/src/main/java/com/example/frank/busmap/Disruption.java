package com.example.frank.busmap;

import android.graphics.Color;

import com.arsy.maps_library.MapRipple;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by frank on 14/03/2018.
 */

public class Disruption {
    LatLng latLng;


    public Disruption(LatLng latLng){
        this.latLng = latLng;
    }


    public void drawDisruption(){
        MapRipple mapRipple = new MapRipple(MainActivity.mMap, new LatLng(51.564072184349996, -0.27827641759000005), TflCalls.mapContext);
        mapRipple.withNumberOfRipples(2);
        mapRipple.withDistance(0.1);
        mapRipple.withFillColor(Color.BLUE);
        mapRipple.withTransparency(0.5f);
        mapRipple.withRippleDuration(5000) ;   //12000ms

        if(mapRipple.isAnimationRunning()){

        }
        mapRipple.startRippleMapAnimation();
    }

    public void createFakeDelay(){


    }


}
