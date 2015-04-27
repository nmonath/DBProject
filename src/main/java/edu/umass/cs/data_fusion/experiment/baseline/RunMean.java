package main.java.edu.umass.cs.data_fusion.experiment.baseline;

import main.java.edu.umass.cs.data_fusion.algorithm.BaselineMean;
import main.java.edu.umass.cs.data_fusion.experiment.Experiment;
import main.java.edu.umass.cs.data_fusion.experiment.ExperimentSetups;

import java.io.File;

public class RunMean {

    public static void main(String[] args) {


        // Full Stock
        Experiment fullStock = ExperimentSetups.getFullStockExperiment(new BaselineMean(),true, new File(new File("output","mean"), "fullstock"));
        fullStock.run();
        fullStock = null;
        System.gc();

        // July 7 Stock
        Experiment july7 = ExperimentSetups.getJulySeventhStockExperiment(new BaselineMean(), true, new File(new File("output", "mean"), "july7"));
        july7.run();
        july7 = null;
        System.gc();

        // Weather
        Experiment weather = ExperimentSetups.getWeatherExperiment(new BaselineMean(), new File(new File("output", "mean"), "weather"));
        weather.run();
        weather = null;
        System.gc();
    }
}
