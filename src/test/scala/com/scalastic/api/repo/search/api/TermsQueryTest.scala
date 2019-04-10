package com.scalastic.api.repo.search.api

import com.scalastic.api.entities.City
import com.scalastic.api.repo.ElasticQueryBuilder

/**
  * Created by Ghazi Naceur on 09/04/2019
  * Email: ghazi.ennacer@gmail.com
  */
object TermsQueryTest extends App {
  private val cities: List[Map[String, Any]] = ElasticQueryBuilder.getDocsWithTermsQuery(Array("cities", "other_persons"), "name", "Konoha", "some other city")
  cities.foreach(city => {
    println(City.toCity(city).toString)
  })

  System.exit(0)
}
