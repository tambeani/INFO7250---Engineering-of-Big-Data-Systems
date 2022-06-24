## Lecture 5 - Hadoop & MapReduce

Hadoop is a framework written in Java for running applications on large clusters of commodity hardware and incorporates features similar to those of Google File system and of MapReduce computing paradigm.
 
### Hadoop Server roles:

A Client either requests for 
    -> Distributed Data processing (MapReduce)
    -> Distributed Data storage (HDFS)

Hadoop Daemons:
1. Namenode
2. Datanode
3. Secondary Namenode
4. Job Tracker

Assume a scenario in which the clients wants to store the file in HDFS, the **Namenode** will decide which block will be stored in which **Datanode** (also known as Slavenodes).

Another daemon called as Job tracker keeps tracks of jobs, for eg: a MapReduce job. Each job is then subdivided into sub-tasks, which are tracked on the Datanode by the Task Tracker ( its a daemon that runs on the slavenode). A task tracker is also the slave to the job tracker daemon.

### 3 major categories of machine roles:
1. Client machines/nodes
2. Master nodes
3. Slave nodes

#### Master nodes:

The master nodes oversee the two key functional pieces that make up Hadoop:
1. Storing lots of data (HDFS)
2. Running parallel computation on all that data (MapReduce)

The namenode coordinates and oversees the data storage function (HDFS). The job tracker oversees and coordinates the parallel processing of data using MapReduce.

#### Slave nodes:

Slave nodes make up the vast majority of the machines and do all the dirty work of storing the data and running the computations. Each slave runs both a *datanode* and a *task tracker* daemon that communicate with and receive instructions from their master nodes. The task tracker daemon is a slave to the job tracker, the data node daemon is a slave to the name node. 

#### Client machines

Client machines have hadoop installed with all the cluster settings, but are neither a Master or a slave. They load the data into the cluster, submit MapReduce jobs describing how the data should be processed, and then retrieve or view the results of the job when its finished. 

### Typical workflow

1. Load data into the cluster (HDFS writes)
2. Analyze the data (MapReduce)
3. Store results in the cluster (HDFS writes)
4. Read the results from the cluster (HDFS reads)

Sample scenario:
How many times did our customer type the word "Refund" into emails sent to customer service?

#### Writing to HDFS:

A. Create a directory for the files

1. Open terminal
2. Navigate to sbin sub-directory in the hadoop directory
3. Execute the start-all.sh script
4. Execute the command `hadoop fs -mkdir -p /june20/ebooks`

> Set the PATH environment variable for hadoop directory first.

B. Move the text file from local file directory to HDFS

1. Execute the command `hadoop fs -copyFromLocal ~/Desktop/pups.txt /june20/ebooks/`

> Note: The file permission is -rw-r--r--.

### Loading files into Hadoop Clusters

Your hadoop cluster is useless until it has data. To accomplish fast parallel processing, we need as many machines as possible working on the loaded data. Thus, the client is going to break the daa file into smaller *blocks* and place those blocks on different machines throughout the cluster. The more blocks we have, the more machines that will be able to work on this data in parallel.

To ensure high availability, we replicate these blocks on other data nodes in the cluster. The standard setting for Hadoop is to have (3) copies of each block in the cluster. This can be configured with the dfs.replication parameter in the hdfs-site.xml.

### Name node

The client breaks the file into blocks. For each block, the client consults the name node and receives a list of datanodes that should have a copy of this block. The client then write the block directly to the data node using TCP port 50010. The receiving datanode replicates the block to other datanode, and the cycle repeats for other blocks.

The namenode isn't in the data path, it only provides the map of where data is and where data should go in the cluster.

### Rack Awareness

There are two key reasons for defining the number of each slave data node in your cluster
1. Data loss prevention
2. Network performance

To tackle the scenario, where all the replicated blocks are present on datanode part of the same rack, we define the network topology and use that information to make an intelligent decision about where data replicas should exist in the cluster. That information is available with the Namenode.

### Data Processing: Map

Map: "Run this computation on your local data". The job tracker delivers java code to nodes with local data. For example, count "refund" in your block

### Data processing: Reduce

Reduce: "Run this computation across Map results". The result of map , i.e the key:value pairs are shuffled and sorted and aggregated by the reducer. Map tasks send output to reducer over the network. Reduce task data output written to and read from HDFS.