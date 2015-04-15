package main.java.edu.umass.cs.data_fusion.experiment;




import main.java.edu.umass.cs.data_fusion.data_structures.Algorithm;
import main.java.edu.umass.cs.data_fusion.data_structures.RecordCollection;
import main.java.edu.umass.cs.data_fusion.data_structures.Result;
import main.java.edu.umass.cs.data_fusion.evaluation.EvaluationMetrics;
import main.java.edu.umass.cs.data_fusion.load.LoadStocks;
import main.java.edu.umass.cs.data_fusion.util.HTMLOutput;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;

public class StockExperiment {

    private Algorithm algorithm;
    private String inputFilename = new File(new File("data", "stock"), "clean_stock_rawdata").getAbsolutePath();
    private String goldFilename = new File(new File("data", "stock"), "nasdaq_truth_golddata").getAbsolutePath();
    private String outputDirname = new File("output", new Date(System.currentTimeMillis()).toString()).getAbsolutePath();

    public StockExperiment(Algorithm algorithm) {
        this.algorithm = algorithm;
    }
    
    public StockExperiment(Algorithm algorithm, String inputFilename, String goldFilename, String outputDirname) {
        this.algorithm = algorithm;
        this.inputFilename = inputFilename;
        this.goldFilename = goldFilename;
        this.outputDirname = outputDirname;
    }
    
    public StockExperiment(Algorithm algorithm, String dataDir, String outputDirname) {
        this.algorithm = algorithm;
        this.inputFilename = new File(dataDir,inputFilename).getAbsolutePath();
        this.goldFilename = new File(dataDir,goldFilename).getAbsolutePath();
        this.outputDirname = outputDirname;
    }

    public void run() {
        LoadStocks loader = new LoadStocks();
        RecordCollection collection = loader.load(new File(inputFilename));

        // Run the algorithm
        ArrayList<Result> results = algorithm.execute(collection);

        // Load the gold data
        RecordCollection gold = loader.loadGold(new File(goldFilename));

        // Evaluate the data
        EvaluationMetrics eval = new EvaluationMetrics(results, gold);
        eval.calcMetrics();
        eval.calcErrorRate();
        eval.calcMNAD();
        eval.printResults();

        // Write out the output
        new File(outputDirname).mkdirs();
        RecordCollection resultsCollection = algorithm.convert(results);
        HTMLOutput.writeHTMLOutput(resultsCollection, gold, new File(outputDirname, "report.html").getAbsolutePath(), true);
        resultsCollection.writeToTSVFile(new File(outputDirname, "output.tsv"), LoadStocks.names);
        // TODO: Write Score to a file

    }

}
