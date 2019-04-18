package com.scalastic.api.repo.high.level.rest.client

import com.scalastic.api.repo.ElasticHighLevelRestClient

/**
  * Created by Ghazi Naceur on 18/04/2019
  * Email: ghazi.ennacer@gmail.com
  */
object CreateIndexTest extends App {
  ElasticHighLevelRestClient.createIndex("scala_index")

  val mapping = "{ \"properties\" : {   \"field_one\" : { \"type\" : \"text\"},  \"field_two\" : { \"type\" : \"text\"},  \"field_three\" : { \"type\" : \"text\" }, \"field_four\" : { \"type\" : \"text\" } }}"
  ElasticHighLevelRestClient.createIndex("new_scala_index", "new_scala_type", mapping)
}
