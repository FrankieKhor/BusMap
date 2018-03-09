package com.example.frank.busmap.Pojo.getTicketPrice;


import com.google.android.gms.nearby.messages.Messages;

/**
 * Created by frank on 08/03/2018.
 */

public class TicketPrice {
    private String index;

    private String $type;

    private Message[] message;

    private String header;

    private Rows[] rows;

    public String getIndex ()
    {
        return index;
    }

    public void setIndex (String index)
    {
        this.index = index;
    }

    public String get$type ()
    {
        return $type;
    }

    public void set$type (String $type)
    {
        this.$type = $type;
    }

    public Message[] getMessages ()
    {
        return message;
    }

    public void setMessages (Messages[] messages)
    {
        this.message = message;
    }

    public String getHeader ()
    {
        return header;
    }

    public void setHeader (String header)
    {
        this.header = header;
    }

    public Rows[] getRows ()
    {
        return rows;
    }

    public void setRows (Rows[] rows)
    {
        this.rows = rows;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [index = "+index+", $type = "+$type+", messages = "+message+", header = "+header+", rows = "+rows+"]";
    }
}
