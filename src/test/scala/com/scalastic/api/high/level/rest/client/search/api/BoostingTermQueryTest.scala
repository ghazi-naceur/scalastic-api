package com.scalastic.api.high.level.rest.client.search.api

import com.scalastic.api.config.PropertiesLoader
import com.scalastic.api.entities.City

/**
  * Created by Ghazi Naceur on 17/04/2019
  * Email: ghazi.ennacer@gmail.com
  */
object BoostingTermQueryTest extends App {
  private val cities: List[Map[String, Any]] = SearchAPIs.searchWithBoostingTermQuery(PropertiesLoader.CITY_INDEX, "name", "Konoha", "prefecture", "Hidden leaf")
  cities.foreach(city => {
    println(City.toCity(city).toString)
  })

  System.exit(0)
}
