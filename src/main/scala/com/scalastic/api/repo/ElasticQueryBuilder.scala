package com.scalastic.api.repo

import java.util.UUID

import com.scalastic.api.client.ElasticClient
import org.elasticsearch.action.ActionListener
import org.elasticsearch.action.bulk.{BulkRequestBuilder, BulkResponse}
import org.elasticsearch.action.delete.{DeleteRequest, DeleteResponse}
import org.elasticsearch.action.get.{GetRequest, GetResponse, MultiGetItemResponse}
import org.elasticsearch.action.index.{IndexRequest, IndexResponse}
import org.elasticsearch.action.search.SearchRequest
import org.elasticsearch.action.update.{UpdateRequest, UpdateResponse}
import org.elasticsearch.client.transport.TransportClient
import org.elasticsearch.client.{RequestOptions, RestHighLevelClient}
import org.elasticsearch.common.unit.TimeValue
import org.elasticsearch.common.xcontent.XContentFactory
import org.elasticsearch.index.query.{MoreLikeThisQueryBuilder, Operator, QueryBuilders}
import org.elasticsearch.index.reindex._
import org.elasticsearch.search.SearchHit
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

  def insert(esIndex: String, esType: String, entity: Map[String, Any]): IndexResponse = {
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

  def getById(esIndex: String, esType: String, esId: String): Map[String, Any] = {
    val getRequest = new GetRequest(esIndex, esType, esId)
    val response = client.get(getRequest, RequestOptions.DEFAULT)
    // asScala : to have a mutable map
    // map(kv => (kv._1,kv._2)).toMap : to get an immutable map
    response.getSource.asScala.map(kv => (kv._1, kv._2)).toMap
  }

  // Getting the first page
  def getAll(index: String): List[Map[String, Any]] = {
    val searchSourceBuilder = new SearchSourceBuilder
    val builder = searchSourceBuilder.query(QueryBuilders.matchAllQuery())
    val searchRequest = new SearchRequest(index)
    extractResult(searchRequest, builder)
  }

  def delete(esIndex: String, esType: String, esId: String): DeleteResponse = {
    val deleteRequest = new DeleteRequest(esIndex, esType, esId)
    client.delete(deleteRequest, RequestOptions.DEFAULT)
  }

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

  def search(index: String, searchCriteria: Map[String, Any]): List[Map[String, Any]] = {
    val searchRequest = new SearchRequest(index)
    val query = QueryBuilders.boolQuery()
    for ((k, v) <- searchCriteria) {
      query.must(QueryBuilders.matchPhraseQuery(k, v))
    }
    val builder = new SearchSourceBuilder().query(query).from(from).size(size)
    extractResult(searchRequest, builder)
  }

  def getDocsWithMatchQuery(index: String, field: String, value: String): List[Map[String, Any]] = {
    val searchRequest = new SearchRequest(index)
    val builder = new SearchSourceBuilder().query(QueryBuilders.matchQuery(field, value)).from(from).size(size)
    extractResult(searchRequest, builder)
  }

  def getDocsWithMultiMatchQuery(indices: Array[String], value: String, fieldNames: String*): List[Map[String, Any]] = {
    val builder: SearchSourceBuilder = new SearchSourceBuilder().query(QueryBuilders.multiMatchQuery(value, fieldNames: _*))
      .from(from).size(size)
    val searchRequest = new SearchRequest(indices, builder)
    extractResult(searchRequest)
  }

  def getDocsWithTermsQuery(indices: Array[String], field: String, values: String*): List[Map[String, Any]] = {
    val builder: SearchSourceBuilder = new SearchSourceBuilder().query(QueryBuilders.termsQuery(field, values: _*))
      .from(from).size(size)
    val searchRequest = new SearchRequest(indices, builder)
    extractResult(searchRequest)
  }

  def getDocsWithTermQuery(index: String, field: String, value: String): List[Map[String, Any]] = {
    val searchRequest = new SearchRequest(index)
    val builder = new SearchSourceBuilder().query(QueryBuilders.termQuery(field, value)).from(from).size(size)
    extractResult(searchRequest, builder)
  }

  def getDocsWithCommonTermsQuery(index: String, field: String, value: String): List[Map[String, Any]] = {
    val searchRequest = new SearchRequest(index)
    val builder = new SearchSourceBuilder().query(QueryBuilders.commonTermsQuery(field, value)).from(from).size(size)
    extractResult(searchRequest, builder)
  }

  def getDocsWithQueryStringQuery(index: String, defaultField: String, defaultOperator: Operator, query: Array[String]): List[Map[String, Any]] = {
    val searchRequest = new SearchRequest(index)
    val fieldsWithOperator = query.mkString(" " + defaultOperator.toString + " ")
    val builder = new SearchSourceBuilder().query(QueryBuilders.queryStringQuery(fieldsWithOperator).defaultField(defaultField)).from(from).size(size)
    extractResult(searchRequest, builder)
  }

  def getDocsWithSimpleQueryStringQuery(index: String, defaultOperator: Operator, query: Array[String]): List[Map[String, Any]] = {
    val searchRequest = new SearchRequest(index)
    val fieldsWithOperator = query.mkString(" " + defaultOperator.toString + " ")
    val builder = new SearchSourceBuilder().query(QueryBuilders.simpleQueryStringQuery(fieldsWithOperator)).from(from).size(size)
    extractResult(searchRequest, builder)
  }

  def getDocsWithPrefixQuery(index: String, field: String, value: String): List[Map[String, Any]] = {
    val searchRequest = new SearchRequest(index)
    val builder = new SearchSourceBuilder().query(QueryBuilders.prefixQuery(field, value)).from(from).size(size)
    extractResult(searchRequest, builder)
  }

  def getDocsWithMoreLikeThisQuery(index: String, fields: Array[String], likeTexts: Array[String], likeItems: Array[MoreLikeThisQueryBuilder.Item]): List[Map[String, Any]] = {
    val searchRequest = new SearchRequest(index)
    val builder = new SearchSourceBuilder().query(QueryBuilders.moreLikeThisQuery(fields, likeTexts, likeItems).minTermFreq(1)
      .maxQueryTerms(12)).from(from).size(size)
    extractResult(searchRequest, builder)
  }

  def getDocsWithRangeQuery(index: String, field: String, lte: Option[Int], gte: Option[Int]): List[Map[String, Any]] = {
    val searchRequest = new SearchRequest(index)
    val query = QueryBuilders.rangeQuery(field)
    if (lte != null) {
      query.lte(lte.get)
    }
    if (gte != null) {
      query.gte(gte.get)
    }
    val builder = new SearchSourceBuilder().query(query).from(from).size(size)
    extractResult(searchRequest, builder)
  }

  def getDocsWithExistsQuery(index: String, field: String): List[Map[String, Any]] = {
    val searchRequest = new SearchRequest(index)
    val builder = new SearchSourceBuilder().query(QueryBuilders.existsQuery(field)).from(from).size(size)
    extractResult(searchRequest, builder)
  }

  def getDocsWithWildcardQuery(index: String, field: String, value: String): List[Map[String, Any]] = {
    val searchRequest = new SearchRequest(index)
    val builder = new SearchSourceBuilder().query(QueryBuilders.wildcardQuery(field, value)).from(from).size(size)
    extractResult(searchRequest, builder)
  }

  def getDocsWithRegexQuery(index: String, field: String, value: String): List[Map[String, Any]] = {
    val searchRequest = new SearchRequest(index)
    val builder = new SearchSourceBuilder().query(QueryBuilders.regexpQuery(field, value)).from(from).size(size)
    extractResult(searchRequest, builder)
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

}
