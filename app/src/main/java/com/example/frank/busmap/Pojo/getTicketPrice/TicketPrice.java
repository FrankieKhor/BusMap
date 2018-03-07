package com.example.frank.busmap.Pojo.getTicketPrice;

import com.google.android.gms.nearby.messages.Messages;

/**
 * Created by frank on 07/03/2018.
 */

public class TicketPrice {
    private String index;

    private String $type;

    private Messages[] messages;

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

    public Messages[] getMessages ()
    {
        return messages;
    }

    public void setMessages (Messages[] messages)
    {
        this.messages = messages;
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
        return "ClassPojo [index = "+index+", $type = "+$type+", messages = "+messages+", header = "+header+", rows = "+rows+"]";
    }
}
