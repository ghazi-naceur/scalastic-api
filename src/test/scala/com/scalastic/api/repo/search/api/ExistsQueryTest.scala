package com.scalastic.api.repo.search.api

import com.scalastic.api.entities.Person
import com.scalastic.api.repo.ElasticQueryBuilder

/**
  * Created by Ghazi Naceur on 10/04/2019
  * Email: ghazi.ennacer@gmail.com
  */
object ExistsQueryTest extends App {
  private val persons: List[Map[String, Any]] = ElasticQueryBuilder.getDocsWithExistsQuery("persons", "lastName")
  persons.foreach(person => {
    println(Person.toPerson(person).toString)
  })

  System.exit(0)
}
