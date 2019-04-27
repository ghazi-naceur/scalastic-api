package com.scalastic.api.high.level.rest.client.indices.api

/**
  * Created by Ghazi Naceur on 19/04/2019
  * Email: ghazi.ennacer@gmail.com
  */
object DeleteIndexTest extends App {
  IndicesAPIs.deleteIndex("scala_index_2")

  System.exit(0)
}
