package edu.umass.cs.data_fusion.data_structures;


import java.lang.String;

// I just started on this so I could get the Record code to compile.
// Should this have the generic type T??
public class Attribute implements Comparable {
    private String name;

    private String rawValue;
    
    public Attribute(String name, String rawValue) {
        this.name = name;
        this.rawValue = rawValue;
    }

    // This is really bad. I just did it to print out the attributes in writeTSV in the same order.
    @Override
    public int compareTo(Object o) {
        if (o instanceof Attribute) {
            Attribute other = (Attribute) o;
            if (this.name.equals(other.getName()))
                return this.rawValue.compareTo(other.getRawValue());
            else
                return this.name.compareTo(other.getName());
        }
        return 0;
    }

    public String getName() {
        return name;
    }

    public String getRawValue() {
        return rawValue;
    }

    @Override
    public int hashCode() {
        return (this.name + this.rawValue).hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof Attribute) && this.name.equals(((Attribute) obj).getName()) && this.rawValue.equals(((Attribute) obj).getRawValue());
    }
}
