package com.example.frank.busmap.Pojo.getAllBusStops;

import org.parceler.Parcel;

/**
 * Created by frank on 08/02/2018.
 */

public class Crowding
{
     String $type;

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
