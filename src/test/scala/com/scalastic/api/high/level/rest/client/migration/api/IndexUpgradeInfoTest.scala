package com.scalastic.api.high.level.rest.client.migration.api

import org.elasticsearch.protocol.xpack.migration.IndexUpgradeInfoResponse

import scala.collection.JavaConverters._

/**
  * Created by Ghazi Naceur on 30/04/2019
  * Email: ghazi.ennacer@gmail.com
  */
object IndexUpgradeInfoTest extends App {
  private val response: IndexUpgradeInfoResponse = MigrationAPI.indexUpgradeInfo("sc_persons")
  for (action <- response.getActions.asScala) {
    println(action._1)
    println(action._2)
  }

  System.exit(0)
}
