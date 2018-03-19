package com.example.frank.busmap;

import android.util.Log;
import android.widget.TextView;

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
    static double routePrice;
    Journeys [] journey;
    List<Double>ticketPrice = new ArrayList<>();
    public SelectingRoute(Journeys []journey){
        this.journey = journey;
    }

    public double getWeightedSum(int num, double [] weight){
        double value[] = new double[3];
        value[0] = getRouteTime(num);
        value[1] = getRoutePrice(this.routePrice, num);
        value[2] = getRouteVehicleChange(num);
        Log.d(TAG, "word " + value[0] + " " + value[1] +" " + value[2] );

        double weightedSum;
        weightedSum = value[0] * weight[0] +  value[1] * weight[1] + value[2] * weight[2];
        Log.d(TAG, "TOTAL Weight " + weightedSum);

        return  weightedSum;
    }

    //Value = time, cost, vehicle change
    public Journeys getTime(int choice){
        double [] weight = new double[3];
        double currentWeight ;
        double currentLowestWeight =0;
        int pathChoice = 0;
        switch (choice){
            case 1:
                weight[0] = 0.5;
                weight[1] = 0.5;
                weight[2] = 0.5;
                pathChoice =0;
                break;
            case 2:
                weight[0] = 1;
                weight[1] = 0.1;
                weight[2] = 0.1;
                pathChoice =1;

                break;
            case 3:
                weight[0] = 0.1;
                weight[1] = 1;
                weight[2] = 0.1;
                pathChoice =2;

                break;
            case 4:
                weight[0] = 0.1;
                weight[1] = 0.1;
                weight[2] = 1;
                pathChoice =3;

                break;
        }

        for(int i =0;i<journey.length;i++){
            currentWeight = getWeightedSum(i,weight );
            Log.d(TAG, "------------------------" + i+ "------------------------");
            Log.d(TAG, " "+currentWeight);

            if(currentLowestWeight == 0 ){
                currentLowestWeight = currentWeight;
                pathChoice = i;
            }else if(currentWeight< currentLowestWeight ){
                currentLowestWeight =  currentWeight;
                pathChoice =i ;
            }
            Log.d(TAG, " Current weight " + currentWeight);
            Log.d(TAG, "lowest weight " + currentLowestWeight);
            Log.d(TAG, "pathCHoice  " + pathChoice);

        }
        return journey[pathChoice];

    }


    public int getRouteTime(int num){
        //Log.d(TAG, "TIME " + Integer.valueOf(journey[num].getDuration()));

        //Adding fake delay
//        if(num == 3){
//            return Integer.valueOf(journey[num].getDuration())-100;
//        }
        return Integer.valueOf(journey[num].getDuration());
    }

    public void addRoutePrice(double routePrice){
        this.routePrice = routePrice;
        Log.d(TAG, "ADDING PRICE" + this.routePrice);
    }


    public double getRoutePrice(double route, int num){
       // Log.d(TAG, "GETTING ROUTE");
        final double dayLimit = 12.50;
        final double BUS_PRICE = 1.50;
        int BusCount = 0;
        double totalPrice = 0;
        //This value is used to make sure it has at least one public transport is used(Needs to work on it)
        int count = 0;
            Legs [] legs = journey[num].getLegs();
            for(int i =0;i<legs.length;i++){
                if(legs[i].getModeName().equals("bus")){
                    BusCount +=1;
                    Log.d(TAG, "b  " +BusCount);
                    count++;
                } else if(legs[i].getModeName().equals("tube") || legs[i].getModeName().equals("overground")){
                    count++;
                }
                else{
                    //bool = false;
                }

            }
            if(count != 0){
                totalPrice = route+(BusCount*BUS_PRICE);
           }
        Log.d(TAG, "Price " + totalPrice + " route " + this.routePrice);
        return totalPrice;
    }

    public int getRouteVehicleChange(int num){
        int transitUsed =0;

        Legs[] legs = journey[num].getLegs();
        Log.d(TAG, "LENGTH " + legs.length);
        for(int i = 0;i<legs.length;i++){
                if(legs[i].getModeName().equals("bus") || legs[i].getModeName().equals("tube") ||legs[i].getModeName().equals("overground")){
                    transitUsed ++;
                }else{
                   // Log.d(TAG, "NAME ELSE " + i + " " + legs[i].getModeName());

                }
            Log.d(TAG, "NAME " + i + " " + legs[i].getModeName());

        }


        Log.d(TAG, "lol  " + transitUsed);
        return transitUsed;
    }




    public void  pathOne(){
//
//        int [] value = new int[3];
//        value
//
//        getWeightedSum(value, );

    }




    public void getQuickestRoute(){
//        int smallest ;
//        Log.d("Route", a + " b " +b + " c " + c);
//        a +=100;
//        b +=20;
//        c += 40;
//
//        if(a<b && a<c){
//            smallest = a;
//            Log.d("Route", a + " b " +b + " c " + c);
//
//           // return journey[0];
//
//        }else if(b<c && b<a){
//            smallest = b;
//            Log.d("Route", a + " b " +b + " c " + c);
//
//           // return journey[1];
//
//        }else{
//            smallest = c;
//            Log.d("Route", a + " b " +b + " c " + c);
//
//           // return journey[2];
//
//        }
//        double [] weight = {1,0.3,0.3};
//        //getWeightedSum(weight);

    }

    public void getCheapestRoute(double price, String type){
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
     //   return journey[currentCount];


    }

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
