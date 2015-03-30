package edu.umass.cs.data_fusion.evaluation;

import java.util.ArrayList;
import java.util.HashMap;


import edu.umass.cs.data_fusion.data_structures.Attribute;
import edu.umass.cs.data_fusion.data_structures.Entity;
import edu.umass.cs.data_fusion.data_structures.Record;
import edu.umass.cs.data_fusion.data_structures.RecordCollection;
import edu.umass.cs.data_fusion.data_structures.Result;

// this code has to be tested with actual output

public class EvaluationMetrics {
	private double precision;
	private double recall;
    private ArrayList<Result> resultRecords;
	private RecordCollection goldRecords;
	
 
	
    public EvaluationMetrics(ArrayList<Result> results, RecordCollection collection){
    	this(results,collection,0.0,0.0);
    }
    public EvaluationMetrics(ArrayList<Result> results, RecordCollection collection, double precision, double recall) {
        this.precision = precision;
        this.recall = recall;
        this.resultRecords=results;
        this.goldRecords = collection;
        
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
        	   if(resultHash.get(entity).containsKey(a)){ 
        		   Attribute resultAttrib = resultHash.get(entity).get(a);
        	       if(goldAttrib.compareTo(resultAttrib)==0){
            	        match++; 
                    }
                }
           }
           
        }
       
    	this.recall = match/totalGold;
    	this.precision = match/totalOutput;
    	 
    }
    
    public double getRecall(){
    	return recall;
    }
    
    public double getPrecision(){
    	return precision;
    }
    
    private HashMap<Entity,HashMap<String,Attribute>> getResultHash(ArrayList<Result> results) {
    	HashMap<Entity,HashMap<String,Attribute>> resultHash = new HashMap<Entity, HashMap<String,Attribute>>();
        for (Result r : results) {
             resultHash.put(r.getEntity(),r.getAttributes());
        }
    	return resultHash;
    }
    
    
    private double getResultNumAttributes(ArrayList<Result> results) {
        double numAttributes = 0;
    	for (Result r : results) {
            numAttributes+=r.getAttributes().size();
        }
        return numAttributes;
    }
}
