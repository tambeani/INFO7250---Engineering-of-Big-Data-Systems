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