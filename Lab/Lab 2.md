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
 
a. Change the ratings.dat file into ratings.csv
b. Add headers for all columns
c. Change the datatype of rating to number

## 3. Define a pipeline for performing below aggregations:

1. Find the average rating per movie_id:

&emsp;key: movie_id<br\>
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







