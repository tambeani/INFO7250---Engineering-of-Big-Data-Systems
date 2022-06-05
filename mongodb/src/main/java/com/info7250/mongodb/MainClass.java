package com.info7250.mongodb;

import org.bson.BSON;
import org.bson.Document;

//import com.mongodb.MongoClient;

public class MainClass {

	public static void main(String[] args) {
		// Step 1 - Connect to database
		
		// Legacy driver
		com.mongodb.MongoClient legacyClient = new com.mongodb.MongoClient();
		
		// Modern driver
		com.mongodb.client.MongoClient modernClient = com.mongodb.client.MongoClients.create();
		
		/* *************************************************************************************
		 * Connection URI 
		 * 
		 * The connection URI specifies the set of instructions for the driver to connect to the 
		 * MongoDB deployment. Following structure is applied to a valid URI
		 * 
		 * mongodb://user:pass@sample.host:27017/<connection options>
		 * *************************************************************************************
		 */
		
		// Step 2 - Select the DB
		com.mongodb.client.MongoDatabase db = modernClient.getDatabase("nyse_lab");
		
		// Step 3 - Select the collection
		com.mongodb.client.MongoCollection<Document> coll = db.getCollection("nsye_A");
		
		/* *************************************************************************************
		 * 
		 * BSON - Binary JSON
		 * 
		 * This data format includes all JSON data structure types including date,different sized
		 * integers, objectids, binary data.
		 * 
		 * Its not human-readable, thus must be converted to JSON. The mongodb driver allows us to
		 * work using the BSON interface by using anyone of the following implementations,
		 * 
		 * 1. Document
		 * 2. BsonDocument
		 * 
		 * and many more.
		 * *************************************************************************************
		 */
		
		// Step 4 - Create a document
		Document doc = new Document();
		doc.append("fname", "Aniruddha");
		
		/* *************************************************************************************
		 * 
		 * 	Document
		 * 
		 *  Its an implementation of the interface BSON.
		 *  
		 *  Constructors
		 *  1. Document() - Creates an empty document instance
		 *  2. Document(String key,String value) - Creates a document with given key/pair
		 *  3. Document(Map<String,Object> map) - Creates a document with given map
		 * 
		 * *************************************************************************************
		 */
		// Step 5 - Insert document into collection
		//coll.insertOne(doc);
		
		// Step 6 - Retrieve the document from the collection
		Document ret = coll.find().first();
		System.out.print(ret.toJson());
				
	}

}
