package com.info7250.mongodb.assignment;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import org.bson.Document;

import com.mongodb.Block;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Sorts;

public class INFO7250Assignment_2_6b_AGG implements Block<Document> {

	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub

		// Establish connection using modern client
				MongoClient client = MongoClients.create();
				
				// Connect to mongodb
				MongoDatabase assignment_2 = client.getDatabase("logs");
				
				// Create/get collections
				MongoCollection<Document>  coll = assignment_2.getCollection("access");
							
				// Define printBlock for each iterable
				Block<Document> printBlock = new INFO7250Assignment_2_6b_AGG();
				
				// Define a pipeline for aggregation
				//List<Document> aggregated = 
				coll.aggregate(
						Arrays.asList(
								Aggregates.group("$month",Accumulators.sum("times_vistited", 1)),
								Aggregates.sort(Sorts.descending("times_vistited"))
								)
						).forEach(printBlock);
						//.into(new ArrayList<>());
				
				//lab_2.getCollection("stock_avg_collection").insertMany(aggregated);
				
				// Close the connection
				client.close();
				
	}

	public void apply(Document t) {
		// TODO Auto-generated method stub
		System.out.println(t.toJson());
	}


}
