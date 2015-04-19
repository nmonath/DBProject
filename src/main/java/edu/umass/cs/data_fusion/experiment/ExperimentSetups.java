package main.java.edu.umass.cs.data_fusion.experiment;

import main.java.edu.umass.cs.data_fusion.data_structures.Algorithm;
import main.java.edu.umass.cs.data_fusion.load.LoadStocks;

import java.io.File;

public class ExperimentSetups {

    public class StockExperiment extends Experiment {
        public StockExperiment (Algorithm algorithm, boolean evaluationWithSlack, File inputFile,File goldFile,File outputDir)  {
            super(algorithm,evaluationWithSlack,new LoadStocks(),inputFile,goldFile,outputDir);
        }
    }
    
    public class JulySeventhStockExperiment extends StockExperiment {
        public JulySeventhStockExperiment (Algorithm algorithm, boolean evaluationWithSlack, File outputDir) {
            super(algorithm,evaluationWithSlack, new File(new File("data","clean_stock"),"stock-2011-07-07.txt"), new File(new File("data","nasdaq_truth"),"stock-2011-07-01-nasdaq-com.txt"),outputDir);
        }
    }

    public class FullStockExperiment extends StockExperiment {
        public FullStockExperiment (Algorithm algorithm, boolean evaluationWithSlack, File outputDir) {
            super(algorithm,evaluationWithSlack, new File(new File("data","stock"),"clean_stock_rawdata"), new File(new File("data","stock"),"nasdaq_truth_golddata"),outputDir);
        }
    }
}
