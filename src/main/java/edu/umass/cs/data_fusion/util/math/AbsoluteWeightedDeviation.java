package main.java.edu.umass.cs.data_fusion.util.math;

import main.java.edu.umass.cs.data_fusion.data_structures.Attribute;

public class AbsoluteWeightedDeviation implements AttributeLossFunction {

    @Override
    public String getName() {
        return this.getClass().getCanonicalName();
    }

    // TODO: Implement this!!
    @Override
    public double loss(Attribute one, Attribute two) {
        return 0;
    }
}
