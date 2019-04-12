package com.scalastic.api.repo

import com.carrotsearch.hppc.cursors.ObjectObjectCursor
import com.scalastic.api.client.ElasticClient
import org.elasticsearch.client.RestHighLevelClient
import org.elasticsearch.client.transport.TransportClient
import org.elasticsearch.cluster.metadata.IndexMetaData

import scala.collection.JavaConverters._

/**
  * Created by Ghazi Naceur on 12/04/2019
  * Email: ghazi.ennacer@gmail.com
  */
object ElasticAdministration {

  val client: RestHighLevelClient = ElasticClient.client
  val transportClient: TransportClient = ElasticClient.transportClient
  val from = 0
  val size = 100

  def getIndices(): Iterable[ObjectObjectCursor[String, IndexMetaData]] = {
    transportClient.admin().cluster().prepareState().get().getState.getMetaData.getIndices.asScala
  }
}
