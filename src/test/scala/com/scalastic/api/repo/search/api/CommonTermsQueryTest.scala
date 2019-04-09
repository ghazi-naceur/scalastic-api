package com.scalastic.api.repo.search.api

import com.scalastic.api.config.PropertiesLoader.{CITY_INDEX, CITY_TYPE, PERSON_INDEX}
import com.scalastic.api.entities.{City, Person}
import com.scalastic.api.repo.ElasticQueryBuilder

/**
  * Created by Ghazi Naceur on 09/04/2019
  * Email: ghazi.ennacer@gmail.com
  */
object CommonTermsQueryTest extends App {

  // 1- First example :
  println("1- First example :")
  val persons = ElasticQueryBuilder.getDocsWithCommonTermsQuery(PERSON_INDEX, "lastName", "Netero")
  persons.foreach(map => {
    println(Person.toPerson(map).toString)
  })
  //   => return result

  println("2- Second example :")
  // 2- Second example :
  val city = City("Konoha", "some prefecture", "Hidden leaf", 5000)
  ElasticQueryBuilder.insert(CITY_INDEX, CITY_TYPE, city.toMap())
  ElasticQueryBuilder.insert(CITY_INDEX, CITY_TYPE, city.toMap())
  ElasticQueryBuilder.insert(CITY_INDEX, CITY_TYPE, city.toMap())
  println("a- :")
  // a :
  val cities1 = ElasticQueryBuilder.getDocsWithCommonTermsQuery(CITY_INDEX, "name", "Konoha")
  cities1.foreach(map => {
    println(City.toCity(map).toString)
  })
  // => return result

  println("b- :")
  // b :
  val cities2 = ElasticQueryBuilder.getDocsWithCommonTermsQuery(CITY_INDEX, "country", "Hidden leaf")
  cities2.foreach(map => {
    println(City.toCity(map).toString)
  })
  // => return result

  System.exit(0)
}
