package com.scalastic.api.repo

import com.scalastic.api.client.ElasticClient
import org.elasticsearch.action.admin.cluster.health.ClusterHealthRequest
import org.elasticsearch.action.admin.indices.close.CloseIndexRequest
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest
import org.elasticsearch.action.admin.indices.get.GetIndexRequest
import org.elasticsearch.action.admin.indices.open.{OpenIndexRequest, OpenIndexResponse}
import org.elasticsearch.action.support.master.AcknowledgedResponse
import org.elasticsearch.client.transport.TransportClient
import org.elasticsearch.client.{RequestOptions, RestClientBuilder, RestHighLevelClient}
import org.elasticsearch.common.unit.TimeValue
import org.elasticsearch.common.xcontent.XContentType

import scala.collection.JavaConverters._


/**
  * Created by Ghazi Naceur on 18/04/2019
  * Email: ghazi.ennacer@gmail.com
  */
object ElasticHighLevelRestClient {

  private val transportClient: TransportClient = ElasticClient.transportClient
  private val client: RestHighLevelClient = ElasticClient.client
  private val llc: RestClientBuilder = ElasticClient.lowLevelClient

  def getIndicesList: List[String] = {
    val request = new ClusterHealthRequest
    val response = client.cluster.health(request, RequestOptions.DEFAULT)
    response.getIndices.keySet().asScala.toList
  }

  def createIndex(index: String): Unit = {
    if (indicesExists(index)) {
      println(s"This index $index is already created.")
    }
    else {
      val request = new CreateIndexRequest(index)
      val indices = client.indices
      indices.create(request, RequestOptions.DEFAULT)
    }
  }

  def createIndex(esIndex: String, esType: String, mapping: String): Unit = {
    if (!indicesExists(esIndex)) {
      val request = new CreateIndexRequest(esIndex)
      val indices = client.indices
      request.mapping(esType, mapping, XContentType.JSON)
      indices.create(request, RequestOptions.DEFAULT)
    } else {
      println(s"This index $esIndex is already created.")
    }
  }

  def deleteIndex(index: String): Unit = {
    val request = new DeleteIndexRequest(index)
    request.timeout(TimeValue.timeValueMinutes(2))
    client.indices().delete(request, RequestOptions.DEFAULT)
  }

  def indicesExists(indices: String*): Boolean = {
    val request = new GetIndexRequest()
    request.indices(indices: _*)
    client.indices().exists(request, RequestOptions.DEFAULT)
  }

  def openIndices(indices: String*): OpenIndexResponse = {
    val request = new OpenIndexRequest(indices: _*)
    client.indices.open(request, RequestOptions.DEFAULT)
  }

  def closeIndices(indices: String*): AcknowledgedResponse = {
    val request = new CloseIndexRequest(indices: _*)
    client.indices.close(request, RequestOptions.DEFAULT)
  }
}
