package com.scalastic.api.repo.high.level.rest.client.document.api

import com.scalastic.api.entities.City
import com.scalastic.api.high.level.rest.client.search.api.SearchAPIs

/**
  * Created by Ghazi Naceur on 26/04/2019
  * Email: ghazi.ennacer@gmail.com
  */
object RegexQueryTest extends App {
  // keyword type (not with the text type)
  private val cities: List[Map[String, Any]] = SearchAPIs.searchWithRegexQuery("cities", "name", "K.*a")
  cities.foreach(city => {
    println(City.toCity(city).toString)
  })

  System.exit(0)
}
