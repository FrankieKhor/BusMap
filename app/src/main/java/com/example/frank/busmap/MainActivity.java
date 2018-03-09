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
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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

import com.codemybrainsout.placesearch.PlaceSearchDialog;
import com.example.frank.busmap.CustomInfoWindow.MapWrapperLayout;
import com.example.frank.busmap.CustomInfoWindow.OnInfoWindowElemTouchListener;
import com.example.frank.busmap.GoogleMap.GoogleMapPermission;
import com.example.frank.busmap.GoogleMap.StreetViewPanorama;
import com.example.frank.busmap.Pojo.getBusArrival.BusArrivalResponse;
import com.example.frank.busmap.Pojo.getJourneyFromTo.Legs;
import com.example.frank.busmap.Pojo.getStopPointArrival.StopPointArrival;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.PlaceDetectionClient;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.maps.android.ui.IconGenerator;
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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Locale;

import retrofit2.Retrofit;

public class MainActivity extends FragmentActivity implements
        OnMapReadyCallback,
        View.OnClickListener{
    //NEED TO STOP USING STATIC VALUE, TACKY WAY OF DOING IT(REVISIT WHEN I HAVE TIME)
    public static final String TAG = "MainActivity";
    static String[] napId;
    static String[] stopName;
    static List<LatLng> latLng;
    private int API_CALL_CHOICE;
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
    static String direction = "inbound";
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
    static SlidingUpPanelLayout sup ;
    static ConstraintLayout layoutBusDirection ;
    static ConstraintLayout layoutBusRoute ;
    static TextInputEditText locationFrom ;
    static TextInputEditText locationTo ;
    private FusedLocationProviderClient mFusedLocationClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sliding_panel);
        this.mapContext = getApplicationContext();
        final SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mGeoDataClient = Places.getGeoDataClient(this, null);
        mPlaceDetectionClient = Places.getPlaceDetectionClient(this, null);
        animationRotate = AnimationUtils.loadAnimation(this, R.anim.rotate);
        sup = (SlidingUpPanelLayout) findViewById(R.id.sliding_layout);
        layoutBusDirection = (ConstraintLayout) findViewById(R.id.choice_A);
        layoutBusRoute = (ConstraintLayout) findViewById(R.id.choice_B);
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

                    sup.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);

                    return true;
                }
                return false;
            }
        });

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        String[] arrayTravelChoice = getResources().getStringArray(R.array.travel_choice);
        spinner = (Spinner) findViewById(R.id.travelChoiceSpinner);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arrayTravelChoice) {

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
                    Toast.makeText(getApplicationContext(), "Selected : " + selectedItemText, Toast.LENGTH_SHORT).show();
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

        locationFrom= (TextInputEditText) findViewById(R.id.places_autocomplete_from);
        locationFrom.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_UP){
                    showPlaceDialog(locationFrom);
                }
                return false;
            }
        });

        locationTo = (TextInputEditText) findViewById(R.id.places_autocomplete_To);
        locationTo.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_UP){
                    showPlaceDialog(locationTo);
                }
                return false;
            }
        });


    }






    public void showPlaceDialog(final TextInputEditText textInputEditText){

        PlaceSearchDialog placeSearchDialog = new PlaceSearchDialog.Builder(this)
                //.setHeaderImage(R.drawable.dialog_header)
                .setHintText("Enter location name")
                .setHintTextColor(R.color.lightGrey)
                .setNegativeText("CANCEL")
                .setNegativeTextColor(R.color.colorGrey)
                .setPositiveText("SUBMIT")
                .setPositiveTextColor(R.color.colorRed)
                //.setLatLngBounds(BOUNDS)
                .setLocationNameListener(new PlaceSearchDialog.LocationNameListener() {
                    @Override
                    public void locationName(String locationName) {
                        //set textview or edittext
                        textInputEditText.setText(locationName);
                        nootNUll();

                    }
                })
                .build();
        placeSearchDialog.show();

    }

    public void nootNUll(){

        if(!locationFrom.getText().toString().equals("") && !locationTo.getText().toString().equals("")){
            Log.d(TAG, "SUCE");
            TextView from = (TextView)findViewById(R.id.fromLocation);
            from.setText(locationFrom.getText().toString());

            TextView to = (TextView)findViewById(R.id.toLocation);
            to.setText(locationTo.getText().toString());

        }else if(locationFrom.getText().toString().equals("") ){
            Log.d(TAG, "from not null");
        }else if(locationTo.getText().toString().equals("")){
            Log.d(TAG, "to not null");

        }

        Log.d(TAG, "loc "+locationFrom.getText() + " " + locationTo.getText());


    }


    public static Context getMapContext(){
        return MainActivity.mapContext ;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.busDirection:
                ImageView image = (ImageView) findViewById(R.id.busDirection);
                if (rotateDirectionLine) {
                    rotateDirectionLine = false;
                    direction = "outbound";
                } else {
                    rotateDirectionLine = true;
                    direction = "inbound";
                }
                Toast.makeText(getApplicationContext(), "Switching direction", Toast.LENGTH_SHORT).show();
                image.startAnimation(animationRotate);
                connectAndGetApiData();
                break;

                //Needs working on
            case R.id.fab_current_location:
                Log.d(TAG, "CURRENT ");
                getAllPermission();
                break;

            case R.id.fab_direction:
                layoutBusRoute.setVisibility(View.GONE);
                layoutBusDirection.setVisibility(View.VISIBLE);
                sup.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
                sup.setPanelHeight(0);
                break;

            case R.id.fab_path:
                API_CALL_CHOICE = 2;
                connectAndGetApiData();

                break;

            case R.id.btnSubmit:
                String[] some_array = getResources().getStringArray(R.array.travel_choice);

                if(spinner.getSelectedItem().toString().equals(some_array[1])){
                    Log.d(TAG, "1");
                }
                else if(spinner.getSelectedItem().toString().equals(some_array[2])) {
                    Log.d(TAG, "2");
                    SelectingRoute sr = new SelectingRoute(TflCalls.journey);
                    Legs [] ab = sr.getQuickestRoute().getLegs();
                    TflCalls po = new TflCalls();
                    mMap.clear();
                    po.addingPaths(ab, 1);

                    Log.d(TAG, "SMAKKKKEESS " + sr.getQuickestRoute().getDuration());
                }
                else if(spinner.getSelectedItem().toString().equals(some_array[3])) {
                    Log.d(TAG, "3");
                    API_CALL_CHOICE = 3;
                    connectAndGetApiData();
                }
                else if(spinner.getSelectedItem().toString().equals(some_array[4])) {
                    Log.d(TAG, "4");
                    SelectingRoute sr = new SelectingRoute(TflCalls.journey);
                    sr.getLeastVehicleChange();

                }
                sup.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);

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
                        GoogleMapPermission googleMapPermission = new GoogleMapPermission(mFusedLocationClient);
                        if (report.areAllPermissionsGranted()) {
                            Toast.makeText(getApplicationContext(), "All permissions are granted!", Toast.LENGTH_SHORT).show();
                            googleMapPermission.updateLocationUI(mMap, true);
                        }




                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            Log.d(TAG, "Denied " + report.getDeniedPermissionResponses().toString());
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

    public void updateLocationUI(GoogleMap map)
    {}


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

    public void createCustomInfoWindow(){
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

        //Setting a location and moving camera
        //getDeviceLocation();
    }
    public static int getPixelsFromDp(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int)(dp * scale + 0.5f);
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
