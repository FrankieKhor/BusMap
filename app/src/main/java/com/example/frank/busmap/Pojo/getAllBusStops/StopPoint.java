package com.example.frank.busmap.Pojo.getAllBusStops;

/**
 * Created by frank on 08/02/2018.
 */

public class StopPoint
{
    private String topMostParentId;

    private String icsId;

    private String lon;

    private String stopLetter;

    private String status;

    private String stationId;

    private String id;

    private String parentId;

    private String stopType;

    private String name;

    private String $type;

    private String[] modes;

    private Lines[] lines;

    private String lat;

    private String [] latLng;

    public String getTopMostParentId ()
    {
        return topMostParentId;
    }

    public void setTopMostParentId (String topMostParentId)
    {
        this.topMostParentId = topMostParentId;
    }

    public void setLatLng (String [] latLng)
    {
        this.latLng = latLng;
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

    public String getStopLetter ()
    {
        return stopLetter;
    }

    public void setStopLetter (String stopLetter)
    {
        this.stopLetter = stopLetter;
    }

    public String getStatus ()
    {
        return status;
    }

    public void setStatus (String status)
    {
        this.status = status;
    }

    public String getStationId ()
    {
        return stationId;
    }

    public void setStationId (String stationId)
    {
        this.stationId = stationId;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getParentId ()
    {
        return parentId;
    }

    public void setParentId (String parentId)
    {
        this.parentId = parentId;
    }

    public String getStopType ()
    {
        return stopType;
    }

    public void setStopType (String stopType)
    {
        this.stopType = stopType;
    }

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public String get$type ()
    {
        return $type;
    }

    public void set$type (String $type)
    {
        this.$type = $type;
    }

    public String[] getModes ()
    {
        return modes;
    }

    public void setModes (String[] modes)
    {
        this.modes = modes;
    }

    public Lines[] getLines ()
    {
        return lines;
    }

    public void setLines (Lines[] lines)
    {
        this.lines = lines;
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
        //Used star to seperate into different arrays
        return id + "*" + name + "|" + lat + "&"+lon + "\n";
    }
}
