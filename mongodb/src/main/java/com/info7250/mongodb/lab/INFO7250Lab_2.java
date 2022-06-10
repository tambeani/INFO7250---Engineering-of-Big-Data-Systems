package com.info7250.mongodb.lab;

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

public class INFO7250Lab_2 implements Block<Document> {

	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub
		
		// Establish connection using modern client
		MongoClient client = MongoClients.create();
		
		// Connect to mongodb
		MongoDatabase lab_2 = client.getDatabase("lab_2");
		
		// Create/get collections
		MongoCollection<Document>  nyse_b = lab_2.getCollection("nyse_B");
		
		// Importing the csv file
		File nyse_csv = new File("C:\\Users\\18573\\Desktop\\BigData\\INFO7250---Engineering-of-Big-Data-Systems\\dataset","NYSE_daily_prices_A.csv");
		Scanner scanner = new Scanner(nyse_csv);
		
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
			currentRow.append("exchange", tokens[0]);
			currentRow.append("stock_symbol", tokens[1]);
			currentRow.append("date", tokens[2]);
			currentRow.append("stock_price_open", Double.parseDouble(tokens[3]));
			currentRow.append("stock_price_high", Double.parseDouble(tokens[4]));
			currentRow.append("stock_price_low", Double.parseDouble(tokens[5]));
			currentRow.append("stock_price_close", Double.parseDouble(tokens[6]));
			currentRow.append("stock_volume", Double.parseDouble(tokens[7]));
			currentRow.append("stock_price_adj_close", Double.parseDouble(tokens[8]));
			
			// Adding the row to the list of documents
			documents.add(currentRow);
			count++;
			
		}
		
		// Insert the list of documents to the collection
		nyse_b.insertMany(documents);
		System.out.print("Inserted rows: "+count);
		
		// Define printBlock for each iterable
		Block<Document> printBlock = new INFO7250Lab_2();
		
		// Define a pipeline for aggregation
		//List<Document> aggregated = 
		nyse_b.aggregate(
				Arrays.asList(
						Aggregates.group("$stock_symbol",Accumulators.avg("stock_avg", "$stock_price_open")),
						Aggregates.sort(Sorts.descending("stock_avg"))
						)
				).forEach(printBlock);
				//.into(new ArrayList<>());
		
		//lab_2.getCollection("stock_avg_collection").insertMany(aggregated);
		
		// Close the connection
		client.close();
	}

	@Override
	public void apply(Document t) {
		// TODO Auto-generated method stub
		System.out.println(t.toJson());
		
	}

}
