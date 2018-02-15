package com.example.frank.busmap.Pojo.getAllBusStops;

import com.google.android.gms.maps.model.LatLng;

import java.util.Arrays;

/**
 * Created by frank on 08/02/2018.
 */

public class OrderedLineRoutes
{
    private String[] naptanIds;

    private String name;

    private String $type;

    private String serviceType;

    public String[] getNaptanIds ()
    {

        return naptanIds ;
    }

    public String getNaptanIds (int num)
    {

        return naptanIds[num] ;
    }

    public int getLength(){
        return naptanIds.length;
    }

    public void setNaptanIds (String[] naptanIds)
    {
        this.naptanIds = naptanIds;
    }

    public String getName ()
    {
        return name;
    }
    public String getName (int num)
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

    public String getServiceType ()
    {
        return serviceType;
    }

    public void setServiceType (String serviceType)
    {
        this.serviceType = serviceType;
    }

    @Override
    public String toString()
    {
        return Arrays.toString(getNaptanIds());
    }
}
