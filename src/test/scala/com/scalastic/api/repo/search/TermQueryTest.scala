package com.scalastic.api.repo.search

import com.scalastic.api.entities.{City, Person}
import com.scalastic.api.repo.ElasticQueryBuilder
import com.scalastic.api.config.PropertiesLoader.{PERSON_INDEX, CITY_INDEX, CITY_TYPE}

/**
  * Created by Ghazi Naceur on 07/04/2019
  * Email: ghazi.ennacer@gmail.com
  */
object TermQueryTest extends App {

  // 1- First example :
  println("1- First example :")
  val persons = ElasticQueryBuilder.getEntitiesFromIndexUsingTermQuery(PERSON_INDEX, "lastName", "Netero")
  persons.foreach(map => {
    println(Person.toPerson(map).toString)
  })
  //   => This operation with "text" mapping (lastName) will return an empty result

  println("2- Second example :")
  // 2- Second example :
      val city = City("Konoha", "some prefecture", "Hidden leaf", 5000)
      ElasticQueryBuilder.insert(CITY_INDEX, CITY_TYPE, city.toMap())
      ElasticQueryBuilder.insert(CITY_INDEX, CITY_TYPE, city.toMap())
      ElasticQueryBuilder.insert(CITY_INDEX, CITY_TYPE, city.toMap())
  println("a- :")
  // a :
        val cities1 = ElasticQueryBuilder.getEntitiesFromIndexUsingTermQuery(CITY_INDEX, "name", "Konoha")
        cities1.foreach(map => {
          println(City.toCity(map).toString)
        })
  // => This operation with "keyword" mapping (name) will return a result

  println("b- :")
  // b :
        val cities2 = ElasticQueryBuilder.getEntitiesFromIndexUsingTermQuery(CITY_INDEX, "country", "Hidden leaf")
        cities2.foreach(map => {
          println(City.toCity(map).toString)
        })
  // => This operation with "text" mapping (country) will return an empty result

  System.exit(0)
}
