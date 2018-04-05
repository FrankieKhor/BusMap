package com.example.frank.busmap.Pojo.getJourneyFromTo;

import org.parceler.Parcel;

/**
 * Created by frank on 29/03/2018.
 */
@Parcel
public class Lines {
    String id;

    String[] routeSections;

     Crowding crowding;

     String created;

     String[] disruptions;

     String name;

     ServiceTypes[] serviceTypes;

     String $type;

     String modeName;

     String modified;

     LineStatuses[] lineStatuses;

    public Lines() {
    }
    public LineStatuses[] getLineStatuses ()
    {
        return lineStatuses;
    }

    public void setLineStatuses (LineStatuses[] lineStatuses)
    {
        this.lineStatuses = lineStatuses;
    }

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String[] getRouteSections ()
    {
        return routeSections;
    }

    public void setRouteSections (String[] routeSections)
    {
        this.routeSections = routeSections;
    }

    public Crowding getCrowding ()
    {
        return crowding;
    }

    public void setCrowding (Crowding crowding)
    {
        this.crowding = crowding;
    }

    public String getCreated ()
    {
        return created;
    }

    public void setCreated (String created)
    {
        this.created = created;
    }

    public String[] getDisruptions ()
    {
        return disruptions;
    }

    public void setDisruptions (String[] disruptions)
    {
        this.disruptions = disruptions;
    }

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public ServiceTypes[] getServiceTypes ()
    {
        return serviceTypes;
    }

    public void setServiceTypes (ServiceTypes[] serviceTypes)
    {
        this.serviceTypes = serviceTypes;
    }

    public String get$type ()
    {
        return $type;
    }

    public void set$type (String $type)
    {
        this.$type = $type;
    }

    public String getModeName ()
    {
        return modeName;
    }

    public void setModeName (String modeName)
    {
        this.modeName = modeName;
    }

    public String getModified ()
    {
        return modified;
    }

    public void setModified (String modified)
    {
        this.modified = modified;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [lineStatuses = "+lineStatuses+", id = "+id+", routeSections = "+routeSections+", crowding = "+crowding+", created = "+created+", disruptions = "+disruptions+", name = "+name+", serviceTypes = "+serviceTypes+", $type = "+$type+", modeName = "+modeName+", modified = "+modified+"]";
    }

}
