package com.scalastic.api.high.level.rest.client.indices.api

/**
  * Created by Ghazi Naceur on 22/04/2019
  * Email: ghazi.ennacer@gmail.com
  */
object ExistsAliasTest extends App {
  println(IndicesAPIs.existsAlias("rollover_index_2"))
  println(IndicesAPIs.existsAlias("rollover_index_2", "rollover_alias_1"))
  println(IndicesAPIs.existsAlias("rollover_index_2", "rollover_alias_1", "abc"))

  System.exit(0)
}
