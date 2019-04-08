package com.scalastic.api.repo.search

import com.scalastic.api.config.PropertiesLoader.{PERSON_INDEX, PERSON_TYPE}
import com.scalastic.api.entities.Person
import com.scalastic.api.repo.ElasticQueryBuilder

/**
  * Created by Ghazi Naceur on 08/04/2019
  * Email: ghazi.ennacer@gmail.com
  */
object MultiGetTest extends App {
  // We consider that indices(i), types(i) and ids(i) are associated to the same entity
  private val persons: List[Map[String, Any]] =
    ElasticQueryBuilder.getDocsWithMultiGet(List(PERSON_INDEX, "persons"), List(PERSON_TYPE, "person"), List("be132f82-81f7-4282-abe5-f18141d70e97", "676bec02-cc7c-4e3d-ab13-6882009cd264"))
  persons.foreach(person => {
    println(Person.toPerson(person).toString)
  })
}
