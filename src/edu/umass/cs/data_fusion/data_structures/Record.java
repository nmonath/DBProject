package edu.umass.cs.data_fusion.data_structures;


import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.util.HashMap;
import java.util.Random;

public class Record {

    private int id;
    private Source source;
    private Entity entity;
    private HashMap<String,Attribute> attributes;
    
    public Record(Source source, Entity entity) {
        this(new Random().nextInt(), source,entity,new HashMap<String, Attribute>());
    }
    
    public Record(int id, Source source, Entity entity) {
       this(id, source,entity,new HashMap<String, Attribute>());
    }

    public Record(int id, Source source, Entity entity, HashMap<String, Attribute> attributes) {
        this.id = id;
        this.source = source;
        this.entity = entity;
        this.attributes = attributes;
    }

    public Record(Source source, Entity entity, HashMap<String, Attribute> attributes) {
        this(new Random().nextInt(), source, entity, attributes);
    }
    
    public Record(Source source, Entity entity, Iterable<Attribute> attributes) {
        this(new Random().nextInt(), source, entity, attributes);
    }
    
    public Record(int id,Source source, Entity entity, Iterable<Attribute> attributes) {
        this.id = id;
        this.source = source;
        this.entity = entity;
        this.attributes = new HashMap<String, Attribute>();
        for (Attribute a: attributes) {
            this.addAttribute(a);
        }
    }


    @Override
    public int hashCode() {
        return this.toString().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof Record) && this.id == ((Record) obj).getId() && this.source.equals(((Record) obj).getSource()) && this.entity.equals(((Record) obj).getEntity());
    }

    public int getId() {
        return id;
    }

    public Source getSource() {
        return source;
    }

    public Entity getEntity() {
        return entity;
    }
    
    public void addAttribute(Attribute attr) {
        attributes.put(attr.getName(),attr);
    }
    
    public HashMap<String,Attribute> getAttributes() {
        return attributes;
    }
    
    public int getNumAttributes(){
    	return attributes.size();
    }
    
    public boolean hasAttribute(String attrName) { return attributes.containsKey(attrName);}
    
    public Attribute getAttribute(String attrName) { return attributes.get(attrName);}

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(100);
        sb.append("Record(");
        sb.append(this.source.toString());
        sb.append(", ");
        sb.append(this.entity.toString());
        for (Attribute a : attributes.values()) {
            sb.append(", ");
            sb.append(a.toString());
        }
        sb.append(")");
        return sb.toString();
    }
}
