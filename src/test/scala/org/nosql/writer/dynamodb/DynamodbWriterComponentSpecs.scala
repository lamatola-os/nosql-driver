package org.nosql.writer.dynamodb

import java.util

import com.amazonaws.services.dynamodbv2.model.{KeyType, ScalarAttributeType}
import com.specs.ComponentSpecs
import org.json.JSONObject
import org.nosql.admin.AdminOperations
import org.nosql.reader.dynamodb.DynamodbReader

/**
  * Created by prayagupd
  * on 2/18/17.
  */

class DynamodbWriterComponentSpecs extends ComponentSpecs {

  val admin = new AdminOperations

  override protected def beforeAll(): Unit = {

    val keySchema =  new util.LinkedHashMap[String, String](){{
      put("consumerOwner", KeyType.HASH.toString)
      put("offset", KeyType.RANGE.toString)
    }}

    val attrs = new util.LinkedHashMap[String, String](){{
      put("consumerOwner", ScalarAttributeType.S.toString)
      put("offset", ScalarAttributeType.S.toString)
    }}

    admin.createTable("Test_ConsumerOffset", keySchema, attrs) shouldBe "CREATING"

    Thread.sleep(6000)
  }

  override protected def afterAll(): Unit = {
    admin.dropTable("Test_ConsumerOffset")
    Thread.sleep(6000)
  }

  feature("DyanamoDatabse writer") {

    scenario("writes to dyanamodb, and reads by hash key and range key") {

      val writer = new DynamodbWriter("Test_ConsumerOffset")

      writer.write(new JSONObject(
        """
          {
           "consumerOwner" : "NewArtistConsumer_192.168.1.1",
           "offset" : "1",
           "streamPartition" : "partition-00001"
          }
        """.stripMargin))

      val reader = new DynamodbReader("Test_ConsumerOffset")

      val actual = reader.read(new util.LinkedHashMap[String, String]{{
        put("consumerOwner", "NewArtistConsumer_192.168.1.1")
      }}, new util.LinkedHashMap[String, String]{{
        put("offset" , "1")
      }})

      assert(actual.map(_.get("offset")).get == "1")
    }

    scenario("reads by field1 which is Hash key") {

      Given("some documents")
      val writer = new DynamodbWriter("Test_ConsumerOffset")

      writer.write(new JSONObject(
        """
          {
           "consumerOwner" : "NewArtistConsumer_192.168.1.2",
           "offset" : "2",
           "streamPartition" : "partition-00002"
          }
        """.stripMargin))

      When("read the Collection")
      val reader = new DynamodbReader("Test_ConsumerOffset")

      val actual = reader.read(
        new util.LinkedHashMap[String, String](){{put("consumerOwner", "NewArtistConsumer_192.168.1.2")}},
        new util.LinkedHashMap[String, String](){{put("offset", "2")}})

      assert(actual.map(_.get("offset")).get == "2")
    }

    ignore("reads by only field2(RANGE key) wont work because PK should have both keys") {
      Given("some documents")
      val writer = new DynamodbWriter("Test_ConsumerOffset")

      writer.write(new JSONObject(
        """
          {
           "consumerOwner" : "NewArtistConsumer_192.168.1.3",
           "offset" : "3",
           "streamPartition" : "partition-00004"
          }
        """.stripMargin))

      When("read the Collection")
      val reader = new DynamodbReader("Test_ConsumerOffset")

      val actual = reader.read(primaryKeys = new util.LinkedHashMap[String, String](),
        rangeKeys = new util.LinkedHashMap[String, String]{{put("offset", "3")}})

      assert(actual.map(_.get("offset")).get == "3")
    }

    ignore("reads by field3(which was not defined in original schema) does not work") {

      Given("some documents")
      val writer = new DynamodbWriter("Test_ConsumerOffset")

      writer.write(new JSONObject(
        """
          {
           "consumerOwner" : "NewArtistConsumer_192.168.1.5",
           "offset" : "5",
           "streamPartition" : "partition-00005"
          }
        """.stripMargin))

      When("reads")
      val reader = new DynamodbReader("Test_ConsumerOffset")

      val actual = reader.read(primaryKeys = new util.LinkedHashMap[String, String]{{put("streamPartition", "partition-00005")}},
        rangeKeys = new util.LinkedHashMap[String, String]())

      actual.map(_.get("offset")).get shouldBe "5"
    }
  }
}
