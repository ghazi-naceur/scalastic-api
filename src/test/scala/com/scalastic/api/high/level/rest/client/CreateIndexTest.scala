package com.scalastic.api.high.level.rest.client

import org.elasticsearch.common.settings.Settings

/**
  * Created by Ghazi Naceur on 18/04/2019
  * Email: ghazi.ennacer@gmail.com
  */
object CreateIndexTest extends App {
  // Without explicit mapping
  private val settings: Settings.Builder = Settings.builder()
    .put("index.number_of_shards", 3)
    .put("index.number_of_replicas", 2)
    .put("index.blocks.read_only", false)

  ElasticHighLevelRestClient.createIndex("scala_index_2", null, settings)

  // With explicit mapping
  val mapping = "{ \"properties\" : {   \"field_one\" : { \"type\" : \"text\"},  \"field_two\" : { \"type\" : \"text\"},  \"field_three\" : { \"type\" : \"text\" }, \"field_four\" : { \"type\" : \"text\" } }}"
  ElasticHighLevelRestClient.createIndex("new_scala_index_3", "new_scala_type", null, mapping)

  System.exit(0)
}
