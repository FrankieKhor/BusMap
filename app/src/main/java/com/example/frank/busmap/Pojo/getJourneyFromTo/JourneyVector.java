package com.example.frank.busmap.Pojo.getJourneyFromTo;

/**
 * Created by frank on 01/03/2018.
 */

public class JourneyVector {

    private String to;

    private String via;

    private String $type;

    private String from;

    private String uri;

    public String getTo ()
    {
        return to;
    }

    public void setTo (String to)
    {
        this.to = to;
    }

    public String getVia ()
    {
        return via;
    }

    public void setVia (String via)
    {
        this.via = via;
    }

    public String get$type ()
    {
        return $type;
    }

    public void set$type (String $type)
    {
        this.$type = $type;
    }

    public String getFrom ()
    {
        return from;
    }

    public void setFrom (String from)
    {
        this.from = from;
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
        return "ClassPojo [to = "+to+", via = "+via+", $type = "+$type+", from = "+from+", uri = "+uri+"]";
    }
}


