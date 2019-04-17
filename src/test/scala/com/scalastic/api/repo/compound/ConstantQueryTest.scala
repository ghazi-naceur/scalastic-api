package com.scalastic.api.repo.compound

import com.scalastic.api.config.PropertiesLoader
import com.scalastic.api.entities.City
import com.scalastic.api.repo.ElasticCompoundQueryBuilder

/**
  * Created by Ghazi Naceur on 17/04/2019
  * Email: ghazi.ennacer@gmail.com
  */
object ConstantQueryTest extends App {
  val cities = ElasticCompoundQueryBuilder.getDocsWithConstantScoreQuery(PropertiesLoader.CITY_INDEX, "name", "Konoha")
  cities.foreach(map => {
    println(City.toCity(map).toString)
  })

  System.exit(0)
}
