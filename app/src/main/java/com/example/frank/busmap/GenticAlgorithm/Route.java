package com.example.frank.busmap.GenticAlgorithm;

import android.util.Log;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * Created by frank on 18/03/2018.
 */

public class Route {
    public static String TAG = "Route";
    int [] genes = new int[6];
    double fitness;
    static double fitesstOverall = 0;
    Random r = new Random();
    HashMap<Integer, List<Stops>> hashMap = new HashMap<>();
    static int index;

    public Route(){

        for(int i =0 ;i<genes.length;i++){
            int randomInt = r.nextInt(2);
            genes[i] =  randomInt;

        }
        // String string = String.valueOf(genes);
        Log.d(TAG, "genes " + Arrays.toString(genes));
    }
    //Weighted sum
    public double fitness(){
        this.index = index;
//
        int choice =1;
        double [] Weight = new double[3];

//        for (int i = 0; i < genes.length; i++) {
//            // Is the character correct?
//           // if (genes[i] == 1) {
//                // If so, increment the score.
//                score+= genes[i];
//            //}
//        }

        switch (choice){
            case 1:
                Weight[0] = 0.5;
                Weight[1] = 0.5;
                Weight[2] = 0.5;
                break;
            case 2:
                Weight[0] = 1;
                Weight[1] = 0.1;
                Weight[2] = 0.1;
                break;
            case 3:
                Weight[0] = 0.1;
                Weight[1] = 1;
                Weight[2] = 0.1;
                break;
            case 4:
                Weight[0] = 0.1;
                Weight[1] = 0.1;
                Weight[2] = 1;
                break;
        }

        double time = 0;
        double cost = 0;
        int vehicleChange = 0;

        for(int i =1;i<=genes.length/2;i++){
            String sep = "" +genes[0] + genes[1];
            String sep1 = "" +genes[2] + genes[3];
            String sep2 = "" +genes[4] + genes[5];

            for(int j =0;j<hashMap.get(i).size();j++){
                if (i == 1 &&hashMap.get(i).get(j).getPath().equals(sep)) {
                    time += hashMap.get(i).get(j).getTime();
                    cost += hashMap.get(i).get(j).getCost();
                    vehicleChange += hashMap.get(i).get(j).getVehicleChange();
                    // Log.d(TAG, "HI 1 " + hashMap.get(i).get(j).getPath() + " " + hashMap.get(i).get(j).getTime() + " " + hashMap.get(i).get(j).getCost() + " " + hashMap.get(i).get(j).getVehicleChange());
                }
                else if (i == 2 &&hashMap.get(i).get(j).getPath().equals(sep1)) {
                    time += hashMap.get(i).get(j).getTime();
                    cost += hashMap.get(i).get(j).getCost();
                    vehicleChange += hashMap.get(i).get(j).getVehicleChange();
                    // Log.d(TAG, "HI 2 " + hashMap.get(i).get(j).getPath() + " " + hashMap.get(i).get(j).getTime() + " " + hashMap.get(i).get(j).getCost() + " " + hashMap.get(i).get(j).getVehicleChange());
                }
                else if (i == 3 &&hashMap.get(i).get(j).getPath().equals(sep2)) {
                    time += hashMap.get(i).get(j).getTime();
                    cost += hashMap.get(i).get(j).getCost();
                    vehicleChange += hashMap.get(i).get(j).getVehicleChange();
                    // Log.d(TAG, "HI 3 " + hashMap.get(i).get(j).getPath() + " " + hashMap.get(i).get(j).getTime() + " " + hashMap.get(i).get(j).getCost() + " " + hashMap.get(i).get(j).getVehicleChange());
                }
            }

        }

        fitness = getWeightedSum(Weight, time, cost, vehicleChange);
        Log.d(TAG, "Fitness " + fitness);

        return fitness;
    }

    public double getfittest(double fitesstOverall, int index){
        Log.d(TAG, "g "  +  this.fitesstOverall +" " + fitesstOverall);

        if(this.fitesstOverall == 0){
            this.fitesstOverall = fitesstOverall;
            this.index = index;
        }
        if(fitesstOverall <this.fitesstOverall){
            this.fitesstOverall = fitesstOverall;
            this.index = index;
        }
        Log.d(TAG, "THE BEST FITTEST "  + " " + this.fitesstOverall + " " + this.index);
        return this.fitesstOverall;

    }

    public double getWeightedSum(double [] weight, double time, double cost, int vehicleChange){
        Log.d(TAG, "POP " + weight[0]+ " " + weight[1] + " " + weight[2] + " " + time + " " + cost + " " +vehicleChange);
        double weightedSum = time * weight[0] + cost * weight[1] + vehicleChange * weight[2];
        return weightedSum;
    }

    public Route crossover(Route partner ){
        Route child = new Route();
        int midPoint = r.nextInt(genes.length);
        for(int i =0;i<genes.length;i++){
            //  Log.d(TAG, " das " + child.getGene());
            if(i> midPoint ){
                child.genes[i] = genes[i];
                // Log.d(TAG,"PA before " + child.getGene());

            }else{
                child.genes[i] = partner.genes[i];
                //  Log.d(TAG,"PB aftrer " + child.getGene());

            }
        }

        return child;
    }

    public void mutate(){
        double mutationRate = 0.01;
        for(int i =0;i<genes.length;i++){
            Float f = r.nextFloat();
            if(f < mutationRate){
                // Log.d(TAG, "Mutating " );
                genes[i] = r.nextInt(2);
                // Log.d(TAG, "Mutating " + genes[i]);

            }
        }
    }


    public double getFitness(){
        return fitness;
    }
    public String getGene(){
        return Arrays.toString(genes);
    }

    public void addHashMap( List<Stops> fruitsList1,List<Stops> fruitsList2, List<Stops> fruitsList3){
        hashMap.put(1, fruitsList1);
        hashMap.put(2, fruitsList2);
        hashMap.put(3, fruitsList3);




    }

}

