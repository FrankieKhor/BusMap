package com.example.frank.busmap.Pojo.getJourneyFromTo;

/**
 * Created by frank on 01/03/2018.
 */

public class Later {
    private String time;

    private String timeIs;

    private String $type;

    private String date;

    private String uri;

    public String getTime ()
    {
        return time;
    }

    public void setTime (String time)
    {
        this.time = time;
    }

    public String getTimeIs ()
    {
        return timeIs;
    }

    public void setTimeIs (String timeIs)
    {
        this.timeIs = timeIs;
    }

    public String get$type ()
    {
        return $type;
    }

    public void set$type (String $type)
    {
        this.$type = $type;
    }

    public String getDate ()
    {
        return date;
    }

    public void setDate (String date)
    {
        this.date = date;
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
        return "ClassPojo [time = "+time+", timeIs = "+timeIs+", $type = "+$type+", date = "+date+", uri = "+uri+"]";
    }

}
