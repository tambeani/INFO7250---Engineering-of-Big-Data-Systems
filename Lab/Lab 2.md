# INFO 7250 - Lab 2 - MongoCompass & Driver API

## Install Mongo Compass

**MongoCompass:**

It the user-interface for interacting with MongoDB in the backend. Its similar to workbench on MySQL db.

**Indexing:**

Helps us uniquely identify each row in a database. The index can help achieve the "unique" property in a collection.

**Used for:**

1. Create a database
2. Importing .csv files
3. Analyze functionality

## Import movielens 1m dataset

Download dataset - https://files.grouplens.org/datasets/movielens/ml-1m.zip<br/>
 
a. Change the ratings.dat file into ratings.csv<br/>
b. Add headers for all columns<br/>
c. Change the datatype of rating to number<br/>
d. Add a new database as "movie_lens"<br/>
e. Add a new collection as "ratings"<br/>

## Define a pipeline for performing below aggregations:

1. Find the average rating per movie_id:

&emsp;key: movie_id<br/>
&emsp;value: rating

&emsp;Define below aggregator in $group stage in MongoCompass:
  ```
  {
    _id: "$movie_id",
    rating_avg: {
      $avg: "$rating"
    }
  }
  ```

2. Sort the rating in descending order: 

&emsp;Define below stage:
```
{
  rating_avg: -1
}
```

3. Filter only movies with average rating greater than equal to 4:

```
{
  rating_avg: {$gte: 4}
}
```
## Using MongoDB Java API on Eclipse:

1. Create a Maven project

    Adding the mongodb dependencies to the pom.xml we can connect to MongoDB for performing different operations.

    We have 3 types of mongo drivers, legacy, modern & bundled (i.e legacy + modern). The legacy driver differs from the modern drivers in the way of creating the mongo client object. 

    Legacy Driver:<br/>
    ```
    MongoClient mongoClient = new MongoClient();
    ```

    Modern Driver:<br/>
    ```
    MongoClient mongoClient = MongoClients.create();
    ```

    This approach eliminates the need for instantiating a new object everytime we need to establish a connection.

    **Latest** *maven-dependency*:<br/>
    ```
    <dependency>
        <groupId>org.mongodb</groupId>
        <artifactId>mongo-java-driver</artifactId>
        <version>3.12.11</version>
    </dependency>
    ```

2. Creating a connection class

    We can now begin by establishing a connection with mongodb & start executing operations on it.

    ```// Establish connection using modern client
    MongoClient client = MongoClients.create();

    // Connect to mongodb
    MongoDatabase nyse_lab = client.getDatabase("lab_2");

    // Create/get collections
    MongoCollection<Document>  nyse_A = nyse_lab.getCollection("nyse_B");
    ```

3. Importing csv files

    For this, we can use the scanner & file api to provide location of the csv file for importing.

    ```
    // Importing the csv file
    File nyse_csv = new File("C:\\Users\\18573\\Desktop\\BigData\\INFO7250---Engineering-of-Big-Data-Systems\\dataset","NYSE_daily_prices_A.csv");

    Scanner scanner = new Scanner(nyse_csv);
    ```

4. Iterating through the csv to add the documents to a list

    ```
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

    ```

5. Add the documents to the collection

    ```
    //Insert the list of documents to the collection
    nyse_b.insertMany(documents);
    System.out.print("Inserted rows: "+count);
    ```

    Output:

    Eclipse:
    ![alt text](https://github.com/tambeani/INFO7250---Engineering-of-Big-Data-Systems/blob/main/screenshots/lab2_insertmany_output.png?raw=true)


    MongoCompass:
    ![alt text](https://github.com/tambeani/INFO7250---Engineering-of-Big-Data-Systems/blob/main/screenshots/lab2_mongocompass_insertmany_output.png?raw=true)

6. Define a pipeline for aggregation:

    ```
    // Define a pipeline for aggregation
    List<Document> aggregated = nyse_b.aggregate(
        Arrays.asList(
            Aggregates.group("$stock_symbol",Accumulators.avg("stock_avg", "$stock_price_open")),
            Aggregates.sort(Sorts.descending("stock_avg"))
            )
        ).into(new ArrayList<>());

    // Adding the aggregated documents in a new collection
    lab_2.getCollection("stock_avg_collection").insertMany(aggregated);
    ```

    OR

    ```
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
    ```

    Output:<br/>
    ![alt text](https://github.com/tambeani/INFO7250---Engineering-of-Big-Data-Systems/blob/main/screenshots/lab2_aggregate_output.png?raw=true)


7. Close the connection

    ```
    // Close the connection
    client.close();
    ```











