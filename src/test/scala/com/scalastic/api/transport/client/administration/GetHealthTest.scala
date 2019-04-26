package com.scalastic.api.transport.client.administration

import org.elasticsearch.action.admin.cluster.health.ClusterHealthResponse
import org.elasticsearch.cluster.health.ClusterIndexHealth

/**
  * Created by Ghazi Naceur on 14/04/2019
  * Email: ghazi.ennacer@gmail.com
  */
object GetHealthTest extends App {

  private val response: Iterable[ClusterIndexHealth] = ElasticAdministration.getHealth
  for (cluster: ClusterIndexHealth <- response) {
    println(cluster.getIndex)
    println(cluster.getNumberOfShards)
    println(cluster.getNumberOfReplicas)
    println(cluster.getStatus)
  }

  private val settings: ClusterHealthResponse = ElasticAdministration.getClusterSettings
  println(settings.getClusterName)
  println(settings.getNumberOfDataNodes)
  println(settings.getNumberOfNodes)

  System.exit(0)
}
