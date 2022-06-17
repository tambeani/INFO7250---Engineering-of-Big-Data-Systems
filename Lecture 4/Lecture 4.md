# Lecture 4 - Sharding & Introduction to Hadoop

## Sharding

1. Sharding is the process of storing data records across multiple machines and is MongoDB's approach to meeting the demands of data growth.
2. As the data size increases, one machine may not be sufficient to store and provide sufficient read and write throughput.
3. Sharding solves this problem with horizontal scaling or *scale-out*
4. With sharding, you add more machine to support the need for data growth and the demand for higher read/write throughput

### Running queries with Sharding:

![alt text](https://github.com/tambeani/INFO7250---Engineering-of-Big-Data-Systems/blob/main/screenshots/lec04_master.png?raw=true)

Here, we are trying to run a select query through multiple MySQL machines. To perform that, we need a MySQL master stores the metadata. The query is sent to the master database(machine) which stores row-level information in a lookup table(metadata).


