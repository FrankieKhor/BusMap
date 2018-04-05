package com.example.frank.busmap.Pojo.getJourneyFromTo;

import org.parceler.Parcel;

/**
 * Created by frank on 29/03/2018.
 */
@Parcel
public class ValidityPeriods {
    String $type;
    String isNow;
    String toDate;
    String fromDate;

    public ValidityPeriods() {
    }
    public String getFromDate ()
    {
        return fromDate;
    }

    public void setFromDate (String fromDate)
    {
        this.fromDate = fromDate;
    }

    public String get$type ()
    {
        return $type;
    }

    public void set$type (String $type)
    {
        this.$type = $type;
    }

    public String getIsNow ()
    {
        return isNow;
    }

    public void setIsNow (String isNow)
    {
        this.isNow = isNow;
    }

    public String getToDate ()
    {
        return toDate;
    }

    public void setToDate (String toDate)
    {
        this.toDate = toDate;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [fromDate = "+fromDate+", $type = "+$type+", isNow = "+isNow+", toDate = "+toDate+"]";
    }
}
