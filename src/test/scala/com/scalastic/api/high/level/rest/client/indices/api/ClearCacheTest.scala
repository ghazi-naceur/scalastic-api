package com.scalastic.api.high.level.rest.client.indices.api

import org.elasticsearch.action.admin.indices.cache.clear.ClearIndicesCacheResponse

/**
  * Created by Ghazi Naceur on 20/04/2019
  * Email: ghazi.ennacer@gmail.com
  */
object ClearCacheTest extends App {
  // Clear cache for all indices
  private val response: ClearIndicesCacheResponse = IndicesAPIs.clearCache()
  println(response.getStatus)
  println("-----")
  // Clear cache for specific indices
  private val response2: ClearIndicesCacheResponse = IndicesAPIs.clearCache("persons", "sc_persons")
  println(response2.getStatus)

  System.exit(0)
}
