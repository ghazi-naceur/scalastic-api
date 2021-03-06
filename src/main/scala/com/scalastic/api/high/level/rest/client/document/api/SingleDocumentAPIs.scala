package com.scalastic.api.high.level.rest.client.document.api

import java.util.UUID

import com.scalastic.api.client.ElasticClient
import org.elasticsearch.action.delete.{DeleteRequest, DeleteResponse}
import org.elasticsearch.action.get.GetRequest
import org.elasticsearch.action.index.{IndexRequest, IndexResponse}
import org.elasticsearch.action.update.{UpdateRequest, UpdateResponse}
import org.elasticsearch.client.{RequestOptions, RestHighLevelClient}
import org.elasticsearch.common.xcontent.{XContentBuilder, XContentFactory, XContentType}
import org.elasticsearch.search.fetch.subphase.FetchSourceContext

import scala.collection.JavaConverters._

object SingleDocumentAPIs {

  private val client: RestHighLevelClient = ElasticClient.client

  def index(esIndex: String, esType: String, entity: Map[String, Any]): IndexResponse = {
    val request = new IndexRequest(esIndex, esType, UUID.randomUUID().toString)
    val builder = XContentFactory.jsonBuilder
    builder.startObject
    for ((k, v) <- entity) {
      builder.field(k, v)
    }
    builder.endObject
    request.source(builder)
    client.index(request, RequestOptions.DEFAULT)
  }

  def index(esIndex: String, esType: String, jsonSource: String): IndexResponse = {
    val request = new IndexRequest(esIndex, esType, UUID.randomUUID().toString)
    request.source(jsonSource, XContentType.JSON)
    client.index(request, RequestOptions.DEFAULT)
  }

  def index(esIndex: String, esType: String, jsonSource: XContentBuilder): IndexResponse = {
    val request = new IndexRequest(esIndex, esType, UUID.randomUUID().toString)
    request.source(jsonSource)
    client.index(request, RequestOptions.DEFAULT)
  }

  def getById(esIndex: String, esType: String, esId: String): Map[String, Any] = {
    val getRequest = new GetRequest(esIndex, esType, esId)
    val response = client.get(getRequest, RequestOptions.DEFAULT)
    // asScala : to have a mutable map
    // map(kv => (kv._1,kv._2)).toMap : to get an immutable map
    response.getSource.asScala.map(kv => (kv._1, kv._2)).toMap
  }

  def exists(esIndex: String, esType: String, esId: String): Boolean = {
    val getRequest = new GetRequest(esIndex, esType, esId)
    getRequest.fetchSourceContext(new FetchSourceContext(false))
    getRequest.storedFields("_none_")
    client.exists(getRequest, RequestOptions.DEFAULT)
  }

  def delete(esIndex: String, esType: String, esId: String): DeleteResponse = {
    val deleteRequest = new DeleteRequest(esIndex, esType, esId)
    client.delete(deleteRequest, RequestOptions.DEFAULT)
  }

  def update(esIndex: String, esType: String, esId: String, map: Map[String, Any]): UpdateResponse = {
    val updateRequest = new UpdateRequest(esIndex, esType, esId)
    val builder = XContentFactory.jsonBuilder
    builder.startObject
    for ((k, v) <- map) {
      builder.field(k, v)
    }
    builder.endObject
    updateRequest.doc(builder)
    client.update(updateRequest, RequestOptions.DEFAULT)
  }
}
