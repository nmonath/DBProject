package edu.umass.cs.data_fusion.experiment;

import edu.umass.cs.data_fusion.algorithm.Upperbound;
import edu.umass.cs.data_fusion.data_structures.RecordCollection;
import edu.umass.cs.data_fusion.evaluation.EvaluationMetrics;
import edu.umass.cs.data_fusion.load.LoadStocks;

import java.io.File;

public class UpperboundExperiment {

    public static void main(String[] args) {

        LoadStocks loader = new LoadStocks();
        RecordCollection collection = loader.load(new File("clean_stock/stock-2011-07-01.txt"));
        RecordCollection gold = loader.loadGold(new File("nasdaq_truth/stock-2011-07-01-nasdaq-com.txt"));
        Upperbound up = new Upperbound(gold);
        EvaluationMetrics eval = new EvaluationMetrics(up.execute(collection),gold);
        eval.calcMetrics();
        eval.printResults();
    }

}
