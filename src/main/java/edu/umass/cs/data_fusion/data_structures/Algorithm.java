package main.java.edu.umass.cs.data_fusion.data_structures;

import java.util.*;

public abstract class Algorithm {
    protected String name;
    protected Source source;
    
    protected Algorithm(String name) {
        this.name = name;
        this.source = new Source(name);
    }
    protected String getName() {
        return name;
    }
    
    public abstract ArrayList<Result> execute(RecordCollection recordCollection);

    protected Set<Source> sourcesWithValue(List<Record> records, Attribute value)  {
        Set<Source> sourcesWithValue = new HashSet<Source>();
        String attrName = value.getName();
        for (Record r : records) {
            if (r.hasAttribute(attrName))
                if (r.getAttribute(attrName).equals(value))
                    sourcesWithValue.add(r.getSource());
        }
        return sourcesWithValue;
    }

    protected Set<Attribute> valuesForAttribute(List<Record> records, String attributeName) {
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

    public RecordCollection convert(ArrayList<Result> results) {
        ArrayList<Record> rec = new ArrayList<Record>(results.size());
        for (Result r : results) {
            rec.add(new Record(r.getSource(),r.getEntity(),r.getAttributes()));
        }
        return new RecordCollection(rec);
    }
    
    public String toString() {
        return "Algorithm(" + name + ")";
    }
    
    public String infoString(RecordCollection collection) {
        return "[Algorithm] Running " + this + " on RecordCollection with " + collection.getEntitiesCount() + " entities, each with ~" + collection.getAttributes().size() + " attributes.";
    }

    public HashMap<Attribute, Integer> getCount(List<Record> records, String attrName) {
        HashMap<Attribute,Integer> votes = new HashMap<Attribute, Integer>();
        for (Record r: records) {
            if (r.hasAttribute(attrName)) {
                Attribute a = r.getAttribute(attrName);
                if (!votes.containsKey(a)) {
                    votes.put(a,0);
                }
                votes.put(a,votes.get(a)+1);
            }
        }
        return votes;
    }

    // TODO: Is there a faster implementation?
    public Attribute getMajorityVote(Map<Attribute,Integer> votes) {
        int max = -1;
        Attribute maxAttr = null;
        for (Attribute a : votes.keySet()) {
            int aCount = votes.get(a);
            if (aCount > max) {
                max = aCount;
                maxAttr = a;
            }
        }
        return maxAttr;
    }

    public HashMap<Attribute, Float> getWeightedCount(List<Record> records, String attrName, Map<Source,Float> weights) {
        HashMap<Attribute,Float> votes = new HashMap<Attribute, Float>();
        for (Record r: records) {
            if (r.hasAttribute(attrName)) {
                Attribute a = r.getAttribute(attrName);
                if (!votes.containsKey(a)) {
                    votes.put(a,0.0f);
                }
                votes.put(a,votes.get(a)+ weights.get(r.getSource()));
            }
        }
        return votes;
    }

    public Attribute getMajorityWeightedVote(Map<Attribute,Float> votes) {
        float max = Float.MIN_VALUE;
        Attribute maxAttr = null;
        for (Attribute a : votes.keySet()) {
            float aCount = votes.get(a);
            if (aCount > max) {
                max = aCount;
                maxAttr = a;
            }
        }
        return maxAttr;
    }

}
