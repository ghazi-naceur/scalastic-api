package com.scalastic.api.high.level.rest.client.indices.api

import org.elasticsearch.action.admin.indices.get.GetIndexResponse

/**
  * Created by Ghazi Naceur on 23/04/2019
  * Email: ghazi.ennacer@gmail.com
  */
object GetIndexTest extends App {
  private val response: GetIndexResponse = IndicesAPIs.getIndex("sc_persons")
  println(response.getIndices.foreach(index => {
    println(index)
  }))
  println(response.getAliases)

  System.exit(0)
}
