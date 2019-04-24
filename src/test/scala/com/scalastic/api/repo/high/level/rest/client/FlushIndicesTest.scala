package com.scalastic.api.repo.high.level.rest.client

import com.scalastic.api.high.level.rest.client.ElasticHighLevelRestClient
import org.elasticsearch.action.admin.indices.flush.FlushResponse

/**
  * Created by Ghazi Naceur on 20/04/2019
  * Email: ghazi.ennacer@gmail.com
  */
object FlushIndicesTest extends App {
  // Flushing all indices
  private val response: FlushResponse = ElasticHighLevelRestClient.flushIndices()
  println(response.getStatus)
  println("-----")
  // Flushing all indices
  private val response2: FlushResponse = ElasticHighLevelRestClient.flushIndices("persons", "sc_persons")
  println(response2.getStatus)

  System.exit(0)
}
