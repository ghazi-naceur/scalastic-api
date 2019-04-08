package com.scalastic.api.repo.search

import com.scalastic.api.entities.Person
import com.scalastic.api.repo.ElasticQueryBuilder
import com.scalastic.api.config.PropertiesLoader.{PERSON_INDEX}


/**
  * Created by Ghazi Naceur on 07/04/2019
  * Email: ghazi.ennacer@gmail.com
  */
object MatchQueryTest extends App {
  val persons = ElasticQueryBuilder.getDocsWithMatchQuery(PERSON_INDEX, "lastName", "Netero")
  persons.foreach(map => {
    println(Person.toPerson(map).toString)
  })

  System.exit(0)
}
