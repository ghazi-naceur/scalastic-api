package com.scalastic.api.client

import java.net.InetAddress

import com.scalastic.api.config.PropertiesLoader
import org.apache.http.HttpHost
import org.elasticsearch.client.{RestClient, RestHighLevelClient}
import org.elasticsearch.client.transport.TransportClient
import org.elasticsearch.common.settings.Settings
import org.elasticsearch.common.transport.TransportAddress
import org.elasticsearch.transport.client.PreBuiltTransportClient

/**
  * Created by Ghazi Naceur on 06/04/2019
  * Email: ghazi.ennacer@gmail.com
  */
object ElasticClient {
  val host: String = PropertiesLoader.HOST
  val port: Int = PropertiesLoader.PORT
  val httpPort: Int = PropertiesLoader.HTTP_PORT
  val client = new RestHighLevelClient(RestClient.builder(new HttpHost(host, port)))
  def transportClient: TransportClient = {
    val settings = Settings.builder()
      .put("cluster.name", "elasticsearch").build()
    new PreBuiltTransportClient(settings)
      .addTransportAddress(new TransportAddress(InetAddress.getByName(host), httpPort))
  }
}
