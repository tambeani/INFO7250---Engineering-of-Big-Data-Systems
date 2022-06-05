# Lab 2

## 1. Install Mongo Compass

**MongoCompass:**

It the user-interface for interacting with MongoDB in the backend. Its similar to workbench on MySQL db.

**Indexing:**

Helps us uniquely identify each row in a database. The index can help achive the "unique" property in a collection.

**Used for:**

1. Create a database
2. Importing .csv files
3. Analyze functionality

## 2. Import movielens 1m dataset

Download dataset - https://files.grouplens.org/datasets/movielens/ml-1m.zip<br/>
 
a. Change the ratings.dat file into ratings.csv<br/>
b. Add headers for all columns<br/>
c. Change the datatype of rating to number<br/>
d. Add a new database as "movie_lens"<br/>
e. Add a new collection as "ratings"<br/>

## 3. Define a pipeline for performing below aggregations:

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
## 4. Using MongoDB Java API on Eclipse:

1. Create a Maven project

Adding the mongodb dependencies to the pom.xml we can connect to MongoDB for performing different operations.

We have 3 types of mongo drivers, legacy, modern & bundled (i.e legacy + modern). The legacy driver differs from the modern drivers in the way of creating the mongo client object. 

Legacy Driver:<br/>
`MongoClient mongoClient = new MongoClient();`

Modern Driver:<br/>
`MongoClient mongoClient = MongoClients.create();`

This approach eliminates the need for instantiating a new object everytime we need to establish a connection.

**Latest** *maven-dependency*:<br/>
```
<dependency>
    <groupId>org.mongodb</groupId>
    <artifactId>mongo-java-driver</artifactId>
    <version>3.12.11</version>
</dependency>
```

2. Add following dependencies







