package com.example.frank.busmap.Pojo.getJourneyFromTo;

/**
 * Created by frank on 01/03/2018.
 */

public class ArrivalPoint {
    private String commonName;

    private String lon;

    private String $type;

    private String placeType;

    private String lat;

    public String getCommonName ()
    {
        return commonName;
    }

    public void setCommonName (String commonName)
    {
        this.commonName = commonName;
    }

    public String getLon ()
    {
        return lon;
    }

    public void setLon (String lon)
    {
        this.lon = lon;
    }

    public String get$type ()
    {
        return $type;
    }

    public void set$type (String $type)
    {
        this.$type = $type;
    }

    public String getPlaceType ()
    {
        return placeType;
    }

    public void setPlaceType (String placeType)
    {
        this.placeType = placeType;
    }

    public String getLat ()
    {
        return lat;
    }

    public void setLat (String lat)
    {
        this.lat = lat;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [commonName = "+commonName+", lon = "+lon+", $type = "+$type+", placeType = "+placeType+", lat = "+lat+"]";
    }

}
