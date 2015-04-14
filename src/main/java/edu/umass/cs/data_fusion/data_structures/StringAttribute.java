package main.java.edu.umass.cs.data_fusion.data_structures;

public class StringAttribute extends Attribute {
	protected String stringValue;

	public StringAttribute(String name, String rawValue) {
		super(name, rawValue);
		
		this.type = AttributeType.STRING;
		this.stringValue = rawValue.trim();
	}
	
	public String getStringValue() {
        return this.stringValue;
    }
	
	@Override
	public String toString() {
		return stringValue;
	}

	@Override
	public int hashCode() {
		return (this.name + this.stringValue).hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return (obj instanceof StringAttribute) && this.name.equals(((StringAttribute) obj).getName()) && this.getStringValue().equals(((StringAttribute) obj).getStringValue());
	}

}
