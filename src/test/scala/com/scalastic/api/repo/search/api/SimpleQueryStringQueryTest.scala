package com.scalastic.api.repo.search.api

import com.scalastic.api.entities.Person
import com.scalastic.api.repo.ElasticQueryBuilder
import org.elasticsearch.index.query.Operator

/**
  * Created by Ghazi Naceur on 09/04/2019
  * Email: ghazi.ennacer@gmail.com
  */
object SimpleQueryStringQueryTest extends App {
  private val persons: List[Map[String, Any]] = ElasticQueryBuilder.getDocsWithSimpleQueryStringQuery("sc_persons", Operator.OR, Array("Takamura", "Netero"))
  persons.foreach(person => {
    println(Person.toPerson(person).toString)
  })
  System.exit(0)
}
