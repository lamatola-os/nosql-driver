package org.nosql.reader

import com.amazonaws.ClientConfiguration
import com.amazonaws.auth.profile.ProfileCredentialsProvider
import com.amazonaws.regions.Regions
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient
import com.amazonaws.services.dynamodbv2.document.DynamoDB
import org.json.JSONObject

/**
  * Created by prayagupd
  * on 2/18/17.
  */

class DynamodbReader(tableName: String) extends Reader {

  private val PROXYHOST: String = "whatever.net"
  private val PORT: Int = 8181

  val config = new ClientConfiguration() withProxyHost(PROXYHOST) withProxyPort(PORT)

  val credentials = new ProfileCredentialsProvider("nihilos")

  val amazonDynamoDB = new AmazonDynamoDBClient(credentials, config)
  amazonDynamoDB.withRegion(Regions.US_WEST_2)

  val dynamoDB = new DynamoDB(amazonDynamoDB)

  override def read(query: JSONObject): JSONObject = {

    val field = query.keys().next()
    val json = dynamoDB.getTable(tableName).getItem(field, query.get(field)).toJSON
    println(json)
    new JSONObject(json)
  }
}
