package com.scalastic.api.high.level.rest.client.cluster.api

import com.scalastic.api.client.ElasticClient
import org.elasticsearch.action.admin.cluster.health.{ClusterHealthRequest, ClusterHealthResponse}
import org.elasticsearch.action.admin.cluster.settings.{ClusterUpdateSettingsRequest, ClusterUpdateSettingsResponse}
import org.elasticsearch.client.{RequestOptions, RestHighLevelClient}
import org.elasticsearch.common.settings.Settings

/**
  * Created by Ghazi Naceur on 28/04/2019
  * Email: ghazi.ennacer@gmail.com
  */
object ClusterAPIs {

  private val client: RestHighLevelClient = ElasticClient.client

  def clusterHealth(indices: String*): ClusterHealthResponse = {
    val request = new ClusterHealthRequest(indices: _*)
    client.cluster().health(request, RequestOptions.DEFAULT)
  }

  def clusterUpdateSettings(transientSettings: Settings, persistentSettings: Settings): ClusterUpdateSettingsResponse = {
    val request = new ClusterUpdateSettingsRequest()
    request.transientSettings(transientSettings)
    request.persistentSettings(persistentSettings)
    client.cluster().putSettings(request, RequestOptions.DEFAULT)
  }
}
