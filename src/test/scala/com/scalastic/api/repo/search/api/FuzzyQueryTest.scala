package com.scalastic.api.repo.search.api

import com.scalastic.api.config.PropertiesLoader.PERSON_INDEX
import com.scalastic.api.entities.Person
import com.scalastic.api.repo.ElasticQueryBuilder

/**
  * Created by Ghazi Naceur on 10/04/2019
  * Email: ghazi.ennacer@gmail.com
  */
object FuzzyQueryTest extends App {
  val persons = ElasticQueryBuilder.getDocsWithFuzzyQuery(PERSON_INDEX, "lastName", "Netero")
  persons.foreach(map => {
    println(Person.toPerson(map).toString)
  })

  System.exit(0)
}
