package main.java.edu.umass.cs.data_fusion.experiment;

import main.java.edu.umass.cs.data_fusion.data_structures.Algorithm;
import main.java.edu.umass.cs.data_fusion.load.LoadStocks;
import main.java.edu.umass.cs.data_fusion.load.LoadWeather;

import java.io.File;

public class ExperimentSetups {
    
    public static Experiment getStockExperiment(Algorithm algorithm, boolean evaluationWithSlack, File inputFile,File goldFile,File outputDir) {
        return new Experiment(algorithm,evaluationWithSlack,new LoadStocks(),inputFile,goldFile,outputDir);
    }
    
    public static Experiment getJulySeventhStockExperiment(Algorithm algorithm, boolean evaluationWithSlack, File outputDir) {
        return getStockExperiment(algorithm,evaluationWithSlack, new File(new File("data","clean_stock"),"stock-2011-07-07.txt"), new File(new File("data","nasdaq_truth"),"stock-2011-07-07-nasdaq-com.txt"),outputDir);
    }
    
    public static Experiment getFullStockExperiment(Algorithm algorithm, boolean evaluationWithSlack, File outputDir) {
        return getStockExperiment(algorithm,evaluationWithSlack, new File(new File("data","stock"),"clean_stock_rawdata"), new File(new File("data","stock"),"nasdaq_truth_golddata"),outputDir);
        
    }
    public static Experiment getWeatherExperiment(Algorithm algorithm, File outputDir) {
        return new Experiment(algorithm,false,new LoadWeather(),new File(new File("data", "weather"), "weather_data_set.txt"),new File(new File("data", "weather"), "weather_ground_truth.txt"),outputDir);
    }
}
