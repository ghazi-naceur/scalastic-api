package com.scalastic.api.high.level.rest.client.indices.api

import org.elasticsearch.action.admin.indices.refresh.RefreshResponse

/**
  * Created by Ghazi Naceur on 20/04/2019
  * Email: ghazi.ennacer@gmail.com
  */
object RefreshApiTest extends App {
  // Refresh all indices
  private val request: RefreshResponse = IndicesAPIs.refreshIndices()
  println(request.getStatus)
  println("-----")
  // Refresh specific indices
  private val request2: RefreshResponse = IndicesAPIs.refreshIndices("persons", "sc_persons")
  println(request2.getStatus)

  System.exit(0)
}
