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
import android.os.Handler;
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

import com.anychart.anychart.AnyChart;
import com.anychart.anychart.AnyChartView;
import com.anychart.anychart.CartesianSeriesBase;
import com.anychart.anychart.DataEntry;
import com.anychart.anychart.EnumsAnchor;
import com.anychart.anychart.Pareto;
import com.anychart.anychart.Pie;
import com.anychart.anychart.Scatter;
import com.anychart.anychart.StrokeLineCap;
import com.anychart.anychart.StrokeLineJoin;
import com.anychart.anychart.ValueDataEntry;
import com.codemybrainsout.placesearch.PlaceSearchDialog;
import com.example.frank.busmap.CustomInfoWindow.MapWrapperLayout;
import com.example.frank.busmap.CustomInfoWindow.OnInfoWindowElemTouchListener;
import com.example.frank.busmap.GenticAlgorithm.Individuals;
import com.example.frank.busmap.GenticAlgorithm.Initialisation;
import com.example.frank.busmap.GenticAlgorithm.Stops;
import com.example.frank.busmap.GoogleMap.DownloadTask;
import com.example.frank.busmap.GoogleMap.GoogleMapPermission;
import com.example.frank.busmap.GoogleMap.SelectingRoute;
import com.example.frank.busmap.GoogleMap.StreetViewPanorama;
import com.example.frank.busmap.Pojo.Rest.BackgroundService;
import com.example.frank.busmap.Pojo.getJourneyFromTo.Legs;
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

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class MainActivity extends FragmentActivity implements
        OnMapReadyCallback,
        View.OnClickListener {

    private final String TAG = MainActivity.class.getName();
    private int API_CALL_CHOICE;
    private Context mapContext;
    private boolean isMarkerRotating;
    private Spinner spinner;
    private GoogleMap mMap;
    private boolean rotateDirectionLine = true;
    private Animation animationRotate;
    private GeoDataClient mGeoDataClient;
    private PlaceDetectionClient mPlaceDetectionClient;
    private LatLng clickAddress;
    private FusedLocationProviderClient mFusedLocationClient;
    private TextInputEditText locationFrom;
    private TextInputEditText locationTo;
    private ViewGroup infoWindow;
    private TextView infoTitle;
    private TextView infoSnippet;
    private TextView infoSnippet2;
    private Button infoButton1;
    private OnInfoWindowElemTouchListener infoButtonListener;

    static String busLineID = "";
    static String direction = "inbound";
    static LatLng middleLat;
    static boolean cBusArrivalisFinished = false;
    static boolean cBusStopisFinished = false;
    static SlidingUpPanelLayout sup;
    static ConstraintLayout layoutBusDirection;
    static ConstraintLayout layoutBusRoute;
    public static LatLng travelFrom = new LatLng(0, 0);
    public static LatLng travelTo = new LatLng(0, 0);
    public MyResultReceiver mReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Contains everything with the map/panels/button included
        setContentView(R.layout.activity_main);
        mapContext = getApplicationContext();
        final SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mGeoDataClient = Places.getGeoDataClient(this, null);
        mPlaceDetectionClient = Places.getPlaceDetectionClient(this, null);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        animationRotate = AnimationUtils.loadAnimation(this, R.anim.rotate);
        sup = (SlidingUpPanelLayout) findViewById(R.id.sliding_layout);
        layoutBusDirection = (ConstraintLayout) findViewById(R.id.choice_A);
        layoutBusRoute = (ConstraintLayout) findViewById(R.id.choice_B);

        //Allows user to search for bus route
        busLineKeyboardSearchClick();
        getChoiceSpinner();
        showCustomAddressSearch();

        ImageView imgFavorite = (ImageView) findViewById(R.id.busDirection);
        imgFavorite.setOnClickListener(this);
        FloatingActionButton fabBack = (FloatingActionButton) findViewById(R.id.fab_current_location);
        fabBack.setOnClickListener(this);
        FloatingActionButton fabDirection = (FloatingActionButton) findViewById(R.id.fab_direction);
        fabDirection.setOnClickListener(this);
        FloatingActionButton planner = (FloatingActionButton) findViewById(R.id.fab_path);
        planner.setOnClickListener(this);
        Button button = (Button) findViewById(R.id.btnSubmit);
        button.setOnClickListener(this);
        mReceiver = new MyResultReceiver(new Handler());


//        AnyChartView anyChartView = findViewById(R.id.any_chart_view);
//
//        Pareto pareto = AnyChart.pareto();
//
//        List<DataEntry> data = new ArrayList<>();
//
//        data.add(new ValueDataEntry("Food is tasteless", 65));
//        data.add(new ValueDataEntry("Wait time", 109));
//        data.add(new ValueDataEntry("Unfriendly staff", 12.5));
//        data.add(new ValueDataEntry("Not clean", 45));
//        data.add(new ValueDataEntry("Overpriced", 250));
//        data.add(new ValueDataEntry("To noisy", 27));
//        data.add(new ValueDataEntry("Food not fresh", 35));
//        data.add(new ValueDataEntry("Small portions", 170));
//        data.add(new ValueDataEntry("Not atmosphere", 35));
//        data.add(new ValueDataEntry("Food is to salty", 35));
//
//        pareto.setData(data);
//
//        pareto.setTitle("Pareto Chart of Restaurant Complaints");
//
//        pareto.getYAxis(0d).setTitle("Defect frequency");
//
//        pareto.getYAxis(1d).setTitle("Cumulative Percentage");
//
//        pareto.setAnimation(true);
//
//        pareto.getLineMarker()
//                .setValue(80d)
//                .setAxis(pareto.getYAxis(1d))
//                .setStroke("#A5B3B3", 1d, "5 2", StrokeLineJoin.ROUND, StrokeLineCap.ROUND);
//
//        pareto.getGetSeries(0d).getTooltip().setFormat("Value: {%Value}");
//
//        CartesianSeriesBase line = pareto.getGetSeries(1d);
//        line.setSeriesType("spline")
//                .setMarkers(true);
//        line.getLabels().setEnabled(true);
//        line.getLabels()
//                .setAnchor(EnumsAnchor.RIGHT_BOTTOM)
//                .setFormat("{%CF}%");
//        line.getTooltip().setFormat("Cumulative Frequency: {%CF}% \\n Relative Frequency: {%RF}%");
//
//        pareto.getCrosshair().setEnabled(true);
//        pareto.getCrosshair().setXLabel(true);
//
//        anyChartView.setChart(pareto);
    }




    public void showCustomAddressSearch()
    {
        locationFrom = (TextInputEditText) findViewById(R.id.places_autocomplete_from);
        locationFrom.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    Log.d(TAG, "TRE");
                    showPlaceDialog(locationFrom);
                }else{
                    Log.d(TAG, "DSADA");
                }
                return false;
            }
        });

        locationTo = (TextInputEditText) findViewById(R.id.places_autocomplete_To);
        locationTo.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    Log.d(TAG, "TRE32");

                    showPlaceDialog(locationTo);
                }else{
                    Log.d(TAG, "gfsdfsd");

                }
                return false;
            }
        });
    }

    public void busLineKeyboardSearchClick()
    {
        final AutoCompleteTextView autoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.busNumber);
        final String[] busNum = getResources().getStringArray(R.array.bus_number);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, busNum);
        autoCompleteTextView.setAdapter(adapter);
        autoCompleteTextView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH ) {
                    if(!autoCompleteTextView.getText().toString().equals("")  && Arrays.asList(busNum).contains(autoCompleteTextView.getText().toString().toLowerCase()))
                    {
                        Toast.makeText(getApplicationContext(), "Getting bus stops " , Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), "You need to enter a correct value " , Toast.LENGTH_SHORT).show();
                    }


                    busLineID = autoCompleteTextView.getText().toString();
                    InputMethodManager inputManager = (InputMethodManager)
                            getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
                    API_CALL_CHOICE = 1;
                    //Allows for the custom window to be shown for all the stops
                    createCustomInfoWindow();
                    connectAndGetApiData();

                    sup.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
                    return true;
                }
                return false;
            }
        });
    }

    public void getChoiceSpinner() {
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
    }

    public void showPlaceDialog(final TextInputEditText textInputEditText) {
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
                        Log.d(TAG, "dsadasda");
                        //set textview or edittext
                        textInputEditText.setText(locationName);
                        checkAutoCompTextView();

                    }
                })

                .build();
        placeSearchDialog.show();
    }

    public void checkAutoCompTextView() {
        if (!locationFrom.getText().toString().equals("") && !locationTo.getText().toString().equals("")) {
            Log.d(TAG, "SUCE");
            String[] address = new String[2];
            address[0] = locationFrom.getText().toString();
            address[1] = locationTo.getText().toString();
            TextView from = (TextView) findViewById(R.id.fromLocation);
            from.setText(locationFrom.getText().toString());

            TextView to = (TextView) findViewById(R.id.toLocation);
            to.setText(locationTo.getText().toString());
            Log.d(TAG, "1 " + address[0]);
            Log.d(TAG, "1 " + address[1]);

            // String address = "3-41+Wykeham+Hill,+Wembley+HA9";
            for (String app : address) {
                String url = getGeocode(app);
               // Log.d(TAG, "URL " + url);
                DownloadTask downloadTask = new DownloadTask(true);
                downloadTask.execute(url);
            }
        } else if (locationFrom.getText().toString().equals("")) {
            Log.d(TAG, "from not null");
        } else if (locationTo.getText().toString().equals("")) {
            Log.d(TAG, "to not null");
        }

        Log.d(TAG, "loc " + locationFrom.getText() + " " + locationTo.getText());
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
                if(TflCalls.BUS_STOP_LINE_RESPONSE_CODE == 200)
                {
                    if (rotateDirectionLine)
                    {
                        rotateDirectionLine = false;
                        direction = "outbound";
                    } else
                    {
                        rotateDirectionLine = true;
                        direction = "inbound";
                    }
                    Toast.makeText(getApplicationContext(), "Switching direction to " + direction, Toast.LENGTH_SHORT).show();
                    image.startAnimation(animationRotate);
                    connectAndGetApiData();
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Need to enter a bus number  " + direction, Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.fab_current_location:
                Log.d(TAG, "CURRENT ");
                getAllPermission();
                break;

            case R.id.fab_direction:
                layoutBusRoute.setVisibility(View.GONE);
                layoutBusDirection.setVisibility(View.VISIBLE);
                if(sup.getPanelState().equals(SlidingUpPanelLayout.PanelState.EXPANDED)){
                    Log.d(TAG, "EXPANDED");

                }else if(sup.getPanelState().equals(SlidingUpPanelLayout.PanelState.COLLAPSED)){
                    Log.d(TAG, "COLLAPASED");
                }else if(sup.getPanelState().equals(SlidingUpPanelLayout.PanelState.DRAGGING)){
                    Log.d(TAG, "DRAGGING");
                }

                Log.d(TAG, "STATE " + sup.getPanelState());
                sup.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
                sup.setPanelHeight(0);
                break;

            case R.id.fab_path:
                API_CALL_CHOICE = 2;

                List<LatLng> l = Arrays.asList(TflCalls.from, TflCalls.to);
                for(LatLng a: l)
                {
                    String url = getReverseGeocode(a);
                  //  Log.d(TAG, "URL " + url);
                    DownloadTask downloadTask = new DownloadTask(false);
                    downloadTask.execute(url);
                }
                connectAndGetApiData();

//                final Intent intent = new Intent(Intent.ACTION_SYNC, null, mapContext, BackgroundService.class);
//                Bundle bundle = new Bundle();
//                intent.putExtra("command", "query");
//                intent.putExtra("receiver", mReceiver);
//                intent.putExtras(bundle);
//                mapContext.startService(intent);


                break;

            case R.id.btnSubmit:
                String[] some_array = getResources().getStringArray(R.array.travel_choice);
                    Initialisation initialisation = new Initialisation(BackgroundService.section1, BackgroundService.section2, BackgroundService.section3);
                    DrawingPath drawingPath = new DrawingPath(mMap, mapContext);
                //SelectingRoute selectingRoute = new SelectingRoute(TflCalls.journey);
//                    Legs [] legs = selectingRoute.getTime(1).getLegs();
                //optimal
                if (spinner.getSelectedItem().toString().equals(some_array[1])) {
                    Log.d(TAG, "OPTIMAL");
                    mMap.clear();
                    Individuals best =initialisation.comparePath(1, 1,4);
                    drawingPath.drawingPaths(best.getLatLngArray(), 1);
                    TextView textView = (TextView) findViewById(R.id.predictionText);
                    textView.setText("Duration: \n" + "Modes: \n" + " ");
                } else if (spinner.getSelectedItem().toString().equals(some_array[2])) {
                    Log.d(TAG, "QUICKEST");
                    mMap.clear();
                    Individuals best =initialisation.comparePath(1, 2,4);
                    drawingPath.drawingPaths(best.getLatLngArray(), 1);
                    TextView textView = (TextView) findViewById(R.id.predictionText);
                    textView.setText("Duration: \n" + "Modes: \n" + " ");
                } else if (spinner.getSelectedItem().toString().equals(some_array[3])) {
                    Log.d(TAG, "COST");
                    mMap.clear();
                    Individuals best =initialisation.comparePath(1, 3,4);
                    drawingPath.drawingPaths(best.getLatLngArray(), 1);
                    TextView textView = (TextView) findViewById(R.id.predictionText);
                    textView.setText("Duration: \n" + "Modes: \n" + " ");
                } else if (spinner.getSelectedItem().toString().equals(some_array[4])) {
                    Log.d(TAG, "VEHICLE");
                    mMap.clear();
                    Individuals best =initialisation.comparePath(1, 4,4);
                    drawingPath.drawingPaths(best.getLatLngArray(), 1);
                    TextView textView = (TextView) findViewById(R.id.predictionText);
                    textView.setText("Duration: \n" +
                            "Modes: \n" +
                            " ");
                }
                sup.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
                break;
        }
    }

    public void addIcon(IconGenerator iconGenerator, CharSequence text, LatLng position) {
        MarkerOptions markerOptions = new MarkerOptions().
                icon(BitmapDescriptorFactory.fromBitmap(iconGenerator.makeIcon(text))).
                position(position);
        mMap.addMarker(markerOptions);
    }

    private void getAllPermission() {
        Dexter.withActivity(this)
                .withPermissions(Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.INTERNET,
                        Manifest.permission.ACCESS_NETWORK_STATE)
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
                })
                .withErrorListener(new PermissionRequestErrorListener() {
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
    private void connectAndGetApiData() {
        TflCalls tflCalls = new TflCalls(mapContext, mMap, MainActivity.this, mReceiver);
        tflCalls.createRetrofit();
        String appId = getString(R.string.tfl_app_id);
        String apiKey = getString(R.string.tfl_key);
        tflCalls.choice(API_CALL_CHOICE, appId, apiKey);
    }

    private void createCustomInfoWindow() {
        final MapWrapperLayout mapWrapperLayout = (MapWrapperLayout) findViewById(R.id.map_relative_layout);
        mapWrapperLayout.init(mMap, getPixelsFromDp(this, 39 + 20));
        this.infoWindow = (ViewGroup) getLayoutInflater().inflate(R.layout.custom_infowindow, null);
        this.infoTitle = (TextView) infoWindow.findViewById(R.id.nameTxt);
        this.infoSnippet = (TextView) infoWindow.findViewById(R.id.addressTxt);
        this.infoButton1 = (Button) infoWindow.findViewById(R.id.btnOne);
        this.infoButtonListener = new OnInfoWindowElemTouchListener(infoButton1, getResources().getDrawable(R.drawable.btn_bg), getResources().getDrawable(R.drawable.btn_bg)) {
            @Override
            protected void onClickConfirmed(View v, Marker marker) {
                Intent intent = new Intent(v.getContext(), StreetViewPanorama.class);
                Bundle args = new Bundle();
                args.putParcelable("Position", clickAddress);
                intent.putExtra("bundle", args);
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
        return (int) (dp * scale + 0.5f);
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
        String url = "https://maps.googleapis.com/maps/api/geocode/" + output + "?" + parameters + "&key=" + apiKey;
        return url;
    }

    //Need to createWaypoints on it, fairly easy but haven't had use for it
    private String getReverseGeocode(LatLng origin)
    {
        String apiKey = getString(R.string.google_maps_key);

        //latlng or address
        String parameters = "latlng="+ origin.latitude + "," +origin.longitude;
        // Output format
        String output = "json";
        String url = "https://maps.googleapis.com/maps/api/geocode/" + output + "?" + parameters + "&key="+ apiKey;
        return url;
    }
}
