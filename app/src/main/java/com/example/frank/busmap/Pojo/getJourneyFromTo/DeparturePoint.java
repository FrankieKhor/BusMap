package com.example.frank.busmap.Pojo.getJourneyFromTo;

import org.parceler.Parcel;

/**
 * Created by frank on 01/03/2018.
 */
@Parcel
public class DeparturePoint {
    String commonName;
    String lon;
    String platformName;
    String[] additionalProperties;
    String icsCode;
    String $type;
    String placeType;
    String lat;
    String stopLetter;
    String naptanId;


    public String getNaptanId() {
        return naptanId;
    }

    public void setNaptanId(String naptanId) {
        this.naptanId = naptanId;
    }



    public String getStopLetter() {
        return stopLetter;
    }

    public void setStopLetter(String stopLetter) {
        this.stopLetter = stopLetter;
    }

    public DeparturePoint() {
    }

    public String getCommonName ()
    {
        return commonName;
    }

    public void setCommonName (String commonName)
    {
        this.commonName = commonName;
    }

    public String getLon ()
    {
        return lon;
    }

    public void setLon (String lon)
    {
        this.lon = lon;
    }

    public String getPlatformName ()
    {
        return platformName;
    }

    public void setPlatformName (String platformName)
    {
        this.platformName = platformName;
    }

    public String[] getAdditionalProperties ()
    {
        return additionalProperties;
    }

    public void setAdditionalProperties (String[] additionalProperties)
    {
        this.additionalProperties = additionalProperties;
    }

    public String getIcsCode ()
    {
        return icsCode;
    }

    public void setIcsCode (String icsCode)
    {
        this.icsCode = icsCode;
    }

    public String get$type ()
    {
        return $type;
    }

    public void set$type (String $type)
    {
        this.$type = $type;
    }

    public String getPlaceType ()
    {
        return placeType;
    }

    public void setPlaceType (String placeType)
    {
        this.placeType = placeType;
    }

    public String getLat ()
    {
        return lat;
    }

    public void setLat (String lat)
    {
        this.lat = lat;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [commonName = "+commonName+", lon = "+lon+", platformName = "+platformName+", additionalProperties = "+additionalProperties+", icsCode = "+icsCode+", $type = "+$type+", placeType = "+placeType+", lat = "+lat+"]";
    }
}
