package com.example.frank.busmap.Pojo.getBusArrival;

/**
 * Created by frank on 14/02/2018.
 */

public class BusArrivalResponse
{
    private String vehicleId;

    private String direction;

    private String expectedArrival;

    private String modeName;

    private String towards;

    private String destinationName;

    private String timeToLive;

    private String currentLocation;

    private String timestamp;

    private String id;

    private String timeToStation;

    private String platformName;

    private String operationType;

    private String destinationNaptanId;

    private String lineName;

    private String bearing;

    private String $type;

    private Timing timing;

    private String stationName;

    private String naptanId;

    private String lineId;

    public String getVehicleId ()
    {
        return vehicleId;
    }

    public void setVehicleId (String vehicleId)
    {
        this.vehicleId = vehicleId;
    }

    public String getDirection ()
    {
        return direction;
    }

    public void setDirection (String direction)
    {
        this.direction = direction;
    }

    public String getExpectedArrival ()
    {
        return expectedArrival;
    }

    public void setExpectedArrival (String expectedArrival)
    {
        this.expectedArrival = expectedArrival;
    }

    public String getModeName ()
    {
        return modeName;
    }

    public void setModeName (String modeName)
    {
        this.modeName = modeName;
    }

    public String getTowards ()
    {
        return towards;
    }

    public void setTowards (String towards)
    {
        this.towards = towards;
    }

    public String getDestinationName ()
    {
        return destinationName;
    }

    public void setDestinationName (String destinationName)
    {
        this.destinationName = destinationName;
    }

    public String getTimeToLive ()
    {
        return timeToLive;
    }

    public void setTimeToLive (String timeToLive)
    {
        this.timeToLive = timeToLive;
    }

    public String getCurrentLocation ()
    {
        return currentLocation;
    }

    public void setCurrentLocation (String currentLocation)
    {
        this.currentLocation = currentLocation;
    }

    public String getTimestamp ()
    {
        return timestamp;
    }

    public void setTimestamp (String timestamp)
    {
        this.timestamp = timestamp;
    }

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getTimeToStation ()
    {
        return timeToStation;
    }

    public void setTimeToStation (String timeToStation)
    {
        this.timeToStation = timeToStation;
    }

    public String getPlatformName ()
    {
        return platformName;
    }

    public void setPlatformName (String platformName)
    {
        this.platformName = platformName;
    }

    public String getOperationType ()
    {
        return operationType;
    }

    public void setOperationType (String operationType)
    {
        this.operationType = operationType;
    }

    public String getDestinationNaptanId ()
    {
        return destinationNaptanId;
    }

    public void setDestinationNaptanId (String destinationNaptanId)
    {
        this.destinationNaptanId = destinationNaptanId;
    }

    public String getLineName ()
    {
        return lineName;
    }

    public void setLineName (String lineName)
    {
        this.lineName = lineName;
    }

    public String getBearing ()
    {
        return bearing;
    }

    public void setBearing (String bearing)
    {
        this.bearing = bearing;
    }

    public String get$type ()
    {
        return $type;
    }

    public void set$type (String $type)
    {
        this.$type = $type;
    }

    public Timing getTiming ()
    {
        return timing;
    }

    public void setTiming (Timing timing)
    {
        this.timing = timing;
    }

    public String getStationName ()
    {
        return stationName;
    }

    public void setStationName (String stationName)
    {
        this.stationName = stationName;
    }

    public String getNaptanId ()
    {
        return naptanId;
    }

    public void setNaptanId (String naptanId)
    {
        this.naptanId = naptanId;
    }

    public String getLineId ()
    {
        return lineId;
    }

    public void setLineId (String lineId)
    {
        this.lineId = lineId;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [vehicleId = "+vehicleId+", direction = "+direction+", expectedArrival = "+expectedArrival+", modeName = "+modeName+", towards = "+towards+", destinationName = "+destinationName+", timeToLive = "+timeToLive+", currentLocation = "+currentLocation+", timestamp = "+timestamp+", id = "+id+", timeToStation = "+timeToStation+", platformName = "+platformName+", operationType = "+operationType+", destinationNaptanId = "+destinationNaptanId+", lineName = "+lineName+", bearing = "+bearing+", $type = "+$type+", timing = "+timing+", stationName = "+stationName+", naptanId = "+naptanId+", lineId = "+lineId+"]";
    }
}

