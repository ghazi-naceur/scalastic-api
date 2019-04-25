package com.scalastic.api.repo.high.level.rest.client.search.api

import com.scalastic.api.entities.City
import com.scalastic.api.high.level.rest.client.search.api.SearchAPIs

/**
  * Created by Ghazi Naceur on 25/04/2019
  * Email: ghazi.ennacer@gmail.com
  */
object TermsQueryTest extends App {
  private val cities: List[Map[String, Any]] = SearchAPIs.searchWithTermsQuery(Array("cities", "other_persons"), "name", "Konoha", "some other city")
  cities.foreach(city => {
    println(City.toCity(city).toString)
  })

  System.exit(0)
}
