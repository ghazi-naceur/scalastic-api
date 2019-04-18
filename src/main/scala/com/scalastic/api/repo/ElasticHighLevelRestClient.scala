package com.scalastic.api.repo

import java.util.List

import com.scalastic.api.client.ElasticClient
import org.elasticsearch.action.ActionListener
import org.elasticsearch.action.admin.indices.create.{CreateIndexRequest, CreateIndexResponse}
import org.elasticsearch.action.admin.indices.get.GetIndexRequest
import org.elasticsearch.client.RestHighLevelClient
import org.elasticsearch.client.transport.TransportClient
import org.elasticsearch.common.xcontent.XContentType

/**
  * Created by Ghazi Naceur on 18/04/2019
  * Email: ghazi.ennacer@gmail.com
  */
object ElasticHighLevelRestClient {

  private val transportClient: TransportClient = ElasticClient.transportClient

  def getIndicesList: scala.List[String] = {
    transportClient.admin.indices.getIndex(new GetIndexRequest).actionGet.getIndices.toList
  }

}
