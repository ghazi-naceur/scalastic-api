package com.scalastic.api.high.level.rest.client.snapshot.api

import com.scalastic.api.client.ElasticClient
import org.elasticsearch.action.admin.cluster.repositories.get.{GetRepositoriesRequest, GetRepositoriesResponse}
import org.elasticsearch.client.{RequestOptions, RestHighLevelClient}

/**
  * Created by Ghazi Naceur on 28/04/2019
  * Email: ghazi.ennacer@gmail.com
  */
object SnapshotAPIs {

  private val client: RestHighLevelClient = ElasticClient.client

  def getRepositories: GetRepositoriesResponse = {
    val request = new GetRepositoriesRequest()
    request.local(true)
    client.snapshot().getRepository(request, RequestOptions.DEFAULT)
  }
}
