package com.scalastic.api.repo.high.level.rest.client

import com.scalastic.api.repo.ElasticHighLevelRestClient
import org.elasticsearch.action.admin.indices.refresh.RefreshResponse

/**
  * Created by Ghazi Naceur on 20/04/2019
  * Email: ghazi.ennacer@gmail.com
  */
object RefreshApiTest extends App {
  // Refresh all indices
  private val request: RefreshResponse = ElasticHighLevelRestClient.refreshIndex()
  println(request.getStatus)
  println("-----")
  // Refresh specific indices
  private val request2: RefreshResponse = ElasticHighLevelRestClient.refreshIndex("persons", "sc_persons")
  println(request2.getStatus)

  System.exit(0)
}
