package com.scalastic.api.repo

import java.util.List

import com.scalastic.api.client.ElasticClient
import org.elasticsearch.action.ActionListener
import org.elasticsearch.action.admin.indices.create.{CreateIndexRequest, CreateIndexResponse}
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest
import org.elasticsearch.action.admin.indices.exists.indices.{IndicesExistsRequest, IndicesExistsResponse}
import org.elasticsearch.action.admin.indices.get.GetIndexRequest
import org.elasticsearch.action.admin.indices.get.GetIndexRequest.Feature
import org.elasticsearch.action.support.master.AcknowledgedResponse
import org.elasticsearch.client.{RequestOptions, RestHighLevelClient}
import org.elasticsearch.client.transport.TransportClient
import org.elasticsearch.common.unit.TimeValue
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

  def createIndex(index: String): Unit = {
    val listener = new ActionListener[CreateIndexResponse]() {
      override def onResponse(createIndexResponse: CreateIndexResponse): Unit = {
        println(s"The index ${createIndexResponse.index} is created ..")
      }

      override def onFailure(e: Exception): Unit = {
        println(s"An error occurred when trying to create index : $e ")
      }
    }

    if (getIndicesList.contains(index)) {
      println(s"This index $index is already created.")
    }
    else {
      val request = new CreateIndexRequest(index)
      val indices = transportClient.admin.indices
      indices.create(request, listener)
    }
  }

  def createIndex(esIndex: String, esType: String, mapping: String): Unit = {
    val listener = new ActionListener[CreateIndexResponse]() {
      override def onResponse(createIndexResponse: CreateIndexResponse): Unit = {
        println(s"The index ${createIndexResponse.index} is created ..")
      }

      override def onFailure(e: Exception): Unit = {
        println(s"An error occurred when trying to create index : $e ")
      }
    }
    val request = new CreateIndexRequest(esIndex)
    val indices = transportClient.admin.indices
    request.mapping(esType, mapping, XContentType.JSON)
    indices.create(request, listener)
  }

  def deleteIndex(index: String): Unit = {
    val listener = new ActionListener[AcknowledgedResponse]() {
        override def onResponse(deleteIndexResponse: AcknowledgedResponse) {
          if (deleteIndexResponse.isAcknowledged){
            println("The index is deleted")
          } else {
            println("The index is not deleted")
          }
        }

        override def onFailure(e: Exception) = {
          println(s"An error occurred when trying to delete index : $e ")
        }
      }
    val request = new DeleteIndexRequest(index)
    request.timeout(TimeValue.timeValueMinutes(2))
    transportClient.admin().indices().delete(request, listener)
  }

  def indicesExists(indices: String*) = {
    val listener = new ActionListener[IndicesExistsResponse]() {
      override def onResponse(response: IndicesExistsResponse) {
        println("The indices exist ? : " + response.isExists)
      }

      override def onFailure(e: Exception) {
        println("The indices do not exist.")
      }
    }
    val request = new IndicesExistsRequest(indices: _*)
    transportClient.admin().indices().exists(request, listener)
  }
}
