package org.nosql.writer.dynamodb

import java.util.Map.Entry

import com.amazonaws.services.dynamodbv2.document.{DynamoDB, Item}
import org.json.JSONObject
import org.nosql.Config
import org.nosql.writer.Writer

import scala.collection.JavaConversions._
/**
  * Created by prayagupd
  * on 2/18/17.
  */

class DynamodbWriter(tableName: String) extends Writer {

  val dynamoDB = new DynamoDB(Config.getHttpConnection)

  override def write(json: JSONObject): Option[List[Entry[String, AnyRef]]] = {

    val item = new Item()

    //one level document
    json.keys().toList.foreach { key => item.withString(key, json.getString(key))}

    println("tables "+dynamoDB.listTables())

    val writeTable = dynamoDB.getTable(tableName)

    println(s"write to table = $writeTable")
    val response = writeTable.putItem(item)

    println(s"response = ${response.getItem}")
    Option(response.getItem).map(_.attributes().toList)
  }

}
