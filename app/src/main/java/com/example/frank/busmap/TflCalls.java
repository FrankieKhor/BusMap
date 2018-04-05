package com.example.frank.busmap;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ahmadrosid.lib.drawroutemap.DrawMarker;
import com.ahmadrosid.lib.drawroutemap.DrawRouteMaps;
import com.ahmadrosid.lib.drawroutemap.LatLngInterpolator;
import com.example.frank.busmap.GenticAlgorithm.Individuals;
import com.example.frank.busmap.GenticAlgorithm.Initialisation;
import com.example.frank.busmap.GenticAlgorithm.Stops;
import com.example.frank.busmap.Pojo.Rest.BackgroundService;
import com.example.frank.busmap.Pojo.Rest.ServiceGenerator;
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
import com.google.android.gms.maps.model.LatLng;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by frank on 05/03/2018.
 */

public class TflCalls implements MyResultReceiver.Receiver{
    private TflApi tflApi;
    private static final String TAG = TflCalls.class.getName();
    private Context mapContext ;
    static Journeys[] journey;
    public static final LatLng from = new LatLng(51.533158, -0.469211);
    public static final LatLng to = new LatLng(51.558656 , -0.276659);
    //These won't be here normally, testing purposes
    public static final LatLng wayPoint1 = new LatLng(51.563905 , -0.443555);
    public static final LatLng wayPoint11 = new LatLng(51.598937 , -0.386461);
    public static final LatLng wayPoint2 = new LatLng(51.542031 , -0.377702);
    public static final LatLng wayPoint22 = new LatLng(51.577549 , -0.327062);
    public static final LatLng wayPoint3 = new LatLng(51.510263 , -0.356443);
    public static final LatLng wayPoint33 = new LatLng(51.564804 , -0.282457);

    public static boolean opple = false;
    public static final String PREFS_FILE_NAME = "MyPrefsFile";
    public static GoogleMap mMap;
    private List<BusArrivalResponse> StudentData;
    private List<StopPointArrival> STOPDATA;
    private List<LatLng> latLng;
    private String[] napId;
    private String[] stopName;
    private static String[] stopArrivalResponse;
    public static String travelFrom = "";
    public static String travelTo ="" ;
    private Activity mapActivity;
    public static int BUS_STOP_LINE_RESPONSE_CODE;
    private final int RUNNING = 1;
    private final int FINISHED = 2;
    private final int ERROR = 3;
    public static ArrayList<Stops> section1 = new ArrayList<Stops>();
    public static ArrayList<Stops> section2 = new ArrayList<Stops>();
    public static ArrayList<Stops> section3 = new ArrayList<Stops>();
    public MyResultReceiver mReceiver;

    public TflCalls(Context context, GoogleMap mMap, Activity mapActivity, MyResultReceiver mReceiver)
    {
        mapContext = context;
        this.mMap = mMap;
        this.mapActivity = mapActivity;
        this.mReceiver = mReceiver;
        this.mReceiver.setReceiver(this);
    }

    public void onPause()
    {
        mReceiver.setReceiver(null); // clear receiver so no leaks.
    }

    public void onReceiveResult(int resultCode, Bundle resultData)
    {
        Log.d(TAG, "RESULT " + resultCode);
        switch (resultCode)
        {
            case RUNNING:
                Log.d(TAG, "RUNNING");
                //show progress bar
                break;
            case FINISHED:
                Log.d(TAG, "FINISHED");
                int length = resultData.getInt("length");
                section1 = Parcels.unwrap(resultData.getParcelable("section1"));
                section2 = Parcels.unwrap(resultData.getParcelable("section2"));
                section3 = Parcels.unwrap(resultData.getParcelable("section3"));
                if(length >=6)
                {
                    Initialisation initialisation = new Initialisation(section1, section2, section3);
                    //initialisation.setUp(1, 1);
                    initialisation.comparePath(1, 1,4);
//                    Log.d(TAG, "S1" + length + " " + section1.toString());
//                    Log.d(TAG, "S2" + length + " " + section2.toString());
//                    Log.d(TAG, "S3" + length + " " + section3.toString());
                    Toast.makeText(mapContext, "COMPLETED", Toast.LENGTH_LONG).show();
                }
                break;
            case ERROR:
                Log.d(TAG, "ERROR");
                break;
        }
    }

    public void createRetrofit()
    {
        tflApi  = ServiceGenerator.createTflClient(TflApi.class);
    }

