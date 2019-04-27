package com.scalastic.api.high.level.rest.client.document.api

import com.scalastic.api.entities.Person
import com.scalastic.api.high.level.rest.client.search.api.SearchAPIs

/**
  * Created by Ghazi Naceur on 27/04/2019
  * Email: ghazi.ennacer@gmail.com
  */

object SearchTemplateTest extends App {
  SearchAPIs.searchTemplate("sc_persons", "lastName", "Netero", "lastname_search").foreach(person => {
    println(Person.toPerson(person).toString)
  })

  System.exit(0)
}
