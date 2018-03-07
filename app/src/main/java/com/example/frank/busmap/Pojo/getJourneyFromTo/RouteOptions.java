package com.example.frank.busmap.Pojo.getJourneyFromTo;

/**
 * Created by frank on 01/03/2018.
 */

public class RouteOptions {
    private LineIdentifier lineIdentifier;

    private String[] directions;

    private String name;

    private String $type;

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
