package com.scalastic.api.high.level.rest.client.document.api

import com.scalastic.api.config.PropertiesLoader.PERSON_INDEX
import com.scalastic.api.entities.Person
import com.scalastic.api.high.level.rest.client.search.api.SearchAPIs

/**
  * Created by Ghazi Naceur on 26/04/2019
  * Email: ghazi.ennacer@gmail.com
  */
object FuzzyQueryTest extends App {
  val persons = SearchAPIs.searchWithFuzzyQuery(PERSON_INDEX, "lastName", "Netero")
  persons.foreach(map => {
    println(Person.toPerson(map).toString)
  })

  System.exit(0)
}
