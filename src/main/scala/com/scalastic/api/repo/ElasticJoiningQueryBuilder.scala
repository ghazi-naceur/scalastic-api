package com.scalastic.api.repo

import com.scalastic.api.client.ElasticClient
import org.apache.lucene.search.join.ScoreMode
import org.elasticsearch.action.search.SearchRequest
import org.elasticsearch.client.transport.TransportClient
import org.elasticsearch.client.{RequestOptions, RestHighLevelClient}
import org.elasticsearch.index.query.QueryBuilders
import org.elasticsearch.search.SearchHit
import org.elasticsearch.search.builder.SearchSourceBuilder

import scala.collection.JavaConverters._
import scala.collection.mutable.ListBuffer

/**
  * Created by Ghazi Naceur on 18/04/2019
  * Email: ghazi.ennacer@gmail.com
  */
object ElasticJoiningQueryBuilder {

  private val client: RestHighLevelClient = ElasticClient.client
  private val transportClient: TransportClient = ElasticClient.transportClient
  private val from = 0
  private val size = 100

  def getDocsWithNestedQuery(index: String, path: String, searchCriteria: Map[String, Any]): List[Map[String, Any]] = {
    val searchRequest = new SearchRequest(index)
    val query = QueryBuilders.boolQuery()
    for ((k, v) <- searchCriteria) {
      query.must(QueryBuilders.matchPhraseQuery(path + "." + k, v))
    }
    val builder = new SearchSourceBuilder().query(QueryBuilders.nestedQuery(path, query, ScoreMode.Max)).from(from).size(size)
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
}
