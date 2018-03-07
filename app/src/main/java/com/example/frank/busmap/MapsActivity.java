package com.example.frank.busmap;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.provider.Settings;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ahmadrosid.lib.drawroutemap.DrawMarker;
import com.ahmadrosid.lib.drawroutemap.DrawRouteMaps;
import com.androidmapsextensions.utils.LatLngUtils;
import com.arsy.maps_library.MapRipple;
import com.example.frank.busmap.Pojo.getAllBusStops.StopPointSequences;
import com.example.frank.busmap.Pojo.getAllBusStops.BusStopResponse;
import com.example.frank.busmap.Pojo.getBusArrival.BusArrivalResponse;
import com.example.frank.busmap.Pojo.getJourneyFromTo.Instruction;
import com.example.frank.busmap.Pojo.getJourneyFromTo.JourneyFromToResponse;
import com.example.frank.busmap.Pojo.getJourneyFromTo.Journeys;
import com.example.frank.busmap.Pojo.getJourneyFromTo.Legs;
import com.example.frank.busmap.Pojo.getJourneyFromTo.Steps;
import com.example.frank.busmap.Pojo.getStopPointArrival.StopPointArrival;
import com.example.frank.busmap.Rest.TflApi;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.PlaceDetectionClient;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.OnStreetViewPanoramaReadyCallback;
import com.google.android.gms.maps.StreetViewPanoramaOptions;
import com.google.android.gms.maps.StreetViewPanoramaView;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CustomCap;
import com.google.android.gms.maps.model.Dash;
import com.google.android.gms.maps.model.Dot;
import com.google.android.gms.maps.model.Gap;
import com.google.android.gms.maps.model.JointType;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PatternItem;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.maps.android.ui.IconGenerator;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.seatgeek.placesautocomplete.OnPlaceSelectedListener;
import com.seatgeek.placesautocomplete.PlacesAutocompleteTextView;
import com.seatgeek.placesautocomplete.model.Place;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MapsActivity extends FragmentActivity implements
        OnMapReadyCallback,
        View.OnClickListener{

    public static final String TAG = "MapsActivity";
    static String[] napId;
    static String[] stopName;
    static List<LatLng> latLng;
    int API_CALL_CHOICE;
    private RecyclerView recyclerView;
    private ArrayList<List<BusArrivalResponse>> data;
    private DataAdapter adapter;
    private LinearLayoutManager layoutManager;
    BitmapDescriptor mBusIcon;
    static List<BusArrivalResponse> StudentData;
    static List<StopPointArrival> STOPDATA;
    static String[] stopArrivalResponse;
    static Context mapContext;
    boolean isMarkerRotating;
    Spinner spinner;
    public static GoogleMap mMap;
    public static Retrofit retrofit = null;
    static String lineID = "";
    static String direction = "Inbound";
    static LatLng middleLat;
    boolean rotateDirectionLine = true;
    Animation animationRotate;
    GeoDataClient mGeoDataClient;
    static boolean cBusArrivalisFinished = false;
    static boolean cBusStopisFinished = false;
    PlaceDetectionClient mPlaceDetectionClient;
    private ViewGroup infoWindow;
    private TextView infoTitle;
    private TextView infoSnippet;
    private TextView infoSnippet2;
    private Button infoButton1;
    private OnInfoWindowElemTouchListener infoButtonListener;
    LatLng clickAddress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sliding_panel);
        MapsActivity.mapContext = getApplicationContext();
//        View b = this.findViewById(android.R.id.content).getRootView();
//        View c = this.findViewById(android.R.id.content);
//        Log.d(TAG, "VIOEW" + view);
//        Log.d(TAG, "VIOEW" + b);
//        Log.d(TAG, "VIOEW" + c);

        final SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mGeoDataClient = Places.getGeoDataClient(this, null);
        mPlaceDetectionClient = Places.getPlaceDetectionClient(this, null);
        //mFusedLocationProviderClient  = LocationServices.getFusedLocationProviderClient(this);
        //getGoogleApiClient();
        animationRotate = AnimationUtils.loadAnimation(this, R.anim.rotate);
          //in onMapReadyCallBack









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
                    API_CALL_CHOICE = 1;
                    createCustomInfoWindow();
                    connectAndGetApiData();

                    SlidingUpPanelLayout sup = (SlidingUpPanelLayout) findViewById(R.id.sliding_layout);
                    sup.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);

                    return true;
                }
                return false;
            }
        });
        String[] some_array = getResources().getStringArray(R.array.travel_choice);

         spinner = (Spinner) findViewById(R.id.travelChoiceSpinner);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, some_array) {

            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return false;
                } else {
                    return true;
                }
            }

            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                } else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemText = (String) parent.getItemAtPosition(position);
                // If user change the default selection
                // First item is disable and it is used for hint
                if (position > 0) {
                    // Notify the selected item text
                    Toast.makeText
                            (getApplicationContext(), "Selected : " + selectedItemText, Toast.LENGTH_SHORT)
                            .show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ImageView imgFavorite = (ImageView) findViewById(R.id.busDirection);
        imgFavorite.setOnClickListener(this);
        FloatingActionButton fabBack = (FloatingActionButton) findViewById(R.id.fab_current_location);
        fabBack.setOnClickListener(this);
        FloatingActionButton fabDirection = (FloatingActionButton) findViewById(R.id.fab_direction);
        fabDirection.setOnClickListener(this);
        FloatingActionButton planner = (FloatingActionButton) findViewById(R.id.fab_path);
        planner.setOnClickListener(this);
        Button button = (Button) findViewById(R.id.btnSubmit) ;
        button.setOnClickListener(this);

        PlacesAutocompleteTextView from = (PlacesAutocompleteTextView) findViewById(R.id.places_autocomplete_from);
        PlacesAutocompleteTextView to = (PlacesAutocompleteTextView) findViewById(R.id.places_autocomplete_from);
        from.setOnPlaceSelectedListener(
                new OnPlaceSelectedListener() {
                    @Override
                    public void onPlaceSelected(final Place place) {
                        Log.d(TAG, "HI" + place.place_id);
                        // do something awesome with the selected place
                    }
                });
    }


