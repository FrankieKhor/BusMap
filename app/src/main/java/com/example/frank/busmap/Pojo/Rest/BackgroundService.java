package com.example.frank.busmap.Pojo.Rest;

import android.app.IntentService;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.ResultReceiver;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.frank.busmap.DrawingPath;
import com.example.frank.busmap.GenticAlgorithm.Stops;
import com.example.frank.busmap.Pojo.getJourneyFromTo.JourneyFromToResponse;
import com.example.frank.busmap.Pojo.getJourneyFromTo.Journeys;
import com.example.frank.busmap.Pojo.getJourneyFromTo.Legs;
import com.google.android.gms.maps.model.LatLng;

import org.parceler.Parcels;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class BackgroundService extends IntentService
{
    public static String TAG = BackgroundService.class.getName();
    public static ArrayList<LatLng> holdLat = new ArrayList<LatLng>();
    public static ArrayList<Stops> section1 = new ArrayList<Stops>();
    public static ArrayList<Stops> section2 = new ArrayList<Stops>();
    public static ArrayList<Stops> section3 = new ArrayList<Stops>();

    public BackgroundService()
    {
        super("BackgroundService");
    }
    private int choice ;
    private Journeys[] journeys;
    public static int wayPointCount = 0;
    private int STATUS_RUNNING = 1;
    private int STATUS_FINISHED = 2;
    private int STATUS_ERROR = 3;
    ArrayList<Legs> arrayList = new ArrayList<>();
    List<Stops> apple = new ArrayList<Stops>();
    int arrayChoice;
    static int callLength;
    String [] mode;
    @Override
    protected void onHandleIntent(@Nullable Intent intent)
    {
        final ResultReceiver receiver = intent.getParcelableExtra("receiver");
        Bundle bundle = intent.getExtras();
        String from = bundle.getString("From");
        String to = bundle.getString("To");
        String appId = bundle.getString("AppId");
        String apiKey = bundle.getString("ApiKey");
        choice = bundle.getInt("Choice");
        arrayChoice = bundle.getInt("ArrayChoice");
        String command = bundle.getString("command");

        Bundle newBundle = new Bundle();

        if(command.equals("getLegs")) {
            receiver.send(STATUS_RUNNING, Bundle.EMPTY);
            try {
                TflApi tflApi = ServiceGenerator.createTflClient(TflApi.class);
                Call<JourneyFromToResponse> busJourney = tflApi.getJourney(from, to, appId, apiKey);

                try
                {
                Response<JourneyFromToResponse> execute = busJourney.execute();
                Log.d(TAG, "Retrofit Success " + busJourney.request().url().toString());
                if (!execute.isSuccessful() && execute.body() == null) {
                    // Log.d(TAG, "NULL");
                    busJourney.clone();
                } else {
                    createWaypoints(execute.body());
                }
                }
                catch (IOException e) {
                 Log.d(TAG, "RETRO " +e.getMessage());
                 busJourney.clone();

                if (e.getMessage().equals("timeout")) {
                    Log.d(TAG, "DASDAFD  PROBLEM ");
                }
                //Log.d(TAG, "Retrofit failure");
            }


                Parcelable wrapped1 = Parcels.wrap(section1);
                newBundle.putParcelable("section1",wrapped1);

                Parcelable wrapped2 = Parcels.wrap(section2);
                newBundle.putParcelable("section2",wrapped2);

                Parcelable wrapped3 = Parcels.wrap(section3);
                newBundle.putParcelable("section3",wrapped3);

                newBundle.putInt("length", callLength);
                receiver.send(STATUS_FINISHED, newBundle);


            }
            catch (Exception e) {
                Log.d(TAG, "BUNDLE ERROR " + e.toString());
                newBundle.putString(Intent.EXTRA_TEXT, e.toString());
                receiver.send(STATUS_ERROR, newBundle);
            }
        }


        }


    public void createWaypoints(JourneyFromToResponse a)
    {
        callLength ++;
        String [] pathValue = {"00", "01", "02", "03"};
        String [] pathValue1 = {"10", "11", "12", "03"};
        String [] pathValue2 = {"20", "21", "22", "03"};

        Journeys[] journ = a.getJourneys();
        this.journeys = journ;
        //Would normally be jour nlength
        for(int i=0;i<3;i++)
        {
            Legs [] legs = journ[i].getLegs();
            List<Stops> exam = new ArrayList<Stops>();
//            Log.d(TAG, "LEGGGGGGGG " + legs.length);
            for(int j =0;j<legs.length;j++)
            {
                Log.d(TAG, "PATH " + Double.valueOf(legs[j].getDuration()) + " " +checkPrice(legs[j].getModeName()) + " "+ checkVehicleChange(legs[j].getModeName()) + " " +
                        " " + legs[j].getPathsOption().getLineString().size() + " " + legs[j].getPathsOption().getLineString());
                exam.add(new Stops( Double.valueOf(legs[j].getDuration()),checkPrice(legs[j].getModeName()) , checkVehicleChange(legs[j].getModeName()), legs[j].getPathsOption().getLineString() ));
               // Log.d(TAG, "EXAMMMM " + exam.get(i).toString());
                holdLat.addAll(legs[j].getPathsOption().getLineString());
            }
            String path = "";
            if(arrayChoice == 1){
                path = pathValue[i];
            }else if(arrayChoice == 2){
                path = pathValue1[i];
            }else if(arrayChoice == 3){
                path = pathValue2[i];
//            }else if((wayPointCount %3) == 3){
//                path = pathValue3[i];
//            }else if(wayPointCount == 4){
//                path = pathValue4[i];
//            }else if(wayPointCount == 5){
//                path = pathValue5[i];
//            }else if(wayPointCount == 6){
//                path = pathValue6[i];
//            }else if(wayPointCount == 7){
//                path = pathValue7[i];
//            }else if(wayPointCount == 8){
//                path = pathValue8[i];
//            }else if(wayPointCount == 9){
//                path = pathValue9[i];
            }else{
                Log.d(TAG, "SOMETHING IS WRONG");
            }
            if(choice == 1){
                section1.add(new Stops(path, addAllCost(exam).getTime(),addAllCost(exam).getCost(),addAllCost(exam).getVehicleChange(),addAllCost(exam).getLatLng()));
            }else if (choice == 2){
                section2.add(new Stops(path, addAllCost(exam).getTime(),addAllCost(exam).getCost(),addAllCost(exam).getVehicleChange(),addAllCost(exam).getLatLng()));

            }else if (choice ==3){
                section3.add(new Stops(path, addAllCost(exam).getTime(),addAllCost(exam).getCost(),addAllCost(exam).getVehicleChange(),addAllCost(exam).getLatLng()));
            }
            apple.add(new Stops(path, addAllCost(exam).getTime(),addAllCost(exam).getCost(),addAllCost(exam).getVehicleChange(),addAllCost(exam).getLatLng()));
            holdLat.clear();

            Log.d(TAG, "Woah1 " + section1.toString().length() + " " + section1.toString());
            Log.d(TAG, "Woah2 " + section2.toString().length() + " " + section2.toString());
            Log.d(TAG, "Woah3 " + section3.toString().length() + " " + section3.toString());

//            for(Stops s : section1){
//                if(s.getPaths().equals("12")){
//                    Log.d(TAG, "Success " + s.toString());
//                    DrawingPath drawingPath = new DrawingPath();
//                }else{
//                    Log.d(TAG, "FAILED " + s.getPaths());
//                }
//            }
//            if((wayPointCount %3) == 0){
//                holdLat.add(new Stops(path, result.getTime(), result.getCost(), result.getVehicleChange(), result.getPathsOption()));
//                Log.d(TAG, "Adding to section 1");
//            }else if((wayPointCount %3)== 1){
//                section2.add(new Stops(path, result.getTime(), result.getCost(), result.getVehicleChange(), result.getPathsOption()));
//                Log.d(TAG, "Adding to section 2");
//
//            }else if((wayPointCount %3)== 2){
//                section3.add(new Stops(path, result.getTime(), result.getCost(), result.getVehicleChange(), result.getPathsOption()));
//                Log.d(TAG, "Adding to section 3");
//
//            }



        }


      //  Initialisation initialisation = new Initialisation();

    }

    public static ArrayList<LatLng> getLat(){
        return holdLat;
    }

    public Legs [] getLegs(int num){
        return this.journeys[num].getLegs();
    }

    public ArrayList<LatLng> checkLat(ArrayList<LatLng> arrayList){
        holdLat.addAll(arrayList);
        return holdLat;
    }

    public Stops addAllCost(List<Stops> s)
    {
        double [] weight ={0.5,0.5,0.5};
        double time = 0;
        double cost = 0;
        final double dayLimit = 12.50;
        int vehicleChange =0;
        ArrayList<LatLng> bob = new ArrayList<>();
        for(int i =0;i<s.size();i++)
        {
            Stops f = s.get(i);
            time += f.getTime();
            cost += f.getCost();
            vehicleChange += f.getVehicleChange();


        }
        bob = holdLat;
        if(cost > dayLimit){
            cost = dayLimit;
        }
        return new Stops(time, cost, vehicleChange, bob);

    }

    private double getWeightedSum(double [] weight, double time, double cost, int vehicleChange)
    {
        return time * weight[0] + cost * weight[1] + vehicleChange * weight[2];
    }

    public int checkVehicleChange(String mode)
    {
        if(mode.equals("bus") || mode.equals("tube") ||mode.equals("overground") || mode.equals("national-rail") || mode.equals("coach")
                || mode.equals("dlr")|| mode.equals("replacement-bus")|| mode.equals("tram")|| mode.equals("tflrail"))
        {
            return 1;
        }
        else
        {
            return 0;
        }

    }

    public double checkPrice(String mode){
       // Log.d(TAG, "MODE " + mode);
        //Don't have time to figure out all the different prices, any mode which includes a vehicle will be 1.50
        final double BUS_PRICE = 1.50;

        if(mode.equals("bus") || mode.equals("tube") ||mode.equals("overground") || mode.equals("national-rail"))
        {
            return BUS_PRICE;
        }
        else
        {
            return 0;
        }
    }




}
