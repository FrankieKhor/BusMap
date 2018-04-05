package com.example.frank.busmap.Pojo.getJourneyFromTo;

import org.parceler.Parcel;

/**
 * Created by frank on 01/03/2018.
 */
@Parcel
public class TimeAdjustments {
     Earlier earlier;

     Earliest earliest;

     Later later;

     String $type;

    public TimeAdjustments() {
    }

    Latest latest;

    public Earlier getEarlier ()
    {
        return earlier;
    }

    public void setEarlier (Earlier earlier)
    {
        this.earlier = earlier;
    }

    public Earliest getEarliest ()
    {
        return earliest;
    }

    public void setEarliest (Earliest earliest)
    {
        this.earliest = earliest;
    }

    public Later getLater ()
    {
        return later;
    }

    public void setLater (Later later)
    {
        this.later = later;
    }

    public String get$type ()
    {
        return $type;
    }

    public void set$type (String $type)
    {
        this.$type = $type;
    }

    public Latest getLatest ()
    {
        return latest;
    }

    public void setLatest (Latest latest)
    {
        this.latest = latest;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [earlier = "+earlier+", earliest = "+earliest+", later = "+later+", $type = "+$type+", latest = "+latest+"]";
    }
}