    public void choice(int API_CALL_CHOICE, String appId, String apiKey)
    {
        switch (API_CALL_CHOICE)
        {
            case 1:
                callBusLine(appId, apiKey);
                break;
            case 2:
                callAlternativeRoute(appId, apiKey);
                break;
            case 3:
                callTicketPrice(appId, apiKey);
        }
    }

    public void callBusLine(String appId, String apiKey)
    {
        getBusStopCall(appId, apiKey);
        //getBusArrivalCall(appId, apiKey);
        //NEEDS TO FIX
        //getBusStopArrival(appId, apiKey);
    }

    public void callAlternativeRoute(String appId, String apiKey)
    {
        getBusJourney(appId, apiKey);
        //getBusTicketPrice(appId, apiKey);
    }

    public void callTicketPrice(String appId, String apiKey){
        getBusTicketPrice(appId, apiKey);
    }

    private void getBusStopCall(final String appId, final String apiKey)
    {
        Call<BusStopResponse> busStopCall = tflApi.getAllBusStops(MainActivity.busLineID, MainActivity.direction, appId, apiKey);
        busStopCall.enqueue(new Callback<BusStopResponse>()
        {
            @Override
            public void onResponse(Call<BusStopResponse> call, Response<BusStopResponse> response)
            {
                Log.d(TAG, " Getting response from all bus stops " + call.request().url().toString());
                if (response.isSuccessful())
                {
                    Log.d(TAG, " BUS STOP Response success " );
                    BUS_STOP_LINE_RESPONSE_CODE = response.code();
                    mMap.clear();
                    getLineData(response.body());
                    MainActivity.cBusArrivalisFinished = true;
                } else
                {
                    Log.d(TAG, " Awaiting response");
                }
            }

            @Override
            public void onFailure(Call<BusStopResponse> call, Throwable t)
            {
                if(t.toString().equals(mapActivity.getString(R.string.tfl_time_out)));
                {
                    Log.d(TAG, t.toString() + " SOMETHING IS WRONG");
                    call.clone();
                    Toast.makeText(mapContext, "Error: Retrying", Toast.LENGTH_SHORT);
                }
            }
        });
    }

    public void getBusArrivalCall(String appId, String apiKey)
    {
        if (MainActivity.cBusArrivalisFinished = true)
        {
            Call<List<BusArrivalResponse>> busArrivalCall = tflApi.getBusArrival(MainActivity.busLineID, appId, apiKey);
            busArrivalCall.enqueue(new Callback<List<BusArrivalResponse>>()
            {

                @Override
                public void onResponse(Call<List<BusArrivalResponse>> call, Response<List<BusArrivalResponse>> response)
                {
                    Log.d(TAG, " Getting response from Bus Arrival " + call.request().url().toString());
                    //    Log.d(TAG, "URL " + call.request().url());

                    //Creating object
                    if (response.isSuccessful())
                    {
                        Log.d(TAG, " ARRIVAL BUS Response success");
                        StudentData = response.body();
                        Collections.sort(StudentData);
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
                    } else
                    {
                        Log.d(TAG, " Awaiting response");
                    }
                    //getStationsBetween();
                }


                @Override
                public void onFailure(Call<List<BusArrivalResponse>> call, Throwable t)
                {
                    Log.d(TAG, t.toString() + " SOMETHING IS WRONG");
                }
            });
        }
    }

    //NEEDS WAY TO ONLY SHOW ONE
    private void getBusStopArrival(final String appId, final String apiKey)
    {
        if (MainActivity.cBusArrivalisFinished)
        {
            String[] qewty = {"4900801618", "490005530W"};
            for (int i = 0; i < napId.length; i++)
            {
                final Call<List<StopPointArrival>> StopArrival = tflApi.getStopArrival(napId[i], appId, apiKey);
                StopArrival.enqueue(new Callback<List<StopPointArrival>>()
                {
                    @Override
                    public void onResponse(Call<List<StopPointArrival>> call, Response<List<StopPointArrival>> response)
                    {
                        Log.d(TAG, " Getting response from Bus Stops " + call.request().url().toString());
                        if (response.isSuccessful())
                        {
                            Log.d(TAG, " BUS STOP ARRIVAL Response success");
                            STOPDATA = response.body();
                            //Collections.sort(STOPDATA);

                            for (int i = 0; i < response.body().size(); i++)
                            {
                                Log.d(TAG, "STATION " + STOPDATA.get(i).getStationName() + " time to statio " + STOPDATA.get(i).getTimeToStation());
                            }
//                    for(int i =0;i<napId.length;i++){
//                        stopArrivalResponse = (napId[i]);
//                        Log.d(TAG, "BLOBLOB " + response.body().getExpectedArrival());
//
//                    }
                        }
                    }

                    @Override
                    public void onFailure(Call<List<StopPointArrival>> call, Throwable t)
                    {
                        Log.d(TAG, " GEtting BUS STOP ARRIVAL FAILURE " + call.request().url().toString());

                        Log.d(TAG, " BUS STOP ARRIVAL FAILURE " + t.toString());

                    }

                });
            }
        }
    }

