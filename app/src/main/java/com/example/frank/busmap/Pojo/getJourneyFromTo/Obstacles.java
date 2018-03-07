package com.example.frank.busmap.Pojo.getJourneyFromTo;

/**
 * Created by frank on 02/03/2018.
 */

public class Obstacles {
    private String stopId;

    private String $type;

    private String type;

    private String incline;


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
