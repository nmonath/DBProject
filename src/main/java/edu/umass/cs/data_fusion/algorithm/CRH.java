package edu.umass.cs.data_fusion.algorithm;


import edu.umass.cs.data_fusion.data_structures.*;
import edu.umass.cs.data_fusion.util.math.AttributeLossFunction;

import java.util.*;

public abstract class CRH extends Algorithm {

    private AttributeLossFunction loss;
    private double delta;
    
    final private int MAX_ITERATIONS = 1000;

    public CRH() {
        super("CRH");
    }
    
    
    abstract public void initializePredictions(Map<Entity,Record> prediction,RecordCollection collection);
    
    abstract public boolean hasConverged(Map<Source,Double> weights, Map<Source,Double> nextWeights, double delta);
    
    abstract public Map<Source,Double> updateWeights(Map<Source,Double> weights, Map<Entity,Record> prediction, RecordCollection collection);

    abstract public void updatePrediction(Map<Entity,Record> prediction, Map<Source,Double> weights, RecordCollection collection);



    @Override
    public ArrayList<Result> execute(RecordCollection recordCollection) {
        
        Map<Entity,Record> predictedTruth = new HashMap<Entity, Record>();
        
        Map<Source,Double> weights = new HashMap<Source, Double>();
        
        initializePredictions(predictedTruth,recordCollection);

        Set<Entity> entities = recordCollection.getEntities();
        
        boolean converged = false;
        int numIters = 0;
        
        while (!converged && numIters < MAX_ITERATIONS) {
            Map<Source,Double> prevWeights = new HashMap<Source, Double>();
            prevWeights.putAll(weights);
            updatePrediction(predictedTruth,weights,recordCollection);
            numIters++;
            converged = hasConverged(prevWeights,weights,delta);
        }
        
        
        return null;
    }
}
