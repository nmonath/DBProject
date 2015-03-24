package edu.umass.cs.data_fusion.data_structures;

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
    
    abstract Result execute(RecordCollection recordCollection);
}
