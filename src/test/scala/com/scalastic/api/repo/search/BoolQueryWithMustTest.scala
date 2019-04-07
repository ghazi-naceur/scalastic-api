package com.scalastic.api.repo.search

import com.scalastic.api.entities.Person
import com.scalastic.api.repo.ElasticQueryBuilder
import com.scalastic.api.config.PropertiesLoader.{PERSON_INDEX}

/**
  * Created by Ghazi Naceur on 07/04/2019
  * Email: ghazi.ennacer@gmail.com
  */
object BoolQueryWithMustTest extends App {
  val map = Map[String, Any]("lastName" -> "Netero", "occupation" -> "Hunter")
  val persons = ElasticQueryBuilder.search(PERSON_INDEX, map)
  persons.foreach(map => {
    println(Person.toPerson(map).toString)
  })

  System.exit(0)
}
