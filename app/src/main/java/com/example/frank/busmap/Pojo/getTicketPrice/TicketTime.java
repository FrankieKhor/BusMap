package com.example.frank.busmap.Pojo.getTicketPrice;

/**
 * Created by frank on 07/03/2018.
 */

public class TicketTime {
    private String description;

    private String $type;

    private String type;

    public String getDescription ()
    {
        return description;
    }

    public void setDescription (String description)
    {
        this.description = description;
    }

    public String get$type ()
    {
        return $type;
    }

    public void set$type (String $type)
    {
        this.$type = $type;
    }

    public String getType ()
    {
        return type;
    }

    public void setType (String type)
    {
        this.type = type;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [description = "+description+", $type = "+$type+", type = "+type+"]";
    }
}
