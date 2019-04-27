package com.scalastic.api.high.level.rest.client.search.api

import com.scalastic.api.entities.Person

/**
  * Created by Ghazi Naceur on 27/04/2019
  * Email: ghazi.ennacer@gmail.com
  */

object MultiSearchTemplateTest extends App {
  val searchTerms = List("Netero", "Uchiha", "Mamuro")
  SearchAPIs.multiSearchTemplate("sc_persons", "lastName", searchTerms).foreach(person => {
    println(Person.toPerson(person).toString)
  })

  System.exit(0)
}
