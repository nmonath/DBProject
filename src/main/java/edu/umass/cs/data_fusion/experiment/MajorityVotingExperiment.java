package main.java.edu.umass.cs.data_fusion.experiment;


import main.java.edu.umass.cs.data_fusion.algorithm.MajorityVote;
import main.java.edu.umass.cs.data_fusion.algorithm.TruthFinder;
import main.java.edu.umass.cs.data_fusion.data_structures.RecordCollection;
import main.java.edu.umass.cs.data_fusion.data_structures.Result;
import main.java.edu.umass.cs.data_fusion.evaluation.EvaluationMetrics;
import main.java.edu.umass.cs.data_fusion.load.LoadStocks;
import main.java.edu.umass.cs.data_fusion.util.HTMLOutput;

import java.io.File;
import java.util.ArrayList;

public class MajorityVotingExperiment {
    
    /*
    Path to data should point to the directory containing
    the data folder which has a subdirectory called stock
    containing:
        clean_stock_rawdata
        nasdaq_truth_golddata
        pop_truth_golddata
    */


    // Usage TruthFinderExperiment <path-to-data-dir> <output-dir>
    public static void main(String[] args) {

        StockExperiment exp = new StockExperiment(new MajorityVote(),"clean_stock/stock-2011-07-07.txt","nasdaq_truth/stock-2011-07-07-nasdaq-com.txt","output/july7");
        exp.run();
        
    }
}

