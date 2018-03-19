package com.example.frank.busmap.GoogleMap;

import android.os.AsyncTask;
import android.util.Log;

import com.example.frank.busmap.MainActivity;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by frank on 09/03/2018.
 */

public class ParserTask extends AsyncTask<String, Integer, HashMap<String, LatLng>> {

    // Parsing the data in non-ui thread
    @Override
    protected HashMap<String, LatLng> doInBackground(String... jsonData) {
        Log.d("PARSER TASK   ", " pooo " );
        HashMap<String, LatLng> hashMap = new HashMap<>();
        JSONObject jObject;
        JSONArray jArray;
        JSONObject jLocation;
        //Last digit is temporary as not addresses are returned in the appropriate format e.g.
        //Uxbridge Road, London, Greater London, W5 3LD
        int [] addressComponent = {0,1,2,6};
        try {

            jObject = new JSONObject(jsonData[0]);
            jArray = jObject.getJSONArray("results").getJSONObject(0).getJSONArray("address_components");
            jLocation = jObject.getJSONArray("results").getJSONObject(0).getJSONObject("geometry").getJSONObject("location");
            LatLng latLng = new LatLng(jLocation.getDouble("lat"), jLocation.getDouble("lng"));
            addressComponent[3] = (jArray.length()-1);
            Log.d("gdfgewr4trerg" , " " + latLng);
            String b = "";
            for(int num: addressComponent){
                b += jArray.getJSONObject(num).getString("short_name") + " ";
            }

           Log.d("dasdas " , " " +  b);

            hashMap.put(b, latLng);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hashMap;
    }

    @Override
    protected void onPostExecute(HashMap<String, LatLng> result) {
        String address ="";
        LatLng latLng;
        for(String key: result.keySet()){
            Log.d("DASDa s", " " + key);
            address = key;
        }
        for(LatLng a: result.values()){
           latLng = a;
           Log.d("POOO", " A " + address + " l " + latLng);
            if(MainActivity.travelFrom.latitude == 0 && MainActivity.travelFrom.longitude == 0 ){
                MainActivity.travelFrom = a;
            }else{
                MainActivity.travelTo = a;

            }

        }



        }

}