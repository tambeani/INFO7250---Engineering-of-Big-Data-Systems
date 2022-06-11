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
import com.mongodb.client.model.Indexes;
import com.mongodb.client.model.Sorts;

public class INFO7250Assignment_2_6_DB_Import implements Block<Document> {

	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub
		
		// Establish connection using modern client
		MongoClient client = MongoClients.create();
		
		// Connect to mongodb
		MongoDatabase logs = client.getDatabase("logs");
		
		// Create/get collections
		MongoCollection<Document>  access = logs.getCollection("access");
		
		// Importing the csv file
		File fileOpen = new File(".","access.csv");
		Scanner scanner = new Scanner(fileOpen);
		
		// Adding a new line to list documents
		List<Document> documents = new ArrayList<Document>();
		
		// Looping through the rows of the csv file
		int count = 0;
		while(scanner.hasNext()) {
						
			// Create a new document for each row to be added
			Document currentRow = new Document(); 
			
			// Get each line of the csv file
			String line = scanner.nextLine();
			
			// Split the line based on the token of ","
			String[] tokens = line.split(",");
			
			if(tokens[0].equals("exchange")) {
				//Skipping the headers line
				continue;
			}
			
			// Adding attributes & values to the row document
			currentRow.append("ip_address", tokens[0]);
			currentRow.append("time_stamp", tokens[1]);
			currentRow.append("url", tokens[2]);
			currentRow.append("protocol", Double.parseDouble(tokens[3]));
			currentRow.append("response", tokens[4]);
			currentRow.append("response_time", tokens[5]);
			
			// Adding the row to the list of documents
			documents.add(currentRow);
			count++;
		}
		
		// Insert the list of documents to the collection
		System.out.println("Inserted rows: "+count);
		
		// Define printBlock for each iterable
		Block<Document> printBlock = new INFO7250Assignment_2_6_DB_Import();
		
		// Close the connection
		client.close();
	}

	public void apply(Document t) {
		// TODO Auto-generated method stub
		System.out.println(t.toJson());
		
	}

}