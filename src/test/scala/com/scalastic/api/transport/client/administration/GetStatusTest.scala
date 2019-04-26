package com.scalastic.api.transport.client.administration

import org.elasticsearch.action.admin.cluster.health.ClusterHealthResponse

/**
  * Created by Ghazi Naceur on 17/04/2019
  * Email: ghazi.ennacer@gmail.com
  */
object GetStatusTest extends App {
  private val response1: ClusterHealthResponse = ElasticAdministration.getYellowStatus("persons", "sc_persons")
  println(response1.getStatus)
  private val response2: ClusterHealthResponse = ElasticAdministration.getGreenStatus("persons", "sc_persons")
  println(response2.getStatus)

  System.exit(0)
}
