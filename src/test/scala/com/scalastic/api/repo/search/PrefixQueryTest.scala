package com.scalastic.api.repo.search

import com.scalastic.api.config.PropertiesLoader.CITY_INDEX
import com.scalastic.api.entities.City
import com.scalastic.api.repo.ElasticQueryBuilder

/**
  * Created by Ghazi Naceur on 07/04/2019
  * Email: ghazi.ennacer@gmail.com
  */
object PrefixQueryTest extends App {
  println("First test :")
  val cities = ElasticQueryBuilder.getDocsWithPrefixQuery(CITY_INDEX, "country", "som")
  cities.foreach(map => {
    println(City.toCity(map).toString)
  })
  // To search with text and keyword fields, prefixQuery would be a good solution
  println("Second test :")
  val cities2 = ElasticQueryBuilder.getDocsWithPrefixQuery(CITY_INDEX, "prefecture", "som")
  cities2.foreach(map => {
    println(City.toCity(map).toString)
  })

  System.exit(0)
}
