package com.example.frank.busmap.Pojo.getJourneyFromTo;

import org.parceler.Parcel;

/**
 * Created by frank on 01/03/2018.
 */

@Parcel
public class Journeys {
    String duration;
    String startDateTime;
    String arrivalDateTime;
    Legs[] legs;
    String $type;

    public Journeys() {
    }

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


