package edu.umass.cs.data_fusion.util.math;


public interface LossFunction<T> {
    
    public String getName();
    
    public double loss(T one, T two);
}
