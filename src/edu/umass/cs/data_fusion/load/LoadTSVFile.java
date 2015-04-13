package edu.umass.cs.data_fusion.load;

import edu.umass.cs.data_fusion.data_structures.*;

import java.io.*;
import java.util.ArrayList;

public class LoadTSVFile {


    private String[] orderedAttributeNames;
    
    private AttributeType[] attributeTypes;

    public LoadTSVFile() {}

    public LoadTSVFile(AttributeType[] attributeTypes) {
        orderedAttributeNames = new String[0];
        this.attributeTypes = attributeTypes;
    }
    
    public LoadTSVFile(String[] orderedAttributeNames, AttributeType[]  attributeTypes) {
        this.orderedAttributeNames = orderedAttributeNames;
        this.attributeTypes = attributeTypes;
    }
    

    public RecordCollection load(File file) {
        String line = "";
        try {
            System.out.println("Loading file: " + file.getAbsolutePath());
            ArrayList<Record>  records = new ArrayList<Record>(10000); // TODO: Maybe there is a way to get the number of lines easily?
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
            line = reader.readLine();
            int lineNo = 0;
            String lineCount = "Lines Read: " + lineNo;
            System.out.print(lineCount);
            while (line != null) {
                if (lineNo % 100 == 0) {
                    for (int i = 0; i < lineCount.length(); i++)
                        System.out.print("\b");
                    System.out.print(lineCount);
                }
                lineCount = "Lines Read: " + lineNo;
                String[] fields = line.split("\t");
                if (fields.length < 2) {
                    System.err.println("\nError reading file " + file.getName() + " malformed line: " + line);
                } else {
                    Record rec = new Record(lineNo, new Source(fields[0]), new Entity(fields[1]));
                    for (int i = 2; i < fields.length; i++) {
                        int j = i-2;
                        // Handle empty attributes
                        if (fields[i].length() > 0)
                            if (j < orderedAttributeNames.length) {
                                Attribute attrToAdd = getAttributeFromString(orderedAttributeNames[j], fields[i], attributeTypes[j]);
                                if (attrToAdd != null)
                                    rec.addAttribute(attrToAdd);
                            } else {
                                Attribute attrToAdd = getAttributeFromString(String.format("Attr%04d", j), fields[i], attributeTypes[j]);
                                if (attrToAdd != null)
                                    rec.addAttribute(attrToAdd);
                            }
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
        } catch (Exception e) {
            System.err.println("ERROR READING LINE: " + line);
            e.printStackTrace();
        }
        return null;
    }

    public RecordCollection loadGold(File file) {
        String line = "";
        try {
            ArrayList<Record>  records = new ArrayList<Record>(1000); // TODO: Maybe there is a way to get the number of lines easily?
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
            line = reader.readLine();
            int lineNo = 0;
            while (line != null) {
                String[] fields = line.split("\t");
                if (fields.length < 1) {
                    System.err.println("Error reading file " + file.getName() + " malformed line: " + line);
                } else {
                    Record rec = new Record(lineNo, new Source("Gold"), new Entity(fields[0]));
                    for (int i = 1; i < fields.length; i++) {
                        int j = i-1;
                        // Handle empty attributes
                        if (fields[i].length() > 0)
                            if (j < orderedAttributeNames.length) {
                                Attribute attrToAdd = getAttributeFromString(orderedAttributeNames[j], fields[i], attributeTypes[j]);
                                if (attrToAdd != null)
                                    rec.addAttribute(attrToAdd);
                            } else {
                                Attribute attrToAdd = getAttributeFromString(String.format("Attr%04d", j), fields[i], attributeTypes[j]);
                                if (attrToAdd != null)
                                    rec.addAttribute(attrToAdd);
                            }
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
        } catch (Exception e) {
            System.err.println("ERROR READING LINE: " + line);
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
        FloatAttribute flt = new FloatAttribute(name,rawValue);
        return (flt.isValidFloat() ? flt : null); 
    }
    
}
