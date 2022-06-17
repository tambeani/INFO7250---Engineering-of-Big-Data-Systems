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
Logical Diagram:</br>![alt text](https://github.com/tambeani/INFO7250---Engineering-of-Big-Data-Systems/blob/main/screenshots/lec04_master_shards.png?raw=true)
</p>

Each shard is an independent database, and collectively, the shards make up a **logical database**.

### Running queries with Sharding:

![alt text](https://github.com/tambeani/INFO7250---Engineering-of-Big-Data-Systems/blob/main/screenshots/lec04_master.png?raw=true)

Here, we are trying to run a select query through multiple MySQL machines. To perform that, we need a MySQL master stores the metadata. The query is sent to the master database(machine) which stores row-level information in a lookup table(metadata).


