package com.example.frank.busmap.GenticAlgorithm;

/**
 * Created by frank on 18/03/2018.
 */

public class Section {
    int section;
    Stops stops;
    int index;
    String path;
    public Section(){
        this.index = index;
        // this.path = path;
    }

    public void getStops(){

    }

    public void getPath(){

    }
    public void addPath(String path){
        this.path = path;
    }
    public int getIndex(){
        return this.index;
    }
    public void addIndex(int index){
        this.index = index;
    }
}

