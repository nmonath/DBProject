package main.java.edu.umass.cs.data_fusion.experiment.baseline;


import main.java.edu.umass.cs.data_fusion.algorithm.MajorityVote;
import main.java.edu.umass.cs.data_fusion.experiment.Experiment;
import main.java.edu.umass.cs.data_fusion.experiment.ExperimentSetups;

import java.io.File;

public class RunMajorityVoting {
    
    
    public static void main(String[] args) {
        
        // Books
        Experiment books = ExperimentSetups.getBookExperiment(new MajorityVote(), new File(new File("output","majorityvote"), "books"));
        books.run();
        books = null;
        System.gc();
        
        // Full Stock
        Experiment fullStock = ExperimentSetups.getFullStockExperiment(new MajorityVote(),true, new File(new File("output","majorityvote"), "fullstock"));
        fullStock.run();
        fullStock = null;
        System.gc();

        // July 7 Stock
        Experiment july7 = ExperimentSetups.getJulySeventhStockExperiment(new MajorityVote(), true, new File(new File("output", "majorityvote"), "july7"));
        july7.run();
        july7 = null;
        System.gc();

        // Weather
        Experiment weather = ExperimentSetups.getWeatherExperiment(new MajorityVote(), new File(new File("output", "majorityvote"), "weather"));
        weather.run();
        weather = null;
        System.gc();
    }
    
}
