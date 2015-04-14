package main.java.edu.umass.cs.data_fusion.evaluation;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import main.java.edu.umass.cs.data_fusion.data_structures.Attribute;
import main.java.edu.umass.cs.data_fusion.data_structures.AttributeType;
import main.java.edu.umass.cs.data_fusion.data_structures.Entity;
import main.java.edu.umass.cs.data_fusion.data_structures.Record;
import main.java.edu.umass.cs.data_fusion.data_structures.RecordCollection;
import main.java.edu.umass.cs.data_fusion.data_structures.Result;

// this code has to be tested with actual output

public class EvaluationMetrics {
	private double precision;
	private double recall;
	private double errorRate;
	private double mnad;
    private ArrayList<Result> resultRecords;
	private RecordCollection goldRecords;
	
 
	
    public EvaluationMetrics(ArrayList<Result> results, RecordCollection collection){
    	this(results,collection,0.0,0.0,0.0,0.0);
    }
    public EvaluationMetrics(ArrayList<Result> results, RecordCollection collection, double precision, double recall, double errorRate, double mnad) {
        this.precision = precision;
        this.recall = recall;
        this.errorRate = errorRate;
        this.mnad = mnad;
        this.resultRecords=results;
        this.goldRecords = collection;
        
    }
    
    
    
    public void calcErrorRate(){
    	
    	double match = 0;
    	HashMap<Entity,HashMap<String,Attribute>> resultHash = getResultHash(resultRecords);
    	double totalOutput = getResultNumAttributes(resultRecords);
        ArrayList<Record> records = goldRecords.getRecords();
        for(Record r: records){
           Entity entity = r.getEntity();
           HashMap<String,Attribute> attribValues = r.getAttributes();
           for(String a : r.getAttributes().keySet()){
        	   Attribute goldAttrib = attribValues.get(a);
        	   if(goldAttrib.getType().equals(AttributeType.STRING)){
                 if (resultHash.containsKey(entity)) {
                   if (resultHash.get(entity).containsKey(a)) {
                       Attribute resultAttrib = resultHash.get(entity).get(a);
                       if (goldAttrib.equals(resultAttrib)) {
                           match++;
                       }
                   }
               }
           }
          } 
        }
        this.errorRate= 1.0-(match/totalOutput);
    }
    
    public void calcMetrics(){
    	
    	double totalGold= 0;
    	double match = 0;
    	HashMap<Entity,HashMap<String,Attribute>> resultHash = getResultHash(resultRecords);
    	double totalOutput = getResultNumAttributes(resultRecords);
        ArrayList<Record> records = goldRecords.getRecords();
        for(Record r: records){
           Entity entity = r.getEntity();
           HashMap<String,Attribute> attribValues = r.getAttributes();
           for(String a : r.getAttributes().keySet()){
        	   totalGold++;
        	   Attribute goldAttrib = attribValues.get(a);
               if (resultHash.containsKey(entity)) {
                   if (resultHash.get(entity).containsKey(a)) {
                       Attribute resultAttrib = resultHash.get(entity).get(a);
                       if (goldAttrib.equals(resultAttrib)) {
                           match++;
                       }
                   }
               }
           }
           
        }
       
    	this.recall = match/totalGold;
    	this.precision = match/totalOutput;
    	 
    }
    
    public void calcMNAD(){
    	
    }
    
    
    public double getRecall(){
    	return recall;
    }
    
    public double getPrecision(){
    	return precision;
    }
    
    public double getErrorRate(){
    	return errorRate;
    }
    
    private HashMap<Entity,HashMap<String,Attribute>> getResultHash(ArrayList<Result> results) {
    	HashMap<Entity,HashMap<String,Attribute>> resultHash = new HashMap<Entity, HashMap<String,Attribute>>();
        for (Result r : results) {
             resultHash.put(r.getEntity(),r.getAttributes());
        }
    	return resultHash;
    }
    
    public void printResults() {
        System.out.println("Precision: " + precision);
        System.out.println("Recall: " + recall);
        System.out.println("F1: " + 2.0*precision*recall/(precision+recall));
        System.out.println("Error Rate: " + errorRate);
    }
    
    
    private double getResultNumAttributes(ArrayList<Result> results) {
        double numAttributes = 0;
    	for (Result r : results) {
            numAttributes+=r.getAttributes().size();
        }
        return numAttributes;
    }
    
    
}
