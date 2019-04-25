package com.scalastic.api.repo.high.level.rest.client.search.api

import com.scalastic.api.entities.Person
import com.scalastic.api.high.level.rest.client.search.api.SearchAPIs
import org.elasticsearch.index.query.Operator

/**
  * Created by Ghazi Naceur on 25/04/2019
  * Email: ghazi.ennacer@gmail.com
  */
object QueryStringQueryTest extends App {
  private val persons: List[Map[String, Any]] = SearchAPIs.searchWithQueryStringQuery("sc_persons", "lastName", Operator.OR, Array("Takamura", "Netero"))
  persons.foreach(person => {
    println(Person.toPerson(person).toString)
  })

  System.exit(0)
}
