package com.scalastic.api.repo.search.api

import com.scalastic.api.entities.Person
import com.scalastic.api.repo.ElasticQueryBuilder
import org.elasticsearch.index.query.Operator

/**
  * Created by Ghazi Naceur on 09/04/2019
  * Email: ghazi.ennacer@gmail.com
  */
object QueryStringQueryTest extends App {
  private val persons: List[Map[String, Any]] = ElasticQueryBuilder.getDocsWithQueryStringQuery("sc_persons", "lastName", Operator.OR, Array("Takamura", "Netero"))
  persons.foreach(person => {
    println(Person.toPerson(person).toString)
  })

}
