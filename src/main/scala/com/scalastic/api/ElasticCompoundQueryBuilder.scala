package com.scalastic.api

import com.scalastic.api.client.ElasticClient
import org.elasticsearch.action.search.SearchRequest
import org.elasticsearch.client.{RequestOptions, RestHighLevelClient}
import org.elasticsearch.index.query.QueryBuilders
import org.elasticsearch.search.SearchHit
import org.elasticsearch.search.builder.SearchSourceBuilder

import scala.collection.JavaConverters._
import scala.collection.mutable.ListBuffer


/**
  * Created by Ghazi Naceur on 17/04/2019
  * Email: ghazi.ennacer@gmail.com
  */
object ElasticCompoundQueryBuilder {

  private val client: RestHighLevelClient = ElasticClient.client
  private val from = 0
  private val size = 100

  def getDocsWithConstantScoreQuery(index: String, field: String, value: String): List[Map[String, Any]] = {
    val searchRequest = new SearchRequest(index)
    val builder = new SearchSourceBuilder().query(QueryBuilders.constantScoreQuery(QueryBuilders.termQuery(field, value))).from(from).size(size)
    extractResult(searchRequest, builder)
  }

  def getDocsWithBoolMustQuery(index: String, searchCriteria: Map[String, Any]): List[Map[String, Any]] = {
    val searchRequest = new SearchRequest(index)
    val query = QueryBuilders.boolQuery()
    for ((k, v) <- searchCriteria) {
      query.must(QueryBuilders.matchPhraseQuery(k, v))
    }
    val builder = new SearchSourceBuilder().query(query).from(from).size(size)
    extractResult(searchRequest, builder)
  }

  def getDocsWithBoolMustNotQuery(index: String, searchCriteria: Map[String, Any]): List[Map[String, Any]] = {
    val searchRequest = new SearchRequest(index)
    val query = QueryBuilders.boolQuery()
    for ((k, v) <- searchCriteria) {
      query.mustNot(QueryBuilders.matchPhraseQuery(k, v))
    }
    val builder = new SearchSourceBuilder().query(query).from(from).size(size)
    extractResult(searchRequest, builder)
  }

  def getDocsWithBoolShouldQuery(index: String, searchCriteria: Map[String, Any]): List[Map[String, Any]] = {
    val searchRequest = new SearchRequest(index)
    val query = QueryBuilders.boolQuery()
    for ((k, v) <- searchCriteria) {
      query.should(QueryBuilders.matchPhraseQuery(k, v))
    }
    val builder = new SearchSourceBuilder().query(query).from(from).size(size)
    extractResult(searchRequest, builder)
  }

  def getDocsWithBoolFilterQuery(index: String, searchCriteria: Map[String, Any]): List[Map[String, Any]] = {
    val searchRequest = new SearchRequest(index)
    val query = QueryBuilders.boolQuery()
    for ((k, v) <- searchCriteria) {
      query.filter(QueryBuilders.matchPhraseQuery(k, v))
    }
    val builder = new SearchSourceBuilder().query(query).from(from).size(size)
    extractResult(searchRequest, builder)
  }

  def getDocsWithDisMaxQuery(index: String, searchCriteria: Map[String, Any]): List[Map[String, Any]] = {
    val searchRequest = new SearchRequest(index)
    val query = QueryBuilders.disMaxQuery()
    for ((k, v) <- searchCriteria) {
      query.add(QueryBuilders.termQuery(k, v))
    }
    query.boost(1.2f).tieBreaker(0.7f)
    val builder = new SearchSourceBuilder().query(query).from(from).size(size)
    extractResult(searchRequest, builder)
  }

  def getDocsWithBoostingQuery(index: String, positiveField: String, positiveValue: String, negativeField: String, negativeValue: String): List[Map[String, Any]] = {
    val searchRequest = new SearchRequest(index)
    val query = QueryBuilders.boostingQuery(QueryBuilders.termQuery(positiveField, positiveValue), QueryBuilders.termQuery(negativeField, negativeValue))
    query.negativeBoost(0.2f)
    val builder = new SearchSourceBuilder().query(query).from(from).size(size)
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
