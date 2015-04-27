package main.java.edu.umass.cs.data_fusion.dataset_creation;

import main.java.edu.umass.cs.data_fusion.data_structures.RecordCollection;
import main.java.edu.umass.cs.data_fusion.data_structures.SyntheticSource;
import main.java.edu.umass.cs.data_fusion.load.LoadAdult;

import java.io.File;
import java.util.ArrayList;

public class CreateAdultDataset {
    
    public static void main(String[] args) {

        
        
        // Note LoadAdult != LoadAdultForDatasetCreation
        // LoadAdult loads files in the Luna Dong format
        // LoadAdultForDatasetCreation loads files in UCI format
        // the adult_gold.tsv file and other files in the data directory are in the Luna Dong format
        LoadAdult loader = new LoadAdult();
        RecordCollection collection = loader.load(new File(new File("data", "adult"), "adult_gold.tsv"));
        
        // Modification code
        CreateSyntheticDataset createSyntheticDataset = new CreateSyntheticDataset();
        
        //synthetic data sources
        ArrayList<SyntheticSource> sources = new ArrayList<SyntheticSource>();
    	SyntheticSource source1 = new SyntheticSource("synth1", 0.1, 1);
    	SyntheticSource source2 = new SyntheticSource("synth2", 1, 3);        
    	sources.add(source1);
    	sources.add(source2);
    	
        RecordCollection noisyData = createSyntheticDataset.createModifiedDataset(collection, sources);
        
        noisyData.writeToTSVFile(new File(new File("data", "adult"), "adult_noisy.tsv"),loader.getOrderedAttributeNames());
        
        
    }
    
}
