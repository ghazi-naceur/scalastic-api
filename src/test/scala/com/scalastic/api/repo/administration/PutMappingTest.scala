package com.scalastic.api.repo.administration

import com.scalastic.api.repo.ElasticAdministration

/**
  * Created by Ghazi Naceur on 13/04/2019
  * Email: ghazi.ennacer@gmail.com
  */
object PutMappingTest extends App {
  val props = Map("name" -> "text", "age" -> "long", "username" -> "keyword", "birth_date" -> "date")
  // index and type already existing
  private val response1 = ElasticAdministration.putMapping("createindex", "createtype", props)
  println(response1)

  System.exit(0)
}
