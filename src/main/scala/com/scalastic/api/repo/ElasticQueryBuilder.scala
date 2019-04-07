package com.scalastic.api.repo

import java.util.UUID

import com.scalastic.api.client.ElasticClient
import org.elasticsearch.action.delete.{DeleteRequest, DeleteResponse}
import org.elasticsearch.action.get.GetRequest
import org.elasticsearch.action.index.{IndexRequest, IndexResponse}
import org.elasticsearch.action.search.SearchRequest
import org.elasticsearch.action.update.{UpdateRequest, UpdateResponse}
import org.elasticsearch.client.transport.TransportClient
import org.elasticsearch.client.{RequestOptions, RestHighLevelClient}
import org.elasticsearch.common.unit.TimeValue
import org.elasticsearch.common.xcontent.XContentFactory
import org.elasticsearch.index.query.QueryBuilders
import org.elasticsearch.search.SearchHit
import org.elasticsearch.search.builder.SearchSourceBuilder

import scala.collection.JavaConverters._
import scala.collection.mutable.ListBuffer

/**
  * Created by Ghazi Naceur on 06/04/2019
  * Email: ghazi.ennacer@gmail.com
  */
object ElasticQueryBuilder {

  val client: RestHighLevelClient = ElasticClient.client
  val transportClient: TransportClient = ElasticClient.transportClient
  val from = 0
  val size = 100

  def insert(es_index: String, es_type: String, entity: Map[String, Any]): IndexResponse = {
    val request = new IndexRequest(es_index, es_type, UUID.randomUUID().toString)
    val builder = XContentFactory.jsonBuilder
    builder.startObject
    for ((k, v) <- entity) {
      builder.field(k, v)
    }
    builder.endObject
    request.source(builder)
    client.index(request, RequestOptions.DEFAULT)
  }

  def update(es_index: String, es_type: String, es_id: String, map: Map[String, Any]): UpdateResponse = {
    val updateRequest = new UpdateRequest(es_index, es_type, es_id)
    val builder = XContentFactory.jsonBuilder
    builder.startObject
    for ((k, v) <- map) {
      builder.field(k, v)
    }
    builder.endObject
    updateRequest.doc(builder)
    client.update(updateRequest, RequestOptions.DEFAULT)
  }

  def getById(es_index: String, es_type: String, es_id: String): Map[String, Any] = {
    val getRequest = new GetRequest(es_index, es_type, es_id)
    val response = client.get(getRequest, RequestOptions.DEFAULT)
    // asScala : to have a mutable map
    // map(kv => (kv._1,kv._2)).toMap : to get an immutable map
    response.getSource.asScala.map(kv => (kv._1, kv._2)).toMap
  }

  def getAll(es_index: String): List[Map[String, Any]] = {
    var result = ListBuffer[Map[String, Any]]()
    val searchSourceBuilder = new SearchSourceBuilder
    val builder = searchSourceBuilder.query(QueryBuilders.matchAllQuery())
    val searchRequest = new SearchRequest(es_index)
    searchRequest.source(builder)
    val response = client.search(searchRequest, RequestOptions.DEFAULT)
    for (hit: SearchHit <- response.getHits.getHits) {
      result += hit.getSourceAsMap.asScala.map(kv => (kv._1, kv._2)).toMap
    }
    result.toList
  }

  def delete(es_index: String, es_type: String, id: String): DeleteResponse = {
    val deleteRequest = new DeleteRequest(es_index, es_type, id)
    client.delete(deleteRequest, RequestOptions.DEFAULT)
  }

  def findAll(es_index: String): List[Map[String, Any]] = {
    var result = ListBuffer[Map[String, Any]]()
    var scrollResp = transportClient.prepareSearch(es_index)
      .setScroll(new TimeValue(60000))
      .setQuery(QueryBuilders.matchAllQuery())
      .setSize(100).get()
    do {
      for (hit: SearchHit <- scrollResp.getHits.getHits) {
        result += hit.getSourceAsMap.asScala.map(kv => (kv._1, kv._2)).toMap
      }
      scrollResp = transportClient.prepareSearchScroll(scrollResp.getScrollId).setScroll(new TimeValue(60000)).execute().actionGet()
    } while (scrollResp.getHits.getHits.length != 0)

    result.toList
  }

  def search(es_index: String, searchCriteria: Map[String, Any]): List[Map[String, Any]] = {
    var result = ListBuffer[Map[String, Any]]()
    val searchRequest = new SearchRequest(es_index)
    val query = QueryBuilders.boolQuery()
    for ((k, v) <- searchCriteria) {
      query.must(QueryBuilders.matchPhraseQuery(k, v))
    }
    val builder = new SearchSourceBuilder().query(query).from(from).size(size)
    searchRequest.source(builder)
    val response = client.search(searchRequest, RequestOptions.DEFAULT)
    for (hit: SearchHit <- response.getHits.getHits) {
      result += hit.getSourceAsMap.asScala.map(kv => (kv._1, kv._2)).toMap
    }
    result.toList
  }

  def getEntitiesFromIndexUsingMatchQuery(es_index: String, field: String, value: String): List[Map[String, Any]] = {
    var result = ListBuffer[Map[String, Any]]()
    val searchRequest = new SearchRequest(es_index)
    val builder = new SearchSourceBuilder().query(QueryBuilders.matchQuery(field, value)).from(from).size(size)
    searchRequest.source(builder)
    val response = client.search(searchRequest, RequestOptions.DEFAULT)
    for (hit: SearchHit <- response.getHits.getHits) {
      result += hit.getSourceAsMap.asScala.map(kv => (kv._1, kv._2)).toMap
    }
    result.toList
  }

  def getEntitiesFromIndexUsingTermQuery(es_index: String, field: String, value: String): List[Map[String, Any]] = {
    var result = ListBuffer[Map[String, Any]]()
    val searchRequest = new SearchRequest(es_index)
    val builder = new SearchSourceBuilder().query(QueryBuilders.termQuery(field, value)).from(from).size(size)
    searchRequest.source(builder)
    val response = client.search(searchRequest, RequestOptions.DEFAULT)
    for (hit: SearchHit <- response.getHits.getHits) {
      result += hit.getSourceAsMap.asScala.map(kv => (kv._1, kv._2)).toMap
    }
    result.toList
  }

  def getDocumentsFromIndexUsingPrefixQuery(es_index: String, field: String, value: String): List[Map[String, Any]] = {
    var result = ListBuffer[Map[String, Any]]()
    val searchRequest = new SearchRequest(es_index)
    val builder = new SearchSourceBuilder().query(QueryBuilders.prefixQuery(field, value)).from(from).size(size)
    searchRequest.source(builder)
    val response = client.search(searchRequest, RequestOptions.DEFAULT)
    for (hit: SearchHit <- response.getHits.getHits) {
      result += hit.getSourceAsMap.asScala.map(kv => (kv._1, kv._2)).toMap
    }
    result.toList
  }
}
