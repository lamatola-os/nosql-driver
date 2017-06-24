package org.nosql.admin

import java.util

import com.amazonaws.services.dynamodbv2.model.{KeyType, ScalarAttributeType}
import com.specs.ComponentSpecs

/**
  * Created by prayagupd
  * on 2/19/17.
  */

class AdminOperationsComponentSpecs extends ComponentSpecs {

  scenario("creates a table") {

    val keySchema =  new util.LinkedHashMap[String, String](){{
        put("partitionKey", KeyType.HASH.toString)
        put("rangeKey", KeyType.RANGE.toString)
      }}

    val attrs = new util.LinkedHashMap[String, String](){{
        put("partitionKey", ScalarAttributeType.S.toString)
        put("rangeKey", ScalarAttributeType.S.toString)
      }}

    val created = new AdminOperations()
      .createTable("KeyValueLookupTable", keySchema, attrs, readThroughput = 10, writeThroughput = 10)
    assert(created == "CREATING")

    Thread.sleep(8000)

    new AdminOperations().dropTable("KeyValueLookupTable")

  }

  scenario("describe a table") {
    val keySchema =  new util.LinkedHashMap[String, String](){{
      put("partitionKey", KeyType.HASH.toString)
      put("rangeKey", KeyType.RANGE.toString)
    }}

    val attrs = new util.LinkedHashMap[String, String](){{
      put("partitionKey", ScalarAttributeType.S.toString)
      put("rangeKey", ScalarAttributeType.S.toString)
    }}

    val created = new AdminOperations()
      .createTable("KeyValueLookupTable1", keySchema, attrs, readThroughput = 10, writeThroughput = 10)
    Thread.sleep(8000)

    val tableInfo = new AdminOperations().describe("KeyValueLookupTable1")

    tableInfo.getKeySchema.size() shouldBe 2
    tableInfo.getKeySchema.get(0).getKeyType shouldBe KeyType.HASH.toString
    tableInfo.getKeySchema.get(1).getKeyType shouldBe KeyType.RANGE.toString

    tableInfo.getAttributeDefinitions.size() shouldBe 2

    new AdminOperations().dropTable("KeyValueLookupTable1")
  }

  scenario("table has hash key") {

    val keySchema =  new util.LinkedHashMap[String, String](){{
      put("partitionKey1", KeyType.HASH.toString)
      put("rangeKey", KeyType.RANGE.toString)
    }}

    val attrs = new util.LinkedHashMap[String, String](){{
      put("partitionKey1", ScalarAttributeType.S.toString)
      put("rangeKey", ScalarAttributeType.S.toString)
    }}

    val created = new AdminOperations()
      .createTable("KeyValueLookupTable_Query", keySchema, attrs, readThroughput = 10, writeThroughput = 10)
    assert(created == "CREATING")

    Thread.sleep(8000)

    val tableInfo = new AdminOperations().describe("KeyValueLookupTable_Query")

    tableInfo.getKeySchema.size() shouldBe 2
    new AdminOperations().partitionKey("KeyValueLookupTable_Query") shouldBe "partitionKey1"

    new AdminOperations().dropTable("KeyValueLookupTable_Query")
  }


  scenario("table has range key") {

    val keySchema =  new util.LinkedHashMap[String, String](){{
      put("partitionKey", KeyType.HASH.toString)
      put("rangeKey1", KeyType.RANGE.toString)
    }}

    val attrs = new util.LinkedHashMap[String, String](){{
      put("partitionKey", ScalarAttributeType.S.toString)
      put("rangeKey1", ScalarAttributeType.S.toString)
    }}

    val created = new AdminOperations()
      .createTable("KeyValueLookupTable_RangeQuery", keySchema, attrs, readThroughput = 10, writeThroughput = 10)
    assert(created == "CREATING")

    Thread.sleep(8000)

    new AdminOperations().sortKey("KeyValueLookupTable_RangeQuery") shouldBe "rangeKey1"

    new AdminOperations().dropTable("KeyValueLookupTable_RangeQuery")
  }

}
