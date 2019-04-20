package com.scalastic.api.repo.high.level.rest.client

import com.scalastic.api.repo.ElasticHighLevelRestClient

/**
  * Created by Ghazi Naceur on 18/04/2019
  * Email: ghazi.ennacer@gmail.com
  */
object CreateIndexTest extends App {
  // Without explicit mapping
  ElasticHighLevelRestClient.createIndex("scala_index_2")

  // With explicit mapping
  val mapping = "{ \"properties\" : {   \"field_one\" : { \"type\" : \"text\"},  \"field_two\" : { \"type\" : \"text\"},  \"field_three\" : { \"type\" : \"text\" }, \"field_four\" : { \"type\" : \"text\" } }}"
  ElasticHighLevelRestClient.createIndex("new_scala_index_3", "new_scala_type", mapping)

  System.exit(0)
}
