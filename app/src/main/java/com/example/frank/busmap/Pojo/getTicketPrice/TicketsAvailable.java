package com.example.frank.busmap.Pojo.getTicketPrice;

/**
 * Created by frank on 08/03/2018.
 */

public class TicketsAvailable {
    private String description;

    private TicketType ticketType;

    private String passengerType;

    private String displayOrder;

    private String $type;

    private TicketTime ticketTime;

    private String cost;

    private String[] messages;

    private String mode;

    public String getDescription ()
    {
        return description;
    }

    public void setDescription (String description)
    {
        this.description = description;
    }

    public TicketType getTicketType ()
    {
        return ticketType;
    }

    public void setTicketType (TicketType ticketType)
    {
        this.ticketType = ticketType;
    }

    public String getPassengerType ()
    {
        return passengerType;
    }

    public void setPassengerType (String passengerType)
    {
        this.passengerType = passengerType;
    }

    public String getDisplayOrder ()
    {
        return displayOrder;
    }

    public void setDisplayOrder (String displayOrder)
    {
        this.displayOrder = displayOrder;
    }

    public String get$type ()
    {
        return $type;
    }

    public void set$type (String $type)
    {
        this.$type = $type;
    }

    public TicketTime getTicketTime ()
    {
        return ticketTime;
    }

    public void setTicketTime (TicketTime ticketTime)
    {
        this.ticketTime = ticketTime;
    }

    public String getCost ()
    {
        return cost;
    }

    public void setCost (String cost)
    {
        this.cost = cost;
    }

    public String[] getMessages ()
    {
        return messages;
    }

    public void setMessages (String[] messages)
    {
        this.messages = messages;
    }

    public String getMode ()
    {
        return mode;
    }

    public void setMode (String mode)
    {
        this.mode = mode;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [description = "+description+", ticketType = "+ticketType+", passengerType = "+passengerType+", displayOrder = "+displayOrder+", $type = "+$type+", ticketTime = "+ticketTime+", cost = "+cost+", messages = "+messages+", mode = "+mode+"]";
    }
}
