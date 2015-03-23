package edu.umass.cs.data_fusion.data_structures;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

// I didn't see this on our list of things to implement, but I thought it was something we had talked about?
// I think it might be useful in implementing the algorithms.
public class RecordCollection {
    
    private ArrayList<Record> records;
    
    private HashMap<Source,ArrayList<Record>> source2records;
    
    private HashMap<Entity,ArrayList<Record>> entity2records;
    
    public RecordCollection(ArrayList<Record> records) {
        this.records = records;
        source2records = new HashMap<Source,ArrayList<Record>>();
        entity2records = new HashMap<Entity,ArrayList<Record>>();
        for (Record r: this.records) {
            if (!source2records.containsKey(r.getSource()))
                source2records.put(r.getSource(),new ArrayList<Record>()); //TODO: What should we set as an initial estimate for the size of this arraylist?
            source2records.get(r.getSource()).add(r);
            if (!entity2records.containsKey(r.getEntity()))
                entity2records.put(r.getEntity(),new ArrayList<Record>());
            entity2records.get(r.getEntity()).add(r);
        }
    }
    
    public ArrayList<Record> getRecords() {
        return records;
    }
    
    public ArrayList<Record> getRecords(Source source) {
        if (source2records.containsKey(source))
            return source2records.get(source);
        else
            return new ArrayList<Record>(); // TODO: Do we want to return an empty array list or null?
    }
    
    public ArrayList<Record> getRecords(Entity entity) {
        if (entity2records.containsKey(entity))
            return entity2records.get(entity);
        else
            return new ArrayList<Record>();// TODO: Do we want to return an empty array list or null?
    }
    
    public Set<Entity> getEntities() {
        return entity2records.keySet();
    }
    
    public Set<Source> getSources() {
        return  source2records.keySet();
    }
    
}
