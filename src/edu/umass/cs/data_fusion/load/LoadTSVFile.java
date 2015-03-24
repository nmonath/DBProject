package edu.umass.cs.data_fusion.load;


import edu.umass.cs.data_fusion.algorithm.MajorityVote;
import edu.umass.cs.data_fusion.data_structures.*;

import java.io.*;
import java.util.ArrayList;

public class LoadTSVFile {


    private ArrayList<String> orderedAttributeNames;
    
    public LoadTSVFile() {
        orderedAttributeNames = new ArrayList<String>();
    }
    
    public LoadTSVFile(ArrayList<String> orderedAttributeNames) {
        this.orderedAttributeNames = orderedAttributeNames;
    }

    public RecordCollection load(File file) {
        try {
            ArrayList<Record>  records = new ArrayList<Record>(1000); // TODO: Maybe there is a way to get the number of lines easily?
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
            String line = reader.readLine();
            int lineNo = 0;
            while (line != null) {
                String[] fields = line.split("\t");
                if (fields.length < 2) {
                    System.err.println("Error reading file " + file.getName() + " malformed line: " + line);
                } else {
                    Record rec = new Record(lineNo, new Source(fields[0]), new Entity(fields[1]));
                    for (int i = 2; i < fields.length; i++) {
                        int j = i-2;
                        if (j < orderedAttributeNames.size())
                            rec.addAttribute(new Attribute(orderedAttributeNames.get(j),fields[i]));
                        else
                            rec.addAttribute(new Attribute(String.format("Attr%04d",j),fields[i]));
                    }
                    records.add(rec);
                }
                line = reader.readLine();
                lineNo += 1;
            }
            return new RecordCollection(records);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public static void main (String[] args) {
        LoadTSVFile loader = new LoadTSVFile();
        RecordCollection recordCollection = loader.load(new File(args[0]));
        MajorityVote mv = new MajorityVote();
        RecordCollection clean = mv.performDataFusion(recordCollection);
        clean.writeToTSVFile(new File(args[1]));
    }
}
