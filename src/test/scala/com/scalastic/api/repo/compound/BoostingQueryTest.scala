package com.scalastic.api.repo.compound

import com.scalastic.api.config.PropertiesLoader
import com.scalastic.api.entities.City
import com.scalastic.api.repo.ElasticCompoundQueryBuilder

/**
  * Created by Ghazi Naceur on 17/04/2019
  * Email: ghazi.ennacer@gmail.com
  */
object BoostingQueryTest extends App {
  private val cities: List[Map[String, Any]] = ElasticCompoundQueryBuilder.getDocsWithBoostingQuery(PropertiesLoader.CITY_INDEX, "name", "Konoha", "prefecture", "Hidden leaf")
  cities.foreach(city => {
    println(City.toCity(city).toString)
  })

  System.exit(0)
}
