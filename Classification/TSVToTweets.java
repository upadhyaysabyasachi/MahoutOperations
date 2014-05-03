
package com.Classification;

import java.io.BufferedReader;
import java.io.FileReader;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.SequenceFile;
import org.apache.hadoop.io.SequenceFile.Writer;
import org.apache.hadoop.io.Text;
//import org.apache.hadoop.io.file.tfile.TFile.Reader;

public class TSVToTweets {
	public static void main(String args[]) throws Exception {
		if (args.length != 2) {
			System.err.println("Arguments: [input tsv file] [output sequence file]");
			return;
		}
		String inputFileName = args[0];
		String outputDirName = args[1];
		Configuration configuration = new Configuration();
		FileSystem fs = FileSystem.get(configuration);
		Writer writer = new SequenceFile.Writer(fs, configuration, new Path(outputDirName + "/chunk-0"),
				Text.class, Text.class);
		int count = 0;
		BufferedReader reader = new BufferedReader(new FileReader(inputFileName));
		Text key = new Text();
		Text value = new Text();
		int id=0;
		while(true) {
			String line = reader.readLine();
			if (line == null) {
				break;
			}
			String[] tokens = line.split("\t", 3);
			if (tokens.length != 2) {
				System.out.println("Skip line: " + line);
				continue;
			}
			String category = tokens[0];
			//String id = tokens[1];
			String message = tokens[1];
			key.set("/" + category + "/" + id );
			id=id+1;
			value.set( message );
			writer.append(key, value);
			count++;
		}
		writer.close();
		System.out.println( "Total Tweets Count: " + count + "." );
		System.out.println( "Sucessfully created the sequence file at " + outputDirName );
	}
	
	
	
}