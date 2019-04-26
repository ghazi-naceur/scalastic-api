package com.scalastic.api.utils

import com.scalastic.api.client.ElasticClient
import org.elasticsearch.action.search.SearchRequest
import org.elasticsearch.client.{RequestOptions, RestHighLevelClient}
import org.elasticsearch.search.SearchHit
import org.elasticsearch.search.builder.SearchSourceBuilder

import scala.collection.JavaConverters._
import scala.collection.mutable.ListBuffer


object DataExtractor {

  private val client: RestHighLevelClient = ElasticClient.client

  def extractResult(searchRequest: SearchRequest, builder: SearchSourceBuilder): List[Map[String, Any]] = {
    searchRequest.source(builder)
    extractResult(searchRequest)
  }

  def extractResult(searchRequest: SearchRequest): List[Map[String, Any]] = {
    var result = ListBuffer[Map[String, Any]]()
    val response = client.search(searchRequest, RequestOptions.DEFAULT)
    for (hit: SearchHit <- response.getHits.getHits) {
      result += hit.getSourceAsMap.asScala.map(kv => (kv._1, kv._2)).toMap
    }
    result.toList
  }
}
