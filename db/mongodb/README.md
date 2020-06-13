State Replication
------------------


      Primary Node
      local state  | local replication log(=operationlog) - after states are applied ([`local.oplog.rs`](https://docs.mongodb.com/manual/reference/local-database/#local.oplog.rs))
      z = 1        | 100 <- z
      y = 2        | 200 <- y
      x = 3        | 300 <- x
                   
                   |       
                   |
                   | operation log replication - replication lag is the delay between the time an 
                   | op occurs on the primary and the time that same op gets applied on the secondary.
                   |
 ----------------------------------------------------------------------------------------
 |                                                                                      |
 |                                                                                      |
 
Secondary Node 1                                              Secondary Node 2
local state | local replog/oplog(` local.oplog.rs`)           local state | local replication log(` local.oplog.rs`)
      z = 1 | 100 <- z                           
      y = 2 | 200 <- y
      x = 3 | 300 <- x 



[operation log](https://docs.mongodb.com/manual/core/replica-set-oplog/) 
- a special capped collection that keeps a rolling record of all operations that modify the data 
stored in your databases. 
MongoDB applies database operations on the primary and then records the operations on the primary’s oplog.
- in In-MemoryMAPv1 engine 50 MB Physical Memory(RAM)

- check local operation log status - `db.getReplicationInfo()`
-  time when the last oplog entry was written to the secondary- `rs.printSlaveReplicationInfo()`

- Arbiters do not maintain a data set. 

https://docs.mongodb.com/manual/core/replica-set-sync/

https://blog.mlab.com/2013/03/replication-lag-the-facts-of-life/
