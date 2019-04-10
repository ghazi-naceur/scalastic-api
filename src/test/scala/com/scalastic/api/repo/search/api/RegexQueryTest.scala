package com.scalastic.api.repo.search.api

import com.scalastic.api.entities.City
import com.scalastic.api.repo.ElasticQueryBuilder

/**
  * Created by Ghazi Naceur on 10/04/2019
  * Email: ghazi.ennacer@gmail.com
  */
object RegexQueryTest extends App {
  // keyword type (not with the text type)
  private val cities: List[Map[String, Any]] = ElasticQueryBuilder.getDocsWithRegexQuery("cities", "name", "K.*a")
  cities.foreach(city => {
    println(City.toCity(city).toString)
  })

  System.exit(0)
}
