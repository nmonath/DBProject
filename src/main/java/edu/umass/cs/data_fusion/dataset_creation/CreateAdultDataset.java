package main.java.edu.umass.cs.data_fusion.dataset_creation;

import main.java.edu.umass.cs.data_fusion.data_structures.RecordCollection;
import main.java.edu.umass.cs.data_fusion.load.LoadAdult;

import java.io.File;

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
        
        RecordCollection noisyData = createSyntheticDataset.createModifiedDataset(collection);
        
        noisyData.writeToTSVFile(new File(new File("data", "adult"), "adult_noisy.tsv"),loader.getOrderedAttributeNames());
        
    }
    
}
