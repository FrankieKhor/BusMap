package com.example.frank.busmap.Pojo.getJourneyFromTo;

/**
 * Created by frank on 01/03/2018.
 */

public class Instruction {
    private String summary;

    private String detailed;

    private String $type;

    private Steps[] steps;

    public String getSummary ()
    {
        return summary;
    }

    public void setSummary (String summary)
    {
        this.summary = summary;
    }

    public String getDetailed ()
    {
        return detailed;
    }

    public void setDetailed (String detailed)
    {
        this.detailed = detailed;
    }

    public String get$type ()
    {
        return $type;
    }

    public void set$type (String $type)
    {
        this.$type = $type;
    }

    public Steps[] getSteps ()
    {
        return steps;
    }

    public void setSteps (Steps[] steps)
    {
        this.steps = steps;
    }

    @Override
    public String toString()
    {
        return summary ;
    }

}
