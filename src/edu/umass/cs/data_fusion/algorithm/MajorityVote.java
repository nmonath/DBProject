package edu.umass.cs.data_fusion.algorithm;


import edu.umass.cs.data_fusion.data_structures.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class MajorityVote {
    
    private String name = "MajorityVote";
    private Source source = new Source(this.name); //TODO: This would go in the parent class


    public RecordCollection performDataFusion(RecordCollection records) { // TODO Replace with ResultCollection
        Set<Entity> entities = records.getEntities();
        ArrayList<Record> resultingTable = new ArrayList<Record>(entities.size());
        for (Entity e: entities) {
            Record result = new Record(source,e);
            Set<String> attributeNames = records.getAttributes(e);
            ArrayList<Record> recordsForE = records.getRecords(e);
            for (String attrName : attributeNames) {
                result.addAttribute(getMajorityVote(getCount(recordsForE,attrName)));
            }
            resultingTable.add(result);
        }
        return new RecordCollection(resultingTable);
    }
    
    public HashMap<Attribute, Integer> getCount(ArrayList<Record> records, String attrName) {
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
    public Attribute getMajorityVote(HashMap<Attribute,Integer> votes) {
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
    
}
