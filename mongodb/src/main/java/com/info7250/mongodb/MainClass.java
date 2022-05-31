package com.info7250.mongodb;

import org.bson.Document;

//import com.mongodb.MongoClient;

public class MainClass {

	public static void main(String[] args) {
		// Step 1 - Connect to database
		
		// Legacy driver
		com.mongodb.MongoClient legacyClient = new com.mongodb.MongoClient();
		
		// Modern driver
		com.mongodb.client.MongoClient modernClient = com.mongodb.client.MongoClients.create();
		
		// Step 2 - Select the DB
		com.mongodb.client.MongoDatabase db = modernClient.getDatabase("nyse_lab");
		
		// Step 3 - Select the collection
		com.mongodb.client.MongoCollection<Document> coll = db.getCollection("nsye_A");
		
		// Step 4 - Create a document
		Document doc = new Document();
		doc.append("fname", "Aniruddha");
		
		// Step 5 - Insert document into collection
		coll.insertOne(doc);
		
		
	}

}
