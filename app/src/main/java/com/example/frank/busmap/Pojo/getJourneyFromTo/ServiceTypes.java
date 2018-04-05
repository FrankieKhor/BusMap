package com.example.frank.busmap.Pojo.getJourneyFromTo;

import org.parceler.Parcel;

/**
 * Created by frank on 29/03/2018.
 */
@Parcel
public class ServiceTypes {
    String $type;
    String uri;
    String name;

    public ServiceTypes() {
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
        return "ClassPojo [name = "+name+", $type = "+$type+", uri = "+uri+"]";
    }
}
