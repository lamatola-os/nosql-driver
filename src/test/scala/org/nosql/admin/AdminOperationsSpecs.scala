package org.nosql.admin

import java.util

import com.amazonaws.services.dynamodbv2.model.{KeyType, ScalarAttributeType}
import com.specs.ComponentSpecs
import org.json.JSONObject

/**
  * Created by prayagupd
  * on 2/19/17.
  */

class AdminOperationsSpecs extends ComponentSpecs {

  scenario("creates a table") {

    val keySchema =  new util.LinkedHashMap[String, String](){{
        put("consumerOwner", KeyType.HASH.toString)
        put("offset", KeyType.RANGE.toString)
      }}

    val attrs = new util.LinkedHashMap[String, String](){{
        put("consumerOwner", ScalarAttributeType.S.toString)
        put("offset", ScalarAttributeType.S.toString)
        put("streamPartition", ScalarAttributeType.S.toString)
      }}

    val created = new AdminOperations().createTable("Test_ConsumerOffset", keySchema, attrs, readThroughput = 10, writeThroughput = 10)
    assert(created == "CREATING")
  }

  scenario("describe a table") {
    val tableInfo = new AdminOperations().describe("ComponentTest_SmartConsumerStream_Consumer")
    println(tableInfo)
  }
}
