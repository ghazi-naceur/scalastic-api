package com.scalastic.api.repo.high.level.rest.client.search.api

import com.scalastic.api.entities.Person
import com.scalastic.api.high.level.rest.client.search.api.SearchAPIs

/**
  * Created by Ghazi Naceur on 25/04/2019
  * Email: ghazi.ennacer@gmail.com
  */
object MoreLikeThisQueryTest extends App {
  private val cities: List[Map[String, Any]] = SearchAPIs.searchWithMoreLikeThisQuery("sc_persons", Array("lastName"), Array("Netero", "Mamuro"), null)
  cities.foreach(person => {
    println(Person.toPerson(person).toString)
  })

  System.exit(0)
}
