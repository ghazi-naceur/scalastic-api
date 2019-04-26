package com.scalastic.api.high.level.rest.client.search.api

import com.scalastic.api.entities.Person

/**
  * Created by Ghazi Naceur on 25/04/2019
  * Email: ghazi.ennacer@gmail.com
  */
object ExistsQueryTest extends App {
  private val persons: List[Map[String, Any]] = SearchAPIs.searchWithExistsQuery("persons", "lastName")
  persons.foreach(person => {
    println(Person.toPerson(person).toString)
  })

  System.exit(0)
}
