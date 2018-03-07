package com.example.frank.busmap.Pojo.getJourneyFromTo;

/**
 * Created by frank on 01/03/2018.
 */

public class Mode {
    private String id;

    private String name;

    private String $type;

    private String type;

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
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

    @Override
    public String toString()
    {
        return "ClassPojo [id = "+id+", name = "+name+", $type = "+$type+", type = "+type+"]";
    }

}
