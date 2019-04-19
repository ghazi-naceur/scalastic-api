package com.scalastic.api.repo.search.api

import com.scalastic.api.entities.Person
import com.scalastic.api.repo.ElasticQueryBuilder

/**
  * Created by Ghazi Naceur on 19/04/2019
  * Email: ghazi.ennacer@gmail.com
  */
object MoreLikeThisQueryTest extends App {
  private val cities: List[Map[String, Any]] = ElasticQueryBuilder.getDocsWithMoreLikeThisQuery("sc_persons", Array("lastName"), Array("Netero", "Mamuro"), null)
  cities.foreach(person => {
    println(Person.toPerson(person).toString)
  })

  System.exit(0)
}
