mongo## PART 3 - MongoDB indexing

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

```
mongoimport --type csv --db nyse_a03_db --collection nyse_a03_coll --headerline ./NYSE_daily_prices_A.csv
```

Output:<br/>
![alt text](https://github.com/tambeani/INFO7250---Engineering-of-Big-Data-Systems/blob/main/screenshots/a03_importing_dataset.png?raw=true)

4. Check for created indexes

```
use nyse_a03_db
db.nyse_a03_coll.getIndexes();
```

Output:<br/>
![alt text](https://github.com/tambeani/INFO7250---Engineering-of-Big-Data-Systems/blob/main/screenshots/a03_created_indexes.png?raw=true)

## PART 4 - MongoDB Indexing

1. Importing the dataset in another database

Output:<br/>
![alt text](https://github.com/tambeani/INFO7250---Engineering-of-Big-Data-Systems/blob/main/screenshots/a03_imported_data.png?raw=true)

2. Creating an index on this database

Output:<br/>
![alt text](https://github.com/tambeani/INFO7250---Engineering-of-Big-Data-Systems/blob/main/screenshots/a03_created_indexe_after.png?raw=true)

3. Checking for created index

Output:<br/>
![alt text](https://github.com/tambeani/INFO7250---Engineering-of-Big-Data-Systems/blob/main/screenshots/a03_created_indexe_after_stats.png?raw=true)

## PART 5 - MongoDB Text search (refer to chapter 9).

### Q. Write and execute one query to perform each of the followings on any collection of your choice.

1. Specify and word matches instead of or word matches.

Output:<br/>
![alt text](https://github.com/tambeani/INFO7250---Engineering-of-Big-Data-Systems/blob/main/screenshots/a03_word_match.png?raw=true)

2. Perform exact phrase matches.

Output:<br/>
![alt text](https://github.com/tambeani/INFO7250---Engineering-of-Big-Data-Systems/blob/main/screenshots/a03_exact_phrase_match.png?raw=true)

3. Exclude documents with certain words.

Output:<br/>
![alt text](https://github.com/tambeani/INFO7250---Engineering-of-Big-Data-Systems/blob/main/screenshots/a03_word_match_certain.png?raw=true)

4. Exclude documents with certain phrases.

Output:<br/>
![alt text](https://github.com/tambeani/INFO7250---Engineering-of-Big-Data-Systems/blob/main/screenshots/a03_word_match_certain_phrases.png?raw=true)


## PART 7 - HDFS Command Assignment

Code:

```
package com.info7250.mongodb.assignment;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

public class PutMerge {

public static void main(String[] args) throws IOException, URISyntaxException {

    Configuration conf = new Configuration();
    FileSystem hdfs = FileSystem.get(new URI("hdfs://localhost:9000"),conf);
    //FileSystem hdfs = FileSystem.get(new URI("hdfs://localhost:9000"),conf)
    FileSystem local = FileSystem.getLocal(conf);

    Path inputDir = new Path("/home/aniruddha/Downloads/nyse/NYSE/");
    Path hdfsFile = new Path("/nyse/");
    
    //FileSystem fs = FileSystem.get(new URI(<url:port>), configuration);
    //Path filePath = new Path(<path/to/file>);

    //System.out.println(inputDir);
    //System.out.println(hdfsFile);

    try {
        FileStatus[] inputFiles = local.listStatus(inputDir);
        //System.out.println(inputFiles);
        FSDataOutputStream out = hdfs.create(hdfsFile);
        //System.out.println(out);

        for (int i = 0; i < inputFiles.length; i++) {
            System.out.println(inputFiles[i].getPath().getName());
            FSDataInputStream in = local.open(inputFiles[i].getPath());
            byte buffer[] = new byte[256];
            int bytesRead = 0;
            while ((bytesRead = in.read(buffer)) > 0) {
                out.write(buffer, 0, bytesRead);
            }
            in.close();
        }
        out.close();
    } catch (Exception e) {
        //e.printStackTrace();

    }
}
}

```

Command:
```
mvn compile exec:java -Dexec.mainClass="com.info7250.mongodb.assignment.PutMerge" -Dexec.cleanupDaemonThreads=false
```
Output:<br/>
![alt text](https://github.com/tambeani/INFO7250---Engineering-of-Big-Data-Systems/blob/main/screenshots/a03_putmerge.png?raw=true)

