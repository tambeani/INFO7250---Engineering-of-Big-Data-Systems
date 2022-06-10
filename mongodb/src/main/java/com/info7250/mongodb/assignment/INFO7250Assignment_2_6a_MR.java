package com.info7250.mongodb.assignment;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import org.bson.Document;

import com.mongodb.Block;
import com.mongodb.MapReduceCommand;
import com.mongodb.client.MapReduceIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Sorts;

public class INFO7250Assignment_2_6a_MR implements Block<Document> {

	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub

		// Establish connection using modern client
				MongoClient client = MongoClients.create();
				
				// Connect to mongodb
				MongoDatabase assignment_2 = client.getDatabase("logs");
				
				// Create/get collections
				MongoCollection<Document>  coll = assignment_2.getCollection("access");
							
				// Define printBlock for each iterable
				Block<Document> printBlock = new INFO7250Assignment_2_6a_MR();
							
				/*
				 * Q. Number of times any webpage was visited by the same IP address
				 */
				
				// Define the map function
				String map = "function(){"
							+ "emit(this.ip_address," + "{\"count\":1});"
						+ "}";
				
				// Define the reduce function
				String reduce = "function(key,values){"
						+ "var result = {\"count\":1};"
						+ "values.forEach("
							+ "function(value){"
								+"result.count += value.count;"
							+ "})"
						+ "}";
				
				// Execute map reduce
				MapReduceIterable<Document> result = coll.mapReduce(map,reduce);
				
				// Print the map reduce result
				for(Document doc:result) {
					System.out.println(doc.toJson());
				}
				
				// Close the connection
				client.close();
				
	}

	public void apply(Document t) {
		// TODO Auto-generated method stub
		System.out.println(t.toJson());
	}


}
