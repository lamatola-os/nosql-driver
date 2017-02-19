package org.nosql.writer.dyanamodb

import com.specs.UnitSpecs
import org.json.JSONObject
import org.nosql.reader.DynamodbReader

/**
  * Created by prayagupd
  * on 2/18/17.
  */

class DynamodbWriterUnitSpecs extends UnitSpecs {

  describe("Dyanamodb writer") {
    it("writes to dyanamodb") {

      val writer = new DynamodbWriter("ConsumerOffsetTest")

      writer.write(new JSONObject(
        """
          {
           "consumerName" : "NewArtistConsumer",
           "offset" : "1234567890"
          }
        """.stripMargin))

      val reader = new DynamodbReader("ConsumerOffsetTest")

      val actual = reader.read(new JSONObject().put("consumerName", "NewArtistConsumer"))

      assert(actual.get("offset") == "1234567890")
    }

    it("reads") {
      val reader = new DynamodbReader("ConsumerOffsetTest")

      val actual = reader.read(new JSONObject().put("consumerName", "NewArtistConsumer"))

      assert(actual.get("offset") == "1234567890")
    }
  }
}
