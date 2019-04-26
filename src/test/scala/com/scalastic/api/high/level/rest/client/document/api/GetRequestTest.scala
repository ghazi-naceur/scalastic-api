package com.scalastic.api.high.level.rest.client.document.api

import com.scalastic.api.config.PropertiesLoader.{PERSON_INDEX, PERSON_TYPE}
import com.scalastic.api.entities.Person

/**
  * Created by Ghazi Naceur on 07/04/2019
  * Email: ghazi.ennacer@gmail.com
  */
object GetRequestTest extends App {
  val personMap = SingleDocumentAPIs.getById(PERSON_INDEX, PERSON_TYPE, "570f3e69-0f9a-4f64-840a-cbd0efa2d30a")
  val person3 = Person.toPerson(personMap)
  println(person3.toString)

  System.exit(0)
}
