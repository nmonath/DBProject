package edu.umass.cs.data_fusion.load;

import edu.umass.cs.data_fusion.data_structures.*;

import java.io.*;
import java.util.ArrayList;

public class LoadTSVFile {


    private String[] orderedAttributeNames;
    
    private AttributeType[] attributeTypes;
    
    public LoadTSVFile() {};
    
    public LoadTSVFile(AttributeType[] attributeTypes) {
        orderedAttributeNames = new String[0];
        this.attributeTypes = attributeTypes;
    }
    
    public LoadTSVFile(String[] orderedAttributeNames, AttributeType[]  attributeTypes) {
        this.orderedAttributeNames = orderedAttributeNames;
        this.attributeTypes = attributeTypes;
    }
    

    public RecordCollection load(File file) {
        try {
            ArrayList<Record>  records = new ArrayList<Record>(1000); // TODO: Maybe there is a way to get the number of lines easily?
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
            String line = reader.readLine();
            int lineNo = 0;
            while (line != null) {
                String[] fields = line.split("\t");
                if (fields.length < 2) {
                    System.err.println("Error reading file " + file.getName() + " malformed line: " + line);
                } else {
                    Record rec = new Record(lineNo, new Source(fields[0]), new Entity(fields[1]));
                    for (int i = 2; i < fields.length; i++) {
                        int j = i-2;
                        if (j < orderedAttributeNames.length)
                            rec.addAttribute(getAttributeFromString(orderedAttributeNames[j],fields[i],attributeTypes[i]));
                        else
                            rec.addAttribute(getAttributeFromString(String.format("Attr%04d",j),fields[i],attributeTypes[i]));
                    }
                    records.add(rec);
                }
                line = reader.readLine();
                lineNo += 1;
            }
            return new RecordCollection(records);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    // Defining it like this lets us define the cleaning methods for each data set differently
    // I think this makes sense?
    protected Attribute getAttributeFromString(String name, String rawValue, AttributeType type)  {
        switch (type) {
            case STRING: {
                return getStringAttributeFromString(name,rawValue);
            }
            case FLOAT: {
                return getFloatAttributeFromString(name,rawValue);
            }
        }
        return null;
    }
    
    protected Attribute getStringAttributeFromString(String name, String rawValue) {
        return new StringAttribute(name,rawValue);
    }
    
    protected Attribute getFloatAttributeFromString(String name, String rawValue) {
        return new FloatAttribute(name,rawValue); 
    }
    
}
