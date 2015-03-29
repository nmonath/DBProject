package edu.umass.cs.data_fusion.load;

import edu.umass.cs.data_fusion.algorithm.TruthFinder;
import edu.umass.cs.data_fusion.data_structures.AttributeType;
import edu.umass.cs.data_fusion.data_structures.RecordCollection;

import java.io.File;

public class LoadStocks extends LoadTSVFile {

    public static String[] names = {"Percent Change", "Last Trading Price", "Open Price", "Change $", "Volume", "Today's High", "Today's Low", "Previous Close", "52wk High", "52wk Low", "Shares Outstanding", "P/E", "Market Cap", "Yield", "Dividend", "EPS"};
    //public static AttributeType[] types = {AttributeType.FLOAT,AttributeType.FLOAT,AttributeType.FLOAT,AttributeType.FLOAT,AttributeType.FLOAT,AttributeType.FLOAT,AttributeType.FLOAT,AttributeType.FLOAT,AttributeType.FLOAT,AttributeType.FLOAT,AttributeType.FLOAT,AttributeType.FLOAT,AttributeType.FLOAT,AttributeType.FLOAT,AttributeType.FLOAT,AttributeType.FLOAT};
    public static AttributeType[] types = {AttributeType.STRING,AttributeType.STRING,AttributeType.STRING,AttributeType.STRING,AttributeType.STRING,AttributeType.STRING,AttributeType.STRING,AttributeType.STRING,AttributeType.STRING,AttributeType.STRING,AttributeType.STRING,AttributeType.STRING,AttributeType.STRING,AttributeType.STRING,AttributeType.STRING,AttributeType.STRING};
    public LoadStocks() {
        super(names,types);
    }
    
    
    public static void main(String[] args) {
        LoadStocks loader = new LoadStocks();
        RecordCollection collection = loader.load(new File("clean_stock/stock-2011-07-01.txt"));
        TruthFinder tf = new TruthFinder();
        RecordCollection cleaner = tf.convert(tf.execute(collection));
        cleaner.writeToTSVFile(new File("outputTest.txt"),names);
    }
    
}
