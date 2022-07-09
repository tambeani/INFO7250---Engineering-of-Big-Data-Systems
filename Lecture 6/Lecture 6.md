## Lecture 6

MapReduce on mongodb vs. MapReduce on Hadoop

**MongoDB**
BSON documents --> Collection --> MongoDB
Documents --> Mapper --> Reducer --> Output document

**Hadoop**
Files --> Directories --> HDFS
Split(Files) --> InputFormat classes --> K,V --> Mapper --> Reducer --> OutputFormat classes --> File in HDFS

InputFormat --> responsible for reading files
OutputFormat --> responsible for storing files

**Splitting of files**
1. Physical split
    File --> Block --> DataNode
2. Logical split
    File --> Split (logical) --> Mapper

MapReduce steps:
1. Write the file to HDFS
2. Open the files, look at the contents -> Decide the InputFormat


### Java documentation
org.apache.hadoop.mapred - Old API
org.apache.hadoop.mapreduce - Newer API

### PutMerge program to write files into HDFS:

Motivation: To copy every log file from different web servers

Approach: To merge all the log files on the fly - instead of combining them requiring a large disk space - this operation is called as PutMerge

> Note: getmerge operation combines all the files from HDFS  before copying them on the local machine. This is the exact opposite of what we need to do.


