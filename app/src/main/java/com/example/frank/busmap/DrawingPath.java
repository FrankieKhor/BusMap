package com.example.frank.busmap;

/**
 * Created by frank on 27/03/2018.
 */


import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.widget.Toast;

import com.ahmadrosid.lib.drawroutemap.DrawMarker;
import com.example.frank.busmap.Pojo.getJourneyFromTo.Legs;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Dash;
import com.google.android.gms.maps.model.Dot;
import com.google.android.gms.maps.model.Gap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PatternItem;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;

/**
 * Created by frank on 27/03/2018.
 */

public class DrawingPath implements GoogleMap.OnPolylineClickListener {
    GoogleMap mMap;
    Context mapContext;
    LatLng from = TflCalls.from;
    LatLng to = TflCalls.to;
    private static PolylineOptions polylineOptions;
    public DrawingPath(GoogleMap mMap, Context mapContext){
        this.mMap = mMap;
        this.mapContext = mapContext;
        polylineOptions = new PolylineOptions();
    }

    public void addingPaths(Legs[] legs)
    {
        // final LatLng fromWembley = MainActivity.travelFrom;
        // final LatLng toEdgaware = MainActivity.travelTo;
        for (int i = 0; i < legs.length; i++)
        {
            // Legs[] legs = journey[i].getLegs();
            //Log.d(TAG, "legleg " + legs.length + " " +legs[0].getInstruction()+ " " +legs[1].getInstruction()+ " " +legs[2].getInstruction()+ " " +legs[3].getInstruction());
            if (i == 0)
            {
                drawingPaths(legs, 1);
            }
            else if (i == 1)
            {
                drawingPaths(legs, 2);
            }
            else if (i == 2)
            {
                drawingPaths(legs, 3);
            }
            else if (i == 3)
            {
                drawingPaths(legs, 4);
            }
            else if (i == 4)
            {
                drawingPaths(legs, 5);
            }
            else if (i == 5)
            {
                drawingPaths(legs, 6);
            }
        }
//        Disruption a = new Disruption(new LatLng(51.564072184349996, -0.27827641759000005), mapContext, mMap);
//        a.drawDisruption();
    }


    public void drawingPaths(Legs[] legs, int colourChoice)
    {
//        final LatLng fromWembley = MainActivity.travelFrom;
//        final LatLng toEdgware = MainActivity.travelTo;

        CameraUpdate location = CameraUpdateFactory.newLatLngZoom(new LatLng(Double.valueOf(legs[legs.length/2].getArrivalPoint().getLat()),
                Double.valueOf(legs[legs.length/2].getArrivalPoint().getLon())), 12);
        mMap.animateCamera(location);


        DrawMarker.getInstance(mapContext).draw(mMap, from, R.drawable.pin_start_24dp );
        DrawMarker.getInstance(mapContext).draw(mMap, to, R.drawable.pin_end_24dp);
        List<PatternItem> patternWalk = Arrays.<PatternItem>asList(new Dot());
        List<PatternItem> patternTube = Arrays.<PatternItem>asList(new Dot(), new Gap(20), new Dash(30), new Gap(20));
        List<PatternItem> patternOverground = Arrays.<PatternItem>asList(new Dot(), new Gap(20), new Dash(30), new Gap(20));

        for(int i =0;i<legs.length;i++)
        {
            LatLng latLoc = new LatLng(Double.valueOf(legs[i].getArrivalPoint().getLat()), Double.valueOf(legs[i].getArrivalPoint().getLon()));
            //Makes sure to not override end stop
            if(i != legs.length-1)
            {
                DrawMarker.getInstance(mapContext).draw(mMap, latLoc, R.drawable.pin_every_24dp, legs[i].getDeparturePoint().getCommonName(), legs[i].getInstruction().getSummary(), legs[i].getArrivalTime() );
            }
            if(legs[i].getModeName().equals("walking"))
            {
                addPolyline(legs, i, colourChoice, patternWalk);
            }
            else if(legs[i].getModeName().equals("tube"))
            {
                addPolyline(legs, i, colourChoice, patternTube);

            }
            else if(legs[i].getModeName().equals("overground"))
            {
                addPolyline(legs, i, colourChoice, patternOverground);
            }
            else if(legs[i].getModeName().equals("bus"))
            {
                addPolyline(legs, i, colourChoice);
            }
            else
            {
                addPolyline(legs, i, colourChoice);
            }
        }
    }

