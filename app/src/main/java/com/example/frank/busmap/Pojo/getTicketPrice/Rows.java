package com.example.frank.busmap.Pojo.getTicketPrice;

/**
 * Created by frank on 08/03/2018.
 */

public class Rows {
    private String to;

    private String startDate;

    private String routeDescription;

    private TicketsAvailable[] ticketsAvailable;

    private String passengerType;

    private String endDate;

    private String from;

    private String specialFare;

    private String[] messages;

    private String fromStation;

    private String throughFare;

    private String validatorInformation;

    private String isTour;

    private String toStation;

    private String displayOrder;

    private String $type;

    private String displayName;

    public String getTo ()
    {
        return to;
    }

    public void setTo (String to)
    {
        this.to = to;
    }

    public String getStartDate ()
    {
        return startDate;
    }

    public void setStartDate (String startDate)
    {
        this.startDate = startDate;
    }

    public String getRouteDescription ()
    {
        return routeDescription;
    }

    public void setRouteDescription (String routeDescription)
    {
        this.routeDescription = routeDescription;
    }

    public TicketsAvailable[] getTicketsAvailable ()
    {
        return ticketsAvailable;
    }

    public void setTicketsAvailable (TicketsAvailable[] ticketsAvailable)
    {
        this.ticketsAvailable = ticketsAvailable;
    }

    public String getPassengerType ()
    {
        return passengerType;
    }

    public void setPassengerType (String passengerType)
    {
        this.passengerType = passengerType;
    }

    public String getEndDate ()
    {
        return endDate;
    }

    public void setEndDate (String endDate)
    {
        this.endDate = endDate;
    }

    public String getFrom ()
    {
        return from;
    }

    public void setFrom (String from)
    {
        this.from = from;
    }

    public String getSpecialFare ()
    {
        return specialFare;
    }

    public void setSpecialFare (String specialFare)
    {
        this.specialFare = specialFare;
    }

    public String[] getMessages ()
    {
        return messages;
    }

    public void setMessages (String[] messages)
    {
        this.messages = messages;
    }

    public String getFromStation ()
    {
        return fromStation;
    }

    public void setFromStation (String fromStation)
    {
        this.fromStation = fromStation;
    }

    public String getThroughFare ()
    {
        return throughFare;
    }

    public void setThroughFare (String throughFare)
    {
        this.throughFare = throughFare;
    }

    public String getValidatorInformation ()
    {
        return validatorInformation;
    }

    public void setValidatorInformation (String validatorInformation)
    {
        this.validatorInformation = validatorInformation;
    }

    public String getIsTour ()
    {
        return isTour;
    }

    public void setIsTour (String isTour)
    {
        this.isTour = isTour;
    }

    public String getToStation ()
    {
        return toStation;
    }

    public void setToStation (String toStation)
    {
        this.toStation = toStation;
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

    public String getDisplayName ()
    {
        return displayName;
    }

    public void setDisplayName (String displayName)
    {
        this.displayName = displayName;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [to = "+to+", startDate = "+startDate+", routeDescription = "+routeDescription+", ticketsAvailable = "+ticketsAvailable+", passengerType = "+passengerType+", endDate = "+endDate+", from = "+from+", specialFare = "+specialFare+", messages = "+messages+", fromStation = "+fromStation+", throughFare = "+throughFare+", validatorInformation = "+validatorInformation+", isTour = "+isTour+", toStation = "+toStation+", displayOrder = "+displayOrder+", $type = "+$type+", displayName = "+displayName+"]";
    }
}
