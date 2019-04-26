package com.scalastic.api.repo

import java.util.UUID

import com.scalastic.api.client.ElasticClient
import org.elasticsearch.action.ActionListener
import org.elasticsearch.action.bulk.{BulkRequestBuilder, BulkResponse}
import org.elasticsearch.action.get.{GetResponse, MultiGetItemResponse}
import org.elasticsearch.action.search.{SearchRequest, SearchRequestBuilder}
import org.elasticsearch.client.transport.TransportClient
import org.elasticsearch.client.{RequestOptions, RestHighLevelClient}
import org.elasticsearch.common.unit.TimeValue
import org.elasticsearch.index.query.QueryBuilders
import org.elasticsearch.index.reindex._
import org.elasticsearch.search.SearchHit
import org.elasticsearch.search.aggregations.{Aggregation, AggregationBuilder}
import org.elasticsearch.search.builder.SearchSourceBuilder

import scala.collection.JavaConverters._
import scala.collection.mutable.ListBuffer

/**
  * Created by Ghazi Naceur on 06/04/2019
  * Email: ghazi.ennacer@gmail.com
  */
object ElasticQueryBuilder {

  private val client: RestHighLevelClient = ElasticClient.client
  private val transportClient: TransportClient = ElasticClient.transportClient
  private val from = 0
  private val size = 100

  // Getting the first page


  def deleteByQuery(criteria: Map[String, Any], indexes: String*): BulkByScrollResponse = {
    val query = DeleteByQueryAction.INSTANCE.newRequestBuilder(transportClient)
    for ((k, v) <- criteria) {
      query.filter(QueryBuilders.matchQuery(k, v))
    }
    query.source(indexes: _*).get()
  }

  def asyncDeleteByQuery(criteria: Map[String, Any], indexes: String*): Unit = {
    val query = DeleteByQueryAction.INSTANCE.newRequestBuilder(transportClient)
    for ((k, v) <- criteria) {
      query.filter(QueryBuilders.matchQuery(k, v))
    }
      .source(indexes: _*)
      .execute(new ActionListener[BulkByScrollResponse]() {
        @Override
        def onResponse(response: BulkByScrollResponse) {
          response.getDeleted
        }

        @Override
        def onFailure(e: Exception) {
          throw new Exception(e)
        }
      })
  }

  def bulk(esIndex: String, esType: String, entities: List[Map[String, Any]]): Unit = {
    val bulkRequest: BulkRequestBuilder = transportClient.prepareBulk()

    for (i <- entities.indices) {
      val builder = transportClient.prepareIndex(esIndex, esType, UUID.randomUUID().toString)
      builder.setSource(entities(i).asJava)
      bulkRequest.add(builder)
    }

    val bulkResponse: BulkResponse = bulkRequest.get()
    if (bulkResponse.hasFailures) {
      // process failures by iterating through each bulk response item
    }
  }

  def reindex(sourceIndex: String, destinationIndex: String, filter: Map[String, Any]): BulkByScrollResponse = {
    //  The "destinationIndex" must be not already created, because indices in ES6 supports only 1 type.
    val builder = ReindexAction.INSTANCE.newRequestBuilder(transportClient)
      .source(sourceIndex)
      .destination(destinationIndex)
    for ((k, v) <- filter) {
      builder.filter(QueryBuilders.matchQuery(k, v))
    }
    builder.get()
  }

  // Scan and scroll
  def findAll(index: String): List[Map[String, Any]] = {
    var result = ListBuffer[Map[String, Any]]()
    var scrollResp = transportClient.prepareSearch(index)
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

  def getDocsWithFuzzyQuery(index: String, field: String, value: String): List[Map[String, Any]] = {
    val searchRequest = new SearchRequest(index)
    val builder = new SearchSourceBuilder().query(QueryBuilders.fuzzyQuery(field, value)).from(from).size(size)
    extractResult(searchRequest, builder)
  }

  def getDocsWithIdsQuery(index: String, ids: String*): List[Map[String, Any]] = {
    val searchRequest = new SearchRequest(index)
    val builder = new SearchSourceBuilder().query(QueryBuilders.idsQuery().addIds(ids: _*)).from(from).size(size)
    extractResult(searchRequest, builder)
  }

  private def extractResult(searchRequest: SearchRequest, builder: SearchSourceBuilder): List[Map[String, Any]] = {
    searchRequest.source(builder)
    extractResult(searchRequest)
  }

  private def extractResult(searchRequest: SearchRequest): List[Map[String, Any]] = {
    var result = ListBuffer[Map[String, Any]]()
    val response = client.search(searchRequest, RequestOptions.DEFAULT)
    for (hit: SearchHit <- response.getHits.getHits) {
      result += hit.getSourceAsMap.asScala.map(kv => (kv._1, kv._2)).toMap
    }
    result.toList
  }

  // We consider that indices(i), types(i) and ids(i) are associated to the same entity
  def getDocsWithMultiGet(indices: List[String], types: List[String], ids: List[String]): List[Map[String, Any]] = {
    if (indices.length != types.length || indices.length != ids.length || types.length != ids.length) {
      throw new IllegalArgumentException(s"Indices, types and ids must have the same size ! indices.length = ${indices.length} , types.length = ${types.length} , ids.length = ${ids.length}")
    }

    var result = ListBuffer[Map[String, Any]]()
    val multiGetItemResponses = transportClient.prepareMultiGet()
    for (i <- indices.indices) {
      multiGetItemResponses.add(indices(i), types(i), ids(i))
    }

    for (itemResponse: MultiGetItemResponse <- multiGetItemResponses.get().getResponses) {
      val response: GetResponse = itemResponse.getResponse
      if (response.isExists) {
        result += response.getSourceAsMap.asScala.map(kv => (kv._1, kv._2)).toMap
      }
    }
    result.toList
  }

  def multiSearch(builders: List[SearchRequestBuilder]): List[Map[String, Any]] = {
    var result = ListBuffer[Map[String, Any]]()
    val builder = transportClient.prepareMultiSearch()
    for (b <- builders) {
      builder.add(b)
    }
    val response = builder.get()

    for (item <- response.getResponses) {
      val response = item.getResponse
      for (hit: SearchHit <- response.getHits.getHits) {
        result += hit.getSourceAsMap.asScala.map(kv => (kv._1, kv._2)).toMap
      }
    }
    result.toList
  }

  def searchWithAggregations(builders: Map[String, AggregationBuilder], indices: String*) = {
    var aggs = ListBuffer[Aggregation]()
    val builder = transportClient.prepareSearch(indices: _*)
    for (b <- builders) {
      builder.addAggregation(b._2)
    }
    val response = builder.get()

    for (b <- builders) {
      aggs += response.getAggregations.get(b._1)
    }
    aggs.toList
  }

}
