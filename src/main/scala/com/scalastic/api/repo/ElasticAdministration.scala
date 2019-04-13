package com.scalastic.api.repo

import com.carrotsearch.hppc.cursors.ObjectObjectCursor
import com.scalastic.api.client.ElasticClient
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse
import org.elasticsearch.action.admin.indices.refresh.RefreshResponse
import org.elasticsearch.client.RestHighLevelClient
import org.elasticsearch.client.transport.TransportClient
import org.elasticsearch.cluster.metadata.IndexMetaData

import scala.collection.JavaConverters._

/**
  * Created by Ghazi Naceur on 12/04/2019
  * Email: ghazi.ennacer@gmail.com
  */
object ElasticAdministration {

  private val client: RestHighLevelClient = ElasticClient.client
  private val transportClient: TransportClient = ElasticClient.transportClient
  private val from = 0
  private val size = 100

  def getIndices(): Iterable[ObjectObjectCursor[String, IndexMetaData]] = {
    transportClient.admin().cluster().prepareState().get().getState.getMetaData.getIndices.asScala
  }

  def createIndex(index: String): CreateIndexResponse = {
    transportClient.admin().indices().prepareCreate(index).get()
  }

  def refreshIndices(indices: String*): RefreshResponse = {
    transportClient.admin().indices().prepareRefresh(indices: _*).get()
  }

}
