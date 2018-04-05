package com.example.frank.busmap.Pojo.getJourneyFromTo;

import org.parceler.Parcel;

/**
 * Created by frank on 02/03/2018.
 */
@Parcel
public class Obstacles {
     String stopId;

     String $type;

     String type;

     String incline;

    public Obstacles() {
    }


    public String getStopId ()
    {
        return stopId;
    }

    public void setStopId (String stopId)
    {
        this.stopId = stopId;
    }

    public String get$type ()
    {
        return $type;
    }

    public void set$type (String $type)
    {
        this.$type = $type;
    }

    public String getType ()
    {
        return type;
    }

    public void setType (String type)
    {
        this.type = type;
    }

    public String getIncline ()
    {
        return incline;
    }

    public void setIncline (String incline)
    {
        this.incline = incline;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [stopId = "+stopId+", $type = "+$type+", type = "+type+", incline = "+incline+"]";
    }
}
