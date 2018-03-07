//package com.example.frank.busmap;
//
//import android.content.Context;
//import android.os.Bundle;
//import android.support.design.widget.FloatingActionButton;
//import android.support.v4.app.FragmentActivity;
//import android.util.Log;
//import android.view.KeyEvent;
//import android.view.View;
//import android.view.animation.AnimationUtils;
//import android.view.inputmethod.EditorInfo;
//import android.view.inputmethod.InputMethodManager;
//import android.widget.ArrayAdapter;
//import android.widget.AutoCompleteTextView;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.google.android.gms.location.places.Places;
//import com.google.android.gms.maps.SupportMapFragment;
//import com.seatgeek.placesautocomplete.OnPlaceSelectedListener;
//import com.seatgeek.placesautocomplete.PlacesAutocompleteTextView;
//import com.seatgeek.placesautocomplete.model.Place;
//import com.sothree.slidinguppanel.SlidingUpPanelLayout;
//
///**
// * Created by frank on 22/02/2018.
// */
//
//public class MainActivity extends FragmentActivity implements View.OnClickListener{
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.sliding_panel);
//
//        final SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
//                .findFragmentById(R.id.map);
//        mGeoDataClient = Places.getGeoDataClient(this, null);
//        mPlaceDetectionClient = Places.getPlaceDetectionClient(this, null);
//        //mFusedLocationProviderClient  = LocationServices.getFusedLocationProviderClient(this);
//       // mapFragment.getMapAsync(MapsActivity);
//        //getGoogleApiClient();
//
//        animationRotate = AnimationUtils.loadAnimation(this, R.anim.rotate);
//
//        //Allows user to search for bus route
//        final AutoCompleteTextView autoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.busNumber);
//        String[] busNum = getResources().getStringArray(R.array.bus_number);
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
//                android.R.layout.simple_list_item_1, busNum);
//        autoCompleteTextView.setAdapter(adapter);
//        autoCompleteTextView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
//                    lineID = autoCompleteTextView.getText().toString();
//                    InputMethodManager inputManager = (InputMethodManager)
//                            getSystemService(Context.INPUT_METHOD_SERVICE);
//
//                    inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
//                            InputMethodManager.HIDE_NOT_ALWAYS);
//                    connectAndGetApiData();
//                    SlidingUpPanelLayout sup = (SlidingUpPanelLayout)findViewById(R.id.sliding_layout) ;
//                    sup.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
//                    return true;
//                }
//                return false;
//            }
//        });
//
//        ImageView imgFavorite = (ImageView) findViewById(R.id.busDirection);
//        imgFavorite.setOnClickListener(this);
//        FloatingActionButton fabBack = (FloatingActionButton) findViewById(R.id.fab_current_location);
//        fabBack.setOnClickListener(this);
//        FloatingActionButton fabDirection = (FloatingActionButton) findViewById(R.id.fab_direction);
//        fabDirection.setOnClickListener(this);
//
//        PlacesAutocompleteTextView from = (PlacesAutocompleteTextView )findViewById(R.id.places_autocomplete_from);
//        PlacesAutocompleteTextView to = (PlacesAutocompleteTextView )findViewById(R.id.places_autocomplete_from);
//        from.setOnPlaceSelectedListener(
//                new OnPlaceSelectedListener() {
//                    @Override
//                    public void onPlaceSelected(final Place place) {
//                        Log.d(TAG, "HI" + place.place_id);
//                        // do something awesome with the selected place
//                    }
//                });
//    }
//
//    @Override
//    public void onClick(View v) {
//        SlidingUpPanelLayout sup = (SlidingUpPanelLayout) findViewById(R.id.sliding_layout);
//        //FloatingActionButton mFab = (FloatingActionButton) findViewById(R.id.fab_back);
//        FloatingActionButton faq = (FloatingActionButton) findViewById(R.id.fab_direction);
//
//        switch (v.getId()){
//            case R.id.busDirection:
//                ImageView image = (ImageView) findViewById(R.id.busDirection);
//                //  Log.d(TAG, direction +" GOO " + rotateDirectionLine);
//                //True will represent inbound and false will be outbound
//
//                if(rotateDirectionLine)
//                {
//                    rotateDirectionLine = false;
//                    direction = "outbound";
//                }
//                else
//                {
//                    rotateDirectionLine = true;
//                    direction = "inbound";
//                }
//                image.startAnimation(animationRotate);
//
//                break;
//
//            case R.id.fab_current_location:
//                Log.d(TAG, "CURRENT ");
//                GoogleMapPermission  a = new GoogleMapPermission();
//                a.updateLocationUI(mMap);
//                //getAllPermission();
//                break;
//
//            case R.id.fab_direction:
//                //mFab.show();
//                sup.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
//                //addVisibilityChanged.onShown(new FloatingActionButton(v.getContext()));
//                break;
//            case R.id.sliding_layout:
//                Log.d(TAG, "TOUCHING ");
////                if(sup.setPanelState(SlidingUpPanelLayout.PanelState)){
////                InputMethodManager inputManager = (InputMethodManager)
////                        getSystemService(Context.INPUT_METHOD_SERVICE);
////
////                inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
////                        InputMethodManager.HIDE_NOT_ALWAYS);
////            }
//                break;
//        }
//    }
//}
