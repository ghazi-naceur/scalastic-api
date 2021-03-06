package com.scalastic.api.high.level.rest.client.indices.api

import org.elasticsearch.action.admin.indices.open.OpenIndexResponse

/**
  * Created by Ghazi Naceur on 20/04/2019
  * Email: ghazi.ennacer@gmail.com
  */
object OpenIndicesTest extends App {
  private val response: OpenIndexResponse = IndicesAPIs.openIndices("sc_persons", "persons", "cities")
  println(response.isAcknowledged)

  System.exit(0)
}