    public void getBusJourney(final String appId, final String apiKey)
    {
        //final String from = MainActivity.travelFrom.toString();
        //final String to = MainActivity.travelTo.toString();
        String f = from.latitude + ", " + from.longitude;
        String t = to.latitude + ", " + to.longitude;
        Call<JourneyFromToResponse> busJourney = tflApi.getJourney(f, t, appId, apiKey);
        Log.d(TAG, " Getting response from Bus journey ");

        busJourney.enqueue(new Callback<JourneyFromToResponse>()
        {
            @Override
            public void onResponse(Call<JourneyFromToResponse> call, Response<JourneyFromToResponse> response)
            {
                MainActivity.layoutBusDirection.setVisibility(View.GONE);
                MainActivity.layoutBusRoute.setVisibility(View.VISIBLE);
                MainActivity.sup.setPanelHeight(110);
                Log.d(TAG, "Bus journey success " + call.request().url().toString());
                mMap.clear();

                JourneyFromToResponse a = response.body();
                journey = a.getJourneys();
                TextView fromLocation = (TextView) mapActivity.findViewById(R.id.fromLocation);
                fromLocation.setText(String.valueOf(TflCalls.travelFrom));
                TextView toLocation = (TextView) mapActivity.findViewById(R.id.toLocation);
                toLocation.setText(String.valueOf(TflCalls.travelTo));
                for(int i =0;i<journey.length;i++){
                    DrawingPath drawingPath= new DrawingPath(mMap, mapContext);
                    drawingPath.addingPaths(journey[i].getLegs());
                }
                getJourneyLegHardCoded(appId, apiKey);

                  //getBusJourneyLegs(appId, apiKey);

            }

            @Override
            public void onFailure(Call<JourneyFromToResponse> call, Throwable t)
            {
                Toast.makeText(mapContext, "Failed, try again",
                        Toast.LENGTH_SHORT).show();
                if(t.toString().equals("java.net.SocketTimeoutException: timeout"))
                {
                    call.clone();
                }
                Log.d(TAG, "Bus journey failure " + call.request().url().toString());
                Log.d(TAG, " " + t.toString() + "      " + mapActivity.getString(R.string.tfl_time_out));
            }
        });
    }

    public void getJourneyLegHardCoded(String apiKey, String appId){
        List<LatLng> path1 = Arrays.asList(from, wayPoint1, to);
        List<LatLng> path2 = Arrays.asList(from, wayPoint2, to);
        List<LatLng> path3 = Arrays.asList(from, wayPoint3, to);
        int fromWhatArray = 1;
        int pathList = 0;
        String from  = "";
        String to = "";
        int arraySection = 1;
        //Loops 3 time where each array list represent all the value of each path
        for(int i =0;i<3;i++)
        {
            if(i ==0)
            {
                pathList = path1.size();
                fromWhatArray = 1;
                arraySection = 1;
            }
            else if(i == 1)
            {
                fromWhatArray = 2;
                pathList = path2.size();
                arraySection = 2;
            }
            else if(i == 2)
            {
                fromWhatArray = 3;
                pathList = path3.size();
                arraySection = 3;
            }else{
                Log.d(TAG, "ERRRRRRRRRRRRRRRRRORRRRRRRRRRRRRRRR");
            }


            for (int j = 0; j < pathList - 1; j++)
            {
                if(fromWhatArray == 1){
                    from = path1.get(j).latitude + "," + path1.get(j).longitude;
                    to = path1.get(j + 1).latitude + "," + path1.get(j + 1).longitude;
                }else if(fromWhatArray == 2){
                    from = path2.get(j).latitude + "," + path2.get(j).longitude;
                    to = path2.get(j + 1).latitude + "," + path2.get(j + 1).longitude;

                }else if(fromWhatArray == 3){
                    from = path3.get(j).latitude + "," + path3.get(j).longitude;
                    to = path3.get(j + 1).latitude + "," + path3.get(j + 1).longitude;
                }

                    createBusWayPoint(apiKey, appId, from, to, arraySection, fromWhatArray);
                }

        }
    }

