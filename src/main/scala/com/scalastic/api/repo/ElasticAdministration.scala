package com.scalastic.api.repo

import com.carrotsearch.hppc.cursors.ObjectObjectCursor
import com.scalastic.api.client.ElasticClient
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse
import org.elasticsearch.action.admin.indices.refresh.RefreshResponse
import org.elasticsearch.action.admin.indices.settings.get.GetSettingsResponse
import org.elasticsearch.client.RestHighLevelClient
import org.elasticsearch.client.transport.TransportClient
import org.elasticsearch.cluster.metadata.IndexMetaData
import org.elasticsearch.common.xcontent.XContentType

import scala.collection.JavaConverters._
import scala.collection.mutable.ListBuffer

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

  def getSettings(indices: String*): GetSettingsResponse = {
    transportClient.admin().indices().prepareGetSettings(indices: _*).get()
  }

  def putMapping(esIndex: String, esType: String, fields: Map[String, Any]) = {
    var props: ListBuffer[String] = ListBuffer()
    for ((k, v) <- fields) {
      props += "  \"" + k + "\": {" + " \"type\": \"" + v + "\"" + " }"
    }
    val str = props.mkString(",")
    transportClient.admin().indices().preparePutMapping(esIndex)
      .setType(esType)
      .setSource("{\n" +
        "  \"properties\": {\n" +
        str
        +
        "  }\n" +
        "}", XContentType.JSON)
      .get()
  }

  def addMapping(esIndex: String, esType: String, field: String, typeField: String): CreateIndexResponse = {
    val builder = transportClient.admin().indices().prepareCreate(esIndex)
    builder.addMapping(esType, field, "type=" + typeField)
    builder.get()
  }
}
