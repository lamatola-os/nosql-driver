package org.nosql.reader.dynamodb

import java.util

import com.amazonaws.services.dynamodbv2.document.DynamoDB
import org.json.JSONObject
import org.nosql.Config
import org.nosql.reader.Reader

/**
  * Created by prayagupd
  * on 2/18/17.
  */

class DynamodbReader(tableName: String) extends Reader {

  val dynamoDB = new DynamoDB(Config.getHttpConnection)

  override def read(primaryKeys: util.LinkedHashMap[String, String],
                    rangeKeys: util.LinkedHashMap[String, String]): Option[JSONObject] = {

    val primaryKeyAttr = primaryKeys.keySet().iterator().next()

    println("Desc = " + dynamoDB.getTable(tableName).describe())

    rangeKeys.isEmpty match {
      case false =>
        val rangeKeyAttr = rangeKeys.keySet().iterator().next()
        val item = dynamoDB.getTable(tableName).getItem(primaryKeyAttr, primaryKeys.get(primaryKeyAttr),
          rangeKeyAttr, rangeKeys.get(rangeKeyAttr))

        val jsonOpt = Option(item).map(_.toJSON)
        println("JSON = $jsonOpt")
        jsonOpt.map(new JSONObject(_))
      case true =>
        val item = dynamoDB.getTable(tableName).getItem(primaryKeyAttr, primaryKeys.get(primaryKeyAttr))

        val jsonOpt = Option(item).map(_.toJSON)
        println("JSON = $jsonOpt")
        jsonOpt.map(new JSONObject(_))
    }
  }
}
