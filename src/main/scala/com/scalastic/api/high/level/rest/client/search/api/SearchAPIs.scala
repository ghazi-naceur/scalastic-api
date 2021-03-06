package com.scalastic.api.high.level.rest.client.search.api

import java.util

import com.scalastic.api.client.ElasticClient
import com.scalastic.api.utils.DataExtractor
import org.elasticsearch.action.explain.{ExplainRequest, ExplainResponse}
import org.elasticsearch.action.fieldcaps.{FieldCapabilities, FieldCapabilitiesRequest}
import org.elasticsearch.action.search._
import org.elasticsearch.client.{Request, RequestOptions, RestClient, RestHighLevelClient}
import org.elasticsearch.common.unit.TimeValue
import org.elasticsearch.index.query.{MoreLikeThisQueryBuilder, Operator, QueryBuilder, QueryBuilders}
import org.elasticsearch.index.rankeval._
import org.elasticsearch.script.ScriptType
import org.elasticsearch.script.mustache.{MultiSearchTemplateRequest, SearchTemplateRequest}
import org.elasticsearch.search.SearchHit
import org.elasticsearch.search.builder.SearchSourceBuilder
import org.elasticsearch.search.sort.{FieldSortBuilder, SortOrder}

import scala.collection.JavaConverters._
import scala.collection.mutable.ListBuffer


object SearchAPIs {

  private val client: RestHighLevelClient = ElasticClient.client
  private val restClient: RestClient = ElasticClient.restClient
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
      scrollResp = client.scroll(new SearchScrollRequest(scrollResp.getScrollId).scroll(new TimeValue(60000)), RequestOptions.DEFAULT)
    } while (scrollResp.getHits.getHits.length != 0)

    result.toList
  }

  def clearScroll(scrollId: String): ClearScrollResponse = {
    val request = new ClearScrollRequest()
    request.addScrollId(scrollId)
    client.clearScroll(request, RequestOptions.DEFAULT)
  }

  def multiSearch(searchCriteria: Map[String, Any]): List[Map[String, _]] = {
    val request = new MultiSearchRequest()
    var result: ListBuffer[Map[String, _]] = ListBuffer()
    for (criteria <- searchCriteria) {
      val sourceBuilder = new SearchSourceBuilder()
      val searchRequest = new SearchRequest()
      sourceBuilder.query(QueryBuilders.matchQuery(criteria._1, criteria._2))
      searchRequest.source(sourceBuilder)
      request.add(searchRequest)
    }

    val response = client.msearch(request, RequestOptions.DEFAULT)
    response.getResponses.foreach(response => {
      response.getResponse.getHits.getTotalHits
      response.getResponse.getHits.getHits.foreach(hit => {
        result += hit.getSourceAsMap.asScala.map(kv => (kv._1, kv._2)).toMap
      })
    })
    result.toList
  }

  def searchTemplate(index: String, field: String, value: String, searchName: String): List[Map[String, Any]] = {
    var result = ListBuffer[Map[String, Any]]()
    val scriptRequest = new Request("POST", "_scripts/" + searchName)
    scriptRequest.setJsonEntity(
      "{" +
        "  \"script\": {" +
        "    \"lang\": \"mustache\"," +
        "    \"source\": {" +
        "      \"query\": { \"match\" : { \"{{field}}\" : \"{{value}}\" } }," +
        "      \"size\" : \"{{size}}\"" +
        "    }" +
        "  }" +
        "}")
    restClient.performRequest(scriptRequest)
    val request = new SearchTemplateRequest()
    request.setRequest(new SearchRequest(index))

    request.setScriptType(ScriptType.STORED)
    request.setScript(searchName)

    val params = new util.HashMap[String, AnyRef]()
    params.put("field", field)
    params.put("value", value)
    params.put("size", "100")
    request.setScriptParams(params)

    val response = client.searchTemplate(request, RequestOptions.DEFAULT)
    for (hit: SearchHit <- response.getResponse.getHits.getHits) {
      result += hit.getSourceAsMap.asScala.map(kv => (kv._1, kv._2)).toMap
    }
    result.toList
  }

  def multiSearchTemplate(index: String, field: String, values: List[String]): List[Map[String, Any]] = {

    var result = ListBuffer[Map[String, Any]]()
    val multiRequest = new MultiSearchTemplateRequest()
    for (searchTerm: String <- values) {
      val request = new SearchTemplateRequest()
      request.setRequest(new SearchRequest(index))

      request.setScriptType(ScriptType.INLINE)
      request.setScript(
        "{" +
          "  \"query\": { \"match\" : { \"{{field}}\" : \"{{value}}\" } }," +
          "  \"size\" : \"{{size}}\"" +
          "}")

      val params = new util.HashMap[String, AnyRef]()
      params.put("field", field)
      params.put("value", searchTerm)
      params.put("size", "100")
      request.setScriptParams(params)

      multiRequest.add(request)

      val response = client.searchTemplate(request, RequestOptions.DEFAULT)
      for (hit: SearchHit <- response.getResponse.getHits.getHits) {
        result += hit.getSourceAsMap.asScala.map(kv => (kv._1, kv._2)).toMap
      }
    }
    result.toList
  }

  def fieldCapabilities(field: String, indices: String*): util.Map[String, util.Map[String, FieldCapabilities]] = {
    val request = new FieldCapabilitiesRequest()
      .fields(field)
      .indices(indices: _*)

    val response = client.fieldCaps(request, RequestOptions.DEFAULT)
    response.get()
  }

  def rankEvaluation(index: String, id: String, requestId: String, field: String, value: String, indices: Array[String]): Map[String, EvalQueryQuality] = {
    val metric = new PrecisionAtK()
    val ratedDocs = new util.ArrayList[RatedDocument]()
    ratedDocs.add(new RatedDocument(index, id, 1))
    val searchQuery = new SearchSourceBuilder()
    searchQuery.query(QueryBuilders.matchQuery(field, value))
    val ratedRequest = new RatedRequest(requestId, ratedDocs, searchQuery)
    val req: List[RatedRequest] = List(ratedRequest)
    val ratedRequests: util.List[RatedRequest] = req.asJava
    val specification = new RankEvalSpec(ratedRequests, metric)
    val request = new RankEvalRequest(specification, indices)
    val response = client.rankEval(request, RequestOptions.DEFAULT)
    response.getPartialResults.asScala.toMap
  }

  def explain(esIndex: String, esType: String, esId: String, field: String, value: String): ExplainResponse = {
    val request = new ExplainRequest(esIndex, esType, esId)
    request.query(QueryBuilders.termQuery(field, value))
    client.explain(request, RequestOptions.DEFAULT)
  }

  // This is the generic one !
  def searchWithQueryBuilder(queryBuilder: QueryBuilder, indices: String*): List[Map[String, Any]] = {
    val searchSourceBuilder = new SearchSourceBuilder
    val builder = searchSourceBuilder.query(queryBuilder).from(from).size(size)
    val searchRequest = new SearchRequest(indices: _*)
    DataExtractor.extractResult(searchRequest, builder)
  }
}
