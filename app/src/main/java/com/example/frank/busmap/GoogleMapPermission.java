package com.example.frank.busmap;

import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

/**
 * Created by frank on 22/02/2018.
 */

public class GoogleMapPermission extends FragmentActivity  {
    private static final String TAG = "GoogleMapPermission";
    Boolean mLocationPermissionGranted = false;
    static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 0;
    private FusedLocationProviderClient mFusedLocationProviderClient ;
    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 34;
    private GoogleApiClient mGoogleApiClient;
    private MultiplePermissionsListener allPermissionsListener;
    private PermissionRequestErrorListener errorListener;
    private LocationServices mLastKnownLocation;
    GoogleMap mMap;

    public void updateLocationUI(GoogleMap mMap) {
        this.mMap = mMap;
        if (mMap == null) {
            Log.d(TAG,"NULL");

            return;
        }
        try {
            if (mLocationPermissionGranted) {
                Log.d(TAG,"1");
                mMap.setMyLocationEnabled(true);
                mMap.getUiSettings().setMyLocationButtonEnabled(true);
                getLastLocation();


            } else {
                Log.d(TAG,"2");
                mMap.setMyLocationEnabled(false);
                mMap.getUiSettings().setMyLocationButtonEnabled(false);
                mLastKnownLocation = null;
                getLocationPermission();
            }
        } catch (SecurityException e)  {
            Log.e("Exception: %s", e.getMessage());
        }
    }

    private void getLocationPermission() {
    /*
     * Request location permission, so that we can get the location of the
     * device. The result of the permission request is handled by a callback,
     * onRequestPermissionsResult.
     */
        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        mLocationPermissionGranted = false;
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = true;
                }
            }
        }
        updateLocationUI(mMap);
    }


    public void getLastLocation(){
        mFusedLocationProviderClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                new LatLng(location.getLatitude(),
                                        location.getLongitude()), 15));
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            updateLocationUI(mMap);
                            // Logic to handle location object
                        }
                    }
                });

    }
}
