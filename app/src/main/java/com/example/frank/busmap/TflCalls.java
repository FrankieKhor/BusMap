package com.example.frank.busmap;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.Toast;

import com.ahmadrosid.lib.drawroutemap.DrawMarker;
import com.ahmadrosid.lib.drawroutemap.DrawRouteMaps;
import com.ahmadrosid.lib.drawroutemap.LatLngInterpolator;
import com.example.frank.busmap.Pojo.getAllBusStops.BusStopResponse;
import com.example.frank.busmap.Pojo.getAllBusStops.OrderedLineRoutes;
import com.example.frank.busmap.Pojo.getAllBusStops.StopPointSequences;
import com.example.frank.busmap.Pojo.getBusArrival.BusArrivalResponse;
import com.example.frank.busmap.Pojo.getJourneyFromTo.JourneyFromToResponse;
import com.example.frank.busmap.Pojo.getJourneyFromTo.Journeys;
import com.example.frank.busmap.Pojo.getJourneyFromTo.Legs;
import com.example.frank.busmap.Pojo.getStopPointArrival.StopPointArrival;
import com.example.frank.busmap.Pojo.getTicketPrice.Rows;
import com.example.frank.busmap.Pojo.getTicketPrice.TicketPrice;
import com.example.frank.busmap.Pojo.getTicketPrice.TicketsAvailable;
import com.example.frank.busmap.Pojo.Rest.TflApi;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.Dash;
import com.google.android.gms.maps.model.Dot;
import com.google.android.gms.maps.model.Gap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PatternItem;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.frank.busmap.MainActivity.mMap;
import static com.example.frank.busmap.MainActivity.napId;
import static com.example.frank.busmap.MainActivity.stopName;

/**
 * Created by frank on 05/03/2018.
 */

public class TflCalls extends FragmentActivity implements GoogleMap.OnPolylineClickListener{
    private TflApi tflCall;
    private static final String TAG = TflCalls.class.getName();
    static Context mapContext = MainActivity.getMapContext();
    static Journeys[] journey;
    static ArrayList<String> wayPoint = new ArrayList<>();
    final LatLng from = new LatLng(51.562075, -0.280665);
    final LatLng to = new LatLng(51.615786, -0.262034);

    //    String appId = getString(R.string.tfl_app_id);
//    String apiKey = getString(R.string.tfl_key);
    private static final String BASE_URL = "https://api.tfl.gov.uk/";

