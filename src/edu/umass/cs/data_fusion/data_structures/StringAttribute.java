package edu.umass.cs.data_fusion.data_structures;

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

}
