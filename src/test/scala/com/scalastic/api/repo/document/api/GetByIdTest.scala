package com.scalastic.api.repo.document.api

import com.scalastic.api.config.PropertiesLoader.{PERSON_INDEX, PERSON_TYPE}
import com.scalastic.api.entities.Person
import com.scalastic.api.repo.ElasticQueryBuilder

/**
  * Created by Ghazi Naceur on 07/04/2019
  * Email: ghazi.ennacer@gmail.com
  */
object GetByIdTest extends App {
  val personMap = ElasticQueryBuilder.getById(PERSON_INDEX, PERSON_TYPE, "c4281022-0b2f-447d-b1c7-1e0503f78c84")
  val person3 = Person.toPerson(personMap)
  println(person3.toString)

  System.exit(0)
}
