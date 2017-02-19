package org.nosql.writer.dyanamodb

import com.amazonaws.ClientConfiguration
import com.amazonaws.auth.profile.ProfileCredentialsProvider
import com.amazonaws.regions.Regions
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient
import com.amazonaws.services.dynamodbv2.document.{DynamoDB, Item}
import org.json.JSONObject
import org.nosql.writer.Writer

/**
  * Created by prayagupd
  * on 2/18/17.
  */

class DynamodbWriter(tableName: String) extends Writer {

  private val PROXYHOST: String = "whatever"
  private val PORT: Int = 8181

  val config = new ClientConfiguration() withProxyHost(PROXYHOST) withProxyPort(PORT)

  val credentials = new ProfileCredentialsProvider("nihilos")

  val amazonDynamoDB = new AmazonDynamoDBClient(credentials, config)
  amazonDynamoDB.withRegion(Regions.US_WEST_2)

  val dynamoDB = new DynamoDB(amazonDynamoDB)

  override def write(json: JSONObject): Unit = {
    val item = new Item().withString("consumerName", "NewArtistConsumer")
      .withString("offset", "1234567890")

    println("jhvjhhh "+dynamoDB.listTables())

    val table = dynamoDB.getTable(tableName)

    println(s"table = $table")
    val response = table.putItem(item)
    println(response.getItem)
  }

}