    public void getBusJourneyLegs(String apiKey, String appId)
    {
        Log.d(TAG, "GETTING JOURNEYT LEG");
        List<LatLng> wayPoint1 = new ArrayList<>();
        List<LatLng> wayPoint2 = new ArrayList<>();
        List<LatLng> wayPoint3 = new ArrayList<>();
        //List<LatLng> wayPoint4 = new ArrayList<>();

        for (int i = 0; i < journey.length; i++)
        {
            Legs[] legs = journey[i].getLegs();
            Log.d(TAG, "I " + i + " " + journey[i].getDuration() + " legs " + legs.length) ;

            for (int j = 1; j < 3; j++)
            {
                LatLng from = new LatLng(Double.valueOf(legs[(j - 1)].getArrivalPoint().getLat()), Double.valueOf(legs[(j - 1)].getArrivalPoint().getLon()));
                LatLng to = new LatLng(Double.valueOf(legs[j].getArrivalPoint().getLat()), Double.valueOf(legs[j].getArrivalPoint().getLon()));
                Log.d(TAG, "From " + from + " " + to);

                if (i == 0)
                {
                    wayPoint1.add(from);
                    wayPoint1.add(to);
                }
                else if (i == 1)
                {
                    wayPoint2.add(from);
                    wayPoint2.add(to);
                }
                else if (i == 2)
                {
                    wayPoint3.add(from);
                    wayPoint3.add(to);
                }
            }

            DrawingPath drawingPath= new DrawingPath(mMap, mapContext);
            drawingPath.addingPaths(legs);

            Log.d(TAG, "-----------------Seperator------------------------ ");
        }
        removeDuplicate(wayPoint1);
        removeDuplicate(wayPoint2);
        removeDuplicate(wayPoint3);

        for(int i =0;i<journey.length;i++)
        {
            int size =0;
            if(i == 0)
            {
                size = wayPoint1.size();
            }
            else if(i == 1)
            {
                size = wayPoint2.size();
            }
            else if(i == 2)
            {
                size = wayPoint3.size();
            }
            Log.d(TAG, "SIZE " + size);
            for (int j = 1; j < size; j++)
            {
                String f = "";
                String t = "";
                if(i ==0)
                {
                    f = wayPoint1.get((j - 1)).latitude + ", " + wayPoint1.get((j - 1)).longitude;
                    t = wayPoint1.get(j).latitude + ", " + wayPoint1.get(j).longitude;

                }
                else if(i == 1)
                {
                    f = wayPoint2.get((j - 1)).latitude + ", " + wayPoint2.get((j - 1)).longitude;
                    t = wayPoint2.get(j).latitude + ", " + wayPoint2.get(j).longitude;

                }
                else if(i == 2)
                {
                    f = wayPoint3.get((j - 1)).latitude + ", " + wayPoint3.get((j - 1)).longitude;
                    t = wayPoint3.get(j).latitude + ", " + wayPoint3.get(j).longitude;
                }
                Log.d(TAG, "FFFFFF " + f + " - " + t);
                createBusWayPoint(apiKey, appId, f, t,1, 1);
            }
            Log.d(TAG, "-----------------Seperator------------------------ ");
        }
//        if(BackgroundService.wayPointCount == journey.length-1){
//            Initialisation initialisation = new Initialisation(BackgroundService.holdLat, BackgroundService.section2);
//            initialisation.setUp();
//
//        }

    }

    public List<LatLng> removeDuplicate(List<LatLng> wayPoint)
    {
        HashSet<LatLng> hashSet = new LinkedHashSet<>();
        for(LatLng a: wayPoint)
        {
            hashSet.add(a);
        }
        wayPoint.clear();
        for(LatLng q: hashSet)
        {
            wayPoint.add(q);
        }
        return  wayPoint;
    }

