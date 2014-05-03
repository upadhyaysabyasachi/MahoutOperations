package com.Classification;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.List;

import org.apache.mahout.classifier.sgd.CsvRecordFactory;
import org.apache.mahout.classifier.sgd.LogisticModelParameters;
import org.apache.mahout.classifier.sgd.OnlineLogisticRegression;
import org.apache.mahout.math.SequentialAccessSparseVector;
import org.apache.mahout.math.Vector;

import com.google.common.base.Charsets;

public class LogisticRegression {
	
	public static void main(String[] args) throws Exception {
		if (args.length < 2) {
			System.out.println("Arguments: [modelpath] [input file]");
		return;
		}
		String modelPath = args[0];
		String inputPath = args[1];
		LogisticModelParameters lmp = LogisticModelParameters.loadFrom(new File(modelPath));
		List<String> tcat = lmp.getTargetCategories();
		int tcount = 0;
		while( tcount < tcat.size() )
		{
			System.out.println( "Category[" + tcount + "]:" + tcat.get( tcount ) );
			tcount++;
		}
		CsvRecordFactory csv = lmp.getCsvRecordFactory();
		OnlineLogisticRegression lr = lmp.createRegression();
		BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(new File(inputPath)), Charsets.UTF_8));
		String line = in.readLine();
		csv.firstLine(line);
		line = in.readLine();
		while( line != null )
		{
			Vector v = new SequentialAccessSparseVector( lmp.getNumFeatures() );
			// To process test data
			int targetv = csv.processLine(line, v, true );
			double score = lr.classifyScalar( v);
			System.out.println( "Expected: " + targetv + " --- Actual: "+ new DecimalFormat( "#.00000" ).format( score ) );
			line = in.readLine();
		}
	}
}