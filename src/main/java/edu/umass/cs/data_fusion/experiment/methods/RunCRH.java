package main.java.edu.umass.cs.data_fusion.experiment.methods;


import main.java.edu.umass.cs.data_fusion.algorithm.CRH;
import main.java.edu.umass.cs.data_fusion.experiment.Experiment;
import main.java.edu.umass.cs.data_fusion.experiment.ExperimentSetups;

import java.io.File;

public class RunCRH {
    
    public static void main(String[] args) {
        
        // Books
        Experiment books = ExperimentSetups.getBookExperiment(new CRH(), new File(new File("output", "CRH"), "books"));
        books.run();
        books = null;
        System.gc();
        
        // Full Stock
        Experiment stocks = ExperimentSetups.getFullStockExperiment(new CRH(), true, new File(new File("output", "CRH"), "full_stocks"));
        stocks.run();
        stocks = null;
        System.gc();
        
        // July 7
        Experiment july7 = ExperimentSetups.getJulySeventhStockExperiment(new CRH(), true, new File(new File("output", "CRH"), "july7"));
        july7.run();
        july7 = null;
        System.gc();
        
        // Weather 
        Experiment weather = ExperimentSetups.getWeatherExperiment(new CRH(), new File(new File("output", "CRH"), "weather"));
        weather.run();
        weather = null;
        System.gc();
    }
}
