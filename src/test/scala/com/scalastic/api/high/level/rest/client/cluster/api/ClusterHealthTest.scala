package com.scalastic.api.high.level.rest.client.cluster.api

import org.elasticsearch.action.admin.cluster.health.ClusterHealthResponse

/**
  * Created by Ghazi Naceur on 28/04/2019
  * Email: ghazi.ennacer@gmail.com
  */
object ClusterHealthTest extends App {
  private val response: ClusterHealthResponse = ClusterAPIs.clusterHealth()
  println(response.getActivePrimaryShards)
  println(response.getActiveShards)
  println(response.getActiveShardsPercent)
  println(response.getClusterName)
  println(response.getDelayedUnassignedShards)
  println(response.getIndices)
  println(response.getNumberOfNodes)
  println(response.getStatus.value())
  println(response.getTaskMaxWaitingTime)
  println(response.getNumberOfDataNodes)

  System.exit(0)
}
