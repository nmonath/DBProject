package main.java.edu.umass.cs.data_fusion.load;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import main.java.edu.umass.cs.data_fusion.algorithm.TruthFinder;
import main.java.edu.umass.cs.data_fusion.data_structures.Entity;
import main.java.edu.umass.cs.data_fusion.data_structures.FloatAttribute;
import main.java.edu.umass.cs.data_fusion.data_structures.Record;
import main.java.edu.umass.cs.data_fusion.data_structures.RecordCollection;
import main.java.edu.umass.cs.data_fusion.data_structures.Source;
import main.java.edu.umass.cs.data_fusion.data_structures.StringAttribute;



public class LoadWeather {
	
	public static RecordCollection load(File file)
	{
		String line = "";
		try {
			ArrayList<Record>  records = new ArrayList<Record>(10000);
            
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
	        line = reader.readLine();
	        int lineNo = 0;
	        String lineCount = "Lines Read: " + lineNo;
	        while (line != null) {
                String[] fields = line.split("\t");
                
                Record rec = new Record(lineNo, new Source(fields[2]), new Entity("sameEntity"));

        		String s = fields[1];
        		if('w' == s.charAt(0))//this is categorical
        		{
        			String rawValue = s.substring(2, s.length());
        			String name = fields[0];
        			StringAttribute strAttr = new StringAttribute(name, rawValue);
                    rec.addAttribute(strAttr);

                    System.out.print(name+"\t" +rawValue+"\t" );
        			
        		}
        		else //continuous
        		{
        			String name = fields[0];
        			String rawValue = fields[1];
        			FloatAttribute floatAttr = new FloatAttribute(name, rawValue);
                    rec.addAttribute(floatAttr);

                    System.out.print(name+"\t" +rawValue+"\t" );
        		}
                records.add(rec);

                line = reader.readLine();
                lineNo += 1;
                
                if (lineNo == 10)
                	break;
	        }
            System.out.println("\n Done Loading.");
            return new RecordCollection(records);
		} catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("ERROR READING LINE: " + line);
            e.printStackTrace();
        }
		return null;
	}

	public static void main(String[] args) {
		RecordCollection collection = load(new File("/Users/Manuel/Documents/Development/github/DBProject/dataset/CRH/data/weather_data_set.txt"));
		TruthFinder tf = new TruthFinder();
        RecordCollection cleaner = tf.convert(tf.execute(collection));
        cleaner.writeToTSVFile(new File("outputTest.txt"));
	}

}
