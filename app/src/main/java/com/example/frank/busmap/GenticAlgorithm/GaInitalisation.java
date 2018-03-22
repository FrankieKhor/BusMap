package com.example.frank.busmap.GenticAlgorithm;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by frank on 18/03/2018.
 */
public class GaInitalisation {
    static String TAG = GaInitalisation.class.getName();
    List<Stops> fruitsList = new ArrayList<Stops>();
    List<Stops> fruitsList2 = new ArrayList<Stops>();
    List<Stops> fruitsList3 = new ArrayList<Stops>();

    public GaInitalisation(List<Stops> path1, List<Stops> path2, List<Stops> path3) {

        fruitsList.add(new Stops("00", 1, 2, 0));
        fruitsList.add(new Stops("01", 5, 7, 0));
        fruitsList.add(new Stops("02", 43, 5, 1));
        fruitsList.add(new Stops("10", 3, 3.50, 0));
        fruitsList.add(new Stops("11", 5, 6, 0));
        fruitsList.add(new Stops("12", 2, 11, 1));
        fruitsList.add(new Stops("20", 14, 14, 0));
        fruitsList.add(new Stops("21", 41, 53.50, 0));
        fruitsList.add(new Stops("22", 40, 5, 1));

        fruitsList2.add(new Stops("00", 1, 1, 0));
        fruitsList2.add(new Stops("01", 51, 5, 0));
        fruitsList2.add(new Stops("02", 43, 7, 1));
        fruitsList2.add(new Stops("10", 23, 9, 0));
        fruitsList2.add(new Stops("11", 5, .50, 0));
        fruitsList2.add(new Stops("12", 432, 8, 1));
        fruitsList2.add(new Stops("20", 2, 3.50, 0));
        fruitsList2.add(new Stops("21", 18, 1, 0));
        fruitsList2.add(new Stops("22", 3, .1, 1));

        fruitsList3.add(new Stops("00", 2, 1, 0));
        fruitsList3.add(new Stops("01", 1, 3, 0));
        fruitsList3.add(new Stops("02", 43, 4, 1));
        fruitsList3.add(new Stops("10", 2, 1, 0));
        fruitsList3.add(new Stops("11", 5, 5.50, 0));
        fruitsList3.add(new Stops("12", 7, 1, 1));
        fruitsList3.add(new Stops("20", 0, 0, 0));
        fruitsList3.add(new Stops("21", 1, 1, 0));
        fruitsList3.add(new Stops("22", 41, 4, 1));



    }

    public void setUp(){
        int totalPopulation = 100;
        Route[] population = new Route[totalPopulation];
        ArrayList<Route> matingPool = new ArrayList<>();
        Random r = new Random();

        for (int i = 0; i < population.length; i++) {
            Log.d(TAG, "" + i);
            population[i] = new Route();
            population[i].addHashMap(fruitsList, fruitsList2, fruitsList3);
            double q = population[i].fitness();
            population[i].getfittest(q, i);


            for (int j = 0; j < population[i].getFitness(); j++) {
                matingPool.add(population[i]);
            }
        }
        //Add back to population pool
        for (int i = 0; i < population.length; i++) {
            int a = r.nextInt(matingPool.size());
            Route partnerA = matingPool.get(a);
            int b = r.nextInt(matingPool.size());
            Route partnerB = matingPool.get(b);
            if (partnerA.getGene().equals(partnerB)) {
                partnerB = matingPool.get(r.nextInt(matingPool.size()));
            }

            Route child = partnerA.crossover(partnerB);
            child.mutate();
            population[i] = child;
        }
    }
}






