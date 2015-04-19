package main.java.edu.umass.cs.data_fusion.data_structures;


import java.lang.String;

public class Attribute implements Comparable {
    protected String name;

    protected String rawValue;

    protected AttributeDataType dataType;
    
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
        return (obj instanceof Attribute) && this.name.equals(((Attribute) obj).getName()) && this.rawValue.equals(((Attribute) obj).getRawValue()) && this.dataType.equals(((Attribute) obj).getDataType());
    }
    
	public AttributeDataType getDataType(){
	    return dataType;
	}
    
    public AttributeType getType() {return type;}

    public static AttributeDataType getDataType(Iterable<Attribute> attributes) {

        boolean first = true;
        AttributeDataType attributeDataType = null;
        for (Attribute a : attributes) {
            AttributeDataType next = a.getDataType();
            if (first)
                attributeDataType = next;
            else if(!next.equals(attributeDataType)) {
                System.err.println("[Attribute.getDataType] ERROR: the collection of attributes passed into getDataType do not have a common data type.");
                return null;
            }
            first = false;
        }
        return attributeDataType;
    }

    public static AttributeType getType(Iterable<Attribute> attributes) {

        boolean first = true;
        AttributeType attributeType = null;
        for (Attribute a : attributes) {
            AttributeType next = a.getType();
            if (first)
                attributeType = next;
            else if(!next.equals(attributeType)) {
                System.err.println("[Attribute.getType] ERROR: the collection of attributes passed into getType do not have a common data type.");
                return null;
            }
            first = false;
        }
        return attributeType;
    }
}
