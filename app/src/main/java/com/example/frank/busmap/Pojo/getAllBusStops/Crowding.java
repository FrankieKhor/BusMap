package com.example.frank.busmap.Pojo.getAllBusStops;

/**
 * Created by frank on 08/02/2018.
 */

public class Crowding
{
    private String $type;

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
        return "ClassPojo [$type = "+$type+"]";
    }
}
