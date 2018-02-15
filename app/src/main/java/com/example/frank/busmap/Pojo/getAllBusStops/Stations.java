package com.example.frank.busmap.Pojo.getAllBusStops;

/**
 * Created by frank on 08/02/2018.
 */

public class Stations
{
    private String id;

    private String topMostParentId;

    private String icsId;

    private String lon;

    private String status;

    private String name;

    private String stopType;

    private String stationId;

    private Lines[] lines;

    private String[] modes;

    private String $type;

    private String lat;

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getTopMostParentId ()
    {
        return topMostParentId;
    }

    public void setTopMostParentId (String topMostParentId)
    {
        this.topMostParentId = topMostParentId;
    }

    public String getIcsId ()
    {
        return icsId;
    }

    public void setIcsId (String icsId)
    {
        this.icsId = icsId;
    }

    public String getLon ()
    {
        return lon;
    }

    public void setLon (String lon)
    {
        this.lon = lon;
    }

    public String getStatus ()
    {
        return status;
    }

    public void setStatus (String status)
    {
        this.status = status;
    }

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public String getStopType ()
    {
        return stopType;
    }

    public void setStopType (String stopType)
    {
        this.stopType = stopType;
    }

    public String getStationId ()
    {
        return stationId;
    }

    public void setStationId (String stationId)
    {
        this.stationId = stationId;
    }

    public Lines[] getLines ()
    {
        return lines;
    }

    public void setLines (Lines[] lines)
    {
        this.lines = lines;
    }

    public String[] getModes ()
    {
        return modes;
    }

    public void setModes (String[] modes)
    {
        this.modes = modes;
    }

    public String get$type ()
    {
        return $type;
    }

    public void set$type (String $type)
    {
        this.$type = $type;
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
        return "ClassPojo [id = "+id+", topMostParentId = "+topMostParentId+", icsId = "+icsId+", lon = "+lon+", status = "+status+", name = "+name+", stopType = "+stopType+", stationId = "+stationId+", lines = "+lines+", modes = "+modes+", $type = "+$type+", lat = "+lat+"]";
    }
}
