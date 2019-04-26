package com.scalastic.api.high.level.rest.client

/**
  * Created by Ghazi Naceur on 20/04/2019
  * Email: ghazi.ennacer@gmail.com
  */
object SplitIndexTest extends App {
  // OPEN BUG : https://github.com/elastic/elasticsearch/issues/29652#
  // 1-
  //  private val settings: Settings.Builder = Settings.builder()
  //    .put("index.number_of_shards", 5)
  //    .put("index.number_of_replicas", 2)
  //    .put("index.blocks.read_only", true)
  //  ElasticHighLevelRestClient.createIndex("scala_index_3", settings)

  //  2-
  //  val person = Person("Isaac", "Netero", 125, "Hunter")
  //  for (i <- 0 until 50) {
  //    ElasticQueryBuilder.insert("scala_index_3", "csv", person.toMap())
  //  }

  //  3-
  //  private val response: ResizeResponse = ElasticHighLevelRestClient.splitIndex("csvs_dup", "scala_index_3", 16)
  //  println(response.isAcknowledged)
  //  println(response.index())

  System.exit(0)
}
