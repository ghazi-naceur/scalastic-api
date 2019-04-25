package com.scalastic.api.repo.high.level.rest.client.search.api

import com.scalastic.api.config.PropertiesLoader.PERSON_INDEX
import com.scalastic.api.entities.Person
import com.scalastic.api.high.level.rest.client.search.api.SearchAPIs

/**
  * Created by Ghazi Naceur on 25/04/2019
  * Email: ghazi.ennacer@gmail.com
  */
object MatchQueryTest extends App {
  val persons = SearchAPIs.searchMatchQuery("lastName", "Netero", PERSON_INDEX, "persons")
  persons.foreach(map => {
    println(Person.toPerson(map).toString)
  })

  System.exit(0)
}
