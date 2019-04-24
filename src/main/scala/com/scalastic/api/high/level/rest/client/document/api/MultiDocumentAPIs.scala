package com.scalastic.api.high.level.rest.client.document.api

import com.scalastic.api.client.ElasticClient
import org.elasticsearch.action.DocWriteRequest
import org.elasticsearch.action.bulk.{BulkRequest, BulkResponse}
import org.elasticsearch.action.get.{MultiGetRequest, MultiGetResponse}
import org.elasticsearch.client.{RequestOptions, RestHighLevelClient}

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
}
