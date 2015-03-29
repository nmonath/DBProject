package edu.umass.cs.data_fusion.data_structures;

import java.util.ArrayList;

public abstract class Algorithm {
    private RecordCollection recordCollection;
    private Result result;
    private String name;
    
    protected Algorithm(String name) {
        this.name = name;
    }
    protected String getName() {
        return name;
    }
    
    public abstract ArrayList<Result> execute(RecordCollection recordCollection);

}
