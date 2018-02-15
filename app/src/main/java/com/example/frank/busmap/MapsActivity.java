package com.example.frank.busmap;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.frank.busmap.Pojo.getAllBusStops.OrderedLineRoutes;
import com.example.frank.busmap.Pojo.getAllBusStops.StopPointSequences;
import com.example.frank.busmap.Pojo.getAllBusStops.BusStopResponse;
import com.example.frank.busmap.Rest.TflApi;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CustomCap;
import com.google.android.gms.maps.model.JointType;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MapsActivity extends FragmentActivity implements
        OnMapReadyCallback,
        View.OnClickListener{

    private static final String TAG = "MapsActivity";
    //List<LatLng> locations = new ArrayList<>();
    //Adds naptanId and LatLng
    //LinkedHashMap<String,LatLng> locations = new LinkedHashMap<>();
    //Adds Naptan Id and l,ocation name
    //HashMap<String, String> naptan_name = new HashMap<>();
    private GoogleMap mMap;
    boolean decision = false;
    private static Retrofit retrofit = null;
    private static final String BASE_URL = "https://api.tfl.gov.uk/";
    String lineID = "";
    String direction = "inbound";
    LatLng middleLat ;
    boolean rotateDirection = true;
    Animation animationRotate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sliding_panel);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        final SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        animationRotate = AnimationUtils.loadAnimation(this, R.anim.rotate);

        //Allows user to search for bus route
        final AutoCompleteTextView autoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.busNumber);
        String[] busNum = getResources().getStringArray(R.array.bus_number);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, busNum);
        autoCompleteTextView.setAdapter(adapter);
        autoCompleteTextView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    lineID = autoCompleteTextView.getText().toString();
                    InputMethodManager inputManager = (InputMethodManager)
                            getSystemService(Context.INPUT_METHOD_SERVICE);

                    inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);

                    //addMarker();
                    connectAndGetApiData();
                    return true;
                }
                return false;
            }
        });

        ImageView imgFavorite = (ImageView) findViewById(R.id.busDirection);
        imgFavorite.setOnClickListener(this);
        FloatingActionButton fabBack = (FloatingActionButton) findViewById(R.id.fab_back);
        fabBack.setOnClickListener(this);
        FloatingActionButton fabDirection = (FloatingActionButton) findViewById(R.id.fab_direction);
        fabDirection.setOnClickListener(this);



    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        //Setting a location and moving camera
        LatLng london = new LatLng(51.509865, -0.118092);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(london, 13));
    }
    //Doesnt add last stop (TACKY WAY OF DOING IT )
    public void addMarker(StopPointSequences worm){
        MarkerOptions options = new MarkerOptions();
        CameraUpdate location = CameraUpdateFactory.newLatLngZoom(
                worm.getIdCoord(worm.getLength()/2), 12);
        mMap.animateCamera(location);
        for(int i =1 ;i<worm.getLength();i++) {
            //PROBLEM HERE NEED TO GET KEY
            options.position(worm.getIdCoord((i-1)));
            mMap.addMarker(options
            .title(worm.getIdName((i-1))));

            if(i == worm.getLength()-1) {
                Log.d(TAG,"ROB");
                options.position(worm.getIdCoord((worm.getLength()-1)));
                mMap.addMarker(options
                        .title(worm.getIdName((worm.getLength()-1))));
            }

            LatLng origin = (LatLng) worm.getIdCoord((i-1));
            LatLng dest = (LatLng) worm.getIdCoord((i));

            // Getting URL to the Google Directions API, Drawing lines between points

           String url = getDirectionsUrl(origin, dest);
            DownloadTask downloadTask = new DownloadTask();
            // Start downloading json data from Google Directions API
            downloadTask.execute(url);

        }
    }
    @Override
    public void onClick(View v) {
        SlidingUpPanelLayout sup = (SlidingUpPanelLayout) findViewById(R.id.sliding_layout);
        switch (v.getId()){
            case R.id.busDirection:
                Log.d(TAG, direction +" GOO " + rotateDirection);
                //True will represent inbound and false will be outbound
                if(rotateDirection)
                {
                    rotateDirection = false;
                    direction = "outbound";
                }
                else
                {
                    rotateDirection = true;
                    direction = "inbound";
                }
                new ImageView(v.getContext()).startAnimation(animationRotate);
                break;

            case R.id.fab_back:
                sup.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
                addVisibilityChanged.onHidden(new FloatingActionButton(v.getContext()));
                break;

            case R.id.fab_direction:
                sup.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
                addVisibilityChanged.onShown(new FloatingActionButton(v.getContext()));
                break;
        }
    }
    //NOT NEEDED JUST LOGGING PURPOSE
    final FloatingActionButton.OnVisibilityChangedListener addVisibilityChanged = new FloatingActionButton.OnVisibilityChangedListener() {
        public void onShown(final FloatingActionButton fab) {
            super.onShown(fab);
            Log.d(TAG, "Visibility showing");
        }

        public void onHidden(final FloatingActionButton fab) {
            super.onHidden(fab);
            super.onHidden(fab);
            Log.d(TAG, "Visibility hidden");
        }
    };

    public void connectAndGetApiData(){
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        TflApi apple = retrofit.create(TflApi.class);
        String appId = getString(R.string.tfl_app_id);
        String apiKey = getString(R.string.tfl_key);
        Call<BusStopResponse> call = apple.getAllBusStops(lineID,direction ,appId ,apiKey);
        call.enqueue(new Callback<BusStopResponse>() {
            @Override
            public void onResponse(Call<BusStopResponse> call, Response<BusStopResponse> response) {
                Log.d(TAG,  " Getting response");

                //Creating object
                if(response.isSuccessful()) {
                    Log.d(TAG,  " Response success");
                     mMap.clear();

                    getLineData(response);
                }else{
                    Log.d(TAG,  " Awaiting response");
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

    public void getLineData(Response<BusStopResponse> response){
        OrderedLineRoutes[] direction = response.body().getOrderedLineRoutes();
        OrderedLineRoutes orderedLineRoutes = new OrderedLineRoutes();
        //Getting rid of square and curly brackets
        String str = Arrays.toString(direction).replaceAll("\\[|\\]", "");
        String [] naptanID = new String[direction.length];
        naptanID = str.split(",");
        orderedLineRoutes.setNaptanIds(naptanID);
        StopPointSequences [] stopPointSequences = response.body().getStopPointSequences();
        StopPointSequences q = new StopPointSequences();

        String stopPoint = Arrays.toString(stopPointSequences).replaceAll("\\[|\\]|", "");
        String [] newArray = stopPoint.split(",");
        for(int i = 0;i<newArray.length;i++){
            newArray[i] = newArray[i].replace("&", ",");
        }

        String [] naptanlineId = new String[newArray.length];
        String [] stopName = new String[newArray.length];
        List<LatLng> latLng = new ArrayList<>();
        for(int i = 0;i<newArray.length;i++) {
            int firstPart = 0;
            int secondPart = 0;
            for (int j = 0; j < newArray[i].length(); j++) {
                String word = newArray[i];
                char c = word.charAt(j);
                if(c == '*'){
                    firstPart = word.indexOf(c);
                    naptanlineId[i] = word.substring(0, word.indexOf(c));
                }else if (c == '|'){
                    secondPart = word.indexOf(c);
                    stopName[i] = word.substring((firstPart+1), word.indexOf(c));
                }else if(c == ','){

                    String latitude = (word.substring((secondPart+1), (word.indexOf(c))));
                    String longitude = (word.substring((word.indexOf(c)+1), (word.length()-1)));
                    latLng.add(new LatLng(Double.valueOf(latitude), Double.valueOf(longitude)));
                    if(i == (newArray.length/2)){
                        middleLat = new LatLng(Double.valueOf(latitude), Double.valueOf(longitude));
                    }
                    //locations.put(naptanlineId[i], new LatLng(Double.valueOf(latitude), Double.valueOf(longitude)));
                    //locations.add(new LatLng(Double.valueOf(latitude), Double.valueOf(longitude)));
                }
            }
        }
//        locations.add(new LatLng(51.515629,-0.072274));
//        locations.add(new LatLng(51.546866, -0.104632));
//        locations.add(new LatLng(51.548726,-0.075274));
//        locations.add(new LatLng(51.546133,-0.07785));
//        locations.add(new LatLng(51.547038,-0.090288));
//        locations.add(new LatLng(51.546671,-0.075549));

        q.setId(naptanlineId);
        q.setIdName(stopName);
        q.setIdCoord(latLng);
        Log.d(TAG,"  dasdas " + q.getIdCoord() );

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
        addMarker(q);
    }





    private class DownloadTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... url) {

            String data = "";

            try {
                data = downloadUrl(url[0]);
            } catch (Exception e) {
                Log.d("Background Task", e.toString());
            }
            return data;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            ParserTask parserTask = new ParserTask();
            parserTask.execute(result);
        }
    }


    /**
     * A class to parse the Google Places in JSON format
     */
    public class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {

        // Parsing the data in non-ui thread
        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {

            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;

            try {
                jObject = new JSONObject(jsonData[0]);
                DirectionJsonParser parser = new DirectionJsonParser();

                routes = parser.parse(jObject);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return routes;
        }

        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> result) {

            ArrayList points = null;
            PolylineOptions lineOptions = null;
            MarkerOptions markerOptions = new MarkerOptions();
            Drawable inboundArrow = getResources().getDrawable(R.drawable.down_arrow_24dp);
            BitmapDescriptor icon = getMarkerIconFromDrawable(inboundArrow);
            Drawable outBoundArrow = getResources().getDrawable(R.drawable.up_arrow_24dp);
            BitmapDescriptor a = getMarkerIconFromDrawable(outBoundArrow);
            for (int i = 0; i < result.size(); i++) {
                points = new ArrayList();
                lineOptions = new PolylineOptions();
                Log.d("HAHA", " "+lineOptions.toString());
                List<HashMap<String, String>> path = result.get(i);

                for (int j = 0; j < path.size(); j++) {
                    HashMap<String, String> point = path.get(j);

                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));
                    LatLng position = new LatLng(lat, lng);

                    points.add(position);
                }

                lineOptions.addAll(points);
                lineOptions.width(12);
                lineOptions.color(Color.BLACK);
                lineOptions.geodesic(true);
                lineOptions.jointType(JointType.ROUND);
                lineOptions.startCap(new CustomCap(((icon))));

            }
            // Drawing polyline in the Google Map for the i-th route
            if(lineOptions != null) {
                Toast toast = Toast.makeText(getApplicationContext(), "Drawing Lines", Toast.LENGTH_SHORT);
                toast.show();
                mMap.addPolyline(lineOptions);
            }
            else {
                Log.d("onPostExecute","without Polylines drawn");
            }
        }
    }

    private BitmapDescriptor getMarkerIconFromDrawable(Drawable drawable) {
        Canvas canvas = new Canvas();
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        canvas.setBitmap(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }


    private String getDirectionsUrl(LatLng origin, LatLng dest) {

        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;

        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;

        // Sensor enabled
        String sensor = "sensor=false";
        String mode = "mode=TRANSIT";
        // Building the parameters to the web service

        String parameters = str_origin + "&" + str_dest + "&" + sensor + "&" + mode;
        // Output format
        String output = "json";

        // Building the url to the web service
        //String url = "https://maps.googleapis.com/maps/api/directions/" + outputFormat + "?" + parameters;
        //String url = "https://maps.googleapis.com/maps/api/directions/json?origin=place_id:ChIJ685WIFYViEgRHlHvBbiD5nE&destination=place_id:ChIJA01I-8YVhkgRGJb0fW4UX7Y&key=AIzaSyD8PhziTf5drvKOLU_bQYnkfLjSHkbZDNM";

        //String url = "https://maps.googleapis.com/maps/api/directions/json?origin=place_id:ChIJ685WIFYViEgRHlHvBbiD5nE&destination=place_id:ChIJA01I-8YVhkgRGJb0fW4UX7Y&key=AIzaSyD8PhziTf5drvKOLU_bQYnkfLjSHkbZDNM";
        String url  = "https://maps.googleapis.com/maps/api/directions/json?" + parameters;

        return url;
    }

    /**
     * A method to download json data from url
     */
    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(strUrl);

            urlConnection = (HttpURLConnection) url.openConnection();

            urlConnection.connect();

            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb = new StringBuffer();

            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            data = sb.toString();

            br.close();

        } catch (Exception e) {
            Log.d("Exception", e.toString());
        } finally {
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }

}
