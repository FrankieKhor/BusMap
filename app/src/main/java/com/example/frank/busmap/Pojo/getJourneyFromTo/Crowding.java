package com.example.frank.busmap.Pojo.getJourneyFromTo;

import org.parceler.Parcel;

/**
 * Created by frank on 29/03/2018.
 */
@Parcel
public class Crowding {
    String $type;

    public Crowding() {
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
        return "ClassPojo [$type = "+$type+"]";
    }
}