//    public void getGoogleApiClient(){
//        mGoogleApiClient = new GoogleApiClient
//                .Builder(this)
//                .addApi(Places.GEO_DATA_API)
//                .addApi(Places.PLACE_DETECTION_API)
//                .enableAutoManage(this, this)
//                .build();
//    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


    }
    public void createCustomInfoWindow(){
        final LatLng address ;
        final String op = "";
        final MapWrapperLayout mapWrapperLayout = (MapWrapperLayout)findViewById(R.id.map_relative_layout);
        mapWrapperLayout.init(mMap, getPixelsFromDp(this, 39 + 20));
        this.infoWindow = (ViewGroup)getLayoutInflater().inflate(R.layout.custom_infowindow, null);

        this.infoTitle = (TextView)infoWindow.findViewById(R.id.nameTxt);
        this.infoSnippet = (TextView)infoWindow.findViewById(R.id.addressTxt);
        this.infoButton1 = (Button)infoWindow.findViewById(R.id.btnOne);
        this.infoButtonListener = new OnInfoWindowElemTouchListener(infoButton1, getResources().getDrawable(R.drawable.btn_bg), getResources().getDrawable(R.drawable.btn_bg)){
            @Override
            protected void onClickConfirmed(View v, Marker marker) {
                Intent intent = new Intent(v.getContext(), StreetViewPanorama.class);
                Bundle args = new Bundle();
                args.putParcelable("Position", clickAddress);
                intent.putExtra("bundle", args );
                startActivity(intent);
                // Here we can perform some action triggered after clicking the button
                Toast.makeText(MapsActivity.this, "click on button 1", Toast.LENGTH_SHORT).show();
            }
        };
        this.infoButton1.setOnTouchListener(infoButtonListener);


        mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker marker) {
                clickAddress = marker.getPosition();
                Log.d(TAG, "MARKER " + marker.getPosition() + " " + marker.getTitle());
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {
                // Setting up the infoWindow with current's marker info
                infoSnippet.setText(marker.getSnippet());
                infoTitle.setText(marker.getTitle());
                infoButtonListener.setMarker(marker);

                // We must call this to set the current marker and infoWindow references
                // to the MapWrapperLayout
                mapWrapperLayout.setMarkerWithInfoWindow(marker, infoWindow);
                return infoWindow;
            }
        });
        // Let's add a couple of markers
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

        //Setting a location and moving camera
        //getDeviceLocation();
    }
    public static int getPixelsFromDp(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int)(dp * scale + 0.5f);
    }


    public static Context getMapContext(){
        return MapsActivity.mapContext ;
    }



    @Override
    public void onClick(View v) {


        SlidingUpPanelLayout sup = (SlidingUpPanelLayout) findViewById(R.id.sliding_layout);
        ConstraintLayout layout = (ConstraintLayout) findViewById(R.id.choice_A);
        ConstraintLayout layout2 = (ConstraintLayout) findViewById(R.id.choice_B);

        //FloatingActionButton mFab = (FloatingActionButton) findViewById(R.id.fab_back);
        FloatingActionButton faq = (FloatingActionButton) findViewById(R.id.fab_direction);


        switch (v.getId()) {
            case R.id.busDirection:
                ImageView image = (ImageView) findViewById(R.id.busDirection);
                //  Log.d(TAG, direction +" GOO " + rotateDirectionLine);
                //True will represent inbound and false will be outbound

                if (rotateDirectionLine) {
                    rotateDirectionLine = false;
                    direction = "outbound";
                } else {
                    rotateDirectionLine = true;
                    direction = "inbound";
                }
                image.startAnimation(animationRotate);

                break;

            case R.id.fab_current_location:
                Log.d(TAG, "CURRENT ");
                GoogleMapPermission a = new GoogleMapPermission();
                a.updateLocationUI(mMap);
                //getAllPermission();
                break;

            case R.id.fab_direction:
                //mFab.show();
                layout2.setVisibility(View.GONE);
                layout.setVisibility(View.VISIBLE);
                sup.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
                sup.setPanelHeight(0);
                //addVisibilityChanged.onShown(new FloatingActionButton(v.getContext()));
                break;
            case R.id.fab_path:
                API_CALL_CHOICE = 2;
                connectAndGetApiData();

                layout.setVisibility(View.GONE);
                layout2.setVisibility(View.VISIBLE);
                sup.setPanelHeight(100);
            case R.id.btnSubmit:
                Log.d(TAG, "ITEM " + spinner.getSelectedItem().toString());
                String[] some_array = getResources().getStringArray(R.array.travel_choice);

                if(spinner.getSelectedItem().toString().equals(some_array[1])){
                    SelectingRoute sr = new SelectingRoute(TflCalls.journey);
                    Legs [] ab = sr.getQuickestRoute().getLegs();
                    TflCalls po = new TflCalls();
                    mMap.clear();
                    po.addingPaths(ab, 1);
                    Log.d(TAG, "SMAKKKKEESS " + sr.getQuickestRoute().getDuration());
                }
                else if(spinner.getSelectedItem().toString().equals(some_array[2])) {
                    Log.d(TAG, "2");
                }
                else if(spinner.getSelectedItem().toString().equals(some_array[3])) {
                    Log.d(TAG, "3");

                }
                else if(spinner.getSelectedItem().toString().equals(some_array[4])) {
                    Log.d(TAG, "4");

                }

//                SelectingRoute p = new SelectingRoute();

                break;
        }
    }




    public void addIcon(IconGenerator iconGenerator, CharSequence text, LatLng position){

        MarkerOptions markerOptions = new MarkerOptions().
                icon(BitmapDescriptorFactory.fromBitmap(iconGenerator.makeIcon(text))).
                position(position);
        mMap.addMarker(markerOptions);

    }


    public void getAllPermission() {
        Dexter.withActivity(this)
                .withPermissions(Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.INTERNET,
                        Manifest.permission.ACCESS_NETWORK_STATE
                )
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        Log.d(TAG, "PROB " + report.getGrantedPermissionResponses().toString());

                        if (report.areAllPermissionsGranted()) {
                            Toast.makeText(getApplicationContext(), "All permissions are granted!", Toast.LENGTH_SHORT).show();
                        }

                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            Log.d(TAG, "DENIESD " + report.getDeniedPermissionResponses().toString());
                            // show alert dialog navigating to Settings
                            showSettingsDialog();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).withErrorListener(new PermissionRequestErrorListener() {
            @Override
            public void onError(DexterError error) {
                Toast.makeText(getApplicationContext(), "Error occurred! ", Toast.LENGTH_SHORT).show();
            }
        })
                .onSameThread()
                .check();
    }

    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Need Permissions");
        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.");
        builder.setPositiveButton("GOTO SETTINGS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                openSettings();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();

    }

    private void openSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 101);
    }

