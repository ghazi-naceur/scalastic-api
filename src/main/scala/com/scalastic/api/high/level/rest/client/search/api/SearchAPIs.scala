package com.scalastic.api.high.level.rest.client.search.api

import com.scalastic.api.client.ElasticClient
import com.scalastic.api.utils.DataExtractor
import org.elasticsearch.action.search.{SearchRequest, SearchScrollRequest}
import org.elasticsearch.client.{RequestOptions, RestHighLevelClient}
import org.elasticsearch.common.unit.TimeValue
import org.elasticsearch.index.query.{MoreLikeThisQueryBuilder, Operator, QueryBuilder, QueryBuilders}
import org.elasticsearch.search.SearchHit
import org.elasticsearch.search.builder.SearchSourceBuilder
import org.elasticsearch.search.sort.{FieldSortBuilder, SortOrder}

import scala.collection.JavaConverters._
import scala.collection.mutable.ListBuffer


object SearchAPIs {

  private val client: RestHighLevelClient = ElasticClient.client
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

  def searchWithPrefixQuery(index: String, field: String, value: String): List[Map[String, Any]] = {
    searchWithQueryBuilder(QueryBuilders.prefixQuery(field, value), index)
  }

  def searchWithMoreLikeThisQuery(index: String, fields: Array[String], likeTexts: Array[String], likeItems: Array[MoreLikeThisQueryBuilder.Item]): List[Map[String, Any]] = {
    searchWithQueryBuilder(QueryBuilders.moreLikeThisQuery(fields, likeTexts, likeItems).minTermFreq(1)
      .maxQueryTerms(12), index)
  }

  def searchWithRangeQuery(index: String, field: String, lte: Option[Int], gte: Option[Int]): List[Map[String, Any]] = {
    val query = QueryBuilders.rangeQuery(field)
    if (lte != null) {
      query.lte(lte.get)
    }
    if (gte != null) {
      query.gte(gte.get)
    }
    searchWithQueryBuilder(query, index)
  }

  def searchWithExistsQuery(index: String, field: String): List[Map[String, Any]] = {
    searchWithQueryBuilder(QueryBuilders.existsQuery(field), index)
  }

  def searchWithWildcardQuery(index: String, field: String, value: String): List[Map[String, Any]] = {
    searchWithQueryBuilder(QueryBuilders.wildcardQuery(field, value), index)
  }

  def searchWithRegexQuery(index: String, field: String, value: String): List[Map[String, Any]] = {
    searchWithQueryBuilder(QueryBuilders.regexpQuery(field, value), index)
  }

  def searchWithFuzzyQuery(index: String, field: String, value: String): List[Map[String, Any]] = {
    searchWithQueryBuilder(QueryBuilders.fuzzyQuery(field, value), index)
  }

  def searchWithIdsQuery(index: String, ids: String*): List[Map[String, Any]] = {
    searchWithQueryBuilder(QueryBuilders.idsQuery().addIds(ids: _*), index)
  }

  def searchWithConstantScoreTermQuery(index: String, field: String, value: String): List[Map[String, Any]] = {
    searchWithQueryBuilder(QueryBuilders.constantScoreQuery(QueryBuilders.termQuery(field, value)), index)
  }

  def searchWithBoolMustMatchPhraseQuery(index: String, searchCriteria: Map[String, Any]): List[Map[String, Any]] = {
    val query = QueryBuilders.boolQuery()
    for ((k, v) <- searchCriteria) {
      query.must(QueryBuilders.matchPhraseQuery(k, v))
    }
    searchWithQueryBuilder(query, index)
  }

  def searchWithBoolMustNotMatchPhraseQuery(index: String, searchCriteria: Map[String, Any]): List[Map[String, Any]] = {
    val query = QueryBuilders.boolQuery()
    for ((k, v) <- searchCriteria) {
      query.mustNot(QueryBuilders.matchPhraseQuery(k, v))
    }
    searchWithQueryBuilder(query, index)
  }

