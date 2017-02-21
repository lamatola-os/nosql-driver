package org.nosql.writer.dynamodb

import java.util

import com.specs.UnitSpecs
import org.json.JSONObject
import org.nosql.reader.dynamodb.DynamodbReader

/**
  * Created by prayagupd
  * on 2/18/17.
  */

class DynamodbWriterUnitSpecs extends UnitSpecs {

  describe("Dyanamodb writer") {

    it("writes to dyanamodb") {

      val writer = new DynamodbWriter("Test_ConsumerOffset")

      writer.write(new JSONObject(
        """
          {
           "consumerOwner" : "NewArtistConsumer_192.168.1.2",
           "offset" : "1234567890",
           "streamPartition" : "partition-00001"
          }
        """.stripMargin))

      val reader = new DynamodbReader("Test_ConsumerOffset")

      val actual = reader.read(new util.LinkedHashMap[String, String]{{
        put("consumerOwner", "NewArtistConsumer_192.168.1.2")
      }}, new util.LinkedHashMap[String, String]{{
        put("offset" , "1234567890")
      }})

      assert(actual.map(_.get("offset")).get == "1234567890")
    }

    it("reads by field1") {
      val reader = new DynamodbReader("Test_ConsumerOffset")

      val actual = reader.read(
        new util.LinkedHashMap[String, String](){{put("consumerOwner", "NewArtistConsumer_192.168.1.2")}},
        new util.LinkedHashMap[String, String](){{put("offset", "1234567890")}})

      assert(actual.map(_.get("offset")).get == "1234567890")
    }

    it("reads by field2") {
      val reader = new DynamodbReader("Test_ConsumerOffset")

      val actual = reader.read(new util.LinkedHashMap[String, String]{{put("offset", "1234567890")}},
        new util.LinkedHashMap[String, String](){{put("offset", "1234567890")}})

      assert(actual.map(_.get("offset")).get == "1234567890")
    }
  }
}
