package com.example.frank.busmap.Pojo.getJourneyFromTo;

/**
 * Created by frank on 01/03/2018.
 */

public class PathAttribute {
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
