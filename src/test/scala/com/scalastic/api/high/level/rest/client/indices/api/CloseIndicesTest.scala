package com.scalastic.api.high.level.rest.client.indices.api

import org.elasticsearch.action.support.master.AcknowledgedResponse

/**
  * Created by Ghazi Naceur on 20/04/2019
  * Email: ghazi.ennacer@gmail.com
  */
object CloseIndicesTest extends App {
  private val response: AcknowledgedResponse = IndicesAPIs.closeIndices("sc_persons", "persons", "cities")
  println(response.isAcknowledged)

  System.exit(0)
}
