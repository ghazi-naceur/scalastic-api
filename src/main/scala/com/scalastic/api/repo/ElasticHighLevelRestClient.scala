package com.scalastic.api.repo

import com.scalastic.api.client.ElasticClient
import org.elasticsearch.action.admin.cluster.health.ClusterHealthRequest
import org.elasticsearch.action.admin.indices.close.CloseIndexRequest
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest
import org.elasticsearch.action.admin.indices.get.GetIndexRequest
import org.elasticsearch.action.admin.indices.open.{OpenIndexRequest, OpenIndexResponse}
import org.elasticsearch.action.admin.indices.refresh.{RefreshRequest, RefreshResponse}
import org.elasticsearch.action.admin.indices.shrink.{ResizeRequest, ResizeResponse}
import org.elasticsearch.action.support.master.AcknowledgedResponse
import org.elasticsearch.client.transport.TransportClient
import org.elasticsearch.client.{RequestOptions, RestClientBuilder, RestHighLevelClient}
import org.elasticsearch.common.settings.Settings
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

  def createIndex(index: String, settings: Settings.Builder): Unit = {
    if (indicesExists(index)) {
      println(s"This index $index is already created.")
    }
    else {
      val request = new CreateIndexRequest(index)
      if (settings != null) {
        request.settings(settings)
      }
      val indices = client.indices
      indices.create(request, RequestOptions.DEFAULT)
    }
  }

  def createIndex(esIndex: String, esType: String, settings: Settings.Builder, mapping: String): Unit = {
    if (!indicesExists(esIndex)) {
      val request = new CreateIndexRequest(esIndex)
      if (settings != null) {
        request.settings(settings)
      }
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

  def shrinkIndex(targetIndex: String, sourceIndex: String): ResizeResponse = {
    val request = new ResizeRequest(targetIndex, sourceIndex)
    //    request.setCopySettings(true)
    client.indices().shrink(request, RequestOptions.DEFAULT)
  }

  /** OPEN BUG : https://github.com/elastic/elasticsearch/issues/29652#
    *
    * @param targetIndex  The target index
    * @param sourceIndex  The source index
    * @param shardsNumber : The requested number of primary shards in the target index must be a
    *                     factor of the number of shards in the source index. For example an index
    *                     with 8 primary shards can be shrunk into 4, 2 or 1 primary shards or an
    *                     index with 15 primary shards can be shrunk into 5, 3 or 1.
    * @return ResizeResponse
    */
  //  def splitIndex(targetIndex: String, sourceIndex: String, shardsNumber: Int): ResizeResponse = {
  //    val request = new ResizeRequest(targetIndex, sourceIndex)
  //    request.setResizeType(ResizeType.SPLIT)
  //    request.getTargetIndexRequest.settings(Settings.builder()
  //      .put("index.number_of_shards", shardsNumber))
  //    client.indices().split(request, RequestOptions.DEFAULT)
  //  }

  def refreshIndex(indices: String*): RefreshResponse = {
    val request = new RefreshRequest(indices: _*)
    client.indices().refresh(request, RequestOptions.DEFAULT)
  }
}
