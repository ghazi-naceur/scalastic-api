package com.scalastic.api.high.level.rest.client

import org.elasticsearch.action.admin.indices.mapping.get.GetMappingsResponse

/**
  * Created by Ghazi Naceur on 21/04/2019
  * Email: ghazi.ennacer@gmail.com
  */
object GetMappingsTest extends App {
  private val response: GetMappingsResponse = ElasticHighLevelRestClient.getMappings("new_index_5", "new_alias")
  response.getMappings.forEach(map => {
    println(map.index)
    println(map.key)
    println(map.value.forEach(value => {
      println(value.index)
      println(value.key)
      println("Mapping : " + value.value.source())
    }))
  })

  System.exit(0)
}
