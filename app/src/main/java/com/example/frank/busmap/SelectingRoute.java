package com.example.frank.busmap;

import android.util.Log;

import com.example.frank.busmap.Pojo.getJourneyFromTo.Journeys;
import com.example.frank.busmap.Pojo.getJourneyFromTo.Legs;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by frank on 05/03/2018.
 */

public class SelectingRoute {
    private static final String TAG = SelectingRoute.class.getName();

    int duration;
    int  a, b, c;
    Journeys [] journey;
    List<Double>ticketPrice = new ArrayList<>();
    public SelectingRoute(Journeys []journey){
        this.journey = journey;
        this.a = Integer.valueOf(journey[0].getDuration());
        this.b = Integer.valueOf(journey[1].getDuration());
        this.c = Integer.valueOf(journey[2].getDuration());
    }
    public SelectingRoute(){

    }



    public Journeys getQuickestRoute(){
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

    public Journeys getCheapestRoute(double price, String type){
        final double dayLimit  =12.50;

        final double BUS_PRICE = 1.50;
        double cheapest =0;
        int currentCount =0;
        int BusCount = 0;
        double totalPrice;
        //This value is used to make sure it has at least one public transport is used(Needs to work on it)
        boolean bool = true;
        for(int i =0;i<journey.length;i++){
            Legs [] legs = journey[i].getLegs();
            for(int j =0;j<legs.length;j++){
                if(legs[j].getModeName().equals("bus")){
                BusCount +=1;
                    Log.d(TAG, "b  " +BusCount);
                    bool = true;
                } else if(legs[j].getModeName().equals("tube") || legs[j].getModeName().equals("overground")){
                    bool= true;
                }
                else{
                    bool = false;
                }


            }
                //Working out the cheapest value
                totalPrice = price+(BusCount*BUS_PRICE);
                Log.d(TAG, " " +totalPrice);
                if(totalPrice > dayLimit){
                    totalPrice = dayLimit;
                }
                Log.d(TAG, "total " +totalPrice);

                if(cheapest == 0 ){
                    cheapest = totalPrice;
                    currentCount =i ;
                }else if(totalPrice< cheapest ){
                    cheapest = totalPrice;
                    currentCount =i ;
                }



            Log.d(TAG, "CHEAPEST " + cheapest + " " + currentCount);

            BusCount = 0;
        }


        Log.d(TAG, "BusCount " + BusCount);
        return journey[currentCount];


    }
    //PREFEREABBLY HAVE A SYSTEM WHERE EACH JOURNEY ARE VALUED DIFFERENTLY
    public void getLeastVehicleChange(){
        int transitUsed =0;
        int leastTransit =0;
        int currentCount = 0;
        for(int i =0;i<journey.length;i++){
            Legs[] legs = journey[i].getLegs();
            for(int j = 0;j<legs.length;j++){
                Log.d(TAG, "NAME " + j + " " + legs[j].getModeName());
                if(legs[j].getModeName().equals("bus") || legs[j].getModeName().equals("tube") ||legs[j].getModeName().equals("overground")){
                transitUsed ++;
                }else{
                    Log.d(TAG, "NAME " + j + " " + legs[j].getModeName());

                }
            }
            if(leastTransit == 0){
                leastTransit = transitUsed;
                currentCount = i;
            }else if(transitUsed < leastTransit){
                leastTransit = transitUsed;
                currentCount = i;
            }

            Log.d(TAG, "TREA " + transitUsed);
            Log.d(TAG, "lkeast  " + leastTransit);

            transitUsed = 0;
        }

        Log.d(TAG, "lol  " + leastTransit);

    }



}
