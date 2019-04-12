package com.scalastic.api.repo.administration

import com.scalastic.api.repo.ElasticAdministration

/**
  * Created by Ghazi Naceur on 12/04/2019
  * Email: ghazi.ennacer@gmail.com
  */
object CreateIndexTest extends App {
  private val response: Any = ElasticAdministration.createIndex("createIndexTest".toLowerCase())
  println(response)
}
