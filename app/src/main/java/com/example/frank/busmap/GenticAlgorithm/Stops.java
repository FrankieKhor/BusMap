package com.example.frank.busmap.GenticAlgorithm;

/**
 * Created by frank on 18/03/2018.
 */

public class Stops {
    private double time, cost;
    private int vehicleChange;
    String path;
    public Stops(String path, double time, double cost, int change){
        this.path = path;
        this.time = time;
        this.cost = cost;
        this.vehicleChange = change;
    }

    public double getTime(){
        return this.time;
    }
    public String getPath(){
        return this.path;
    }


    public double getCost(){
        return this.cost;
    }

    public int getVehicleChange(){
        return this.vehicleChange;
    }

    public String toString(){
        return " time " + this.time + " cost " + this.cost + " vehicle change " + this.vehicleChange;
    }
}
