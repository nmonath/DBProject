package main.java.edu.umass.cs.data_fusion.data_structures.author;


import main.java.edu.umass.cs.data_fusion.data_structures.Attribute;
import main.java.edu.umass.cs.data_fusion.data_structures.AttributeType;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AuthorListAttribute extends Attribute {

    private Set<AuthorName> authors;
    
    public AuthorListAttribute(String name, String rawValue, AttributeType type) {
        super(name, rawValue);
        this.type = type;
        authors = getAuthors(rawValue);
    }

    public String addDelimeters(String listOfNames) {
        Pattern punc = Pattern.compile("\\p{Punct}");
        // If there are no delimeters in the str
        boolean noDelim = !punc.matcher(listOfNames).find();
        if (!noDelim)
            return listOfNames;
        else {
            String[] split = listOfNames.split("\\s");
            // The main question here is where to split up into groups of 1, 2 or 3
            // Here is what we do:
            // 1. If there are an odd number of names, delim each one
            // 2. If there are a multiple of 2 names, delim each as 1st and last
            if (split.length % 2 == 0) {
                StringBuffer result = new StringBuffer(listOfNames.length());
                for (int i = 0; i < split.length; i += 2) {
                    result.append(split[i]).append(" ").append(split[i+1]).append(" | ");
                }
                return result.toString();
            } else  {
                StringBuffer result = new StringBuffer(listOfNames.length());
                for (int i = 0; i < split.length; i += 1) {
                    result.append(split[i]).append(" | ");
                }
                return result.toString();
            }
        }
    }

    public Set<AuthorName> getAuthors(String rawString) {
        String string = addDelimeters(rawString);

        Set<AuthorName> names = new HashSet<AuthorName>();

        // Case 1: Last, First MI
        Pattern case1 = Pattern.compile("\\b[a-zA-Z]+(\\b)?,(\\s)?[a-zA-Z]+\\s[a-zA-Z](\\.)?\\b");
        // Case 2: Last, First Middle
        Pattern case2 = Pattern.compile("\\b[a-zA-Z]+(\\b)?,(\\s)?[a-zA-Z]+\\s[a-zA-Z]+\\b");
        // Case 2: Last, First 
        Pattern case3 = Pattern.compile("\\b[a-zA-Z]+(\\b)?,(\\s)?[a-zA-Z]+\\b");
        // Case 3: First MI Last
        Pattern case4 = Pattern.compile("\\b[a-zA-Z]+\\s[a-zA-Z](\\.)?\\s[a-zA-Z]+");
        // Case 4: First Middle Last
        Pattern case5 = Pattern.compile("\\b[a-zA-Z]+\\s[a-zA-Z]+\\s[a-zA-Z]+");
        // Case 5: First Last
        Pattern case6 = Pattern.compile("\\b[a-zA-Z]+\\s[a-zA-Z]+");
        // Case 6: FI Last
        Pattern case7 = Pattern.compile("\\b[a-zA-Z](\\.)?\\s[a-zA-Z]+");
        // Case 7: Init1 Init2 Last
        Pattern case8 = Pattern.compile("\\b[a-zA-Z][\\s\\.][a-zA-Z][\\s\\.]([a-zA-Z](\\.)?)?\\s[a-zA-Z]+");
        // Case 8: Single word
        Pattern case9 = Pattern.compile("\\b[a-zA-Z]\\b");

        // conditions for being done 1) No matches found, 2) reached end of the string
        boolean done = false;
        while (!done) {
            Matcher m1 = case1.matcher(string);
            Matcher m2 = case2.matcher(string);
            Matcher m3 = case3.matcher(string);
            Matcher m4 = case4.matcher(string);
            Matcher m5 = case5.matcher(string);
            Matcher m6 = case6.matcher(string);
            Matcher m7 = case7.matcher(string);
            Matcher m8 = case8.matcher(string);
            Matcher m9 = case9.matcher(string);
            

            if (m1.find()) {
                String res = string.substring(m1.start(), m1.end());
                if (m1.end() == string.length())
                    done = true;
                String[] splt = res.split(",");
                String last = splt[0];
                String[] splt2 = splt[1].split("\\s");
                String first = splt2[0];
                String mi = splt2[1].substring(0, 1);
                AuthorName name = new AuthorName();
                name.addName(AuthorNameType.LAST, last);
                name.addName(AuthorNameType.FIRST, first);
                name.addName(AuthorNameType.MIDDLE_INIT, mi);
                names.add(name);
                string = string.substring(m1.end());
            } else if (m2.find()) {
                String res = string.substring(m2.start(), m2.end());
                if (m2.end() == string.length())
                    done = true;
                String[] splt = res.split(",");
                String last = splt[0];
                String[] splt2 = splt[1].split("\\s");
                String first = splt2[0];
                String middle = splt2[1];
                AuthorName name = new AuthorName();
                name.addName(AuthorNameType.LAST, last);
                name.addName(AuthorNameType.FIRST, first);
                name.addName(AuthorNameType.MIDDLE, middle);
                names.add(name);
                string = string.substring(m2.end());
            } else if (m3.find()) {
                String res = string.substring(m3.start(), m3.end());
                if (m3.end() == string.length())
                    done = true;
                String[] splt = res.split(",");
                String last = splt[0];
                String first = splt[1].trim();
                AuthorName name = new AuthorName();
                name.addName(AuthorNameType.LAST, last);
                name.addName(AuthorNameType.FIRST, first);
                names.add(name);
                string = string.substring(m3.end());
            } else if (m4.find()) {
                String res = string.substring(m4.start(), m4.end());
                if (m4.end() == string.length())
                    done = true;
                String[] splt = res.split("\\s");
                AuthorName name = new AuthorName();
                name.addName(AuthorNameType.FIRST, splt[0]);
                name.addName(AuthorNameType.MIDDLE_INIT, splt[1].substring(0, 1));
                name.addName(AuthorNameType.LAST, splt[2]);
                names.add(name);
                string = string.substring(m4.end());
            } else if (m5.find()) {
                String res = string.substring(m5.start(), m5.end());
                if (m5.end() == string.length())
                    done = true;
                String[] splt = res.split("\\s");
                AuthorName name = new AuthorName();
                name.addName(AuthorNameType.FIRST, splt[0]);
                name.addName(AuthorNameType.MIDDLE, splt[1]);
                name.addName(AuthorNameType.LAST, splt[2]);
                names.add(name);
                string = string.substring(m5.end());
            } else if (m6.find()) {
                String res = string.substring(m6.start(), m6.end());
                if (m6.end() == string.length())
                    done = true;
                String[] splt = res.split("\\s");
                AuthorName name = new AuthorName();
                name.addName(AuthorNameType.FIRST, splt[0]);
                name.addName(AuthorNameType.LAST, splt[1]);
                names.add(name);
                string = string.substring(m6.end());
            } else if (m7.find()) {
                String res = string.substring(m7.start(), m7.end());
                if (m7.end() == string.length())
                    done = true;
                String[] splt = res.split("\\s");
                AuthorName name = new AuthorName();
                name.addName(AuthorNameType.FIRST_INIT, splt[0].substring(0, 1));
                name.addName(AuthorNameType.LAST, splt[1]);
                names.add(name);
                string = string.substring(m7.end());
            } else if (m8.find()) {
                String res = string.substring(m8.start(), m8.end());
                if (m8.end() == string.length())
                    done = true;
                String[] splt = res.split("\\s");
                AuthorName name = new AuthorName();
                name.addName(AuthorNameType.FIRST_INIT, splt[0].substring(0, 1));
                name.addName(AuthorNameType.MIDDLE_INIT, splt[1].substring(0, 1));
                name.addName(AuthorNameType.LAST, splt[2]);
                names.add(name);
                string = string.substring(m6.end());
            } else if (m9.find()) {
                String res = string.substring(m9.start(), m9.end());
                if (m9.end() == string.length())
                    done = true;
                AuthorName name = new AuthorName();
                name.addName(AuthorNameType.UNKNOWN, res);
                names.add(name);
                string = string.substring(m9.end());
            } else {
                done = true;
            }
        }
        if (names.isEmpty()) {
            System.err.println("[AuthorListAttribute] Couldn't parse: " + rawString);
        }
        return names;
    }

    @Override
    public int hashCode() {
        return this.toString().hashCode();
    }
    
    @Override
    public String toString() {
        StringBuilder stringBuffer = new StringBuilder();
        Iterator<AuthorName> authorNameIterator = authors.iterator();
        if (authorNameIterator.hasNext())
            stringBuffer.append(authorNameIterator.next().toString());
        while(authorNameIterator.hasNext())
            stringBuffer.append(", ").append(authorNameIterator.next());
        return stringBuffer.toString();
    }
    
    
    public static void main(String[] args) {

        AuthorListAttribute a = new AuthorListAttribute("blah", "tea", AttributeType.CATEGORICAL);

        String[] names = {"Dan Farmer Wietse Venema", "Farmer, Dan; Venema, Wietse", "Farmer, Dan, Venema, Wietse", "Farmer, Dan Venema, Wietse"};
        for (String n : names) {
            Set<AuthorName> ns = a.getAuthors(n);
            System.out.println(ns);
        }
    }

}
