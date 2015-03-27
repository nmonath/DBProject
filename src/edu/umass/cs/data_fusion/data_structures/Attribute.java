package edu.umass.cs.data_fusion.data_structures;


import java.lang.String;

public class Attribute implements Comparable {
    protected String name;

    protected String rawValue;

    // We can change this around with Manuel's code, but for now I added this in.
    // StringAttribute will have this set to STRING and FloatAttribute set to FLOAT
    protected AttributeType type;
    
    public Attribute(String name, String rawValue) {
        this.name = name;
        this.rawValue = rawValue;
    }

    // TODO: How should this be defined
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
