## PART 1 - READING ASSIGNMENT https://learning.oreilly.com/library/view/mongodb-in-action/9781617291609/?ar (Links to an external site.) (Links to an external site.)
  Chapter 4. Document-oriented data
  Chapter 5. Constructing queries
  Chapter 6. Aggregation
Note: If you cannot access the chapters, enter your neu email as @northeastern.edu instead of @husky.neu.edu

## PART 2 - PROGRAMMING ASSIGNMENT
 Write a .bat/.sh to import the entire NYSE dataset (stocks A to Z) into MongoDB.
 NYSE Dataset Link: http://newton.neu.edu/nyse/NYSE_daily_prices_A.csv

 ### Downloading the dataset

 Write below lines of code:<br/>
 ```
 sudo apt install -y curl
 curl -O http://newton.neu.edu/nyse/NYSE_daily_prices_A.csv
 ```

*Note:* 

Before we begin, we need to make sure that mongodb is installed on Linux subsystem.<br/>Run below lines of code on a WSL:<br/>
```
sudo apt install -y mongodb
cd ..
sudo mkdir /usr/bin/data/db
sudo chmod 777 /usr/bin/data/db
/usr/bin/mongod --db-path=/usr/bin/data/db
```

This should start the mongodb daemon-process.<br/>Run below code for checking if db is running:<br/>

```
sudo lsof -i -P -n|grep LISTEN
```

### .sh file
```
#!/bin/bash
FILES=./dataset/NYSE_daily_prices_A.csv
for f in $FILES
do
  echo "Processing $f ..."
  # set MONGODB_HOME environment
  MONGODB_HOME=/usr/bin
  $MONGODB_HOME/mongoimport --type csv --db nyse_a02_db --collection nyse_a02_col --headerline $f
Done
```

## PART 3.1. Use the NYSE database to find the average price of stock_price_high values for each stock using MapReduce.

## PART 3.2. Part 3.1 result will not be correct as AVERAGE is a commutative operation but not associative. Use a FINALIZER to find the correct average.
(Hint: pass sum and count from the reducer)
(https://docs.mongodb.com/manual/reference/method/db.collection.mapReduce/index.html)

## PART 4. Calculate the average stock price of each price of all stocks using $avg aggregation.
https://docs.mongodb.com/manual/reference/operator/aggregation/avg/ (Links to an external site.) (Links to an external site.)

## PART 5.1 - PROGRAMMING ASSIGNMENT
Import the Movielens dataset into MongoDB. Refer to README about file contents and headings.
https://grouplens.org/datasets/movielens/1m/ (Links to an external site.) (Links to an external site.)   [you may replace :: in the dateset with comma or tab to import]

Find the number Females and Males from the users collection using MapReduce. Do the same thing using count() to compare the results.
Find the number of Movies per year using MapReduce
Find the number of Movies per rating using MapReduce

## PART 5.2 - PROGRAMMING ASSIGNMENT
  Repeat 5.1 using Aggregation Pipeline.

## PART 6 - PROGRAMMING ASSIGNMENT [access.log  Download access.log]
Write a Java (could be a console app - will only run once to import the data into MongoDB) program to read the access.log file (attached), and insert into access collection.  Once the data are inserted into MongoDB, do the followings using MapReduce:
- Number of times any webpage was visited by the same IP address.
- Number of times any webpage was visited each month.

## PART 7 - PROGRAMMING ASSIGNMENT
Redo Part-6 using Aggregation Pipeline.

