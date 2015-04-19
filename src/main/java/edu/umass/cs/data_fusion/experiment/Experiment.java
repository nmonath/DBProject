package main.java.edu.umass.cs.data_fusion.experiment;


import main.java.edu.umass.cs.data_fusion.data_structures.Algorithm;
import main.java.edu.umass.cs.data_fusion.data_structures.RecordCollection;
import main.java.edu.umass.cs.data_fusion.data_structures.Result;
import main.java.edu.umass.cs.data_fusion.evaluation.EvaluationMetrics;
import main.java.edu.umass.cs.data_fusion.evaluation.EvaluationMetricsWithTolerance;
import main.java.edu.umass.cs.data_fusion.load.LoadTSVFile;
import main.java.edu.umass.cs.data_fusion.util.HTMLOutput;

import java.io.File;
import java.util.ArrayList;

public class Experiment {


    private Algorithm algorithm;
    private boolean evaluationWithSlack;
    private RecordCollection inputData;
    private RecordCollection gold;
    private File outputDir;
    private LoadTSVFile loader;
    
    public Experiment(Algorithm algorithm, boolean evaluationWithSlack, LoadTSVFile loader, RecordCollection inputData, RecordCollection gold, File outputDir) {
        this.algorithm = algorithm;
        this.evaluationWithSlack = evaluationWithSlack;
        this.loader = loader;
        this.inputData = inputData;
        this.gold = gold;
        this.outputDir = outputDir;
    }
    
    public Experiment(Algorithm algorithm, boolean evaluationWithSlack, LoadTSVFile loader, File inputFile, File goldFile, File outputDir) {
        this(algorithm,evaluationWithSlack,loader,loader.load(inputFile),loader.loadGold(goldFile),outputDir);
    }
    
    
    public void run() {
        // Run the algorithm
        ArrayList<Result> results = algorithm.execute(inputData);

        EvaluationMetrics evaluator;
        if (evaluationWithSlack)
            evaluator = new EvaluationMetricsWithTolerance(results,inputData,gold);
        else
            evaluator = new EvaluationMetrics(results,gold);
        
        // Evaluate the data
        evaluator.calcMetrics();
        evaluator.calcErrorRate();
        evaluator.calcMNAD();
        evaluator.printResults();

        // Write the output
        outputDir.mkdirs();
        RecordCollection resultsCollection = algorithm.convert(results);
        HTMLOutput.writeHTMLOutput(loader.getOrderedAttributeNames(), resultsCollection, gold, new File(outputDir, "report.html").getAbsolutePath(), true, evaluator);
        resultsCollection.writeToTSVFile(new File(outputDir, "output.tsv"), loader.getOrderedAttributeNames());

    }
    

}
