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

        // CMD arguments
        File dataPath = new File(args[0]);
        File outputDir = new File(args[1]);


        // Make output dir if it doesn't exist
        outputDir.mkdirs();

        // Load the data to fuse
        LoadStocks loader = new LoadStocks();
        RecordCollection collection = loader.load(new File(dataPath, "data/stock/clean_stock_rawdata"));

        // Run the algorithm
        MajorityVote mv = new MajorityVote();
        ArrayList<Result> results = mv.execute(collection);

        // Load the gold data
        RecordCollection gold = loader.loadGold(new File("data/stock/nasdaq_truth_golddata"));

        // Evaluate the data
        EvaluationMetrics eval = new EvaluationMetrics(results, gold);
        eval.calcMetrics();
        eval.calcErrorRate();
        eval.calcMNAD();
        eval.printResults();

        // Write out the output
        RecordCollection resultsCollection = mv.convert(results);
        HTMLOutput.writeHTMLOutput(resultsCollection, gold, new File(outputDir, "report.html").getAbsolutePath(), true);
        resultsCollection.writeToTSVFile(new File(outputDir, "output.tsv"), LoadStocks.names);
        // TODO: Write Score to a file
    }
}
