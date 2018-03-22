package com.example.frank.busmap.Pojo.Rest;
import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.frank.busmap.GenticAlgorithm.GaInitalisation;
import com.example.frank.busmap.GenticAlgorithm.Stops;
import com.example.frank.busmap.Pojo.getJourneyFromTo.JourneyFromToResponse;
import com.example.frank.busmap.Pojo.getJourneyFromTo.Journeys;
import com.example.frank.busmap.Pojo.getJourneyFromTo.Legs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by frank on 21/03/2018.
 */

public class BackgroundService extends IntentService {
    public static String TAG = BackgroundService.class.getName();
    public BackgroundService() {
        super("BackgroundService");
    }

    //PASs TFL CLINET, FROM, To, APPID, APIKEY
    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.d(TAG, "Background");
        String from = intent.getStringExtra("From");
        String to = intent.getStringExtra("To");
        String appId = intent.getStringExtra("AppId");
        String apiKey = intent.getStringExtra("ApiKey");
       TflApi tflApi = ServiceGenerator.createTflClient(TflApi.class);

    Call<JourneyFromToResponse> busJourney = tflApi.getJourney(from, to, appId, apiKey);

    try
    {
        Response<JourneyFromToResponse> execute = busJourney.execute();
        Log.d(TAG, "Retrofit Success " + busJourney.request().url().toString());
        if(execute.isSuccessful() && execute.body() == null){
            Log.d(TAG, "NULL");
            busJourney.clone();
        }else {
            work(execute.body());

        }

    }
    catch(IOException e)
    {
        //e.printStackTrace();
        Log.d(TAG, "Retrofit failure");

    }
    }
    public void work(JourneyFromToResponse a ){
        List<Stops> fruitsList = new ArrayList<Stops>();
        List<Stops> fruitsList2 = new ArrayList<Stops>();
        List<Stops> fruitsList3 = new ArrayList<Stops>();
        //GaInitalisation gaInitalisation = new GaInitalisation(fruitsList, fruitsList2, fruitsList3);
        Journeys[] journ = a.getJourneys();
        //May change to include last option
        for (int i = 0; i < journ.length-1; i++) {
            String [] pathValue = {"00", "01", "02", "03"};
            Legs [] l = journ[i].getLegs();
            Log.d(TAG, "LENGLEGNT " + journ.length);
            Log.d(TAG, " journey " + Double.valueOf(journ[i].getDuration()) + " " + pathValue[i] + " " + journ[i].getLegs(0).getModeName());

           fruitsList.add(new Stops(pathValue[i], Double.valueOf(journ[i].getDuration()), 2, 0));
//
//            fruitsList.add(new Stops("", Double.valueOf(j[i].getDuration()), 2, 2));
        }
        Log.d(TAG, "FR " + fruitsList.toString());

    }

    public int checkVehicleChange(String mode){

        if(mode.equals("bus") || mode.equals("tube") ||mode.equals("overground")){
            return 1;
        }else{
            return 0;
        }

    }


}
