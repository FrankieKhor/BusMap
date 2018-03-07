package com.example.frank.busmap.Pojo.getAllBusStops;
import com.example.frank.busmap.Pojo.getAllBusStops.OrderedLineRoutes;
import com.example.frank.busmap.Pojo.getAllBusStops.Stations;
import com.example.frank.busmap.Pojo.getAllBusStops.StopPointSequences;

public class BusStopResponse
{
    private String[] lineStrings;

    private Stations[] stations;

    private OrderedLineRoutes[] orderedLineRoutes;

    private String direction;

    private String lineName;

    private StopPointSequences[] stopPointSequences;

    private String $type;

    private String isOutboundOnly;

    private String lineId;

    private String mode;

    public String[] getLineStrings ()
    {
        return lineStrings;
    }

    public void setLineStrings (String[] lineStrings)
    {
        this.lineStrings = lineStrings;
    }

    public Stations[] getStations ()
    {
        return stations;
    }

    public void setStations (Stations[] stations)
    {
        this.stations = stations;
    }

    public OrderedLineRoutes[] getOrderedLineRoutes ()
    {
        return orderedLineRoutes;
    }
    public String getOrderedLineRoutes (int num)
    {
        return orderedLineRoutes[num].getNaptanIds(num);
    }

    public void setOrderedLineRoutes (OrderedLineRoutes[] orderedLineRoutes)
    {
        this.orderedLineRoutes = orderedLineRoutes;
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

    public void setLineName (String lineName)
    {
        this.lineName = lineName;
    }

    public StopPointSequences[] getStopPointSequences ()
    {
        return stopPointSequences;
    }
    public String getStopPointSequences (int num)
    {
        return stopPointSequences[num].getIdName(num);
    }

    public void setStopPointSequences (StopPointSequences[] stopPointSequences)
    {
        this.stopPointSequences = stopPointSequences;
    }

    public String get$type ()
    {
        return $type;
    }

    public void set$type (String $type)
    {
        this.$type = $type;
    }

    public String getIsOutboundOnly ()
    {
        return isOutboundOnly;
    }

    public void setIsOutboundOnly (String isOutboundOnly)
    {
        this.isOutboundOnly = isOutboundOnly;
    }

    public String getLineId ()
    {
        return lineId;
    }

    public void setLineId (String lineId)
    {
        this.lineId = lineId;
    }

    public String getMode ()
    {
        return mode;
    }

    public void setMode (String mode)
    {
        this.mode = mode;
    }
    public int getOrderedLineLength ()
    {
        return orderedLineRoutes.length;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [lineStrings = "+lineStrings+", stations = "+stations+", orderedLineRoutes = "+orderedLineRoutes+", direction = "+direction+", lineName = "+lineName+", stopPointSequences = "+stopPointSequences+", $type = "+$type+", isOutboundOnly = "+isOutboundOnly+", lineId = "+lineId+", mode = "+mode+"]";
    }
}

