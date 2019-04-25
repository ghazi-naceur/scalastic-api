package com.scalastic.api.repo.high.level.rest.client.search.api

import com.scalastic.api.entities.Person
import com.scalastic.api.high.level.rest.client.search.api.SearchAPIs

/**
  * Created by Ghazi Naceur on 25/04/2019
  * Email: ghazi.ennacer@gmail.com
  */
object RangeQueryTest extends App {
  println("--- First test : ")
  private val persons1: List[Map[String, Any]] = SearchAPIs.searchWithRangeQuery("persons", "age", Option(130), Option(10))
  persons1.foreach(person => {
    println(Person.toPerson(person).toString)
  })

  println("--- Second test : ")
  private val persons2: List[Map[String, Any]] = SearchAPIs.searchWithRangeQuery("persons", "age", null, Option(10))
  persons2.foreach(person => {
    println(Person.toPerson(person).toString)
  })

  println("--- Third test : ")
  private val persons3: List[Map[String, Any]] = SearchAPIs.searchWithRangeQuery("persons", "age", Option(130), null)
  persons3.foreach(person => {
    println(Person.toPerson(person).toString)
  })

  System.exit(0)
}
