package com.example.frank.busmap.GenticAlgorithm;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

//Represent each
public class Stops
{
    private double time, cost;
    private int vehicleChange;
    private String paths;
    private ArrayList<LatLng> arrayList ;
    String [] mode;

    public Stops(String path, double time, double cost, int change, ArrayList<LatLng> LatList) {
        this.paths = path;
        this.time = time;
        this.cost = cost;
        this.vehicleChange = change;
        this.arrayList = new ArrayList<>(LatList);
    }

    public Stops(double time, double cost, int change, ArrayList<LatLng> LatList ){
        this.time = time;
        this.cost = cost;
        this.vehicleChange = change;
        this.arrayList = new ArrayList<>(LatList);
    }

    public double getTime()
    {
        return this.time;
    }

    public ArrayList<LatLng> getLatLng()
    {
        return this.arrayList;
    }

    public int getLatLngSize()
    {
        return this.arrayList.size();
    }

    public String getPaths()
    {
        return this.paths;
    }

    public double getCost()
    {
        return this.cost;
    }

    public int getVehicleChange()
    {
        return this.vehicleChange;
    }

    @Override
    public String toString()
    {
        return " paths "+ this.paths +" time " + this.time + " cost " + this.cost + " vehicle change " + this.vehicleChange
                + " arraySize " + this.arrayList.size() ;
    }
}
