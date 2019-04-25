package com.scalastic.api.high.level.rest.client.document.api

import com.scalastic.api.client.ElasticClient
import org.elasticsearch.action.DocWriteRequest
import org.elasticsearch.action.bulk.{BulkRequest, BulkResponse}
import org.elasticsearch.action.get.{MultiGetRequest, MultiGetResponse}
import org.elasticsearch.client.{RequestOptions, RestHighLevelClient}
import org.elasticsearch.index.query.QueryBuilder
import org.elasticsearch.index.reindex.{BulkByScrollResponse, DeleteByQueryRequest, ReindexRequest, UpdateByQueryRequest}
import org.elasticsearch.script.Script

object MultiDocumentAPIs {

  private val client: RestHighLevelClient = ElasticClient.client

  def bulk(esIndex: String, esType: String, requests: List[DocWriteRequest[_]]): BulkResponse = {
    val request = new BulkRequest()
    for (req <- requests) {
      request.add(req)
    }
    client.bulk(request, RequestOptions.DEFAULT)
  }

  def multiGet(items: List[MultiGetRequest.Item]): MultiGetResponse = {
    val request = new MultiGetRequest()
    for (item <- items) {
      request.add(item)
    }
    client.mget(request, RequestOptions.DEFAULT)
  }

  def reindex(destinationIndex: String, sourceIndices: String*): BulkByScrollResponse = {
    val request = new ReindexRequest()
    request.setSourceIndices(sourceIndices: _*)
    request.setDestIndex(destinationIndex)
    request.setRefresh(true)
    client.reindex(request, RequestOptions.DEFAULT)
  }

  def updateByQuery(script: Script, query: QueryBuilder, indices: String*): BulkByScrollResponse = {
    val request = new UpdateByQueryRequest(indices: _*)
    request.setConflicts("proceed")
    request.setScript(script)
    if (query != null) {
      request.setQuery(query)
    }
    client.updateByQuery(request, RequestOptions.DEFAULT)
  }

  def deleteByQuery(query: QueryBuilder, indices: String*): BulkByScrollResponse = {
    val request = new DeleteByQueryRequest(indices: _*)
    request.setConflicts("proceed")
    if (query != null) {
      request.setQuery(query)
    }
    client.deleteByQuery(request, RequestOptions.DEFAULT)
  }
}
