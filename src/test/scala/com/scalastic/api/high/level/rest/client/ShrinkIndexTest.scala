package com.scalastic.api.high.level.rest.client

import org.elasticsearch.action.admin.indices.shrink.ResizeResponse

/**
  * Created by Ghazi Naceur on 20/04/2019
  * Email: ghazi.ennacer@gmail.com
  */
object ShrinkIndexTest extends App {
  // 1-
  //  val person = Person("Isaac", "Netero", 125, "Hunter")
  //  for (i <- 0 until 50)
  //  ElasticQueryBuilder.insert("scala_index_2", "csv", person.toMap())

  // 2-
  /**
    * PUT http://localhost:9200/scala_index_2/_settings
    * { "index": { "blocks.read_only": true }}
    */

  // 1- Fill up source index
  // 2- readonly source index
  // 3- shrink (target index should be inexistant)
  private val response: ResizeResponse = ElasticHighLevelRestClient.shrinkIndex("csvs_dup", "scala_index_2")
  println(response.index())
  println(response.isAcknowledged)

  System.exit(0)
}
