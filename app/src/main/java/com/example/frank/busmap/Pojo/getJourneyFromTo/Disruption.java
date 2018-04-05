package com.example.frank.busmap.Pojo.getJourneyFromTo;

import org.parceler.Parcel;

/**
 * Created by frank on 29/03/2018.
 */
@Parcel
public class Disruption {
    String category;
    String description;
    String categoryDescription;
    String closureText;
    String $type;
    String[] affectedStops;
    String[] affectedRoutes;

    public Disruption() {
    }

    public String[] getAffectedRoutes ()
    {
        return affectedRoutes;
    }

    public void setAffectedRoutes (String[] affectedRoutes)
    {
        this.affectedRoutes = affectedRoutes;
    }

    public String getCategory ()
    {
        return category;
    }

    public void setCategory (String category)
    {
        this.category = category;
    }

    public String getDescription ()
    {
        return description;
    }

    public void setDescription (String description)
    {
        this.description = description;
    }

    public String getCategoryDescription ()
    {
        return categoryDescription;
    }

    public void setCategoryDescription (String categoryDescription)
    {
        this.categoryDescription = categoryDescription;
    }

    public String getClosureText ()
    {
        return closureText;
    }

    public void setClosureText (String closureText)
    {
        this.closureText = closureText;
    }

    public String get$type ()
    {
        return $type;
    }

    public void set$type (String $type)
    {
        this.$type = $type;
    }

    public String[] getAffectedStops ()
    {
        return affectedStops;
    }

    public void setAffectedStops (String[] affectedStops)
    {
        this.affectedStops = affectedStops;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [affectedRoutes = "+affectedRoutes+", category = "+category+", description = "+description+", categoryDescription = "+categoryDescription+", closureText = "+closureText+", $type = "+$type+", affectedStops = "+affectedStops+"]";
    }
}
