package com.scalastic.api.repo.high.level.rest.client.search.api

import com.scalastic.api.config.PropertiesLoader.{CITY_INDEX, CITY_TYPE, PERSON_INDEX}
import com.scalastic.api.entities.{City, Person}
import com.scalastic.api.high.level.rest.client.document.api.SingleDocumentAPIs
import com.scalastic.api.high.level.rest.client.search.api.SearchAPIs

/**
  * Created by Ghazi Naceur on 25/04/2019
  * Email: ghazi.ennacer@gmail.com
  */
object TermQueryTest extends App {

  // 1- First example :
  println("1- First example :")
  val persons = SearchAPIs.searchWithTermQuery(Array(PERSON_INDEX, "persons"), "lastName", "Netero")
  persons.foreach(map => {
    println(Person.toPerson(map).toString)
  })
  //   => This operation with "text" mapping (lastName) will return an empty result

  println("2- Second example :")
  // 2- Second example :
  val city = City("Konoha", "some prefecture", "Hidden leaf", 5000)
  SingleDocumentAPIs.index(CITY_INDEX, CITY_TYPE, city.toMap())
  SingleDocumentAPIs.index(CITY_INDEX, CITY_TYPE, city.toMap())
  SingleDocumentAPIs.index(CITY_INDEX, CITY_TYPE, city.toMap())
  println("a- :")
  // a :
  val cities1 = SearchAPIs.searchWithTermQuery(Array(CITY_INDEX), "name", "Konoha")
  cities1.foreach(map => {
    println(City.toCity(map).toString)
  })
  // => This operation with "keyword" mapping (name) will return a result

  println("b- :")
  // b :
  val cities2 = SearchAPIs.searchWithTermQuery(Array(PERSON_INDEX, "persons"), "country", "Hidden leaf")
  cities2.foreach(map => {
    println(City.toCity(map).toString)
  })
  // => This operation with "text" mapping (country) will return an empty result

  System.exit(0)
}
