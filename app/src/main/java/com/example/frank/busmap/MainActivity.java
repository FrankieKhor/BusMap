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
import android.net.Uri;
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
import com.example.frank.busmap.GoogleMap.DownloadTask;
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
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.ui.IconGenerator;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.util.ArrayList;
import java.util.List;

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
    public static LatLng travelFrom = new LatLng(0,0);
    public static LatLng travelTo = new LatLng(0,0);

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
        Log.d(TAG, "OPPPPP");

       // ArrayList<Double> w = CS2004.ReadNumberFile("1000 Primes.txt");

        int [] op = {0,1,1,2,2,0,1,0,0};
        ArrayList<Integer> w = new ArrayList<>();
        for(int q:op){
            w.add(q);
        }

//        FitnessCalc.addPathValue("0111");
//
//        // Create an initial population
//        Population myPop = new Population(4, true);
//
//        // Evolve our population until we reach an optimum solution
//        int generationCount = 0;
//        while (myPop.getFittest().getFitness() < FitnessCalc.getMaxFitness()) {
//            generationCount++;
//            System.out.println("Generation: " + generationCount + " Fittest: " + myPop.getFittest().getFitness());
//            myPop = Algorithm.evolvePopulation(myPop);
//        }
//        Log.d(TAG, "Solution found!");
//        Log.d(TAG,"Generation: " + generationCount);
//        Log.d(TAG,"Genes:");
//        Log.d(TAG, "Fittest solution " +myPop.getFittest());



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
                        checkAutoCompTextView();

                    }
                })
                .build();
        placeSearchDialog.show();

    }

    public void checkAutoCompTextView(){
        if(!locationFrom.getText().toString().equals("") && !locationTo.getText().toString().equals("")){
            Log.d(TAG, "SUCE");

            String [] address = new String[2];
            address[0] = locationFrom.getText().toString();
            address[1] = locationTo.getText().toString();

            TextView from = (TextView)findViewById(R.id.fromLocation);
            from.setText(locationFrom.getText().toString());

            TextView to = (TextView)findViewById(R.id.toLocation);
            to.setText(locationTo.getText().toString());
            Log.d(TAG, "1 " + address[0]);
            Log.d(TAG, "1 " + address[1]);

            // String address = "3-41+Wykeham+Hill,+Wembley+HA9";
            for(String app: address){
                String url = getGeocode(app);
                Log.d(TAG, "URL " + url);
                DownloadTask downloadTask = new DownloadTask();
                downloadTask.execute(url);
            }


        }else if(locationFrom.getText().toString().equals("") ){
            Log.d(TAG, "from not null");
        }else if(locationTo.getText().toString().equals("")){
            Log.d(TAG, "to not null");

        }

        Log.d(TAG, "loc "+locationFrom.getText() + " " + locationTo.getText());

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        TflCalls tflCalls = new TflCalls(mapContext);
        tflCalls.addDataFromMain(mMap);
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
                SelectingRoute sr = new SelectingRoute(TflCalls.journey);
                //optimal
                if(spinner.getSelectedItem().toString().equals(some_array[1])){
                    Log.d(TAG, "OPTIMAL");
                    TflCalls tflCalls = new TflCalls(mapContext);
                    Legs [] ab = sr.getTime(1).getLegs();
                    mMap.clear();
                    tflCalls.addingPaths(ab, 1);
                    TextView textView = (TextView) findViewById(R.id.predictionText);
                    textView.setText("Duration: \n" +
                            "Modes: \n" +
                            " ");
                }
                else if(spinner.getSelectedItem().toString().equals(some_array[2])) {
                    Log.d(TAG, "QUICKEST");
                    TflCalls tflCalls = new TflCalls(mapContext);
                    Legs [] ab = sr.getTime(2).getLegs();
                    mMap.clear();
                    tflCalls.addingPaths(ab, 2);
                    TextView textView = (TextView) findViewById(R.id.predictionText);
                    textView.setText("Duration: \n" +
                            "Modes: \n" +
                            " ");
                }
                else if(spinner.getSelectedItem().toString().equals(some_array[3])) {
                    Log.d(TAG, "COST");
                    TflCalls tflCalls = new TflCalls(mapContext);
                    Legs [] ab = sr.getTime(3).getLegs();
                    mMap.clear();
                    tflCalls.addingPaths(ab, 3);
                    TextView textView = (TextView) findViewById(R.id.predictionText);
                    textView.setText("Duration: \n" +
                            "Modes: \n" +
                            " ");
                }
                else if(spinner.getSelectedItem().toString().equals(some_array[4])) {
                    Log.d(TAG, "VEHICLE");
                    TflCalls tflCalls = new TflCalls(mapContext);
                    Legs [] ab = sr.getTime(4).getLegs();
                    mMap.clear();
                    tflCalls.addingPaths(ab, 4);
                    TextView textView = (TextView) findViewById(R.id.predictionText);
                    textView.setText("Duration: \n" +
                            "Modes: \n" +
                            " ");
                }
                sup.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
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

    //Will use RxJava for multiple Api request
    public void connectAndGetApiData() {

        TflCalls tflCalls = new TflCalls(mapContext);
        tflCalls.createRetrofit();
        String appId = getString(R.string.tfl_app_id);
        String apiKey = getString(R.string.tfl_key);
        tflCalls.choice(API_CALL_CHOICE, appId, apiKey);
//
//        tflCall.getAllBusStops(lineID, direction, appId, apiKey)
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeOn(Schedulers.io())
//                .subscribe(status -> );

        //Observable<BusStopResponse> busStopCall = tflCall.getAllBusStops( )  ;

//        tflCall.getAllBusStops(lineID, direction, okhttp3.Credentials.basic(appId, apiKey)
//        .subsc);
    }


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

    //Convert to latlng
    private String getGeocode(String address) {
        String parameters = "address=" + address;
        parameters = parameters.replaceAll(" ", "+");
        // Output format
        String output = "json";
        String apiKey = getString(R.string.google_maps_key);
        String url  = "https://maps.googleapis.com/maps/api/geocode/" + output +"?" + parameters + "&key=" + apiKey;
        return url;
    }

    //Need to work on it, fairly easy but haven't had use for it
    private String getReverseGeocode(LatLng origin, LatLng dest) {
        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;
        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;
        // Sensor enabled
        String sensor = "sensor=false";
        String mode = "mode=TRANSIT";
        // Building the parameters to the web service
        //latng or address
        String parameters = "latlng";
        // Output format
        String output = "json";
        String url  = "https://maps.googleapis.com/maps/api/geocode/" + output +"?" + parameters;

        return url;
    }



}
