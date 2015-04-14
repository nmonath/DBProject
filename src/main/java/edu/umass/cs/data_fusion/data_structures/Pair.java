package main.java.edu.umass.cs.data_fusion.data_structures;

public class Pair<T,U> {
    
    final public T one;
    final public U two;
    
    public Pair(T one, U two) {
        this.one = one;
        this.two = two;
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof Pair) && (one.equals(((Pair) obj).one) && two.equals(((Pair) obj).two));
    }

    @Override
    public int hashCode() {
        // Based on Scala's implementation of hashcodes for tuples? TODO: Is this ok?
        int hashCode = 2;
        if (one != null)
            hashCode = hashCode * 41 + one.hashCode();
        if (two != null)
            hashCode = hashCode * 41 + two.hashCode();
        return hashCode;
    }
}
