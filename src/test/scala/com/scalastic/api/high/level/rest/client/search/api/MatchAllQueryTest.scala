package com.scalastic.api.high.level.rest.client.search.api

import com.scalastic.api.entities.Person

/**
  * Created by Ghazi Naceur on 25/04/2019
  * Email: ghazi.ennacer@gmail.com
  */
object MatchAllQueryTest extends App {
  // Getting the first page
  val list = SearchAPIs.searchWithMatchAllQuery("sc_persons", "persons")
  list.foreach(map => {
    println(Person.toPerson(map).toString)
  })

  System.exit(0)
}
