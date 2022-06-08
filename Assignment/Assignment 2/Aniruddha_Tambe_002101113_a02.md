## PART 1 - READING ASSIGNMENT 

https://learning.oreilly.com/library/view/mongodb-in-action/9781617291609/?ar

  Chapter 4. Document-oriented data
  Chapter 5. Constructing queries
  Chapter 6. Aggregation

Note: If you cannot access the chapters, enter your neu email as @northeastern.edu instead of @husky.neu.edu

## PART 2 - PROGRAMMING ASSIGNMENT
 
 **Q.** Write a .bat/.sh to import the entire NYSE dataset (stocks A to Z) into MongoDB. 
 
 NYSE Dataset Link: http://newton.neu.edu/nyse/NYSE_daily_prices_A.csv

 ### Downloading the dataset:

 ```
 sudo apt install -y curl
 curl -O http://newton.neu.edu/nyse/NYSE_daily_prices_A.csv
 ```

 Output:
 ![alt text](https://github.com/tambeani/INFO7250---Engineering-of-Big-Data-Systems/blob/main/screenshots/a02_curl_output.png?raw=true)

**Note:**  Before we begin, we need to make sure that mongodb is installed on Linux subsystem.Run below lines of code on a WSL:<br/>

```
sudo apt install -y mongodb
cd ..
sudo mkdir /usr/bin/data/db
sudo chmod 777 /usr/bin/data/db
/usr/bin/mongod --dbpath=/usr/bin/data/db
```

This should start the mongodb daemon-process. Run below code for checking if db is running:<br/>

```
sudo lsof -i -P -n|grep LISTEN
```

Output:<br/>
![alt text](https://github.com/tambeani/INFO7250---Engineering-of-Big-Data-Systems/blob/main/screenshots/a02_mongod_process.png?raw=true)

### Scripting the .sh file:
```
#!/bin/bash
FILES=./NYSE_daily_prices_A.csv
for f in $FILES
do
  echo "Processing $f ..."
  # set MONGODB_HOME environment
  MONGODB_HOME=/usr/bin
  $MONGODB_HOME/mongoimport --type csv --db nyse_a02_db --collection nyse_a02_col --headerline $f
done
```

### Running the bash file:

```
sudo vi mongo_a02.sh
bash mongo_a02.sh
```

Output:
![alt text](https://github.com/tambeani/INFO7250---Engineering-of-Big-Data-Systems/blob/main/screenshots/a02_mongoimport_output.png?raw=true)

## PART 3.1. Use the NYSE database to find the average price of stock_price_high values for each stock using MapReduce.

### Start the mongodb shell:

```
cd ~
/usr/bin/mongo
```

Output:<br/>![alt text](https://github.com/tambeani/INFO7250---Engineering-of-Big-Data-Systems/blob/main/screenshots/a02_mongoshell_startup.png?raw=true)

### Define map & reduce function:

```
use nyse_a02_db
var map = function(){emit(this.stock_symbol,this.stock_price_high);}
var reduce = function(stock_symbol,stock_price_high){var avg = Array.avg(stock_price_high);return avg;}
```

Output:<br/>![alt text](https://github.com/tambeani/INFO7250---Engineering-of-Big-Data-Systems/blob/main/screenshots/a02_map_reduce_function.png?raw=true)

### Run mapReduce function:

```
db.nyse_a02_col.mapReduce(map,reduce,{out: "stock_avg_collection"});
show collections
```

Output:<br/>![alt text](https://github.com/tambeani/INFO7250---Engineering-of-Big-Data-Systems/blob/main/screenshots/a02_mapreduce_output.png?raw=true)

### Printing mapReduce output:

```
db.stock_avg_collection.find().pretty();
```

Output:<br/>![alt text](https://github.com/tambeani/INFO7250---Engineering-of-Big-Data-Systems/blob/main/screenshots/a02_mapreduce_pretty_output.png?raw=true)

## PART 3.2. Part 3.1 result will not be correct as AVERAGE is a commutative operation but not associative. Use a FINALIZER to find the correct average.

### Redefine map,reduce & finalizer:

Map function:<br/>
```
var map_final = function(){
	emit(this.stock_symbol,{sum: this.stock_price_high,count:1});
}
```

Reduce function:<br/>
```
var reduce_final = function(stock_symbol,price_out){
	var num = {sum:0,count:0}
	for(var i=0; i<price_out.length;i++){
		num.sum += price_out[i].sum;
		num.count += price_out[i].count;

	}
	return num;
};
```

Finalize function:<br/>
```
var finalise = function(stock_symbol,result){
	result.avg = result.sum/result.count;
	return result.avg;
};
```

Output:<br/>![alt text](https://github.com/tambeani/INFO7250---Engineering-of-Big-Data-Systems/blob/main/screenshots/a02_redef_output.png?raw=true)

### Run mapReduce with finalize:
```
db.nyse_a02_col.mapReduce(map_final,reduce_final,{out: "stock_avg_final_collection",finalize:finalise});
```

Output:<br/>![alt text](https://github.com/tambeani/INFO7250---Engineering-of-Big-Data-Systems/blob/main/screenshots/a02_mapreducefinal_output.png?raw=true)


## PART 4. Calculate the average stock price of each price of all stocks using $avg aggregation.

Using the `aggregate()` function of mongodb for calculation the average stock price:

	
	db.nyse_a02_col.aggregate([
		{
			$group: { _id: "$stock_symbol",
			stock_open_avg:{$avg: "$stock_price_open"},
			stock_high_avg:{$avg: "$stock_price_high"},
			stock_low_avg:{$avg: "$stock_price_low"},
			stock_close_avg:{$avg: "$stock_price_close"},
			stock_adj_avg:{$avg: "$stock_price_adj_close"}}	
		},
		{
			$sort: {stock_symbol: -1}
		}
	]);

Output:<br/>![alt text](https://github.com/tambeani/INFO7250---Engineering-of-Big-Data-Systems/blob/main/screenshots/a02_aggregate_output.png?raw=true)

## PART 5.1 - PROGRAMMING ASSIGNMENT

Import the Movielens dataset into MongoDB. 

```
cd ~
curl -O https://files.grouplens.org/datasets/movielens/ml-1m.zip
sudo apt install -y unzip
unzip ml-1m.zip
cd ml-1m
cp ratings.dat ratings.csv
cp movies.dat movies.csv
cp users.dat users.csv
sed -i 's/::/,/g' ratings.csv
sed -i 's/::/,/g' movies.csv
sed -i 's/::/,/g' users.csv
```

Let us add headers to each csv file

```
sed -i '1s/^/user_id,gender,age,occupation,zipcode\n/' users.csv
sed -i '1s/^/movie_id,title,genre\n/' movies.csv
sed -i '1s/^/user_id,movie_id,rating,timestamp\n/' ratings.csv
```

Importing the csv files into collections
```
cd ~
/usr/bin/mongoimport --type csv --db movielens --collection ratings --headerline ./ml-1m/ratings.csv
/usr/bin/mongoimport --type csv --db movielens --collection users --headerline ./ml-1m/users.csv
/usr/bin/mongoimport --type csv --db movielens --collection movies --headerline ./ml-1m/movies.csv
```
**Q. Find the number Females and Males from the users collection using MapReduce. Do the same thing using count() to compare the results.**

**Using MapReduce:**

Define a map function to emit the gender
```
var map = function(){
	emit(this.gender,{count:1});
};
```

Define a reduce function to count
```
var reduce = function(key,values){
	var result = {count: 0};
	values.forEach(function(value) { result.count += value.count; } );
    return result;
};
```

Run the mapReduce command:
```
use movielens
db.users.mapReduce(map,reduce,{out:"users_count_genderwise"});
```

Output:<br/>![alt text](https://github.com/tambeani/INFO7250---Engineering-of-Big-Data-Systems/blob/main/screenshots/a02_genderwise_mapreduce_output.png?raw=true)

**Using count():**

```
db.users.find({gender:"M"}).count();
db.users.find({gender:"F"}).count();
```

Output:<br/>![alt text](https://github.com/tambeani/INFO7250---Engineering-of-Big-Data-Systems/blob/main/screenshots/a02_genderwise_count_output.png?raw=true)

**Q.Find the number of Movies per year using MapReduce**

**Q.Find the number of Movies per rating using MapReduce**

Define map function
```
var map = function(){
	emit(this.rating,{"count":1});
};
```

Define a reduce function
```
var reduce = function(key,values){
	var result = {"count": 0};
	values.forEach(function(value){ result.count += value.count;});
	return result;
};
```

Execute the mapReduce function
```
use movielens
db.ratings.mapReduce(map,reduce,{out:"movies_per_rating"});
```

Output:<br/>![alt text](https://github.com/tambeani/INFO7250---Engineering-of-Big-Data-Systems/blob/main/screenshots/a02_movies_per_rating_output.png?raw=true)

## PART 5.2 - PROGRAMMING ASSIGNMENT
  Repeat 5.1 using Aggregation Pipeline.

## PART 6 - PROGRAMMING ASSIGNMENT [access.log  Download access.log]
Write a Java (could be a console app - will only run once to import the data into MongoDB) program to read the access.log file (attached), and insert into access collection.  Once the data are inserted into MongoDB, do the followings using MapReduce:
- Number of times any webpage was visited by the same IP address.
- Number of times any webpage was visited each month.

## PART 7 - PROGRAMMING ASSIGNMENT
Redo Part-6 using Aggregation Pipeline.

