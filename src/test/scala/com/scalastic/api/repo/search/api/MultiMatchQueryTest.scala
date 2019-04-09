package com.scalastic.api.repo.search.api

import com.scalastic.api.entities.Person
import com.scalastic.api.repo.ElasticQueryBuilder

/**
  * Created by Ghazi Naceur on 09/04/2019
  * Email: ghazi.ennacer@gmail.com
  */
object MultiMatchQueryTest extends App {
  private val persons: List[Map[String, Any]] = ElasticQueryBuilder.getDocsWithMultiMatchQuery(Array("sc_persons", "other_persons"), "Uchiha", "lastName", "firstName", "occupation")
  persons.foreach(person => {
    println(Person.toPerson(person).toString)
  })

  System.exit(0)
}
