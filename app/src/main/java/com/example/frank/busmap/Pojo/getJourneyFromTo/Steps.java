package com.example.frank.busmap.Pojo.getJourneyFromTo;

/**
 * Created by frank on 01/03/2018.
 */

public class Steps {
    private String cumulativeTravelTime;

    private String streetName;

    private String turnDirection;

    private String skyDirectionDescription;

    private String skyDirection;

    private String distance;

    private String description;

    private String descriptionHeading;

    private PathAttribute pathAttribute;

    private String trackType;

    private String $type;

    private String longitude;

    private String latitude;

    private String cumulativeDistance;

    public String getCumulativeTravelTime ()
    {
        return cumulativeTravelTime;
    }

    public void setCumulativeTravelTime (String cumulativeTravelTime)
    {
        this.cumulativeTravelTime = cumulativeTravelTime;
    }

    public String getStreetName ()
    {
        return streetName;
    }

    public void setStreetName (String streetName)
    {
        this.streetName = streetName;
    }

    public String getTurnDirection ()
    {
        return turnDirection;
    }

    public void setTurnDirection (String turnDirection)
    {
        this.turnDirection = turnDirection;
    }

    public String getSkyDirectionDescription ()
    {
        return skyDirectionDescription;
    }

    public void setSkyDirectionDescription (String skyDirectionDescription)
    {
        this.skyDirectionDescription = skyDirectionDescription;
    }

    public String getSkyDirection ()
    {
        return skyDirection;
    }

    public void setSkyDirection (String skyDirection)
    {
        this.skyDirection = skyDirection;
    }

    public String getDistance ()
    {
        return distance;
    }

    public void setDistance (String distance)
    {
        this.distance = distance;
    }

    public String getDescription ()
    {
        return description;
    }

    public void setDescription (String description)
    {
        this.description = description;
    }

    public String getDescriptionHeading ()
    {
        return descriptionHeading;
    }

    public void setDescriptionHeading (String descriptionHeading)
    {
        this.descriptionHeading = descriptionHeading;
    }

    public PathAttribute getPathAttribute ()
    {
        return pathAttribute;
    }

    public void setPathAttribute (PathAttribute pathAttribute)
    {
        this.pathAttribute = pathAttribute;
    }

    public String getTrackType ()
    {
        return trackType;
    }

    public void setTrackType (String trackType)
    {
        this.trackType = trackType;
    }

    public String get$type ()
    {
        return $type;
    }

    public void set$type (String $type)
    {
        this.$type = $type;
    }

    public String getLongitude ()
    {
        return longitude;
    }

    public void setLongitude (String longitude)
    {
        this.longitude = longitude;
    }

    public String getLatitude ()
    {
        return latitude;
    }
    public String getLatLng ()
    {
        return latitude +"," + longitude;
    }

    public void setLatitude (String latitude)
    {
        this.latitude = latitude;
    }

    public String getCumulativeDistance ()
    {
        return cumulativeDistance;
    }

    public void setCumulativeDistance (String cumulativeDistance)
    {
        this.cumulativeDistance = cumulativeDistance;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [cumulativeTravelTime = "+cumulativeTravelTime+", streetName = "+streetName+", turnDirection = "+turnDirection+", skyDirectionDescription = "+skyDirectionDescription+", skyDirection = "+skyDirection+", distance = "+distance+", description = "+description+", descriptionHeading = "+descriptionHeading+", pathAttribute = "+pathAttribute+", trackType = "+trackType+", $type = "+$type+", longitude = "+longitude+", latitude = "+latitude+", cumulativeDistance = "+cumulativeDistance+"]";
    }
}
