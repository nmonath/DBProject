package main.java.edu.umass.cs.data_fusion.data_structures;


// We can change this around with Manuel's code, but for now I added this in
public enum AttributeType {
    CONTINUOUS,CATEGORICAL;

    public static AttributeType fromString(String str) {
        if (str.equalsIgnoreCase("continuous"))
            return CONTINUOUS;
        else if (str.equalsIgnoreCase("categorical"))
            return CATEGORICAL;
        else
            return null;
    }
}
