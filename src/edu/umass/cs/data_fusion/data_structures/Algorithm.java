package edu.umass.cs.data_fusion.data_structures;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public abstract class Algorithm {
    private RecordCollection recordCollection;
    private Result result;
    private String name;
    
    protected Algorithm(String name) {
        this.name = name;
    }
    protected String getName() {
        return name;
    }
    
    public abstract ArrayList<Result> execute(RecordCollection recordCollection);

    protected Set<Source> sourcesWithValue(ArrayList<Record> records, Attribute value)  {
        Set<Source> sourcesWithValue = new HashSet<Source>();
        String attrName = value.getName();
        for (Record r : records) {
            if (r.hasAttribute(attrName))
                if (r.getAttribute(attrName).equals(value))
                    sourcesWithValue.add(r.getSource());
        }
        return sourcesWithValue;
    }

    protected Set<Attribute> valuesForAttribute(ArrayList<Record> records, String attributeName) {
        Set<Attribute> valuesForAttribute = new HashSet<Attribute>();
        for (Record r: records) {
            Attribute attr = r.getAttribute(attributeName);
            if (attr != null)
                valuesForAttribute.add(attr);
        }
        return valuesForAttribute;
    }
    
    protected int numberOfValuesProvidedBySource(RecordCollection collection, Source source) {
        ArrayList<Record> recordsForSource = collection.getRecords(source);
        int numValuesForSource = 0;
        for (Record r : recordsForSource) {
            numValuesForSource += r.getAttributes().size();
        }
        return numValuesForSource;
    }

}
