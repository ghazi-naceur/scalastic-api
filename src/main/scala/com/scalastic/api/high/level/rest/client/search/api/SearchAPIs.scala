package com.scalastic.api.high.level.rest.client.search.api

import com.scalastic.api.utils.DataExtractor
import org.elasticsearch.action.search.SearchRequest
import org.elasticsearch.index.query.{Operator, QueryBuilder, QueryBuilders}
import org.elasticsearch.search.builder.SearchSourceBuilder


object SearchAPIs {

  private val from = 0
  private val size = 100

  def searchWithMatchAllQuery(indices: String*): List[Map[String, Any]] = {
    val searchSourceBuilder = new SearchSourceBuilder
    val builder = searchSourceBuilder.query(QueryBuilders.matchAllQuery())
    val searchRequest = new SearchRequest(indices: _*)
    DataExtractor.extractResult(searchRequest, builder)
  }

  def searchWithMatchQuery(field: String, value: String, indices: String*): List[Map[String, Any]] = {
    searchWithQueryBuilder(QueryBuilders.matchQuery(field, value), indices: _*)
  }

  def searchWithBoolMustMatchPhraseQuery(searchCriteria: Map[String, Any], indices: String*): List[Map[String, Any]] = {
    val query = QueryBuilders.boolQuery()
    for ((k, v) <- searchCriteria) {
      query.must(QueryBuilders.matchPhraseQuery(k, v))
    }
    searchWithQueryBuilder(query, indices: _*)
  }

  def searchWithMultiMatchQuery(indices: Array[String], value: String, fieldNames: String*): List[Map[String, Any]] = {
    searchWithQueryBuilder(QueryBuilders.multiMatchQuery(value, fieldNames: _*), indices: _*)
  }

  def searchWithTermsQuery(indices: Array[String], field: String, values: String*): List[Map[String, Any]] = {
    searchWithQueryBuilder(QueryBuilders.termsQuery(field, values: _*), indices: _*)
  }

  def searchWithTermQuery(indices: Array[String], field: String, value: String): List[Map[String, Any]] = {
    searchWithQueryBuilder(QueryBuilders.termQuery(field, value), indices: _*)
  }

  def searchWithCommonTermsQuery(indices: Array[String], field: String, value: String): List[Map[String, Any]] = {
    searchWithQueryBuilder(QueryBuilders.commonTermsQuery(field, value), indices: _*)
  }

  def searchWithQueryStringQuery(index: String, defaultField: String, defaultOperator: Operator, query: Array[String]): List[Map[String, Any]] = {
    val fieldsWithOperator = query.mkString(" " + defaultOperator.toString + " ")
    searchWithQueryBuilder(QueryBuilders.queryStringQuery(fieldsWithOperator).defaultField(defaultField), index)
  }

  def searchWithSimpleQueryStringQuery(index: String, defaultOperator: Operator, query: Array[String]): List[Map[String, Any]] = {
    val fieldsWithOperator = query.mkString(" " + defaultOperator.toString + " ")
    searchWithQueryBuilder(QueryBuilders.simpleQueryStringQuery(fieldsWithOperator), index)
  }

  // This is the generic one !
  def searchWithQueryBuilder(queryBuilder: QueryBuilder, indices: String*): List[Map[String, Any]] = {
    val searchSourceBuilder = new SearchSourceBuilder
    val builder = searchSourceBuilder.query(queryBuilder).from(from).size(size)
    val searchRequest = new SearchRequest(indices: _*)
    DataExtractor.extractResult(searchRequest, builder)
  }
}
