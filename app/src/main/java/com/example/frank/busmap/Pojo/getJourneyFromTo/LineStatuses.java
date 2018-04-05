package com.example.frank.busmap.Pojo.getJourneyFromTo;
import org.parceler.Parcel;

/**
 * Created by frank on 29/03/2018.
 */
@Parcel
public class LineStatuses {
    String created;
    String reason;
    Disruption disruption;
    ValidityPeriods[] validityPeriods;
    String $type;
    String statusSeverity;
    String lineId;
    String statusSeverityDescription;
    String id;

    public LineStatuses() {
    }

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getCreated ()
    {
        return created;
    }

    public void setCreated (String created)
    {
        this.created = created;
    }

    public String getReason ()
    {
        return reason;
    }

    public void setReason (String reason)
    {
        this.reason = reason;
    }

    public Disruption getDisruption ()
    {
        return disruption;
    }

    public void setDisruption (Disruption disruption)
    {
        this.disruption = disruption;
    }

    public ValidityPeriods[] getValidityPeriods ()
    {
        return validityPeriods;
    }

    public void setValidityPeriods (ValidityPeriods[] validityPeriods)
    {
        this.validityPeriods = validityPeriods;
    }

    public String get$type ()
    {
        return $type;
    }

    public void set$type (String $type)
    {
        this.$type = $type;
    }

    public String getStatusSeverity ()
    {
        return statusSeverity;
    }

    public void setStatusSeverity (String statusSeverity)
    {
        this.statusSeverity = statusSeverity;
    }

    public String getLineId ()
    {
        return lineId;
    }

    public void setLineId (String lineId)
    {
        this.lineId = lineId;
    }

    public String getStatusSeverityDescription ()
    {
        return statusSeverityDescription;
    }

    public void setStatusSeverityDescription (String statusSeverityDescription)
    {
        this.statusSeverityDescription = statusSeverityDescription;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [id = "+id+", created = "+created+", reason = "+reason+", disruption = "+disruption+", validityPeriods = "+validityPeriods+", $type = "+$type+", statusSeverity = "+statusSeverity+", lineId = "+lineId+", statusSeverityDescription = "+statusSeverityDescription+"]";
    }

}
