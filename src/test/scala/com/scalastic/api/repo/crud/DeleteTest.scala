package com.scalastic.api.repo.crud

import com.scalastic.api.config.PropertiesLoader.{PERSON_INDEX, PERSON_TYPE}
import com.scalastic.api.repo.ElasticQueryBuilder

/**
  * Created by Ghazi Naceur on 07/04/2019
  * Email: ghazi.ennacer@gmail.com
  */
object DeleteTest extends App {
  val response = ElasticQueryBuilder.delete(PERSON_INDEX, PERSON_TYPE, "c4281022-0b2f-447d-b1c7-1e0503f78c84")
  println(response.toString)
  println(response.status())
}