//    private void createPermissionListener(){
//        MultiplePermissionsListener feedbackViewMultiplePermissionListener =
//                new AddMultiplePermissionListener(this);
//        allPermissionsListener =
//                new CompositeMultiplePermissionsListener(feedbackViewMultiplePermissionListener,
//                        SnackbarOnAnyDeniedMultiplePermissionsListener.Builder.with(contentView))
//    }


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


    //Will use RxJava for multiple Api request






    public void connectAndGetApiData() {
        TflCalls a = new TflCalls();
        a.createRetrofit();
        a.createRetrofitClass();
        String appId = getString(R.string.tfl_app_id);
        String apiKey = getString(R.string.tfl_key);
        a.choice(API_CALL_CHOICE, appId, apiKey);
//
//        tflCall.getAllBusStops(lineID, direction, appId, apiKey)
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeOn(Schedulers.io())
//                .subscribe(status -> );

        //Observable<BusStopResponse> busStopCall = tflCall.getAllBusStops( )  ;

//        tflCall.getAllBusStops(lineID, direction, okhttp3.Credentials.basic(appId, apiKey)
//        .subsc);




    }













    public void getStationsBetween(){
        Log.d(TAG, "MOO " + napId.length);

        String towardsNaptan = "";
        String previousNaptan = "";
        double distance ;
        for(int i =1;i<=(napId.length-1);i++){
            Log.d(TAG, " LAT " + latLng.get(i).toString());

            String op = napId[i];
           // Log.d(TAG, "MATCH " + napId[i-1] + " previous " + napId[i]);
            if(napId[(i-1)].equals(op)){

                previousNaptan = napId[i];
               // distance = SphericalUtil.computeDistanceBetween();
            }
        }

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

    }


    /**
     * A class to parse the Google Places in JSON format
     */



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
