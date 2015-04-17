package main.java.edu.umass.cs.data_fusion.experiment;

import main.java.edu.umass.cs.data_fusion.algorithm.Upperbound;
import main.java.edu.umass.cs.data_fusion.data_structures.RecordCollection;
import main.java.edu.umass.cs.data_fusion.data_structures.Result;
import main.java.edu.umass.cs.data_fusion.evaluation.EvaluationMetrics;
import main.java.edu.umass.cs.data_fusion.load.LoadStocks;
import main.java.edu.umass.cs.data_fusion.util.HTMLOutput;

import java.io.File;
import java.util.ArrayList;

public class UpperboundExperiment {

    public static void main(String[] args) {

        LoadStocks loader = new LoadStocks();
        RecordCollection collection = loader.load(new File("clean_stock/stock-2011-07-01.txt"));
        RecordCollection gold = loader.loadGold(new File("nasdaq_truth/stock-2011-07-01-nasdaq-com.txt"));
        Upperbound up = new Upperbound(gold);
        ArrayList<Result> results = up.execute(collection);
        EvaluationMetrics eval = new EvaluationMetrics(results,gold);
        eval.calcMetrics();
        eval.printResults();
        HTMLOutput.writeHTMLOutput(up.convert(results),gold,"htmltest.html",true,eval);
    }

}
