## PART 2 - Programming assignment

1. Creating /logs directory

Output:<br/>
![alt text](https://github.com/tambeani/INFO7250---Engineering-of-Big-Data-Systems/blob/main/screenshots/a04_creating_dir.png?raw=true)

2. Copy access.log file into HDFS

Output:<br/>
![alt text](https://github.com/tambeani/INFO7250---Engineering-of-Big-Data-Systems/blob/main/screenshots/a04_movefile.png?raw=true)

3. Run jar file without combiner

Driver class:
```
package com.info7250.hadoop.accesslogs_mr;

import java.io.IOException;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

public class HitMain {
	
	public static void main(String args[]) throws IllegalArgumentException, IOException, ClassNotFoundException, InterruptedException {
		
		Job job = Job.getInstance();
        job.setJarByClass(HitMain.class);
        
        // Specify various job-specific parameters  
        job.setMapperClass(HitCounter.class);
        job.setReducerClass(HitReducer.class);
        
        job.setInputFormatClass(TextInputFormat.class);
        job.setOutputFormatClass(TextOutputFormat.class);
        
        
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);
        
        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));
        
        job.setJobName("myjob");
        
        System.exit(job.waitForCompletion(true) ? 0 :1);
	}

}

```

Output:<br/>
![alt text](https://github.com/tambeani/INFO7250---Engineering-of-Big-Data-Systems/blob/main/screenshots/a04_2_mr.png?raw=true)

4. Run jar with a combiner

Driver class
```
package com.info7250.hadoop.accesslogs_mr;

import java.io.IOException;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

public class HitMain {
	
	public static void main(String args[]) throws IllegalArgumentException, IOException, ClassNotFoundException, InterruptedException {
		
		Job job = Job.getInstance();
        job.setJarByClass(HitMain.class);
        
        // Specify various job-specific parameters  
        job.setMapperClass(HitCounter.class);
        job.setCombinerClass(HitReducer.class);
        job.setReducerClass(HitReducer.class);
        
        job.setInputFormatClass(TextInputFormat.class);
        job.setOutputFormatClass(TextOutputFormat.class);
        
        
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);
        
        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));
        
        job.setJobName("myjob");
        
        System.exit(job.waitForCompletion(true) ? 0 :1);
	}

}
```

Output:<br/>
![alt text](https://github.com/tambeani/INFO7250---Engineering-of-Big-Data-Systems/blob/main/screenshots/a04_2_cmb.png?raw=true)

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

## PART 6 - Programming Assignment

1. checksum

Output:<br/>
![alt text](https://github.com/tambeani/INFO7250---Engineering-of-Big-Data-Systems/blob/main/screenshots/a03_1.png?raw=true)

2. help

Output:<br/>
![alt text](https://github.com/tambeani/INFO7250---Engineering-of-Big-Data-Systems/blob/main/screenshots/a03_2.png?raw=true)

3. ls

Output:<br/>
![alt text](https://github.com/tambeani/INFO7250---Engineering-of-Big-Data-Systems/blob/main/screenshots/a03_3.png?raw=true)

4. mkdir

Output:<br/>
![alt text](https://github.com/tambeani/INFO7250---Engineering-of-Big-Data-Systems/blob/main/screenshots/a03_4.png?raw=true)

5. count

Output:<br/>
![alt text](https://github.com/tambeani/INFO7250---Engineering-of-Big-Data-Systems/blob/main/screenshots/a03_5.png?raw=true)

6. touchz

Output:<br/>
![alt text](https://github.com/tambeani/INFO7250---Engineering-of-Big-Data-Systems/blob/main/screenshots/a03_6.png?raw=true)

7. cat

Output:<br/>
![alt text](https://github.com/tambeani/INFO7250---Engineering-of-Big-Data-Systems/blob/main/screenshots/a03_7.png?raw=true)

8. cp

Output:<br/>
![alt text](https://github.com/tambeani/INFO7250---Engineering-of-Big-Data-Systems/blob/main/screenshots/a03_8.png?raw=true)

9. rm

Output:<br/>
![alt text](https://github.com/tambeani/INFO7250---Engineering-of-Big-Data-Systems/blob/main/screenshots/a03_9.png?raw=true)

10. mv

Output:<br/>
![alt text](https://github.com/tambeani/INFO7250---Engineering-of-Big-Data-Systems/blob/main/screenshots/a03_10.png?raw=true)

11. du

Output:<br/>
![alt text](https://github.com/tambeani/INFO7250---Engineering-of-Big-Data-Systems/blob/main/screenshots/a03_11.png?raw=true)

12. dus

Output:<br/>
![alt text](https://github.com/tambeani/INFO7250---Engineering-of-Big-Data-Systems/blob/main/screenshots/a03_12.png?raw=true)

13. stat

Output:<br/>
![alt text](https://github.com/tambeani/INFO7250---Engineering-of-Big-Data-Systems/blob/main/screenshots/a03_13.png?raw=true)

14. setrep

Output:<br/>
![alt text](https://github.com/tambeani/INFO7250---Engineering-of-Big-Data-Systems/blob/main/screenshots/a03_14.png?raw=true)

15. copyFromLocal

Output:<br/>
![alt text](https://github.com/tambeani/INFO7250---Engineering-of-Big-Data-Systems/blob/main/screenshots/a03_15.png?raw=true)

16. moveFromLocal

Output:<br/>
![alt text](https://github.com/tambeani/INFO7250---Engineering-of-Big-Data-Systems/blob/main/screenshots/a03_16.png?raw=true)

17. moveToLocal

Output:<br/>
![alt text](https://github.com/tambeani/INFO7250---Engineering-of-Big-Data-Systems/blob/main/screenshots/a03_17.png?raw=true)

Definition: The Hadoop fs shell command moveToLocal moves the file or directory from the Hadoop filesystem to the destination in the local filesystem.

18. -ls -R 

Output:<br/>
![alt text](https://github.com/tambeani/INFO7250---Engineering-of-Big-Data-Systems/blob/main/screenshots/a03_18.png?raw=true)

19. df

Output:<br/>
![alt text](https://github.com/tambeani/INFO7250---Engineering-of-Big-Data-Systems/blob/main/screenshots/a03_19.png?raw=true)

20. get

Output:<br/>
![alt text](https://github.com/tambeani/INFO7250---Engineering-of-Big-Data-Systems/blob/main/screenshots/a03_20.png?raw=true)

21. expunge

Output:<br/>
![alt text](https://github.com/tambeani/INFO7250---Engineering-of-Big-Data-Systems/blob/main/screenshots/a03_21.png?raw=true)

22. chmod

Output:<br/>
![alt text](https://github.com/tambeani/INFO7250---Engineering-of-Big-Data-Systems/blob/main/screenshots/a03_22.png?raw=true)

23. usage

Output:<br/>
![alt text](https://github.com/tambeani/INFO7250---Engineering-of-Big-Data-Systems/blob/main/screenshots/a03_23.png?raw=true)

24. tail

Output:<br/>
![alt text](https://github.com/tambeani/INFO7250---Engineering-of-Big-Data-Systems/blob/main/screenshots/a03_24.png?raw=true)

25. head

Output:<br/>
![alt text](https://github.com/tambeani/INFO7250---Engineering-of-Big-Data-Systems/blob/main/screenshots/a03_25.png?raw=true)

Displays first kilobyte of the file to stdout.

26. rmdir

Output:<br/>
![alt text](https://github.com/tambeani/INFO7250---Engineering-of-Big-Data-Systems/blob/main/screenshots/a03_26.png?raw=true)

27. rm -r

Output:<br/>
![alt text](https://github.com/tambeani/INFO7250---Engineering-of-Big-Data-Systems/blob/main/screenshots/a03_27.png?raw=true)

28. rm -r

Output:<br/>
![alt text](https://github.com/tambeani/INFO7250---Engineering-of-Big-Data-Systems/blob/main/screenshots/a03_28.png?raw=true)

29. cp

Output:<br/>
![alt text](https://github.com/tambeani/INFO7250---Engineering-of-Big-Data-Systems/blob/main/screenshots/a03_29.png?raw=true)

30. test

Output:<br/>
![alt text](https://github.com/tambeani/INFO7250---Engineering-of-Big-Data-Systems/blob/main/screenshots/a03_30.png?raw=true)

31. appendToFile

Output:<br/>
![alt text](https://github.com/tambeani/INFO7250---Engineering-of-Big-Data-Systems/blob/main/screenshots/a03_31.png?raw=true)

32. getMerge

Output:<br/>
![alt text](https://github.com/tambeani/INFO7250---Engineering-of-Big-Data-Systems/blob/main/screenshots/a03_32.png?raw=true)

33. text

Output:<br/>
![alt text](https://github.com/tambeani/INFO7250---Engineering-of-Big-Data-Systems/blob/main/screenshots/a03_33.png?raw=true)

34. getfacl

Output:<br/>
![alt text](https://github.com/tambeani/INFO7250---Engineering-of-Big-Data-Systems/blob/main/screenshots/a03_34.png?raw=true)

35. put

Output:<br/>
![alt text](https://github.com/tambeani/INFO7250---Engineering-of-Big-Data-Systems/blob/main/screenshots/a03_35.png?raw=true)

36. getfattr

Output:<br/>
![alt text](https://github.com/tambeani/INFO7250---Engineering-of-Big-Data-Systems/blob/main/screenshots/a03_36.png?raw=true)

37. find

Output:<br/>
![alt text](https://github.com/tambeani/INFO7250---Engineering-of-Big-Data-Systems/blob/main/screenshots/a03_37.png?raw=true)

38. chgrp

Output:<br/>
![alt text](https://github.com/tambeani/INFO7250---Engineering-of-Big-Data-Systems/blob/main/screenshots/a03_38.png?raw=true)

39. chown

Output:<br/>
![alt text](https://github.com/tambeani/INFO7250---Engineering-of-Big-Data-Systems/blob/main/screenshots/a03_39.png?raw=true)

40. setfacl

Output:<br/>
![alt text](https://github.com/tambeani/INFO7250---Engineering-of-Big-Data-Systems/blob/main/screenshots/a03_40.png?raw=true)

Usage: hadoop fs -setfacl [-R] [-b |-k -m |-x <acl_spec> <path>] |[--set <acl_spec> <path>]

41. setfattr

Output:<br/>
![alt text](https://github.com/tambeani/INFO7250---Engineering-of-Big-Data-Systems/blob/main/screenshots/a03_41.png?raw=true)

42. touch

Output:<br/>
![alt text](https://github.com/tambeani/INFO7250---Engineering-of-Big-Data-Systems/blob/main/screenshots/a03_42.png?raw=true)

Updates the access and modification times of the file specified by the URI to the current time. If the file does not exist, then a zero length file is created at URI with current time as the timestamp of that URI.

43. chgrp

Output:<br/>
![alt text](https://github.com/tambeani/INFO7250---Engineering-of-Big-Data-Systems/blob/main/screenshots/a03_43.png?raw=true)

44. concat

Output:<br/>
![alt text](https://github.com/tambeani/INFO7250---Engineering-of-Big-Data-Systems/blob/main/screenshots/a03_44.png?raw=true)

Concatenate existing source files into the target file. Target file and source files should be in the same directory.

45. createSnapshot

Output:<br/>
![alt text](https://github.com/tambeani/INFO7250---Engineering-of-Big-Data-Systems/blob/main/screenshots/a03_45.png?raw=true)

46. renameSnapshot

Output:<br/>
![alt text](https://github.com/tambeani/INFO7250---Engineering-of-Big-Data-Systems/blob/main/screenshots/a03_46.png?raw=true)

47. deleteSnapshot

Output:<br/>
![alt text](https://github.com/tambeani/INFO7250---Engineering-of-Big-Data-Systems/blob/main/screenshots/a03_47.png?raw=true)

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

