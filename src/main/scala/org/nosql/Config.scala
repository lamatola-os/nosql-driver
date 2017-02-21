package org.nosql

import com.amazonaws.ClientConfiguration
import com.amazonaws.auth.profile.ProfileCredentialsProvider
import com.amazonaws.regions.Regions
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient

/**
  * Created by prayagupd
  * on 2/19/17.
  */

object Config {

  def getHttpConnection: AmazonDynamoDBClient = {
    val PROXYHOST: String = "x.y.net"
    val PORT: Int = 8181

    val config = new ClientConfiguration() withProxyHost (PROXYHOST) withProxyPort (PORT)

    val credentials = new ProfileCredentialsProvider("x-y")

    val amazonDynamoDB = new AmazonDynamoDBClient(credentials, config)
    amazonDynamoDB.withRegion(Regions.US_WEST_2)

    amazonDynamoDB
  }

}
