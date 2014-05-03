package com.Clustering;

//package com.bigdataleap.datascience.clustering.points;

import java.io.*;
import java.util.*;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapred.SequenceFileAsBinaryInputFormat;
import org.apache.mahout.clustering.Cluster;
import org.apache.mahout.clustering.classify.WeightedVectorWritable;
import org.apache.mahout.clustering.kmeans.KMeansDriver;
import org.apache.mahout.clustering.kmeans.Kluster;
import org.apache.mahout.common.distance.EuclideanDistanceMeasure;
import org.apache.mahout.math.*;
import org.apache.mahout.math.Vector;

public class ClusteringPoints {

            public static final double[][] points = { { 1, 1 }, { 2, 1 }, { 1, 2 },
                                    { 2, 2 }, { 3, 3 }, { 8, 8 }, { 9, 8 }, { 8, 9 }, { 9, 9 } };

            public static void writePointsToFile(List<Vector> points, String fileName,
                                    FileSystem fs, Configuration conf) throws IOException {
                        
                        Path path = new Path(fileName);
                        
                        SequenceFile.Writer writer = new SequenceFile.Writer(fs, conf, path,
                                                LongWritable.class, VectorWritable.class);
                        
                        long recNum = 0;
                        
                        VectorWritable vec = new VectorWritable();
                        
                        for (Vector point : points) {
                                    vec.set(point);
                                    writer.append(new LongWritable(recNum++), vec);
                        }
                        
                        writer.close();
            }

            public static List<Vector> getPoints(double[][] raw) {
                        
                        List<Vector> points = new ArrayList<Vector>();
                        
                        for (int i = 0; i < raw.length; i++) {
                                    double[] fr = raw[i];
                                    Vector vec = new RandomAccessSparseVector(fr.length);
                                    vec.assign(fr);
                                    points.add(vec);
                        }
                        
                        return points;
            }
            
            public static void main(String args[]) throws Exception{
            	
            	int k=2;
            	
            	List<Vector> vectors=getPoints(points);
            	
            	File testData = new File("pcluster");
            	
            	if(!testData.exists()){
            		testData.mkdir();
            	}
            	
            	testData = new File("pcluster/points");
            	
            	Configuration conf=new Configuration();
            	
            	FileSystem fs = FileSystem.get(conf);
            	
            	writePointsToFile(vectors, "pcluster/points/pfile", fs, conf);
            	
            	Path path = new Path("pcluster/cfile/part-r-00000");
            	
            	SequenceFile.Writer writer = new SequenceFile.Writer(fs, conf, path, Text.class, Kluster.class);
            	
            	for (int i=0; i<k; i++){
            		Vector vec=vectors.get(i);
            		Kluster cluster= new Kluster(vec,i,new EuclideanDistanceMeasure());
            		writer.append(new Text((cluster).getIdentifier()), cluster);
            		
            	}
            	
            	writer.close();
            	
            	KMeansDriver.run(conf, new Path("pcluster/points"), new Path("pcluster/cfile"), new Path("pcluster/output"), 
            			new EuclideanDistanceMeasure(), 0.001, 10, true, 0.5, true);
            	SequenceFile.Reader reader = new SequenceFile.Reader(fs, new Path("pcluster/output/" + Cluster.CLUSTERED_POINTS_DIR +"/part-m-00000"), conf);


            	IntWritable key = new IntWritable();


            	WeightedVectorWritable value = new WeightedVectorWritable();


            	while(reader.next(key,value)){

            	System.out.println(value.toString() + " belongs to cluster " + key.toString());

            	}


            	reader.close();

            	
            }

            
}


