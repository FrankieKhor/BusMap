package com.example.frank.busmap;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ahmadrosid.lib.drawroutemap.DrawMarker;
import com.ahmadrosid.lib.drawroutemap.DrawRouteMaps;
import com.arsy.maps_library.MapRipple;
import com.example.frank.busmap.Pojo.getAllBusStops.BusStopResponse;
import com.example.frank.busmap.Pojo.getAllBusStops.OrderedLineRoutes;
import com.example.frank.busmap.Pojo.getAllBusStops.StopPoint;
import com.example.frank.busmap.Pojo.getAllBusStops.StopPointSequences;
import com.example.frank.busmap.Pojo.getBusArrival.BusArrivalResponse;
import com.example.frank.busmap.Pojo.getJourneyFromTo.JourneyFromToResponse;
import com.example.frank.busmap.Pojo.getJourneyFromTo.Journeys;
import com.example.frank.busmap.Pojo.getJourneyFromTo.Legs;
import com.example.frank.busmap.Pojo.getStopPointArrival.StopPointArrival;
import com.example.frank.busmap.Rest.TflApi;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
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

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.frank.busmap.MapsActivity.getPixelsFromDp;
import static com.example.frank.busmap.MapsActivity.mMap;
import static com.example.frank.busmap.MapsActivity.napId;
import static com.example.frank.busmap.MapsActivity.stopName;

/**
 * Created by frank on 05/03/2018.
 */

public class TflCalls extends FragmentActivity implements GoogleMap.OnPolylineClickListener{
    private TflApi tflCall;
    private static final String TAG = TflCalls.class.getName();
    static Context mapContext = MapsActivity.getMapContext();

    static Journeys[] journey;


    //    String appId = getString(R.string.tfl_app_id);
//    String apiKey = getString(R.string.tfl_key);
    private static final String BASE_URL = "https://api.tfl.gov.uk/";

