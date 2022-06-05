# Lab 2

1. ## Install Mongo Compass

**MongoCompass:**
It the user-interface for interacting with MongoDB in the backend. Its similar to workbench on MySQL db.

**Indexing:**
Helps us uniquely identify each row in a database. The index can help achive the "unique" property in a collection.

**Used for:**

1. Create a database
2. Importing .csv files
 - Change the data types
 - Alternative for mongoimport
3. Analyze functionality
 - Schema information & data visualization

2. ## Import movielens 1m dataset
 
a. Change the ratings.dat file into ratings.csv
b. Add headers for all columns
c. Change the datatype of rating to number

3. ## Define a pipeline for performing below aggregations:

> Average rating per movie_id

  key: movie_id
  value: rating

  Define below aggregator in $group stage in MongoCompass:
  {
    _id: "$movie_id",
    rating_avg: {
      $avg: "$rating"
    }
  }

> Sorting the rating in descending order, define below stage

{
  rating_avg: -1
}

> Filter only movies with average rating greater than equal to 4

{
  rating_avg: {$gte: 4}
}


**Using MongoDB Java API on Eclipse:**






