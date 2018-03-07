package com.example.frank.busmap.Pojo.getJourneyFromTo;

/**
 * Created by frank on 01/03/2018.
 */

public class Journeys {

    private String duration;

    private String startDateTime;

    private String arrivalDateTime;

    private Legs[] legs;

    private String $type;

    public String getDuration ()
    {
        return duration;
    }

    public void setDuration (String duration)
    {
        this.duration = duration;
    }

    public String getStartDateTime ()
    {
        return startDateTime;
    }

    public void setStartDateTime (String startDateTime)
    {
        this.startDateTime = startDateTime;
    }

    public String getArrivalDateTime ()
    {
        return arrivalDateTime;
    }

    public void setArrivalDateTime (String arrivalDateTime)
    {
        this.arrivalDateTime = arrivalDateTime;
    }

    public Legs[] getLegs ()
    {
        return legs;
    }
    public Legs getLegs (int num)
    {

        return legs[num];
    }

    public void setLegs (Legs[] legs)
    {
        this.legs = legs;
    }

    public String get$type ()
    {
        return $type;
    }

    public void set$type (String $type)
    {
        this.$type = $type;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [duration = "+duration+", startDateTime = "+startDateTime+", arrivalDateTime = "+arrivalDateTime+", legs = "+legs+", $type = "+$type+"]";
    }
}


