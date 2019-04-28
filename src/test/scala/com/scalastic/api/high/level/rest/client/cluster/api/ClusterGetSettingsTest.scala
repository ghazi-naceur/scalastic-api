package com.scalastic.api.high.level.rest.client.cluster.api

import org.elasticsearch.action.admin.cluster.settings.ClusterGetSettingsResponse

/**
  * Created by Ghazi Naceur on 28/04/2019
  * Email: ghazi.ennacer@gmail.com
  */
object ClusterGetSettingsTest extends App {
  private val response: ClusterGetSettingsResponse = ClusterAPIs.clusterGetSettings()
  println(response.getDefaultSettings)
  println(response.getTransientSettings)
  println(response.getPersistentSettings)

  System.exit(0)
}
