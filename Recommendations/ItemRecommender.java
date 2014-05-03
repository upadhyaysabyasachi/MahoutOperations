package com.Recommendations;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

import org.apache.mahout.math.hadoop.similarity.cooccurrence.measures.PearsonCorrelationSimilarity;

public class ItemRecommender {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException,TasteException {
		// TODO Auto-generated method stub

			DataModel model=new FileDataModel(new File("C:\\Users\\upadhs4\\Desktop\\Lab\\Data\\ratingslarge.csv"));
			
			ItemSimilarity similarity=new org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity(model);
			
			System.out.println("sdsdsd"+similarity.itemSimilarity(112L, 262L));
			
			GenericItemBasedRecommender iRecommender=new GenericItemBasedRecommender(model, similarity);
			
			List<RecommendedItem> recommendations = iRecommender.recommend(1L, 10);
			
			for(RecommendedItem recom : recommendations){
				
				System.out.println("Books recommended for"+1L+"are :" + recom);
			}
			
			
			
			
			
			
			
	}

}