    public void createBusWayPoint(String appId, String apiKey, final String from, final String to, int choice, int fromArray )
    {
        Log.d(TAG, "createBusWayPoint itnent ");
        final Intent intent = new Intent(Intent.ACTION_SYNC, null, mapContext, BackgroundService.class);
        Bundle bundle = new Bundle();
        bundle.putString("From", from);
        bundle.putString("To", to);
        bundle.putString("AppId", appId);
        bundle.putString("ApiKey", apiKey);
        bundle.putInt("Choice", choice);
        bundle.putString("command", "getLegs");
        bundle.putInt("ArrayChoice", fromArray);
        intent.putExtra("receiver", mReceiver);

        intent.putExtras(bundle);
        mapContext.startService(intent);

    }

    //Will be changed to sync request and  nap id will not be manually inputted
    public void getBusTicketPrice(String appId, String apiKey)
    {
        String stopPointFrom = "940GZZLUWYP";
        String stopPointTo = "940GZZLUEGW";
        Call<List<TicketPrice>> getTickTock = tflApi.getTicketPrices(stopPointFrom, stopPointTo, appId, apiKey );
        Log.d(TAG, " Getting ticket response" );

        getTickTock.enqueue(new Callback<List<TicketPrice>>()
        {
            @Override
            public void onResponse(Call<List<TicketPrice>> call, Response<List<TicketPrice>> response)
            {
                Log.d(TAG, "Bus ticket price "+call.request().url().toString());
                List<TicketPrice> rb = response.body();
                double totalCost;
                String ticketType;
                Legs [] lo = new Legs[0];
                for(int i =0;i<rb.size()-1;i++)
                {
                    Rows[] row = rb.get(i).getRows();
                    TicketsAvailable[] ticketsAvailable =  row[i].getTicketsAvailable();

                    //ONly using offpeak but ticketsAvailable.length would use this
                    for(int j =0;j<1;j++)
                    {
                        totalCost = Double.valueOf(ticketsAvailable[j].getCost());
                        ticketType = ticketsAvailable[j].getTicketTime().getType();
                        //lo = selectingRoute.getCheapestRoute(totalCost, ticketType).getLegs();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<TicketPrice>> call, Throwable t)
            {
                Log.d(TAG, "tick price failure " + call.request().url().toString() + " " + t.toString());

            }
        });
    }

    public void getLineData(BusStopResponse busStopResponse)
    {
        StopPointSequences[] bsResponse = busStopResponse.getStopPointSequences();

        //Removes [,|,] characters and split them based on comma
        String replaceBrackets = Arrays.toString(bsResponse).replaceAll("\\[|\\]|", "");
        String[] newStopPointSequence = replaceBrackets.split(",");
        for (int i = 0; i < newStopPointSequence.length; i++)
        {
            newStopPointSequence[i] = newStopPointSequence[i].replace("&-", ",-");
        }
        napId = new String[newStopPointSequence.length];
        stopName = new String[newStopPointSequence.length];
        latLng = new ArrayList<>();

        for (int i = 0; i < newStopPointSequence.length; i++)
        {
            //Log.d(TAG, " i " + i);
            int firstPart = 0;
            int secondPart = 0;
            for (int j = 0; j < newStopPointSequence[i].length(); j++)
            {
                String word = newStopPointSequence[i];
                //Log.d(TAG, " new word " + word);
                char c = word.charAt(j);
                if (c == '*')
                {
                    firstPart = word.indexOf(c);
                    napId[i] = word.substring(0, word.indexOf(c));
                    //response[i].setId(napId[i], i);
                }
                else if (c == '|')
                {
                    //Log.d(TAG, "  trert " + i);
                    secondPart = word.indexOf(c);
                    stopName[i] = word.substring((firstPart + 1), word.indexOf(c));
                    //response[i].setIdName(stopName[i], i);
                }
                else if (c == ',')
                {
                    String latitude = (word.substring((secondPart + 1), (word.indexOf(c))));
                    String longitude = (word.substring((word.indexOf(c) + 1), (word.length() - 1)));
                    latLng.add(new LatLng(Double.valueOf(latitude), Double.valueOf(longitude)));
                    if (i == (newStopPointSequence.length / 2))
                    {
                        MainActivity.middleLat = new LatLng(Double.valueOf(latitude), Double.valueOf(longitude));
                    }
                }
            }
        }

        // Log.d(TAG, " LOG " + Arrays.toString(newStopPointSequence));
        Log.d(TAG, "  A " + Arrays.toString(napId) + " l " + napId.length);
        Log.d(TAG, "  B " + Arrays.toString(stopName)+" l " + stopName.length);
        Log.d(TAG, "  C " + latLng.toString()+" l " + latLng.size());
        //           Log.d(TAG, "  D " + response[0].getId());
//            Log.d(TAG, "  E " + response[0].getIdName());
//            Log.d(TAG, "  F " + response[0].getIdCoord());
        drawBusLine(busStopResponse.getOrderedLineRoutes());
        //addMarker( );
    }

    //Possible solution for drawing all the lines between station is to use PolyUtil.encode
    public void drawBusLine(OrderedLineRoutes [] orderedLineRoutes)
    {
        CameraUpdate location = CameraUpdateFactory.newLatLngZoom(
                latLng.get(latLng.size() / 2), 12);
        mMap.animateCamera(location);

        for (int i = 1; i < latLng.size(); i++) {
            float rotationDegrees = (float) com.example.frank.busmap.LatLngUtils.angleFromCoordinate(latLng.get(i).latitude, latLng.get(i).longitude, latLng.get((i - 1)).latitude, latLng.get((i - 1)).longitude);
            //Drawing lines
            DrawRouteMaps.getInstance(mapContext).draw(latLng.get((i - 1)), latLng.get(i), mMap);
            //Drawing arrows in between stops
            //LatLng middlePos = com.example.frank.busmap.LatLngUtils.midPoint(latLng.get((i-1)).latitude, latLng.get((i-1)).longitude, latLng.get(i).latitude, latLng.get(i).longitude);
            //DrawMarker.getInstance(mapContext).draw(mMap, middlePos, R.drawable.up_arrow_24dp, stopName[(i-1)], rotationDegrees);

            //Drawing markers
            if (i == 1)
            {
                DrawMarker.getInstance(mapContext).draw(mMap, latLng.get(0), R.drawable.pin_start_24dp, stopName[(i - 1)], MainActivity.direction + " towards" + orderedLineRoutes[0].getName());
            }
            else if (i == (latLng.size() - 1))
            {
                DrawMarker.getInstance(mapContext).draw(mMap, latLng.get(latLng.size() - 1), R.drawable.pin_end_24dp, stopName[(latLng.size() - 1)], MainActivity.direction + " towards" + orderedLineRoutes[0].getName());
            }
            else
            {
                DrawMarker.getInstance(mapContext).draw(mMap, latLng.get((i - 1)), R.drawable.pin_every_24dp, stopName[(i - 1)], MainActivity.direction + " towards" + orderedLineRoutes[0].getName());
                DrawMarker.getInstance(mapContext).draw(mMap, latLng.get(i), R.drawable.pin_every_24dp, stopName[i], MainActivity.direction + " towards" + orderedLineRoutes[0].getName());
            }


        }

        //NEEDS TO BE ABLE TO SIMULTANEOUSLY LOOP THROUGH ARRAY
        DrawMarker.getInstance(mapContext).animateMarkerToGB(mMap, R.drawable.bus_24dp, latLng.get(0), latLng.get(1), new LatLngInterpolator.Linear(), 15000);
        DrawMarker.getInstance(mapContext).animateMarkerToGB(mMap, R.drawable.bus_24dp, latLng.get(latLng.size() / 2), latLng.get((latLng.size() / 5)), new LatLngInterpolator.Linear(), 20000);
        DrawMarker.getInstance(mapContext).animateMarkerToGB(mMap, R.drawable.bus_24dp, latLng.get(6), latLng.get(7), new LatLngInterpolator.Linear(), 15000);
        DrawMarker.getInstance(mapContext).animateMarkerToGB(mMap, R.drawable.bus_24dp, latLng.get(latLng.size() - 1), latLng.get((latLng.size()) - 10), new LatLngInterpolator.Linear(), 30000);
    }

    public void getStationsBetween()
    {
        Log.d(TAG, "MOO " + napId.length);

        String towardsNaptan = "";
        String previousNaptan = "";
        double distance ;
        for(int i =1;i<=(napId.length-1);i++)
        {
            Log.d(TAG, " LAT " + latLng.get(i).toString());
            String op = napId[i];
            // Log.d(TAG, "MATCH " + napId[i-1] + " previous " + napId[i]);
            if(napId[(i-1)].equals(op))
            {
                previousNaptan = napId[i];
                // distance = SphericalUtil.computeDistanceBetween();
            }
        }
    }
}
