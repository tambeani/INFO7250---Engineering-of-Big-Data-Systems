# Lecture 4 - Sharding & Introduction to Hadoop

## Sharding

1. Sharding is the process of storing data records across multiple machines and is MongoDB's approach to meeting the demands of data growth
2. As the data size increases, one machine may not be sufficient to store and provide sufficient read and write throughput
3. Sharding solves this problem with horizontal scaling or *scale-out*
4. With sharding, you add more machine to support the need for data growth and the demand for higher read/write throughput

### Purpose of sharding:

1. Database systems with large datasets and high throughput applications can challenge the capacity of a single server
2. High query rates can exhaust the CPU capacity of a single server
3. Larger datasets exceed the storage capacity of a single machine
4. Finally, working set sizes larger than the system's RAM stress the I/O capacity of the disk drives
5. To address this issues of scales, database systems have basic 2 approaches
    - Vertical scaling
    - Sharding (Horizontal scaling)

**Vertical Scaling** adds more CPU capacity & storage resources to increase capacity. However, scaling in this way has limitations. High performance systems with large numbers of CPUs and larger amount of RAM are disproportionately expensive. Additionally, cloud-based providers may only provision smaller instances. As a result, there is practical maximum capability for vertical scaling.

**Sharding with horizontal scaling** addresses the challenges of scaling to support high throughput and large data sets. Each shard(individual machine) handles fewer operations as the cluster grows. It also reduces the amount of data each shard stores. As the cluster grows, the data stored in each shard reduces.

<p align="center">
<b>Logical Diagram:</b></br>
    <img src="https://github.com/tambeani/INFO7250---Engineering-of-Big-Data-Systems/blob/main/screenshots/lec04_master_shards.png?raw=true" alt="Sublime's custom image"/>
</p>


1. Each shard is an independent database, and collectively, the shards make up a **logical database**.

### Running queries with Sharding:

![alt text](https://github.com/tambeani/INFO7250---Engineering-of-Big-Data-Systems/blob/main/screenshots/lec04_master.png?raw=true)

Here, we are trying to run a select query through multiple MySQL machines. To perform that, we need a MySQL master stores the metadata. The query is sent to the master database(machine) which stores row-level information in a lookup table(metadata).

## Sharding in MongoDB:

MongoDB supports sharding through the configuration of a sharded clusters. Below is the logical diagram of the shardeded clusters,

<p align="center">
    <img src="https://github.com/tambeani/INFO7250---Engineering-of-Big-Data-Systems/blob/main/screenshots/lec04_cluster_shards.png?raw=true" alt="Sublime's custom image"/>
</p>

Above diagram contains,
1. 3 Config servers
2. 2 App servers
3. 2 Shards

**Config Servers**: They do not store any working data, but store metadata (i.e mapping of cluster's data set to shards).

**App Servers**(mongos): These are the shard servers also known as query routers. They act as interface between the client applications and appropriate shard(s). They primarily handle the incoming read requests directed to the sharded cluster. They consult the config servers to target the operation to the appropriate shard. In other words it will route the query to the correct shard. 

**Shard**: Each shard can be an individual mongod instance or a replica set. To provide high availability and data consistency, each shard is a replica set.


Here, each shard is a physical database. To implement sharding we use the `mongos.exe` utility to start the **shard server.** Thus, we create multiple mongodb instances, alternatively we could also create replica sets and add them to the shard.  

### Data distribution in MongoDB:

1. MongoDB distributes the data at a collection level
2. Sharding partitions a collection's data by the shard key

### Shard Key:

To shard a collection we use a shard key. It is either an indexed field or an indexed compound field that exists in every document in the collection. MongoDB divides the shard key into chunks and distributes the chunks/blocks evenly across the shards. 

To divide the shard key, MongoDB uses either
1. range based partitioning -> creating non-overlapping chunks on a number line based on a numeric key

![alt text](https://github.com/tambeani/INFO7250---Engineering-of-Big-Data-Systems/blob/main/screenshots/lec04_range_based_partitioning.png?raw=true)

2. hash based partitioning -> computes hash of a field's value, and then uses these hashes to create chunks

![alt text](https://github.com/tambeani/INFO7250---Engineering-of-Big-Data-Systems/blob/main/screenshots/lec04_hash_based_partitioning.png?raw=true)

This type of partitioning ensures a more random distribution in the cluster.

### Maintaining a balanced data distribution:

Addition or removal of shards within the cluster can give rise to an imbalance. MongoDB ensures this balance by 2 background processes,
- splitting -> splits chunks in half if threshold for max size is crossed
- balancer -> migrates chunks to other shards when threshold for migration is crossed
### Creating a replica set on the same machine:

1. Create 2 additional copies of mongodb folders
2. Navigate to bin folder & run terminal
3. Run below command

```
mongod --replSet rs0 --port 27017 --bind_ip localhost --dbpath C:\data\db --oplogSize 128
mongod --replSet rs0 --port 27018 --bind_ip localhost --dbpath C:\data2\db --oplogSize 128
mongod --replSet rs0 --port 27019 --bind_ip localhost --dbpath C:\data3\db --oplogSize 128
```

4. Create 3 seperate clients for connecting to above instances

```
mongo --port 27017
mongo --port 27018
mongo --port 27019
```

5. Create a configuration script on the primary

```
rsconf = {
  _id: "rs0",
  members: [
    {
     _id: 0,
     host: "localhost:27017"
    },
    {
     _id: 1,
     host: "localhost:27018"
    },
    {
     _id: 2,
     host: "localhost:27019"
    }
   ]
}
```
In the above file we can make a member an arbiter or hidden based on our requirement.

6. Run below command on 27017 client

```
rs.initiate(rsconf)
```


## Hadoop

**Hadoop is not a database, it is an ecosystem (framework).**

It has the following module:
1. Hadoop common:</br></br>Provides common utilities that supports other Hadoop modules
2. Hadoop Distributed File System:</br></br>A distributed file system that provides high-throughput access to application data 
3. Hadoop YARN(yet another resource negotiator):</br></br>Its a resource manager for job scheduling and cluster resource management
4. Hadoop MapReduce:</br></br>A YARN-based system for parallel processing of large-dataset

### Hadoop as an ecosystem:

<p align="center">
    <img src="https://github.com/tambeani/INFO7250---Engineering-of-Big-Data-Systems/blob/main/screenshots/lec04_hadoop_ecosystem.png?raw=true" alt="Sublime's custom image"/>
</p>

### HDFS:

The distributed file system consists of numerous underlying physical machines also known as **datanodes** (w/ individual file systems) which are complimented by a single logical machine also known as **namenode** (w/ distributed file system) accessible to the end-user. 

One can assume Hadoop's HDFS as an operating system which enables us access to files and directories. Like an OS, we can install MySQL or any other database(Hbase can be ran on Hadoop). It also enables us to run distributed file system on commodity hardware.

Salient features:
1. High fault-tolerance
2. Designed to be deployed on low-cost hardware
3. Provides high throughput access to application data

### Pre-requisites for Hadoop:

1. Unix kernel (Linux)
2. Java
3. SSH (password-less)

### HDFS architecture

![alt text](https://github.com/tambeani/INFO7250---Engineering-of-Big-Data-Systems/blob/main/screenshots/lec04_hdfs_architecture.png?raw=true)



