package com.Recommendations;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.eval.RecommenderEvaluator;
import org.apache.mahout.cf.taste.impl.eval.AverageAbsoluteDifferenceRecommenderEvaluator;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.EuclideanDistanceSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

import org.apache.mahout.math.hadoop.similarity.cooccurrence.measures.PearsonCorrelationSimilarity;

public class BookRecommender2 {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException,TasteException {
		// TODO Auto-generated method stub

			DataModel model=new FileDataModel(new File("C:\\Users\\upadhs4\\Desktop\\Lab\\Data\\ratingslarge.csv"));
			UserSimilarity similarity=new EuclideanDistanceSimilarity(model);
			
			UserNeighborhood neighborhood=new NearestNUserNeighborhood(50, similarity, model);
			
			Recommender recommender=new GenericUserBasedRecommender(model, neighborhood, similarity);
			
			short userid= Short.parseShort("1");
			
			List<RecommendedItem> recommendations=recommender.recommend(userid,10);
			
			System.out.println( "Total number of recommendations are: "+ recommendations.size() );

			

			

			for( RecommendedItem recom : recommendations)
			{

				System.out.println("Books recommended for "+ userid +" is :"+ recom );

			}

			//System.out.println("Recommender is :\t"+recommender.);
			
		    
			RecommenderEvaluator evaluator=null;
			evaluator=new AverageAbsoluteDifferenceRecommenderEvaluator();
			
			EuclideanRecommender eRecommender= new EuclideanRecommender();
			
			
			
			
			
	}

}
