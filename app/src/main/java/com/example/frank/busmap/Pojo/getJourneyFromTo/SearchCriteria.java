package com.example.frank.busmap.Pojo.getJourneyFromTo;

import org.parceler.Parcel;

/**
 * Created by frank on 01/03/2018.
 */
@Parcel
public class SearchCriteria {
     String dateTimeType;

     String dateTime;

     String $type;

     TimeAdjustments timeAdjustments;

    public SearchCriteria() {
    }

    public String getDateTimeType ()
    {
        return dateTimeType;
    }

    public void setDateTimeType (String dateTimeType)
    {
        this.dateTimeType = dateTimeType;
    }

    public String getDateTime ()
    {
        return dateTime;
    }

    public void setDateTime (String dateTime)
    {
        this.dateTime = dateTime;
    }

    public String get$type ()
    {
        return $type;
    }

    public void set$type (String $type)
    {
        this.$type = $type;
    }

    public TimeAdjustments getTimeAdjustments ()
    {
        return timeAdjustments;
    }

    public void setTimeAdjustments (TimeAdjustments timeAdjustments)
    {
        this.timeAdjustments = timeAdjustments;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [dateTimeType = "+dateTimeType+", dateTime = "+dateTime+", $type = "+$type+", timeAdjustments = "+timeAdjustments+"]";
    }

}
