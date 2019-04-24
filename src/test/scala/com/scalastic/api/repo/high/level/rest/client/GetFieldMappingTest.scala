package com.scalastic.api.repo.high.level.rest.client

import com.scalastic.api.high.level.rest.client.ElasticHighLevelRestClient
import org.elasticsearch.action.admin.indices.mapping.get.GetFieldMappingsResponse

/**
  * Created by Ghazi Naceur on 21/04/2019
  * Email: ghazi.ennacer@gmail.com
  */
object GetFieldMappingTest extends App {
  private val response: GetFieldMappingsResponse = ElasticHighLevelRestClient.getFieldMapping("new_index_5", "new_alias", "id", "age")
  response.mappings().forEach((k,v) => {
    println(k)
    println(v.forEach((k,v) => {
      println(k)
      println(v)
      println("------")
    }))
  })

  private val data: GetFieldMappingsResponse.FieldMappingMetaData = response.fieldMappings("new_index_5", "new_alias", "id")
  println(data.fullName())
  println(data.sourceAsMap())

  System.exit(0)
}