  def searchWithBoolShouldMatchPhraseQuery(index: String, searchCriteria: Map[String, Any]): List[Map[String, Any]] = {
    val query = QueryBuilders.boolQuery()
    for ((k, v) <- searchCriteria) {
      query.should(QueryBuilders.matchPhraseQuery(k, v))
    }
    searchWithQueryBuilder(query, index)
  }

  def searchWithBoolFilterMatchPhraseQuery(index: String, searchCriteria: Map[String, Any]): List[Map[String, Any]] = {
    val query = QueryBuilders.boolQuery()
    for ((k, v) <- searchCriteria) {
      query.filter(QueryBuilders.matchPhraseQuery(k, v))
    }
    searchWithQueryBuilder(query, index)
  }

  def searchWithDisMaxTermQuery(index: String, searchCriteria: Map[String, Any]): List[Map[String, Any]] = {
    val query = QueryBuilders.disMaxQuery()
    for ((k, v) <- searchCriteria) {
      query.add(QueryBuilders.termQuery(k, v))
    }
    query.boost(1.2f).tieBreaker(0.7f)
    searchWithQueryBuilder(query, index)
  }

  def searchWithBoostingTermQuery(index: String, positiveField: String, positiveValue: String, negativeField: String, negativeValue: String): List[Map[String, Any]] = {
    val query = QueryBuilders.boostingQuery(QueryBuilders.termQuery(positiveField, positiveValue), QueryBuilders.termQuery(negativeField, negativeValue))
    query.negativeBoost(0.2f)
    searchWithQueryBuilder(query, index)
  }

  def findAllWithScanAndScroll(index: String): List[Map[String, Any]] = {
    var result = ListBuffer[Map[String, Any]]()
    /**
      * var scrollResp = client.prepareSearch(index)
      * .setScroll(new TimeValue(60000))
      * .setQuery(QueryBuilders.matchAllQuery())
      * .setSize(100).get()
      * do {
      * for (hit: SearchHit <- scrollResp.getHits.getHits) {
      * result += hit.getSourceAsMap.asScala.map(kv => (kv._1, kv._2)).toMap
      * }
      * scrollResp = client.prepareSearchScroll(scrollResp.getScrollId).setScroll(new TimeValue(60000)).execute().actionGet()
      * } while (scrollResp.getHits.getHits.length != 0)
      */
    val request = new SearchRequest(index).scroll(new TimeValue(60000))
    val searchSourceBuilder = new SearchSourceBuilder()
    searchSourceBuilder.query(QueryBuilders.matchAllQuery())
    searchSourceBuilder.sort(FieldSortBuilder.DOC_FIELD_NAME, SortOrder.ASC)
    request.source(searchSourceBuilder)

    var scrollResp = client.search(request, RequestOptions.DEFAULT)

    do {
      for (hit: SearchHit <- scrollResp.getHits.getHits) {
        result += hit.getSourceAsMap.asScala.map(kv => (kv._1, kv._2)).toMap
      }
      //      scrollResp = client.searchScroll(new SearchScrollRequest(scrollResp.getScrollId), RequestOptions.DEFAULT).scroll(new TimeValue(60000))
      scrollResp = client.scroll(new SearchScrollRequest(scrollResp.getScrollId).scroll(new TimeValue(60000)), RequestOptions.DEFAULT)
    } while (scrollResp.getHits.getHits.length != 0)

    result.toList
  }

  // This is the generic one !
  def searchWithQueryBuilder(queryBuilder: QueryBuilder, indices: String*): List[Map[String, Any]] = {
    val searchSourceBuilder = new SearchSourceBuilder
    val builder = searchSourceBuilder.query(queryBuilder).from(from).size(size)
    val searchRequest = new SearchRequest(indices: _*)
    DataExtractor.extractResult(searchRequest, builder)
  }
}
