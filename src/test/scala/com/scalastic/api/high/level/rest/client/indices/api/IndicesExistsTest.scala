package com.scalastic.api.high.level.rest.client.indices.api

/**
  * Created by Ghazi Naceur on 19/04/2019
  * Email: ghazi.ennacer@gmail.com
  */
object IndicesExistsTest extends App {
  println(IndicesAPIs.indicesExists("sc_persons", "persons"))
  println(IndicesAPIs.indicesExists("sc_persons", "persons", "abc"))

  System.exit(0)
}
