package main.java.edu.umass.cs.data_fusion.experiment.baseline;

import main.java.edu.umass.cs.data_fusion.algorithm.BaselineMean;
import main.java.edu.umass.cs.data_fusion.algorithm.BaselineMedian;
import main.java.edu.umass.cs.data_fusion.experiment.Experiment;
import main.java.edu.umass.cs.data_fusion.experiment.ExperimentSetups;

import java.io.File;

public class RunMedian {

    public static void main(String[] args) {


        // Full Stock
        Experiment fullStock = ExperimentSetups.getFullStockExperiment(new BaselineMedian(), true, new File(new File("output", "median"), "fullstock"));
        fullStock.run();
        fullStock = null;
        System.gc();

        // July 7 Stock
        Experiment july7 = ExperimentSetups.getJulySeventhStockExperiment(new BaselineMedian(), true, new File(new File("output", "median"), "july7"));
        july7.run();
        july7 = null;
        System.gc();

        // Weather
        Experiment weather = ExperimentSetups.getWeatherExperiment(new BaselineMedian(), new File(new File("output", "median"), "weather"));
        weather.run();
        weather = null;
        System.gc();
    }
}
