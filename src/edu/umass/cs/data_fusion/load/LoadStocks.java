package edu.umass.cs.data_fusion.load;


import edu.umass.cs.data_fusion.data_structures.Attribute;
import edu.umass.cs.data_fusion.data_structures.AttributeType;

public class LoadStocks extends LoadTSVFile {
    

    protected String[] attributeNames = {"Percent Change", "Last Trading Price", "Open Price", "Change $", "Volume", "Today's High", "Today's Low", "Previous Close", "52wk High", "52wk Low", "Shares Outstanding", "P/E", "Market Cap", "Yield", "Dividend", "EPS"};
    private AttributeType[] attributeTypes = {AttributeType.FLOAT,AttributeType.FLOAT,AttributeType.FLOAT,AttributeType.FLOAT,AttributeType.FLOAT,AttributeType.FLOAT,AttributeType.FLOAT,AttributeType.FLOAT,AttributeType.FLOAT,AttributeType.FLOAT,AttributeType.FLOAT,AttributeType.FLOAT,AttributeType.FLOAT,AttributeType.FLOAT,AttributeType.FLOAT,AttributeType.FLOAT};
    
    @Override
    protected Attribute getFloatAttributeFromString(String name, String rawValue) {
        double value = Double.parseDouble(rawValue.replaceAll("[^\\.0-9]","")); // TODO: Work more on this regex. Remove everything except numbers and periods
        return new Attribute(name,rawValue); // new FloatAttribute(name,rawValue,value) 
    }
    
}
