## PART 3 - MongoDB indexing

1. Creating the NYSE dataset

```
show dbs
use nyse_a03_db
```

Output:<br/>
![alt text](https://github.com/tambeani/INFO7250---Engineering-of-Big-Data-Systems/blob/main/screenshots/a03_creating_db.png?raw=true)

2. Creating an index on `stock_symbol` attribute

```
db.nyse_a03_coll.createIndex(
    {
        "stock_symbol" : 1
    }
    );
```

Output:<br/>
![alt text](https://github.com/tambeani/INFO7250---Engineering-of-Big-Data-Systems/blob/main/screenshots/a03_creating_index.png?raw=true)

3. Importing the dataset

'''
mongoimport --type csv --db nyse_a03_db --collection nyse_a03_coll --headerline ./NYSE_daily_prices_A.csv
'''

Output:<br/>
![alt text](https://github.com/tambeani/INFO7250---Engineering-of-Big-Data-Systems/blob/main/screenshots/a03_importing_dataset.png?raw=true)