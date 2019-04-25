package com.scalastic.api.high.level.rest.client.tasks.api

import com.scalastic.api.client.ElasticClient
import org.elasticsearch.action.admin.cluster.node.tasks.list.{ListTasksRequest, ListTasksResponse}
import org.elasticsearch.client.{RequestOptions, RestHighLevelClient}

object TasksAPIs {

  private val client: RestHighLevelClient = ElasticClient.client

  def listTasks(): ListTasksResponse = {
    val request = new ListTasksRequest()
    request.setDetailed(true)
    client.tasks().list(request, RequestOptions.DEFAULT)
  }
}
