package edu.umass.cs.data_fusion.data_structures;


import java.lang.String;

// I just started on this so I could get the Record code to compile.
// Should this have the generic type T??
public class Attribute {
    private String name;

    private String rawValue;

    //private T value;

    public Attribute(String name, String rawValue) {
        this.name = name;
        this.rawValue = rawValue;
    }

    public String getName() {
        return name;
    }

    public String getRawValue() {
        return rawValue;
    }

    
}