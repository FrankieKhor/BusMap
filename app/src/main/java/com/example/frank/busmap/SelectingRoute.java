package com.example.frank.busmap;

import android.util.Log;

import com.example.frank.busmap.Pojo.getJourneyFromTo.Journeys;
import com.example.frank.busmap.Pojo.getJourneyFromTo.Legs;

/**
 * Created by frank on 05/03/2018.
 */

public class SelectingRoute {
    int duration;
    int  a, b, c;
    Journeys [] journey;
    public SelectingRoute(Journeys []journey){
        this.journey = journey;
        this.a = Integer.valueOf(journey[0].getDuration());
        this.b = Integer.valueOf(journey[1].getDuration());
        this.c = Integer.valueOf(journey[2].getDuration());
    }

    public Journeys  getQuickestRoute(){
        int smallest ;
        Log.d("Route", a + " b " +b + " c " + c);
        a +=100;
        b +=20;
        c += 40;

        if(a<b && a<c){
            smallest = a;
            Log.d("Route", a + " b " +b + " c " + c);

            return journey[0];

        }else if(b<c && b<a){
            smallest = b;
            Log.d("Route", a + " b " +b + " c " + c);

            return journey[1];

        }else{
            smallest = c;
            Log.d("Route", a + " b " +b + " c " + c);

            return journey[2];

        }

    }

    public void getCheapestRoute(){

    }



}
