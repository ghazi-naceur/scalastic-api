package com.scalastic.api.compound

import com.scalastic.api.ElasticCompoundQueryBuilder
import com.scalastic.api.config.PropertiesLoader
import com.scalastic.api.entities.City

/**
  * Created by Ghazi Naceur on 17/04/2019
  * Email: ghazi.ennacer@gmail.com
  */
object DisMaxQueryTest extends App {
  val map = Map[String, Any]("name" -> "Konoha", "prefecture" -> "Hidden leaf")
  val cities = ElasticCompoundQueryBuilder.getDocsWithDisMaxQuery(PropertiesLoader.CITY_INDEX, map)
  cities.foreach(map => {
    println(City.toCity(map).toString)
  })

  System.exit(0)
}
