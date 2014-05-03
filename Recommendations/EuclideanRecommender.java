package com.Recommendations;
import java.io.File;
import java.io.IOException;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.eval.RecommenderBuilder;
import org.apache.mahout.cf.taste.eval.RecommenderEvaluator;
import org.apache.mahout.cf.taste.impl.eval.AverageAbsoluteDifferenceRecommenderEvaluator;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.ThresholdUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.EuclideanDistanceSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;


public class EuclideanRecommender implements RecommenderBuilder {

       /**
       * @param args
       */
       
       @Override
       public Recommender buildRecommender(DataModel model) throws TasteException {
              
              
              // TODO Auto-generated method stub
              
              UserSimilarity similarity = new EuclideanDistanceSimilarity(model);
              UserNeighborhood neighborhood = new ThresholdUserNeighborhood(.7,similarity,model);
              return new GenericUserBasedRecommender(model,neighborhood,similarity);
              
       }


       public static void main(String[] args) throws IOException, TasteException {
              // TODO Auto-generated method stub
              
              RecommenderEvaluator evaluator = null;
              evaluator = new AverageAbsoluteDifferenceRecommenderEvaluator();
              
              DataModel model = new FileDataModel(new File("C:\\Users\\upadhs4\\Desktop\\Lab\\Data\\ratingslarge.csv"));
              EuclideanRecommender eRecommender = new EuclideanRecommender();
              
              
              eRecommender.buildRecommender(model);
              double score = evaluator.evaluate(eRecommender, null, model, 0.95, 0.05);
              System.out.println(score);

       }
       
}
