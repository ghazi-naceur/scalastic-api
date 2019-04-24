package com.scalastic.api.high.level.rest.client.document.api

import java.util.UUID

import com.scalastic.api.client.ElasticClient
import org.elasticsearch.action.index.{IndexRequest, IndexResponse}
import org.elasticsearch.client.{RequestOptions, RestHighLevelClient}
import org.elasticsearch.common.xcontent.{XContentBuilder, XContentFactory, XContentType}

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
}
