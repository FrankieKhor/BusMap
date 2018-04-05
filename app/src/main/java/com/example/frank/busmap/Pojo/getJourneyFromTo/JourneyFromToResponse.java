package com.example.frank.busmap.Pojo.getJourneyFromTo;

import org.parceler.Parcel;

/**
 * Created by frank on 01/03/2018.
 */
@Parcel
public class JourneyFromToResponse {

    Journeys[] journeys;
    SearchCriteria searchCriteria;
    String recommendedMaxAgeMinutes;
    String[] stopMessages;
    Lines[] lines;
    String $type;
    JourneyVector journeyVector;

    public JourneyFromToResponse() {
    }

    public Journeys[] getJourneys ()
    {
        return journeys;
    }

    public void setJourneys (Journeys[] journeys)
    {
        this.journeys = journeys;
    }

    public SearchCriteria getSearchCriteria ()
    {
        return searchCriteria;
    }

    public void setSearchCriteria (SearchCriteria searchCriteria)
    {
        this.searchCriteria = searchCriteria;
    }

    public String getRecommendedMaxAgeMinutes ()
    {
        return recommendedMaxAgeMinutes;
    }

    public void setRecommendedMaxAgeMinutes (String recommendedMaxAgeMinutes)
    {
        this.recommendedMaxAgeMinutes = recommendedMaxAgeMinutes;
    }

    public String[] getStopMessages ()
    {
        return stopMessages;
    }

    public void setStopMessages (String[] stopMessages)
    {
        this.stopMessages = stopMessages;
    }

    public Lines[] getLines ()
    {
        return lines;
    }

    public void setLines (Lines[] lines)
    {
        this.lines = lines;
    }

    public String get$type ()
    {
        return $type;
    }

    public void set$type (String $type)
    {
        this.$type = $type;
    }

    public JourneyVector getJourneyVector ()
    {
        return journeyVector;
    }

    public void setJourneyVector (JourneyVector journeyVector)
    {
        this.journeyVector = journeyVector;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [journeys = "+journeys+", searchCriteria = "+searchCriteria+", recommendedMaxAgeMinutes = "+recommendedMaxAgeMinutes+", stopMessages = "+stopMessages+", lines = "+lines+", $type = "+$type+", journeyVector = "+journeyVector+"]";
    }
}