    public Retrofit createRetrofit() {

        if (MapsActivity.retrofit == null) {
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();
            MapsActivity.retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return MapsActivity.retrofit;
    }

    public void createRetrofitClass(){
        this.tflCall = MapsActivity.retrofit.create(TflApi.class);

    }

    public void choice(int API_CALL_CHOICE, String appId, String apiKey){
        switch (API_CALL_CHOICE) {
            case 1:
                callMapInfo(appId, apiKey);
                break;
            case 2:
                callAlternateBus(appId, apiKey);
                break;
        }
    }

    private void getBusStopCall(final String appId, final String apiKey) {

        Call<BusStopResponse> busStopCall = tflCall.getAllBusStops(MapsActivity.lineID, MapsActivity.direction, appId, apiKey);
        busStopCall.enqueue(new Callback<BusStopResponse>() {
            @Override
            public void onResponse(Call<BusStopResponse> call, Response<BusStopResponse> response) {
                Log.d(TAG, " Getting response from all bus stops");
                //  Log.d(TAG, "URL " + call.request().url());
                //Creating object
                if (response.isSuccessful()) {
                    Log.d(TAG, " BUS STOP Response success");
                    MapsActivity.mMap.clear();
                    getLineData(response.body());
                    MapsActivity.cBusArrivalisFinished = true;
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

//    public void createCustomInfoWindow(){
//        final MapWrapperLayout mapWrapperLayout = (MapWrapperLayout)findViewById(R.id.map_relative_layout);
//        mapWrapperLayout.init(mMap, getPixelsFromDp(this, 39 + 20));
//        this.infoWindow = (ViewGroup)getLayoutInflater().inflate(R.layout.custom_infowindow, null);
//
//        this.infoTitle = (TextView)infoWindow.findViewById(R.id.nameTxt);
//        this.infoSnippet = (TextView)infoWindow.findViewById(R.id.addressTxt);
//        this.infoButton1 = (Button)infoWindow.findViewById(R.id.btnOne);
//        this.infoButtonListener = new OnInfoWindowElemTouchListener(infoButton1, getResources().getDrawable(R.drawable.btn_bg), getResources().getDrawable(R.drawable.btn_bg)){
//            @Override
//            protected void onClickConfirmed(View v, Marker marker) {
//                // Here we can perform some action triggered after clicking the button
//                Toast.makeText(MapsActivity.this, "click on button 1", Toast.LENGTH_SHORT).show();
//            }
//        };
//        this.infoButton1.setOnTouchListener(infoButtonListener);
//
//
//        mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
//            @Override
//            public View getInfoWindow(Marker marker) {
//                Log.d(TAG, "MARKER " + marker.getId());
//                return null;
//            }
//
//            @Override
//            public View getInfoContents(Marker marker) {
//                // Setting up the infoWindow with current's marker info
//                infoSnippet.setText(marker.getTitle());
//                infoTitle.setText(marker.getSnippet());
//                infoButtonListener.setMarker(marker);
//
//                // We must call this to set the current marker and infoWindow references
//                // to the MapWrapperLayout
//                mapWrapperLayout.setMarkerWithInfoWindow(marker, infoWindow);
//                return infoWindow;
//            }
//        });
////        TextView tv = (TextView) findViewById(R.id.mobileTxt) ;
////        tv.setText("BOOOOOO");
//        // Let's add a couple of markers
//        mMap.addMarker(new MarkerOptions()
//                .position(latlng1)
//                .title("Source")
//                .snippet("Comapny Name")
//                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
//
//        mMap.addMarker(new MarkerOptions()
//                .position(latlng2)
//                .title("Destination")
//                .snippet("AmisunXXXXXX")
//                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));
//
//        //googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng, 15));
//        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latlng1, 10));
//        //Setting a location and moving camera
//        //getDeviceLocation();
//    }

    public void getBusArrivalCall(String appId, String apiKey) {
        if (MapsActivity.cBusArrivalisFinished = true) {
            Call<List<BusArrivalResponse>> busArrivalCall = tflCall.getBusArrival(MapsActivity.lineID, appId, apiKey);
            busArrivalCall.enqueue(new Callback<List<BusArrivalResponse>>() {

                @Override
                public void onResponse(Call<List<BusArrivalResponse>> call, Response<List<BusArrivalResponse>> response) {
                    Log.d(TAG, " Getting response from Bus Arrival");
                    //    Log.d(TAG, "URL " + call.request().url());

                    //Creating object
                    if (response.isSuccessful()) {
                        Log.d(TAG, " ARRIVAL BUS Response success");
                        MapsActivity.StudentData = response.body();
                        Collections.sort(MapsActivity.StudentData);
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
                        MapsActivity.cBusStopisFinished = true;
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
        if (MapsActivity.cBusArrivalisFinished) {
            String[] qewty = {"4900801618", "490005530W"};
            for (int i = 0; i < napId.length; i++) {
                final Call<List<StopPointArrival>> StopArrival = tflCall.getStopArrival(napId[i], appId, apiKey);
                StopArrival.enqueue(new Callback<List<StopPointArrival>>() {
                    @Override
                    public void onResponse(Call<List<StopPointArrival>> call, Response<List<StopPointArrival>> response) {
                        Log.d(TAG, " Getting response from Bus Stop");
                        if (response.isSuccessful()) {
                            Log.d(TAG, " BUS STOP ARRIVAL Response success");
                            MapsActivity.STOPDATA = response.body();
                            //Collections.sort(STOPDATA);

                            for (int i = 0; i < response.body().size(); i++) {
                                Log.d(TAG, "STATION " + MapsActivity.STOPDATA.get(i).getStationName() + " time to statio " + MapsActivity.STOPDATA.get(i).getTimeToStation());

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

    public void callAlternateBus(String appId, String apiKey) {
        getBusJourney(appId, apiKey);
    }
    public void getBusJourney(String appId, String apiKey) {
        final String from = "51.564169, -0.278199";
        final String to = "51.540703, -0.299549";

        Call<JourneyFromToResponse> busJourney = tflCall.getJourney(from, to, appId, apiKey);
        Log.d(TAG, " Getting response from Bus journey");

        busJourney.enqueue(new Callback<JourneyFromToResponse>() {
            @Override
            public void onResponse(Call<JourneyFromToResponse> call, Response<JourneyFromToResponse> response) {
                Log.d(TAG, "Bus journey success " + call.request().url().toString());

                JourneyFromToResponse a = response.body();
                Log.d(TAG, " " + a.getJourneys().length);
                //drawLineHardcode();
                drawPathLine(a);
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
    public void drawPathLine(JourneyFromToResponse jftr) {

        MapsActivity.mMap.clear();
        journey = jftr.getJourneys();
        Legs[] legs = journey[0].getLegs();
        Legs[] legs1 = journey[1].getLegs();
        Legs[] legs2 = journey[2].getLegs();
        Legs[] legs3 = journey[3].getLegs();
        CameraUpdate location = CameraUpdateFactory.newLatLngZoom(new LatLng(51.564072184349996, -0.27827641759000005), 14);
        MapsActivity.mMap.animateCamera(location);

        //                IconGenerator iconGenerator = new IconGenerator(this);
//                addIcon(iconGenerator, "Disruption", new LatLng(51.564072184349996, -0.27827641759000005));
//
//                MapRipple mapRipple = new MapRipple(mMap, new LatLng(51.564072184349996, -0.27827641759000005), mapContext);
//                mapRipple.withNumberOfRipples(2);
//                mapRipple.withDistance(0.1);
//                mapRipple.withFillColor(Color.BLUE);
//                mapRipple.withTransparency(0.5f);
//                mapRipple.withRippleDuration(5000) ;   //12000ms
//
//
//                if(mapRipple.isAnimationRunning()){
//}
//                mapRipple.startRippleMapAnimation();

        //legs[0].getPath().setLineString();
//        Log.d(TAG, "duration 1 " + journey[0].getDuration());
//        Log.d(TAG, "duration 2 " + journey[1].getDuration());
//        Log.d(TAG, "duration 3 " + journey[2].getDuration());

        Log.d(TAG, "legs " + legs[1].getPath().getLineString().toString());
        Log.d(TAG, "legs1 " + legs.length);
        Log.d(TAG, "legs2 " + legs1.length);
        Log.d(TAG, "legs3 " + legs2.length);
        Log.d(TAG, "legs4 " + legs3.length);


        addingPaths(legs, 1);
//        addingPaths(legs1, 2);
//        addingPaths(legs2, 3);
//        addingPaths(legs3, 4);


    }
    public void addingPaths(Legs[] legs, int colourChoice){
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
            case 3: polylineOptions.color(Color.argb(1,255,89,0));
            break;
            case 4: polylineOptions.color(Color.GREEN);
                break;
        }

        Polyline polyline = MapsActivity.mMap.addPolyline(polylineOptions);
        polyline.setTag(legs[i].getInstruction() + " and Arrive in " + legs[i].getDuration() + " minute");
        MapsActivity.mMap.setOnPolylineClickListener(this);
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
            case 3: polylineOptions.color(Color.argb(1,255,89,0));
                break;
            case 4: polylineOptions.color(Color.GREEN);
                break;
        }

        Polyline polyline = MapsActivity.mMap.addPolyline(polylineOptions);
        polyline.setTag(legs[i].getInstruction() + " and Arrive in " + legs[i].getDuration() + " minute");
        MapsActivity.mMap.setOnPolylineClickListener(this);


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
        MapsActivity.latLng = new ArrayList<>();

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
                    MapsActivity.latLng.add(new LatLng(Double.valueOf(latitude), Double.valueOf(longitude)));
                    if (i == (newStopPointSequence.length / 2)) {
                        MapsActivity.middleLat = new LatLng(Double.valueOf(latitude), Double.valueOf(longitude));
                    }
                }
            }

        }

        // Log.d(TAG, " LOG " + Arrays.toString(newStopPointSequence));
        Log.d(TAG, "  A " + Arrays.toString(napId) + " l " + napId.length);
        Log.d(TAG, "  B " + Arrays.toString(stopName)+" l " + stopName.length);
        Log.d(TAG, "  C " + MapsActivity.latLng.toString()+" l " + MapsActivity.latLng.size());
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
            MapsActivity.latLng.get(MapsActivity.latLng.size() / 2), 12);
    MapsActivity.mMap.animateCamera(location);

    for (int i = 1; i < MapsActivity.latLng.size(); i++) {
        float rotationDegrees = (float) com.example.frank.busmap.LatLngUtils.angleFromCoordinate(MapsActivity.latLng.get(i).latitude, MapsActivity.latLng.get(i).longitude, MapsActivity.latLng.get((i - 1)).latitude, MapsActivity.latLng.get((i - 1)).longitude);
        //Drawing lines
        DrawRouteMaps.getInstance(mapContext)
                .draw(MapsActivity.latLng.get((i - 1)), MapsActivity.latLng.get(i), MapsActivity.mMap);
        //Drawing arrows in between stops
        //LatLng middlePos = com.example.frank.busmap.LatLngUtils.midPoint(latLng.get((i-1)).latitude, latLng.get((i-1)).longitude, latLng.get(i).latitude, latLng.get(i).longitude);
        //DrawMarker.getInstance(mapContext).draw(mMap, middlePos, R.drawable.up_arrow_24dp, stopName[(i-1)], rotationDegrees);


        //Drawing markers
        if (i == 1) {
            DrawMarker.getInstance(mapContext).draw(MapsActivity.mMap, MapsActivity.latLng.get(0), R.drawable.pin_start_24dp, stopName[(i - 1)] ,MapsActivity.direction + " towards" + orderedLineRoutes[0].getName() );
        } else if (i == (MapsActivity.latLng.size() - 1)) {
            DrawMarker.getInstance(mapContext).draw(MapsActivity.mMap, MapsActivity.latLng.get(MapsActivity.latLng.size() - 1), R.drawable.pin_end_24dp, stopName[(MapsActivity.latLng.size() - 1)], MapsActivity.direction + " towards" + orderedLineRoutes[0].getName());
        } else {
            DrawMarker.getInstance(mapContext).draw(MapsActivity.mMap, MapsActivity.latLng.get((i - 1)), R.drawable.pin_every_24dp, stopName[(i - 1)], MapsActivity.direction + " towards" + orderedLineRoutes[0].getName());
            DrawMarker.getInstance(mapContext).draw(MapsActivity.mMap, MapsActivity.latLng.get(i), R.drawable.pin_every_24dp, stopName[i], MapsActivity.direction + " towards" + orderedLineRoutes[0].getName());
        }


    }

}


}
