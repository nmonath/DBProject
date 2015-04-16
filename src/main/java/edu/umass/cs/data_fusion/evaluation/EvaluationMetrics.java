package main.java.edu.umass.cs.data_fusion.evaluation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import main.java.edu.umass.cs.data_fusion.data_structures.*;

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
    	HashMap<Entity,HashMap<String,Attribute>> resultHash = getResultHash(); // We could alternatively use the RecordCollection which has this automatically.
        double totalOutput = goldRecords.getTotalNumberOfAttributes(); // I think that the denominator should be the # of attributes provided in the gold set right? Not the predicted.
        ArrayList<Record> records = goldRecords.getRecords();
        for(Record r: records){
           Entity entity = r.getEntity();
           HashMap<String,Attribute> attribValues = r.getAttributes();
           for(String a : r.getAttributes().keySet()){
        	   Attribute goldAttrib = attribValues.get(a);
        	   if(goldAttrib.getType().equals(AttributeType.CATEGORICAL)){ // Only take the categorical ones
                 if (resultHash.containsKey(entity)) {
                   if (resultHash.get(entity).containsKey(a)) {
                       Attribute resultAttrib = resultHash.get(entity).get(a);
                       if (resultAttrib.getType().equals(AttributeType.CATEGORICAL)) {
                           if (goldAttrib.equals(resultAttrib)) {
                               match++;
                           }
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
    	HashMap<Entity,HashMap<String,Attribute>> resultHash = getResultHash();
    	double totalOutput = getResultNumAttributes();
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
    	float totalnormalizedDist = 0;
    	int totalnumAttributeValues = 0;
    	
    	HashMap<String, ArrayList<Float>> attributeValueDistances = new HashMap<String, ArrayList<Float>>();
    	HashMap<String,Float> attributeValueSum = new HashMap<String, Float>();
    	
    	HashMap<Entity,HashMap<String,Attribute>> resultHash = getResultHash();
    	
        ArrayList<Record> records = goldRecords.getRecords();
        for(Record r: records){
           Entity entity = r.getEntity();
           HashMap<String,Attribute> attribValues = r.getAttributes();
           for(String a : r.getAttributes().keySet()){
        	   Attribute goldAttrib = attribValues.get(a);
        	   if(goldAttrib.getClass()  == FloatAttribute.class){
        		 FloatAttribute floatgoldAttrib = (FloatAttribute)goldAttrib;
                 if (resultHash.containsKey(entity)) {
                   if (resultHash.get(entity).containsKey(a)) {
                       Attribute resultAttrib = resultHash.get(entity).get(a);
                       if(resultAttrib.getClass()  == FloatAttribute.class){
                    	   FloatAttribute floatresultAttrib = (FloatAttribute)resultAttrib; 
                           Float distance = Math.abs(floatgoldAttrib.getFloatValue()-floatresultAttrib.getFloatValue()); 
                           if(attributeValueDistances.containsKey(a)){
                        	   ArrayList<Float> d = attributeValueDistances.get(a);
                        	   d.add(distance);
                        	   attributeValueDistances.put(a, d);
                        	   attributeValueSum.put(a, attributeValueSum.get(a)+distance);
                           }
                           else{
                        	   ArrayList<Float> d = new ArrayList<Float>();
                        	   d.add(distance);
                        	   attributeValueDistances.put(a,d);
                        	   attributeValueSum.put(a, distance);
                           }
                       
                      }
                   }
               }
           }
          } 
        }
        for (Map.Entry<String, ArrayList<Float>> entry : attributeValueDistances.entrySet()) {
            
            String attrName = entry.getKey();
            float recordNum = (float)entry.getValue().size();
            float sum = attributeValueSum.get(attrName);
            float variance = 0;
            float mean = sum/recordNum;
            for(Float f : entry.getValue()){
            	variance += Math.pow(f-mean,2); 
            }
            variance /= (recordNum-1);
            for(Float f : entry.getValue()){
            	totalnormalizedDist += f/variance;
            	totalnumAttributeValues+=1;
            }
        }
        mnad = totalnormalizedDist/(float)totalnumAttributeValues;
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
    
    public double getMNAD(){
    	return mnad;
    }
    
    private HashMap<Entity,HashMap<String,Attribute>> getResultHash() {
    	HashMap<Entity,HashMap<String,Attribute>> resultHash = new HashMap<Entity, HashMap<String,Attribute>>();
        for (Result r : resultRecords) {
             resultHash.put(r.getEntity(),r.getAttributes());
        }
    	return resultHash;
    }
    
    public void printResults() {
        System.out.println("Precision: " + precision);
        System.out.println("Recall: " + recall);
        System.out.println("F1: " + 2.0*precision*recall/(precision+recall));
        System.out.println("Error Rate: " + errorRate);
        System.out.println("Mean Normalized Absolute Distance: " + mnad);
    }
    
    
    private double getResultNumAttributes() {
        double numAttributes = 0;
    	for (Result r : resultRecords) {
            numAttributes+=r.getAttributes().size();
        }
        return numAttributes;
    }
    

    
    
    
    
}