    public void drawingPaths(ArrayList<LatLng> latLngs, int colourChoice)
    {
        mMap.clear();
        Log.d("DRAWSAD", "dfgsgdf " + latLngs.size());

//        final LatLng fromWembley = MainActivity.travelFrom;
//        final LatLng toEdgware = MainActivity.travelTo;

        CameraUpdate location = CameraUpdateFactory.newLatLngZoom(latLngs.get(latLngs.size()/2), 12);
        mMap.animateCamera(location);


        DrawMarker.getInstance(mapContext).draw(mMap, from, R.drawable.pin_start_24dp );
        DrawMarker.getInstance(mapContext).draw(mMap, to, R.drawable.pin_end_24dp);
        List<PatternItem> patternWalk = Arrays.<PatternItem>asList(new Dot());
        List<PatternItem> patternTube = Arrays.<PatternItem>asList(new Dot(), new Gap(20), new Dash(30), new Gap(20));
        List<PatternItem> patternOverground = Arrays.<PatternItem>asList(new Dot(), new Gap(20), new Dash(30), new Gap(20));

        for(int i =0;i<latLngs.size();i++)
        {
            //Makes sure to not override end stop
//            if(i > 0)
//            {
//                DrawMarker.getInstance(mapContext).draw(mMap, latLngs.get(i), R.drawable.pin_every_24dp );
//            }
            polylineOptions = new PolylineOptions()
                    .clickable(true)
                    .addAll(latLngs);
            mMap.addPolyline(polylineOptions);
        }

        Polyline polyline = mMap.addPolyline(polylineOptions);
        polyline.setTag("asda");
        mMap.setOnPolylineClickListener(this);

    }

    private void addPolyline(Legs [] legs, int i,  int colorChoice,List<PatternItem> pattern )
    {
        String colour = "";
        polylineOptions = new PolylineOptions()
                .clickable(true)
                .addAll(legs[i].getPathsOption().getLineString())
                .pattern(pattern);

        switch (colorChoice){
            case 1: polylineOptions.color(Color.BLACK);
                colour = "black";
                break;
            case 2: polylineOptions.color(Color.BLUE);
                colour = "blue";
                break;
            case 3: polylineOptions.color(Color.RED);
                colour = "red";
                break;
            case 4: polylineOptions.color(Color.GREEN);
                colour = "green";
                break;
        }

        Polyline polyline = mMap.addPolyline(polylineOptions);
        polyline.setTag(legs[i].getInstruction() + " and Arrive in " + legs[i].getDuration() + " minute " + " , colour " + colour);
        mMap.setOnPolylineClickListener(this);
    }

    public void clearLine(){
        mMap.clear();
    }


    private void addPolyline(Legs[] legs, int i, int colorChoice )
    {
        String colour = "";
        polylineOptions = new PolylineOptions()
                .clickable(true)
                .addAll(legs[i].getPathsOption().getLineString());

        switch (colorChoice){
            case 1: polylineOptions.color(Color.BLACK);
            colour = "black";
                break;
            case 2: polylineOptions.color(Color.BLUE);
                colour = "blue";
                break;
            case 3: polylineOptions.color(Color.RED);
                colour = "red";
                break;
            case 4: polylineOptions.color(Color.GREEN);
                colour = "green";
                break;
        }

        Polyline polyline = mMap.addPolyline(polylineOptions);
        polyline.setTag(legs[i].getInstruction() + " and Arrive in " + legs[i].getDuration() + " minute " + " , colour " + colour);
        mMap.setOnPolylineClickListener(this);


    }

    @Override
    public void onPolylineClick(Polyline polyline)
    {
        Toast.makeText(mapContext, polyline.getTag().toString(),
                Toast.LENGTH_SHORT).show();
    }
}

