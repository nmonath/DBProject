package main.java.edu.umass.cs.data_fusion.data_structures.author;


import main.java.edu.umass.cs.data_fusion.data_structures.Attribute;
import main.java.edu.umass.cs.data_fusion.data_structures.AttributeDataType;
import main.java.edu.umass.cs.data_fusion.data_structures.AttributeType;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AuthorListAttribute extends Attribute {

    private Set<AuthorName> authors;
    
    public AuthorListAttribute(String name, String rawValue, AttributeType type) {
        super(name, rawValue);
        this.type = type;
        authors = getAuthorsGreedy(rawValue);
        dataType = AttributeDataType.AUTHOR_LIST;
    }

    public Set<AuthorName> getAuthors() {
        return authors;
    }

    public String addDelimeters(String listOfNames) {
        listOfNames = listOfNames.trim();
        Pattern punc = Pattern.compile("\\p{Punct}");
        // If there are no delimeters in the str
        boolean noDelim = !punc.matcher(listOfNames).find();
        if (!noDelim) {
            rawValue = listOfNames;
            return listOfNames;
        } else {
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
            } else if (split.length > 3)  {
                StringBuffer result = new StringBuffer(listOfNames.length());
                for (int i = 0; i < split.length; i += 1) {
                    result.append(split[i]).append(" | ");
                }
                rawValue = result.toString();
                return result.toString();
            } else {
                rawValue = listOfNames;
                return rawValue;
            }
                
        }
    }
    
    
    public Set<AuthorName> getAuthorsGreedy(String rawString) {
        
        boolean done = false;
        String string = rawString;
        Set<AuthorName> names = new HashSet<AuthorName>();
        
        while (!done && string.length() > 0) {
            AuthorNameRegex[] regexes = {new AuthorNameRegex1(), new AuthorNameRegex2(), new AuthorNameRegex3(), 
                    new AuthorNameRegex4(), new AuthorNameRegex5(), new AuthorNameRegex6(), new AuthorNameRegex7(),
                    new AuthorNameRegex8(), new AuthorNameRegex9(), new AuthorNameRegex10()};
            for (AuthorNameRegex reg : regexes) {
                reg.match(string);
            }
            Arrays.sort(regexes);
            AuthorName name = regexes[0].getName();
            if (name != null) {
                names.add(name);
                string = regexes[0].updatedString();
            } else 
                done = true;
        }
        return names;
    }

    public Set<AuthorName> getAuthorsNaive(String rawString) {
        String string = addDelimeters(rawString.toLowerCase());

        Set<AuthorName> names = new HashSet<AuthorName>();

        // Case 1: Last, First MI
        Pattern case1 = Pattern.compile("\\b[a-z]+(\\b)?,(\\s)?[a-z]+\\s[a-z](\\.)?");
        // Case 2: Last, First Middle
        Pattern case2 = Pattern.compile("\\b[a-z]+(\\b)?,(\\s)?[a-z]+\\s[a-z]+\\b");
        // Case 2: Last, First 
        Pattern case3 = Pattern.compile("\\b[a-z]+(\\b)?,(\\s)?[a-z]+\\b");
        // Case 3: First MI Last
        Pattern case4 = Pattern.compile("\\b[a-z]+\\s[a-z](\\.)?\\s[a-z]+");
        // Case 4: First Middle Last
        Pattern case5 = Pattern.compile("\\b[a-z]+\\s[a-z]+\\s[a-z]+");
        // Case 5: First Last
        Pattern case6 = Pattern.compile("\\b[a-z]+\\s[a-z]+");
        // Case 6: FI Last
        Pattern case7 = Pattern.compile("\\b[a-z](\\.)?\\s[a-z]+");
        // Case 7: Init1 Init2 Last
        Pattern case8 = Pattern.compile("\\b[a-z][\\s\\.][a-z][\\s\\.]([a-z](\\.)?)?\\s[a-z]+\\b");
        // Case 8: Single word
        Pattern case9 = Pattern.compile("\\b[a-z]+\\b");

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
                String[] splt2 = splt[1].trim().split("\\s");
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
                name.addName(AuthorNameType.LAST, res);
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

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof AuthorListAttribute) && ((AuthorListAttribute) obj).authors.equals(this.authors);
    }
    
    private abstract class AuthorNameRegex implements Comparable<AuthorNameRegex> {
        protected Pattern pattern;
        protected Matcher matcher;
        protected int start;
        protected int length;
        protected String string;
        final protected static int MAX_STRING_LENGTH = 1000000;
        
        public AuthorNameRegex(String regex) {
            pattern = Pattern.compile(regex);
        }
        
        public void match(String str) {
            assert str.length() < MAX_STRING_LENGTH;
            this.string = str.trim().toLowerCase();
            matcher = pattern.matcher(string);
            if (matcher.find()) {
                start = matcher.start();
                length = matcher.end() - start;
            } else {
                start = string.length();
                length = 0;
            }
        }
        
        public void clear() {
            matcher = null;
            start = Integer.MAX_VALUE;
            length = 0;
        }
        @Override
        public int compareTo(AuthorNameRegex o1) {
            return (this.start - o1.start)*MAX_STRING_LENGTH + (o1.length - this.length); // Sort ascending by starting position, break ties by picking the longer one
        }
        
        abstract public AuthorName getName();
        
        public String updatedString() {
            return string.substring(start + length);
        }
    }
    
    private class AuthorNameRegex1 extends AuthorNameRegex {
        public AuthorNameRegex1() {
            super("\\b[a-z]+(\\b)?,(\\s)?[a-z]+\\s[a-z](\\.)?\\b");
        }
        
        public AuthorName getName() {
            if (start < string.length()) {
                String res = string.substring(start, start + length);
                String[] splt = res.split(",");
                String last = splt[0];
                String[] splt2 = splt[1].trim().split("\\s");
                String first = splt2[0];
                String mi = splt2[1].substring(0, 1);
                AuthorName name = new AuthorName();
                name.addName(AuthorNameType.LAST, last);
                name.addName(AuthorNameType.FIRST, first);
                name.addName(AuthorNameType.MIDDLE_INIT, mi);
                return name;
            } 
            return null;
        }

    }
    
    private class AuthorNameRegex2 extends AuthorNameRegex {
        
        public AuthorNameRegex2() {
            super("\\b[a-z]+(\\b)?,(\\s)?[a-z]+\\s[a-z]+\\b");
        }
        
        public AuthorName getName() {
            if (start < string.length()) {
                String res = string.substring(start, start + length);
                String[] splt = res.split(",");
                String last = splt[0];
                String[] splt2 = splt[1].split("\\s");
                String first = splt2[0];
                String middle = splt2[1];
                AuthorName name = new AuthorName();
                name.addName(AuthorNameType.LAST, last);
                name.addName(AuthorNameType.FIRST, first);
                name.addName(AuthorNameType.MIDDLE, middle);
                return name;
            }
            return null;
        }
    }
    
    private class AuthorNameRegex3 extends AuthorNameRegex {
        
        public AuthorNameRegex3() {
            super("\\b[a-z]+(\\b)?,(\\s)?[a-z]+\\b");
        }

        public AuthorName getName() {
            if (start < string.length()) {
                String res = string.substring(start, start + length);
                String[] splt = res.split(",");
                String last = splt[0];
                String first = splt[1].trim();
                AuthorName name = new AuthorName();
                name.addName(AuthorNameType.LAST, last);
                name.addName(AuthorNameType.FIRST, first);
                return name;
            }
            return null;
        }
    }
    
    private class AuthorNameRegex4 extends AuthorNameRegex {
        
        public AuthorNameRegex4() {
            super("\\b[a-z]+\\s[a-z](\\.)?\\s[a-z]+\\b");
        }
        
        public AuthorName getName() {
            if (start < string.length()) {

                String res = string.substring(start, start + length);
                String[] splt = res.split("\\s");
                AuthorName name = new AuthorName();
                name.addName(AuthorNameType.FIRST, splt[0]);
                name.addName(AuthorNameType.MIDDLE_INIT, splt[1].substring(0, 1));
                name.addName(AuthorNameType.LAST, splt[2]);
                return name;
            }
            return null;
        }
    }
    
    private class AuthorNameRegex5 extends AuthorNameRegex {
        
        public AuthorNameRegex5() {
            super("\\b[a-z]+\\s[a-z]+\\s[a-z]+");
        }
        
        public AuthorName getName() {
            if (start < string.length()) {
                String res = string.substring(start, start + length);
                String[] splt = res.split("\\s");
                AuthorName name = new AuthorName();
                name.addName(AuthorNameType.FIRST, splt[0]);
                name.addName(AuthorNameType.MIDDLE, splt[1]);
                name.addName(AuthorNameType.LAST, splt[2]);
                return name;
            }
            return null;
        }
        
    }
    
    private class AuthorNameRegex6 extends AuthorNameRegex {
        
        public AuthorNameRegex6() {
            super("\\b[a-z]+\\s[a-z]+");
        }
        
        public AuthorName getName() {
            if (start < string.length()) {
                String res = string.substring(start, start + length);
                String[] splt = res.split("\\s");
                AuthorName name = new AuthorName();
                name.addName(AuthorNameType.FIRST, splt[0]);
                name.addName(AuthorNameType.LAST, splt[1]);
                return name;
            }
            return null;
        }
    }
    
    private class AuthorNameRegex7 extends AuthorNameRegex {
        
        public AuthorNameRegex7() {
            super("\\b[a-z](\\.)?\\s[a-z]+");
        }
        
        public AuthorName getName() {
            if (start < string.length()) {
                String res = string.substring(start, start + length);
                String[] splt = res.split("\\s");
                AuthorName name = new AuthorName();
                name.addName(AuthorNameType.FIRST_INIT, splt[0].substring(0, 1));
                name.addName(AuthorNameType.LAST, splt[1]);
                return name;
            }
            return null;
        }
    }
    
    private class AuthorNameRegex8 extends AuthorNameRegex {
        
        public AuthorNameRegex8() {
            super("\\b[a-z][\\s\\.](\\s)?[a-z][\\s\\.](\\s)?([a-z](\\.)?)?\\s[a-z]+\\b");
        }
        
        public AuthorName getName() {
            if (start < string.length()) {
                String res = string.substring(start, start + length).replace(".", " ");
                String[] splt = res.split("\\s+");
                AuthorName name = new AuthorName();
                name.addName(AuthorNameType.FIRST_INIT, splt[0].substring(0, 1));
                name.addName(AuthorNameType.MIDDLE_INIT, splt[1].substring(0, 1));
                name.addName(AuthorNameType.LAST, splt[2]);
                return name;
            }
            return null;
        }
    }

    private class AuthorNameRegex9 extends AuthorNameRegex {
        
        public AuthorNameRegex9() {
            super("\\b[a-z]+\\b");
        }
        
        public AuthorName getName() {
            if (start < string.length()) {
                String res = string.substring(start, start + length);
                AuthorName name = new AuthorName();
                name.addName(AuthorNameType.LAST, res);
                return name;
            }
            return null;
        }
    }

    private class AuthorNameRegex10 extends AuthorNameRegex {

        public AuthorNameRegex10() {
            super("\\b[a-z]+(\\b)?,(\\s)?[a-z][\\s\\.](\\s)?[a-z][\\s\\.](\\s)?\\b");
        }

        public AuthorName getName() {
            if (start < string.length()) {
                String res = string.substring(start, start + length);
                String[] splt = res.split(",");
                String last = splt[0];
                String[] splt2 = splt[1].replace("."," ").trim().split("\\s+");
                String first = splt2[0];
                String middle = splt2[1];
                AuthorName name = new AuthorName();
                name.addName(AuthorNameType.LAST, last);
                name.addName(AuthorNameType.FIRST_INIT, first.substring(0, 1));
                name.addName(AuthorNameType.MIDDLE_INIT, middle.substring(0, 1));
                return name;
            }
            return null;
        }
    }
}
