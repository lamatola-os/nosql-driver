package org.nosql.admin

import java.util

import com.amazonaws.services.dynamodbv2.document.DynamoDB
import com.amazonaws.services.dynamodbv2.model.{AttributeDefinition, CreateTableRequest, KeySchemaElement, ProvisionedThroughput}
import org.json.JSONObject
import org.nosql.Config

import scala.collection.JavaConversions._

/**
  * Created by prayagupd
  * on 2/18/17.
  */

trait AdminOps {
  def describe(tableName : String) : String

  def createTable(name: String, partitionKey: util.LinkedHashMap[String, String],
                  fieldsDefinitions: util.LinkedHashMap[String, String], throughput: Long,
                  writeThroughput : Long): String

  def dropTable(tableName: String) : String

}

class AdminOperations extends AdminOps {

  val dynamoDB = new DynamoDB(Config.getHttpConnection)

  override def createTable(name: String, partitionKeyType: util.LinkedHashMap[String, String],
                           fieldsDefinitions: util.LinkedHashMap[String, String],
                           readThroughput : Long = 10, writeThroughput : Long = 10): String = {

    val createTable = new CreateTableRequest().withTableName(name)

    partitionKeyType.keySet().foreach(primaryKey => {
      println(s"adding $primaryKey")
      createTable.withKeySchema(new KeySchemaElement(primaryKey, partitionKeyType.get(primaryKey)))
    })

    fieldsDefinitions.keySet().foreach(attribute => createTable.withAttributeDefinitions(
      new AttributeDefinition(attribute, fieldsDefinitions.get(attribute))))

    createTable.withProvisionedThroughput(new ProvisionedThroughput(readThroughput, writeThroughput))

    println(createTable)
    dynamoDB.createTable(createTable).getDescription.getTableStatus
  }

  override def describe(tableName: String): String = {
    val tableInfo = dynamoDB.getTable(tableName).describe()
    val json = tableInfo.toString//.replaceAll(",}", "}")
    println(json)
    json
  }

  override def dropTable(tableName: String): String = {
    val deleteTable = dynamoDB.getTable(tableName)
    val deletion = deleteTable.delete()

    deleteTable.waitForDelete()

    println(deletion.getTableDescription.getLatestStreamLabel)
    deletion.getTableDescription.getTableStatus
  }
}
