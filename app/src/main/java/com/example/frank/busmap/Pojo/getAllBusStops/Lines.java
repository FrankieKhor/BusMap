package com.example.frank.busmap.Pojo.getAllBusStops;

/**
 * Created by frank on 08/02/2018.
 */

public class Lines
{
    private String id;

    private Crowding crowding;

    private String name;

    private String $type;

    private String type;

    private String uri;

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public Crowding getCrowding ()
    {
        return crowding;
    }

    public void setCrowding (Crowding crowding)
    {
        this.crowding = crowding;
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

    public String getType ()
    {
        return type;
    }

    public void setType (String type)
    {
        this.type = type;
    }

    public String getUri ()
    {
        return uri;
    }

    public void setUri (String uri)
    {
        this.uri = uri;
    }

    @Override
    public String toString()
    {
        return "[id = "+id+", name = "+name+", lat = "+$type+", type = "+type+", uri = "+uri+"]";
    }
}
