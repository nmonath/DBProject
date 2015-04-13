package edu.umass.cs.data_fusion.util.math;

import edu.umass.cs.data_fusion.data_structures.Attribute;

public class ZeroOneLoss implements AttributeLossFunction {
    
    final private String name = "ZeroOneLoss";
    
    @Override
    public String getName() {
        return name;
    }

    @Override
    public double loss(Attribute one, Attribute two) {
        return (one.equals(two)) ? 1 : 0;
    }
}
