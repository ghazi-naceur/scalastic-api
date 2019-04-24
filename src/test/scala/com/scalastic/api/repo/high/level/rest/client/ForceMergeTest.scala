package com.scalastic.api.repo.high.level.rest.client

import com.scalastic.api.high.level.rest.client.ElasticHighLevelRestClient
import org.elasticsearch.action.admin.indices.forcemerge.ForceMergeResponse

/**
  * Created by Ghazi Naceur on 20/04/2019
  * Email: ghazi.ennacer@gmail.com
  */
object ForceMergeTest extends App {
  // Force merge for all indices
  private val response: ForceMergeResponse = ElasticHighLevelRestClient.forceMerge()
  println(response.getStatus)
  println("-----")
  // Force merge for specific indices
  private val response2: ForceMergeResponse = ElasticHighLevelRestClient.forceMerge("persons", "sc_persons")
  println(response2.getStatus)

  System.exit(0)
}
