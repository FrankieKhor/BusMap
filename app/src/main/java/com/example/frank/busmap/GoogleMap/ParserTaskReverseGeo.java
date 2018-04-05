package com.example.frank.busmap.GoogleMap;

import android.os.AsyncTask;
import android.util.Log;

import com.example.frank.busmap.MainActivity;
import com.example.frank.busmap.TflCalls;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by frank on 09/03/2018.
 */

public class ParserTaskReverseGeo extends AsyncTask<String, Integer, HashMap<String, LatLng>>
{
    private String TAG = ParserTaskReverseGeo.class.getName();

    // Parsing the data in non-ui thread
    @Override
    protected HashMap<String, LatLng> doInBackground(String... jsonData)
    {
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
            String b = jArray.getJSONObject(0).getString("short_name")  +", " + jArray.getJSONObject(1).getString("short_name");
           //Log.d(TAG , "bbbbbbbbbbbbbbb " +  b);
            hashMap.put(b, latLng);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return hashMap;
    }

    @Override
    protected void onPostExecute(HashMap<String, LatLng> result)
    {
        String address ="";
        for(String key: result.keySet())
        {
         //   Log.d(TAG, "fs " + key);
            address = key;
        }
        //Log.d(TAG, "ADDD " + address );

        if(TflCalls.travelFrom.equals(""))
        {
            //Log.d(TAG,"1111");
            TflCalls.travelFrom = address;
           // Log.d(TAG, "TFLCALLL " + TflCalls.travelFrom);
        }
        else if(TflCalls.travelTo.equals(""))
        {
           // Log.d(TAG,"2222");
            TflCalls.travelTo = address;
            //Log.d(TAG, "TFLCALLL " + TflCalls.travelTo);
        }
    }
}