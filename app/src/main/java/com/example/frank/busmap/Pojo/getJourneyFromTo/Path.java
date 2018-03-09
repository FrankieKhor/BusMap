package com.example.frank.busmap.Pojo.getJourneyFromTo;

import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by frank on 01/03/2018.
 */

public class Path {
    private ArrivalPoint arrivalPoint;

    private String[] obstacles;

    private String[] disruptions;

    private String departureTime;

    private String isDisrupted;

    private Mode mode;

    private String distance;

    private String duration;

    private String arrivalTime;

    private RouteOptions[] routeOptions;

    private String hasFixedLocations;

    private Path path;

    private DeparturePoint departurePoint;

    private String $type;

    private Instruction instruction;

    private String[] plannedWorks;
    private String lineString;
    private String[] arrayLineString;

    public String getLineString (int num)
    {
        return arrayLineString[num];
    }

    public ArrayList<LatLng> getLineString ()
    {
        String improve = lineString.replaceAll("\\[|\\]", "");
        arrayLineString = (improve.split(","));
        ArrayList<LatLng> lob= new ArrayList<>();

        for(int i =1;i<arrayLineString.length;i+=2){
            double a = Double.valueOf(arrayLineString[(i-1)]);
            double b = Double.valueOf(arrayLineString[i]);
            lob.add(new LatLng(a,b));
            //to[(i-1)] = new LatLng(a,b);
        }

        return lob;
    }



    public void setLineString (String lineString)
    {
        this.lineString = lineString;
    }
    public ArrivalPoint getArrivalPoint ()
    {
        return arrivalPoint;
    }

    public void setArrivalPoint (ArrivalPoint arrivalPoint)
    {
        this.arrivalPoint = arrivalPoint;
    }

    public String[] getObstacles ()
    {
        return obstacles;
    }

    public void setObstacles (String[] obstacles)
    {
        this.obstacles = obstacles;
    }

    public String[] getDisruptions ()
    {
        return disruptions;
    }

    public void setDisruptions (String[] disruptions)
    {
        this.disruptions = disruptions;
    }

    public String getDepartureTime ()
    {
        return departureTime;
    }

    public void setDepartureTime (String departureTime)
    {
        this.departureTime = departureTime;
    }

    public String getIsDisrupted ()
    {
        return isDisrupted;
    }

    public void setIsDisrupted (String isDisrupted)
    {
        this.isDisrupted = isDisrupted;
    }

    public Mode getMode ()
    {
        return mode;
    }

    public void setMode (Mode mode)
    {
        this.mode = mode;
    }

    public String getDistance ()
    {
        return distance;
    }

    public void setDistance (String distance)
    {
        this.distance = distance;
    }

    public String getDuration ()
    {
        return duration;
    }

    public void setDuration (String duration)
    {
        this.duration = duration;
    }

    public String getArrivalTime ()
    {
        return arrivalTime;
    }

    public void setArrivalTime (String arrivalTime)
    {
        this.arrivalTime = arrivalTime;
    }

    public RouteOptions[] getRouteOptions ()
    {
        return routeOptions;
    }

    public void setRouteOptions (RouteOptions[] routeOptions)
    {
        this.routeOptions = routeOptions;
    }

    public String getHasFixedLocations ()
    {
        return hasFixedLocations;
    }

    public void setHasFixedLocations (String hasFixedLocations)
    {
        this.hasFixedLocations = hasFixedLocations;
    }

    public Path getPath ()
    {
        return path;
    }

    public void setPath (Path path)
    {
        this.path = path;
    }

    public DeparturePoint getDeparturePoint ()
    {
        return departurePoint;
    }

    public void setDeparturePoint (DeparturePoint departurePoint)
    {
        this.departurePoint = departurePoint;
    }

    public String get$type ()
    {
        return $type;
    }

    public void set$type (String $type)
    {
        this.$type = $type;
    }

    public Instruction getInstruction ()
    {
        return instruction;
    }

    public void setInstruction (Instruction instruction)
    {
        this.instruction = instruction;
    }

    public String[] getPlannedWorks ()
    {
        return plannedWorks;
    }

    public void setPlannedWorks (String[] plannedWorks)
    {
        this.plannedWorks = plannedWorks;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [arrivalPoint = "+arrivalPoint+", obstacles = "+obstacles+", disruptions = "+disruptions+", departureTime = "+departureTime+", isDisrupted = "+isDisrupted+", mode = "+mode+", distance = "+distance+", duration = "+duration+", arrivalTime = "+arrivalTime+", routeOptions = "+routeOptions+", hasFixedLocations = "+hasFixedLocations+", path = "+path+", departurePoint = "+departurePoint+", $type = "+$type+", instruction = "+instruction+", plannedWorks = "+plannedWorks+"]";
    }


}
