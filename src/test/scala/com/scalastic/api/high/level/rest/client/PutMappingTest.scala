package com.scalastic.api.high.level.rest.client

import org.elasticsearch.action.support.master.AcknowledgedResponse

/**
  * Created by Ghazi Naceur on 21/04/2019
  * Email: ghazi.ennacer@gmail.com
  */
object PutMappingTest extends App {

  ElasticHighLevelRestClient.createIndex("new_index_5", null, null)

  val mapping = "{\n       \"properties\" : {\n         \"age\" : {\n           \"type\" : \"long\"\n         },\n         \"firstName\" : {\n           \"type\" : \"text\"\n         },\n         \"lastName\" : {\n           \"type\" : \"text\"\n         },\n         \"occupation\" : {\n           \"type\" : \"text\"\n         },\n         \"id\" : {\n           \"type\" : \"text\"\n         }\n       }\n     }"
  private val response: AcknowledgedResponse = ElasticHighLevelRestClient.putMapping("new_index_5", "new_alias", mapping)
  println(response.isAcknowledged)

  System.exit(0)
}
