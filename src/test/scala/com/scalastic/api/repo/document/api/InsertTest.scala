package com.scalastic.api.repo.document.api

import com.scalastic.api.config.PropertiesLoader.{PERSON_INDEX, PERSON_TYPE}
import com.scalastic.api.entities.Person
import com.scalastic.api.repo.ElasticQueryBuilder

/**
  * Created by Ghazi Naceur on 07/04/2019
  * Email: ghazi.ennacer@gmail.com
  */
object InsertTest extends App {
  val person = Person("Isaac", "Netero", 125, "Hunter")
  val response = ElasticQueryBuilder.insert(PERSON_INDEX, PERSON_TYPE, person.toMap())
  println(response.toString)
  println(response.status())

  System.exit(0)
}
