package com.example.frank.busmap.Pojo.getBusArrival;

/**
 * Created by frank on 14/02/2018.
 */

public class Timing
{
    private String countdownServerAdjustment;

    private String sent;

    private String source;

    private String read;

    private String received;

    private String $type;

    private String insert;

    public String getCountdownServerAdjustment ()
    {
        return countdownServerAdjustment;
    }

    public void setCountdownServerAdjustment (String countdownServerAdjustment)
    {
        this.countdownServerAdjustment = countdownServerAdjustment;
    }

    public String getSent ()
    {
        return sent;
    }

    public void setSent (String sent)
    {
        this.sent = sent;
    }

    public String getSource ()
    {
        return source;
    }

    public void setSource (String source)
    {
        this.source = source;
    }

    public String getRead ()
    {
        return read;
    }

    public void setRead (String read)
    {
        this.read = read;
    }

    public String getReceived ()
    {
        return received;
    }

    public void setReceived (String received)
    {
        this.received = received;
    }

    public String get$type ()
    {
        return $type;
    }

    public void set$type (String $type)
    {
        this.$type = $type;
    }

    public String getInsert ()
    {
        return insert;
    }

    public void setInsert (String insert)
    {
        this.insert = insert;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [countdownServerAdjustment = "+countdownServerAdjustment+", sent = "+sent+", source = "+source+", read = "+read+", received = "+received+", $type = "+$type+", insert = "+insert+"]";
    }
}