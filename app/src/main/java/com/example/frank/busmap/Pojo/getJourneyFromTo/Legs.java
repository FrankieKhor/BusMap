package com.example.frank.busmap.Pojo.getJourneyFromTo;

import org.parceler.Parcel;

/**
 * Created by frank on 01/03/2018.
 */
//Will look into using native serialization just to understand it but using parcelable  because of performance

@Parcel
public class Legs {
    ArrivalPoint arrivalPoint;
    Obstacles[] obstacles;
    Disruptions[] disruptions;
    String departureTime;
    String isDisrupted;
    Mode mode;
    String distance;
    String duration;
    String arrivalTime;
    RouteOptions[] routeOptions;
    String hasFixedLocations;
    Path path;
    DeparturePoint departurePoint;
    String $type;
    Instruction instruction;
    String[] plannedWorks;

    public Legs() {
    }

    public ArrivalPoint getArrivalPoint ()
    {
        return arrivalPoint;
    }

    public void setArrivalPoint (ArrivalPoint arrivalPoint)
    {
        this.arrivalPoint = arrivalPoint;
    }

    public Obstacles[] getObstacles ()
    {
        return obstacles;
    }

    public void setObstacles (Obstacles[] obstacles)
    {
        this.obstacles = obstacles;
    }

    public Disruptions[] getDisruptions ()
    {
        return disruptions;
    }

    public void setDisruptions (Disruptions[] disruptions)
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
    public String getModeName ()
    {
        return mode.getName();
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

    public Path getPathsOption()
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
