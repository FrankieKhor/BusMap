package com.example.frank.busmap.Pojo.getTicketPrice;

/**
 * Created by frank on 08/03/2018.
 */

public class Message {
    private String messageText;

    private String bulletOrder;

    private String $type;

    public String getMessageText ()
    {
        return messageText;
    }

    public void setMessageText (String messageText)
    {
        this.messageText = messageText;
    }

    public String getBulletOrder ()
    {
        return bulletOrder;
    }

    public void setBulletOrder (String bulletOrder)
    {
        this.bulletOrder = bulletOrder;
    }

    public String get$type ()
    {
        return $type;
    }

    public void set$type (String $type)
    {
        this.$type = $type;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [messageText = "+messageText+", bulletOrder = "+bulletOrder+", $type = "+$type+"]";
    }
}
