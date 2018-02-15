package com.example.frank.busmap.Pojo.getAllBusStops;

import com.google.android.gms.maps.model.LatLng;

import java.util.Arrays;
import java.util.List;

/**
 * Created by frank on 08/02/2018.
 */

public class StopPointSequences
{
    private String[] nextBranchIds;

    private String[] prevBranchIds;

    private String branchId;

    private String direction;

    private String lineName;

    private String $type;

    private String serviceType;

    private StopPoint[] stopPoint;

    private String[] id;
    private String[] idName;
    private List<LatLng> idCoord;

    private String lineId;

    public String[] getNextBranchIds ()
    {
        return nextBranchIds;
    }

    public void setNextBranchIds (String[] nextBranchIds)
    {
        this.nextBranchIds = nextBranchIds;
    }

    public String[] getPrevBranchIds ()
    {
        return prevBranchIds;
    }

    public void setPrevBranchIds (String[] prevBranchIds)
    {
        this.prevBranchIds = prevBranchIds;
    }

    public String getBranchId ()
    {
        return branchId;
    }

    public void setBranchId (String branchId)
    {
        this.branchId = branchId;
    }

    public String getDirection ()
    {
        return direction;
    }

    public void setDirection (String direction)
    {
        this.direction = direction;
    }

    public String getLineName ()
    {
        return lineName;
    }
    public int getLength(){
        return id.length;
    }


    public void setLineName (String lineName)
    {
        this.lineName = lineName;
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
    public int getCoordSize ()
    {
        return idCoord.size();
    }

    public StopPoint[] getStopPoint ()
    {
        return stopPoint;
    }

    public void setStopPoint (StopPoint[] stopPoint)
    {
        this.stopPoint = stopPoint;
    }

    public String getLineId ()
    {
        return lineId;
    }
    public void setLineId (String lineId)
    {
        this.lineId = lineId;
    }

    public String getId (int num)
    {
        return this.id[num];
    }

    public void setId (String []id)
    {
        this.id  = id;
    }
    public String getIdName (int num)
    {
        return this.idName[num];
    }

    public void setIdName (String[] idName)
    {
        this.idName = idName;
    }
    public LatLng getIdCoord (int num)
    {
        return this.idCoord.get(num);
    }
    public List<LatLng> getIdCoord ()
    {
        return this.idCoord;
    }

    public void setIdCoord (List<LatLng> idCoord)
    {
        this.idCoord = idCoord;
    }

    @Override
    public String toString()
    {
        return Arrays.toString(getStopPoint()) +" " ;
    }
}