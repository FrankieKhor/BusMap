package com.example.frank.busmap.Pojo.getJourneyFromTo;


import org.parceler.Parcel;

/**
 * Created by frank on 01/03/2018.
 */
@Parcel
public class RouteOptions {

    LineIdentifier lineIdentifier;
    String[] directions;
    String name;
    String $type;

    public RouteOptions() {
    }

    public LineIdentifier getLineIdentifier ()
    {
        return lineIdentifier;
    }

    public void setLineIdentifier (LineIdentifier lineIdentifier)
    {
        this.lineIdentifier = lineIdentifier;
    }

    public String[] getDirections ()
    {
        return directions;
    }

    public void setDirections (String[] directions)
    {
        this.directions = directions;
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

    @Override
    public String toString()
    {
        return "ClassPojo [lineIdentifier = "+lineIdentifier+", directions = "+directions+", name = "+name+", $type = "+$type+"]";
    }

}
