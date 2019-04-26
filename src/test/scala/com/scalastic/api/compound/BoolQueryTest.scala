package com.scalastic.api.compound

import com.scalastic.api.ElasticCompoundQueryBuilder
import com.scalastic.api.config.PropertiesLoader.PERSON_INDEX
import com.scalastic.api.entities.Person

/**
  * Created by Ghazi Naceur on 17/04/2019
  * Email: ghazi.ennacer@gmail.com
  */
object BoolQueryTest extends App {
  val map = Map[String, Any]("lastName" -> "Netero", "occupation" -> "Hunter")

  println("1- Bool Must :")
  val persons1 = ElasticCompoundQueryBuilder.getDocsWithBoolMustQuery(PERSON_INDEX, map)
  persons1.foreach(map => {
    println(Person.toPerson(map).toString)
  })

  println("2- Bool Must Not :")
  val persons2 = ElasticCompoundQueryBuilder.getDocsWithBoolMustNotQuery(PERSON_INDEX, map)
  persons2.foreach(map => {
    println(Person.toPerson(map).toString)
  })

  println("3- Bool Should :")
  val persons3 = ElasticCompoundQueryBuilder.getDocsWithBoolShouldQuery(PERSON_INDEX, map)
  persons3.foreach(map => {
    println(Person.toPerson(map).toString)
  })

  println("4- Bool Filter :")
  val persons4 = ElasticCompoundQueryBuilder.getDocsWithBoolFilterQuery(PERSON_INDEX, map)
  persons4.foreach(map => {
    println(Person.toPerson(map).toString)
  })

  System.exit(0)
}
