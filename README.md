
NoSQL
-----

[Why is NoSQL faster than SQL?](http://programmers.stackexchange.com/a/175546/31060)


Relation DB                                                             | NoSQL
------------------------------------------------------------------------|-----------------


CAP theorem, AXP 2015, JWN 2016
-------------------------------

C
---

- Consistency in CAP actually means linearizability, which is a very specific (and very strong) 
notion of consistency. 
In particular it has got nothing to do with the C in ACID, even though that C also stands for 
"consistency". 

- The meaning of linearizability below.

If **operation B** started after **operation A** successfully completed, then **operation B** 
must see the the system in the same state as it was on completion of **operation A**, 
or a newer state.


![](https://robertgreiner.com/content/images/2019/09/CAP-CP.png)

[Strong consistency models](https://aphyr.com/posts/313-strong-consistency-models)

eg. [MongoDB Write/Read consistency](http://stackoverflow.com/a/11297667/432903), HBase

```
MongoDB is strongly consistent by default - if you do a write and then do a read, assuming the write 
was successful you will always be able to read the result of the write you just read. 
This is because MongoDB is a single-master system and all reads go to the primary by default. 

If you optionally enable reading from the secondaries then MongoDB becomes eventually consistent 
where it's possible to read out-of-date results. (MWA, 2016)

MongoDB also gets high-availability through automatic failover in replica sets: 
http://www.mongodb.org/display/DOCS/Replica+Sets
```

[Write Concern for Replica Sets](https://docs.mongodb.com/manual/core/replica-set-write-concern/)

w: 1 is the default write concern for MongoDB.

```
cfg = rs.conf()
cfg.settings = {}
cfg.settings.getLastErrorDefaults = { w: "majority", wtimeout: 5000 }
rs.reconfig(cfg)

// Requests acknowledgment that write operations have propagated to the majority of voting nodes, 
including the primary, and have been written to the on-disk journal for these nodes.
```

[Redis - CP](https://www.quora.com/What-is-Redis-in-the-context-of-the-CAP-Theorem)

https://aphyr.com/posts/283-jepsen-redis

A
---

```
Availability in CAP is defined as “every request received by a non-failing [database] node in the 
system must result in a [non-error] response”. 
It’s not sufficient for some node to be able to handle the request: any non-failing node needs to 
be able to handle it. 

Many so-called “highly available” (i.e. low downtime) systems actually do not meet this definition 
of availability.
```

![CAP](https://robertgreiner.com/content/images/2019/09/CAP-AP.png)

eg. [Cassandra](https://wiki.apache.org/cassandra/ArchitectureOverview) - A but also provides C, based on tuning

```
Cassandra provides consistency when RRC + WRC > N(RF) 
(read replica count + write replica count > replication factor).
```

Cassandra Parameters for Dummies- http://www.ecyrd.com/cassandracalculator/

P
---

```
Partition Tolerance (terribly mis-named) basically means that you’re communicating over an 
asynchronous network that may delay or drop messages. The internet and all our datacenters have this 
property, so you don’t really have any choice in this matter.
```

![]()

[CAP Theorem: Revisited, Robert Greiner](http://robertgreiner.com/2014/08/cap-theorem-revisited/)

[Brewer's CAP Theorem, The kool aid Amazon and Ebay have been drinking](http://www.julianbrowne.com/article/viewer/brewers-cap-theorem)

2 nodes in a network, N1 and N2. They both share a piece of data V

![](http://www.julianbrowne.com/assets/attachments/brewers-cap-theorem/images/scenario-1.png)

[MongoDB/NoSQL - CP](https://docs.google.com/document/d/10byC4oKozabZ7lqnr1_UVRGR_HavEppiSYSROTzYDR0/edit)
--------------

https://codahale.com/you-cant-sacrifice-partition-tolerance/

http://blog.nahurst.com/visual-guide-to-nosql-systems

https://aphyr.com/posts/322-jepsen-mongodb-stale-reads

https://www.infoq.com/news/2014/04/bitcoin-banking-mongodb

http://www.sarahmei.com/blog/2013/11/11/why-you-should-never-use-mongodb/

http://hackingdistributed.com/2014/04/06/another-one-bites-the-dust-flexcoin/

[mongodb on distributed consistency](https://www.google.com/search?espv=2&biw=1440&bih=829&q=mongodb+on+distributed+consistency&oq=mongodb+on+distributed+consistency&gs_l=serp.3...4431.4661.0.5028.3.3.0.0.0.0.112.321.0j3.3.0....0...1c.1.64.serp..0.0.0.dyztyYVpmLY)

[Please stop calling databases CP or AP](https://martin.kleppmann.com/2015/05/11/please-stop-calling-databases-cp-or-ap.html)

CosmosDB
----------
- https://docs.microsoft.com/en-us/azure/cosmos-db/introduction