    public Retrofit createRetrofit() {

        if (MainActivity.retrofit == null) {
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();
            MainActivity.retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return MainActivity.retrofit;
    }

    public void createRetrofitClass(){
        this.tflCall = MainActivity.retrofit.create(TflApi.class);

    }

    public void choice(int API_CALL_CHOICE, String appId, String apiKey){
        switch (API_CALL_CHOICE) {
            case 1:
                callMapInfo(appId, apiKey);
                break;
            case 2:
                callAlternateBus(appId, apiKey);
                break;
            case 3:
                callTicketPrice(appId, apiKey);
        }
    }

    private void getBusStopCall(final String appId, final String apiKey) {

        Call<BusStopResponse> busStopCall = tflCall.getAllBusStops(MainActivity.lineID, MainActivity.direction, appId, apiKey);
        busStopCall.enqueue(new Callback<BusStopResponse>() {
            @Override
            public void onResponse(Call<BusStopResponse> call, Response<BusStopResponse> response) {
                Log.d(TAG, " Getting response from all bus stops " + call.request().url().toString());
                //  Log.d(TAG, "URL " + call.request().url());
                //Creating object
                if (response.isSuccessful()) {
                    Log.d(TAG, " BUS STOP Response success");
                    MainActivity.mMap.clear();
                    getLineData(response.body());
                    MainActivity.cBusArrivalisFinished = true;
                } else {
                    Log.d(TAG, " Awaiting response");
                }



                //String encode = PolyUtil.encode(latLng);
//                List<LatLng> qt = new ArrayList<>();
//                //qt =PolyUtil.decode("ChIJ685WIFYViEgRHlHvBbiD5nE");
                // "hIJA01I-8YVhkgRGJb0fW4UX7Y&key=AIzaSyD8PhziTf5drvKOLU_bQYnkfLjSHkbZDNM");

                //Log.d(TAG, "fds " + baby.toString());

                //convertLatLng(orderedLineRoutes, q);

//                  for(int i = 0 ;i<direction.length;i++) {
//                      Log.d(TAG, "direction" + Arrays.toString(direction));
//                  }

            }

            @Override
            public void onFailure(Call<BusStopResponse> call, Throwable t) {
                Log.d(TAG, t.toString() + " SOMETHING IS WRONG");
            }
        });
    }


    public void getBusArrivalCall(String appId, String apiKey) {
        if (MainActivity.cBusArrivalisFinished = true) {
            Call<List<BusArrivalResponse>> busArrivalCall = tflCall.getBusArrival(MainActivity.lineID, appId, apiKey);
            busArrivalCall.enqueue(new Callback<List<BusArrivalResponse>>() {

                @Override
                public void onResponse(Call<List<BusArrivalResponse>> call, Response<List<BusArrivalResponse>> response) {
                    Log.d(TAG, " Getting response from Bus Arrival " + call.request().url().toString());
                    //    Log.d(TAG, "URL " + call.request().url());

                    //Creating object
                    if (response.isSuccessful()) {
                        Log.d(TAG, " ARRIVAL BUS Response success");
                        MainActivity.StudentData = response.body();
                        Collections.sort(MainActivity.StudentData);
//                        for (int i = 0; i < StudentData.size(); i++) {
//
//                            nextStation.add(StudentData.get(i).getStationName());
//                            int time = (Integer) StudentData.get(i).getTimeToStation() / 60;
//                            timeToStation.add(time);
//                            towardsDestination.add(StudentData.get(i).getDestinationName());
//                            vehicleId.add(StudentData.get(i).getVehicleId());
//
//                        }

                        // Log.d(TAG, "Bus ID " + StudentData.size());

                        //Log.d(TAG, "Bus ID " + StudentData.get(0).getStationName());
//                        Log.d(TAG, "Time to station: " + timeToStation);
//                        Log.d(TAG, "Going to " + nextStation.toString() + " minute");
//                        Log.d(TAG, "towards " + towardsDestination.toString());
                        MainActivity.cBusStopisFinished = true;
                        //   Log.d(TAG, "cBusStopisFinished" + cBusStopisFinished);
                    } else {
                        Log.d(TAG, " Awaiting response");
                    }
                    //getStationsBetween();
                }


                @Override
                public void onFailure(Call<List<BusArrivalResponse>> call, Throwable t) {
                    Log.d(TAG, t.toString() + " SOMETHING IS WRONG");
                }
            });

        }
    }

    //NEEDS WAY TO ONLY SHOW ONE
    private void getBusStopArrival(final String appId, final String apiKey) {
        if (MainActivity.cBusArrivalisFinished) {
            String[] qewty = {"4900801618", "490005530W"};
            for (int i = 0; i < napId.length; i++) {
                final Call<List<StopPointArrival>> StopArrival = tflCall.getStopArrival(napId[i], appId, apiKey);
                StopArrival.enqueue(new Callback<List<StopPointArrival>>() {
                    @Override
                    public void onResponse(Call<List<StopPointArrival>> call, Response<List<StopPointArrival>> response) {
                        Log.d(TAG, " Getting response from Bus Stops " + call.request().url().toString());
                        if (response.isSuccessful()) {
                            Log.d(TAG, " BUS STOP ARRIVAL Response success");
                            MainActivity.STOPDATA = response.body();
                            //Collections.sort(STOPDATA);

                            for (int i = 0; i < response.body().size(); i++) {
                                Log.d(TAG, "STATION " + MainActivity.STOPDATA.get(i).getStationName() + " time to statio " + MainActivity.STOPDATA.get(i).getTimeToStation());

                            }
//                    for(int i =0;i<napId.length;i++){
//                        stopArrivalResponse = (napId[i]);
//                        Log.d(TAG, "BLOBLOB " + response.body().getExpectedArrival());
//
//                    }

                        }
                    }

                    @Override
                    public void onFailure(Call<List<StopPointArrival>> call, Throwable t) {
                        Log.d(TAG, " GEtting BUS STOP ARRIVAL FAILURE " + call.request().url().toString());

                        Log.d(TAG, " BUS STOP ARRIVAL FAILURE " + t.toString());

                    }

                });
            }
        }
    }


    public void callMapInfo(String appId, String apiKey) {
        getBusStopCall(appId, apiKey);
        //getBusArrivalCall(appId, apiKey);
        //NEEDS TO FIX
        //getBusStopArrival(appId, apiKey);
    }

    public void callTicketPrice(String appId, String apiKey){
        getBusTicketPrice(appId, apiKey);
    }

    public void callAlternateBus(String appId, String apiKey) {
        getBusJourney(appId, apiKey);
        getBusTicketPrice(appId, apiKey);
    }

    public void getBusJourney(final String appId, final String apiKey) {
//        final String from = MainActivity.travelFrom.toString();
//        final String to = MainActivity.travelTo.toString();
//
        String f = from.latitude + ", " + from.longitude;
        String t = to.latitude + ", " + to.longitude;
        Call<JourneyFromToResponse> busJourney = tflCall.getJourney(f, t, appId, apiKey);
        Log.d(TAG, " Getting response from Bus journey ");

        busJourney.enqueue(new Callback<JourneyFromToResponse>() {
            @Override
            public void onResponse(Call<JourneyFromToResponse> call, Response<JourneyFromToResponse> response) {
                MainActivity.layoutBusDirection.setVisibility(View.GONE);
                MainActivity.layoutBusRoute.setVisibility(View.VISIBLE);
                MainActivity.sup.setPanelHeight(110);
                Log.d(TAG, "Bus journey success " + call.request().url().toString());

                JourneyFromToResponse a = response.body();
                journey = a.getJourneys();
                //getBusJourneyLegs(appId, apiKey);
                drawPathLine();
            }

            @Override
            public void onFailure(Call<JourneyFromToResponse> call, Throwable t) {
                Toast.makeText(mapContext, "Failed, try again",
                        Toast.LENGTH_SHORT).show();

                Log.d(TAG, "Bus journey failure " + call.request().url().toString());
                Log.d(TAG, " " + t.toString());
            }
        });


    }

    public void getBusJourneyLegs(String apiKey, String appId){
        Log.d(TAG, "GETTING JOURNEYT LEG");

        ArrayList<String> a = new ArrayList<>();
        a.add("010");



        for(int i =0;i<journey.length;i++){
            Legs [] legs = journey[i].getLegs();
            for(int j =1;j<legs.length;j++){
                String from = legs[(j-1)].getArrivalPoint().getLatLng();
                String to = legs[j].getArrivalPoint().getLatLng();
                //Log.d(TAG, "DE " + from + " -> " + to);
                wayPoint.add(from);
                wayPoint.add(to);
            }
            evaulatePath(apiKey, appId);

            Log.d(TAG, "-----------------Seperator------------------------ ");

        }

//        for(int i =0;i<journey.length;i++){
//            Log.d(TAG, " I " + i + " "  +journey.length);
//            Legs [] legs = journey[i].getLegs();
//            if(i == 0){
//                Log.d(TAG, "a1");
//                addingPaths(legs, 1);
//            }
//            else if(i == 1){
//                Log.d(TAG, "a2");
//
//                addingPaths(legs, 2);
//            }
//            else if(i == 2){
//                Log.d(TAG, "a3");
//
//                addingPaths(legs, 3);
//            }
//            else if(i == 3){
//                Log.d(TAG, "a4");
//
//                addingPaths(legs, 4);
//            }
//        }

//        Legs [] legs;
//        for(int i =0;i<journey.length-1;i++){
//            legs = journey[i].getLegs();
//            Log.d(TAG, "II  " + i + " " + legs.length);
//            //Would use legs.length but limiting to first option
//            //for(int j =0;j<1;j++){
//                String from = legs[0].getArrivalPoint().getLatLng();
//                String to = legs[(1)].getArrivalPoint().getLatLng();
//                String lo = legs[(journey.length-1)].getArrivalPoint().getLatLng();
//                getBusJourneys(apiKey, appId, from, to);
//                getBusJourneys(apiKey, appId, to, lo);
//                Log.d(TAG, "FROM " + from + "    to    " + to);
//           //}
//            Log.d(TAG, "COMEPLTE 1");
//
//
//            Log.d(TAG, "------------------------DIFFEREMT LEG-------------------------- ");
//        }

    }

    public void evaulatePath(String appId, String apiKey){

        Log.d(TAG, "EVA " + wayPoint.toString());
        Log.d(TAG, "length " + wayPoint.size());

        for(int i =1;i<wayPoint.size()-1;i++){
            String from =wayPoint.get((i-1));
            String to = wayPoint.get(i);
            getBusJourneys(appId, apiKey, from , to);
        }
    }

    public void getBusJourneys(String appId, String apiKey, final String from, final String to) {


        Call<JourneyFromToResponse> busJourney = tflCall.getJourney(from, to, appId, apiKey);
    //        Log.d(TAG, " Getting response from Bus journey 2 ");

        busJourney.enqueue(new Callback<JourneyFromToResponse>() {
            @Override
            public void onResponse(Call<JourneyFromToResponse> call, Response<JourneyFromToResponse> response) {
                Log.d(TAG, " Getting response from Bus journey 2 " + call.request().url().toString());
                //  Log.d(TAG, "FROM " + from + " to " + to);
                JourneyFromToResponse a = response.body();
                Journeys [] j = a.getJourneys();
                for(int i =0;i<j.length;i++){
                    Log.d(TAG, " journey " + j[i].getDuration());

                }
            }

            @Override
            public void onFailure(Call<JourneyFromToResponse> call, Throwable t) {
                Toast.makeText(mapContext, "Failed, try again",
                        Toast.LENGTH_SHORT).show();

                Log.d(TAG, "Bus journey failure " + call.request().url().toString());
                Log.d(TAG, " " + t.toString());
            }
        });


    }

    public void getBusTicketPrice(String appId, String apiKey){
        String stopPointFrom = "940GZZLUWYP";
        String stopPointTo = "940GZZLUEGW";
        Call<List<TicketPrice>> getTickTock = tflCall.getTicketPrices(stopPointFrom, stopPointTo, appId, apiKey );
        Log.d(TAG, " Getting ticket response" );

        getTickTock.enqueue(new Callback<List<TicketPrice>>() {
            @Override
            public void onResponse(Call<List<TicketPrice>> call, Response<List<TicketPrice>> response) {
                Log.d(TAG, "Bus ticket price "+call.request().url().toString());
                List<TicketPrice> rb = response.body();
                double totalCost;
                String ticketType;
                Legs [] lo = new Legs[0];
                for(int i =0;i<rb.size()-1;i++){
                    Rows[] row = rb.get(i).getRows();
                    TicketsAvailable[] ticketsAvailable =  row[i].getTicketsAvailable();

                    //ONly using offpeak but ticketsAvailable.length would use this
                    for(int j =0;j<1;j++){
                         totalCost = Double.valueOf(ticketsAvailable[j].getCost());
                         ticketType = ticketsAvailable[j].getTicketTime().getType();
                        SelectingRoute selectingRoute = new SelectingRoute(journey);
                        selectingRoute.addRoutePrice(totalCost);
                        //lo = selectingRoute.getCheapestRoute(totalCost, ticketType).getLegs();
                    }
                }
//                    mMap.clear();
//                addingPaths(lo, 1);


            }

            @Override
            public void onFailure(Call<List<TicketPrice>> call, Throwable t) {
                Log.d(TAG, "tick price failure " + call.request().url().toString() + " " + t.toString());

            }
        });

    }
    public void drawPathLine() {
        MainActivity.mMap.clear();
    //Example below


       // final LatLng fromWembley = MainActivity.travelFrom;
       // final LatLng toEdgaware = MainActivity.travelTo;



        for(int i =0;i<journey.length;i++){
           // Log.d(TAG, " I " + i + " "  +journey.length);
            Legs [] legs = journey[i].getLegs();
            if(i == 0){
                //Log.d(TAG, "a1");
                addingPaths(legs, 1);
            }
            else if(i == 1){
               // Log.d(TAG, "a2");

                addingPaths(legs, 2);
            }
            else if(i == 2){
               // Log.d(TAG, "a3");

                addingPaths(legs, 3);
            }
            else if(i == 3){
                //Log.d(TAG, "a4");

                addingPaths(legs, 4);
            }
        }


//        Legs [] legs = journey[0].getLegs();
//        Legs [] legs1 = journey[1].getLegs();
//        Legs [] legs2 = journey[2].getLegs();
//        Legs [] legs3 = journey[3].getLegs();
//
//        addingPaths(legs, 1);
//        addingPaths(legs1, 2);
//        addingPaths(legs2, 3);
//        addingPaths(legs3, 4);

        CameraUpdate location = CameraUpdateFactory.newLatLngZoom(new LatLng(51.564072184349996, -0.27827641759000005), 14);
        MainActivity.mMap.animateCamera(location);

        //                IconGenerator iconGenerator = new IconGenerator(this);
//                addIcon(iconGenerator, "Disruption", new LatLng(51.564072184349996, -0.27827641759000005));
//
        Disruption a = new Disruption(new LatLng(51.564072184349996, -0.27827641759000005));
        a.drawDisruption();




    }




    public void addingPaths(Legs[] legs, int colourChoice){

//        final LatLng fromWembley = MainActivity.travelFrom;
//        final LatLng toEdgware = MainActivity.travelTo;


        DrawMarker.getInstance(mapContext).draw(MainActivity.mMap, from, R.drawable.pin_start_24dp );
        DrawMarker.getInstance(mapContext).draw(MainActivity.mMap, to, R.drawable.pin_end_24dp);


        List<PatternItem> patternWalk = Arrays.<PatternItem>asList(new Dot());
        List<PatternItem> patternTube = Arrays.<PatternItem>asList(new Dot(), new Gap(20), new Dash(30), new Gap(20));
        List<PatternItem> patternOverground = Arrays.<PatternItem>asList(new Dot(), new Gap(20), new Dash(30), new Gap(20));

        for(int i =0;i<legs.length;i++){
            if(legs[i].getModeName().equals("walking"))
            {
                addPolyline(legs, i, colourChoice, patternWalk);
            }
            else if(legs[i].getModeName().equals("tube"))
            {
                addPolyline(legs, i, colourChoice, patternTube);

            }else if(legs[i].getModeName().equals("overground"))
            {
                addPolyline(legs, i, colourChoice, patternOverground);
            }
            else if(legs[i].getModeName().equals("bus"))
            {
                addPolyline(legs, i, colourChoice);
            }
            else{
                addPolyline(legs, i, colourChoice);

            }

        }
    }

    public void addPolyline(Legs [] legs, int i,  int colorChoice,List<PatternItem> pattern ){
        PolylineOptions polylineOptions = new PolylineOptions()
                .clickable(true)
                .addAll(legs[i].getPath().getLineString())
                .pattern(pattern);


        switch (colorChoice){
            case 1: polylineOptions.color(Color.BLACK);
            break;
            case 2: polylineOptions.color(Color.BLUE);
            break;
            case 3: polylineOptions.color(Color.RED);
            break;
            case 4: polylineOptions.color(Color.GREEN);
                break;
        }

        Polyline polyline = MainActivity.mMap.addPolyline(polylineOptions);
        polyline.setTag(legs[i].getInstruction() + " and Arrive in " + legs[i].getDuration() + " minute");
        MainActivity.mMap.setOnPolylineClickListener(this);
    }


    public void addPolyline(Legs [] legs, int i,  int colorChoice ){
        PolylineOptions polylineOptions = new PolylineOptions()
                .clickable(true)
                .addAll(legs[i].getPath().getLineString());

        switch (colorChoice){
            case 1: polylineOptions.color(Color.BLACK);
                break;
            case 2: polylineOptions.color(Color.BLUE);
                break;
            case 3: polylineOptions.color(Color.RED);
                break;
            case 4: polylineOptions.color(Color.GREEN);
                break;
        }

        Polyline polyline = MainActivity.mMap.addPolyline(polylineOptions);
        polyline.setTag(legs[i].getInstruction() + " and Arrive in " + legs[i].getDuration() + " minute");
        MainActivity.mMap.setOnPolylineClickListener(this);


    }

    @Override
    public void onPolylineClick(Polyline polyline) {
        // Flip from solid stroke to dotted stroke pattern.

        Toast.makeText(mapContext, polyline.getTag().toString(),
                Toast.LENGTH_SHORT).show();
    }






    public void getLineData(BusStopResponse busStopResponse) {
         StopPointSequences[] bsResponse = busStopResponse.getStopPointSequences();

        String replaceBrackets = Arrays.toString(bsResponse).replaceAll("\\[|\\]|", "");
        String[] newStopPointSequence = replaceBrackets.split(",");

        //Log.d(TAG, "BLAB " + newStopPointSequence.length + " " + Arrays.toString(newStopPointSequence));

        for (int i = 0; i < newStopPointSequence.length; i++) {
            newStopPointSequence[i] = newStopPointSequence[i].replace("&", ",");
        }

        napId = new String[newStopPointSequence.length];
        stopName = new String[newStopPointSequence.length];
        MainActivity.latLng = new ArrayList<>();

        for (int i = 0; i < newStopPointSequence.length; i++) {
            //Log.d(TAG, " i " + i);
            int firstPart = 0;
            int secondPart = 0;
            for (int j = 0; j < newStopPointSequence[i].length(); j++) {
                String word = newStopPointSequence[i];
                //Log.d(TAG, " new word " + word);
                char c = word.charAt(j);
                if (c == '*') {
                    firstPart = word.indexOf(c);
                    napId[i] = word.substring(0, word.indexOf(c));
                    //response[i].setId(napId[i], i);
                } else if (c == '|') {
                    //Log.d(TAG, "  trert " + i);
                    secondPart = word.indexOf(c);
                    stopName[i] = word.substring((firstPart + 1), word.indexOf(c));
                    //response[i].setIdName(stopName[i], i);
                } else if (c == ',') {

                    String latitude = (word.substring((secondPart + 1), (word.indexOf(c))));
                    String longitude = (word.substring((word.indexOf(c) + 1), (word.length() - 1)));
                    MainActivity.latLng.add(new LatLng(Double.valueOf(latitude), Double.valueOf(longitude)));
                    if (i == (newStopPointSequence.length / 2)) {
                        MainActivity.middleLat = new LatLng(Double.valueOf(latitude), Double.valueOf(longitude));
                    }
                }
            }

        }

        // Log.d(TAG, " LOG " + Arrays.toString(newStopPointSequence));
        Log.d(TAG, "  A " + Arrays.toString(napId) + " l " + napId.length);
        Log.d(TAG, "  B " + Arrays.toString(stopName)+" l " + stopName.length);
        Log.d(TAG, "  C " + MainActivity.latLng.toString()+" l " + MainActivity.latLng.size());
        //           Log.d(TAG, "  D " + response[0].getId());
//            Log.d(TAG, "  E " + response[0].getIdName());
//            Log.d(TAG, "  F " + response[0].getIdCoord());
        drawLine(busStopResponse.getOrderedLineRoutes());
        //addMarker( );

        // Log.d(TAG,"  dasdas " + q.getIdCoord() );

//        for(int i = 0;i<q.getLength();i++){
//            naptan_name.put(q.getId(i), q.getIdName(i));
//        }
//
//        for (String name: naptan_name.keySet()){
//            String key =name.toString();
//            String value = naptan_name.get(name).toString();
//            Log.d(TAG, "KEY " + key + " value " + value);
//
//        }


    }
//
//            @Override
//            public void onConnected(@Nullable Bundle bundle) {
//
//            }
//
//            @Override
//            public void onConnectionSuspended(int i) {
//
//            }
//
//            @Override
//            public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
//
//            }

public void drawLine(OrderedLineRoutes [] orderedLineRoutes) {
    CameraUpdate location = CameraUpdateFactory.newLatLngZoom(
            MainActivity.latLng.get(MainActivity.latLng.size() / 2), 12);
    MainActivity.mMap.animateCamera(location);

    for (int i = 1; i < MainActivity.latLng.size(); i++) {
        float rotationDegrees = (float) com.example.frank.busmap.LatLngUtils.angleFromCoordinate(MainActivity.latLng.get(i).latitude, MainActivity.latLng.get(i).longitude, MainActivity.latLng.get((i - 1)).latitude, MainActivity.latLng.get((i - 1)).longitude);
        //Drawing lines
        DrawRouteMaps.getInstance(mapContext)
                .draw(MainActivity.latLng.get((i - 1)), MainActivity.latLng.get(i), MainActivity.mMap);
        //Drawing arrows in between stops
        //LatLng middlePos = com.example.frank.busmap.LatLngUtils.midPoint(latLng.get((i-1)).latitude, latLng.get((i-1)).longitude, latLng.get(i).latitude, latLng.get(i).longitude);
        //DrawMarker.getInstance(mapContext).draw(mMap, middlePos, R.drawable.up_arrow_24dp, stopName[(i-1)], rotationDegrees);

        //Drawing markers
        if (i == 1) {
            DrawMarker.getInstance(mapContext).draw(MainActivity.mMap, MainActivity.latLng.get(0), R.drawable.pin_start_24dp, stopName[(i - 1)] , MainActivity.direction + " towards" + orderedLineRoutes[0].getName() );
        } else if (i == (MainActivity.latLng.size() - 1)) {
            DrawMarker.getInstance(mapContext).draw(MainActivity.mMap, MainActivity.latLng.get(MainActivity.latLng.size() - 1), R.drawable.pin_end_24dp, stopName[(MainActivity.latLng.size() - 1)], MainActivity.direction + " towards" + orderedLineRoutes[0].getName());
        } else {
            DrawMarker.getInstance(mapContext).draw(MainActivity.mMap, MainActivity.latLng.get((i - 1)), R.drawable.pin_every_24dp, stopName[(i - 1)], MainActivity.direction + " towards" + orderedLineRoutes[0].getName());
            DrawMarker.getInstance(mapContext).draw(MainActivity.mMap, MainActivity.latLng.get(i), R.drawable.pin_every_24dp, stopName[i], MainActivity.direction + " towards" + orderedLineRoutes[0].getName());
        }


    }

    //NEEDS TO BE ABLE TO SIMULTANEOUSLY LOOP THROUGH ARRAY
    DrawMarker.getInstance(mapContext).animateMarkerToGB(mMap, R.drawable.bus_24dp,  MainActivity.latLng.get(0),MainActivity.latLng.get(1), new LatLngInterpolator.Linear(), 15000);
    DrawMarker.getInstance(mapContext).animateMarkerToGB(mMap, R.drawable.bus_24dp,  MainActivity.latLng.get(MainActivity.latLng.size()/2),MainActivity.latLng.get((MainActivity.latLng.size()/5)), new LatLngInterpolator.Linear(), 20000);
    DrawMarker.getInstance(mapContext).animateMarkerToGB(mMap, R.drawable.bus_24dp,  MainActivity.latLng.get(6),MainActivity.latLng.get(7), new LatLngInterpolator.Linear(), 15000);
    DrawMarker.getInstance(mapContext).animateMarkerToGB(mMap, R.drawable.bus_24dp,  MainActivity.latLng.get(MainActivity.latLng.size()-1),MainActivity.latLng.get((MainActivity.latLng.size())-10), new LatLngInterpolator.Linear(), 30000);



}


}
