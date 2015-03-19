package edu.umass.cs.data_fusion.data_structures;


import java.lang.Object;
import java.lang.Override;
import java.lang.String;

public class Entity {

    private String identifier = "";

    public Entity(String identifier) {
        this.identifier = identifier;
    }

    @Override
    public int hashCode() {
        return identifier.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof Entity) && identifier.equals(((Entity) obj).identifier);
    }

    public String getIdentifier() {
        return identifier;
    }

    @Override
    public String toString() {
        return "Entity(identifier: " + identifier + ")";
    }
}
