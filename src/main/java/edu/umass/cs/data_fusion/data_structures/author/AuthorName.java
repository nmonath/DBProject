package main.java.edu.umass.cs.data_fusion.data_structures.author;


import java.util.HashMap;
import java.util.Iterator;


public class AuthorName {

    public HashMap<String,AuthorNameType> spelling2type = new HashMap<String, AuthorNameType>();
    public HashMap<AuthorNameType,String> type2spelling = new HashMap<AuthorNameType, String>();
    
    public void addName(AuthorNameType type, String spelling) {
        spelling2type.put(spelling,type);
        type2spelling.put(type,spelling);
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        Iterator<AuthorNameType> types = type2spelling.keySet().iterator();
        if (types.hasNext()) {
            AuthorNameType next = types.next();
            sb.append(next.toString()).append(":").append(type2spelling.get(next));
        }
        while (types.hasNext()) {
            AuthorNameType next = types.next();
            sb.append(", ").append(next.toString()).append(":").append(type2spelling.get(next));
        }
        sb.append("]");
        return sb.toString();
    }
    
    @Override
    public boolean equals(Object obj) {
        return (obj instanceof AuthorName) && this.toString().equals(((AuthorName) obj).toString());
    }
    
    @Override
    public int hashCode() {
        return toString().hashCode();
    }

}
