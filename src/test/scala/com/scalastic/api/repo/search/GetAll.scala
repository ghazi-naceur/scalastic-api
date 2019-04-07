package com.scalastic.api.repo.search

import com.scalastic.api.entities.Person
import com.scalastic.api.repo.ElasticQueryBuilder
import com.scalastic.api.config.PropertiesLoader.{PERSON_INDEX}

/**
  * Created by Ghazi Naceur on 07/04/2019
  * Email: ghazi.ennacer@gmail.com
  */
object GetAll extends App {
  // Getting the first page
  val list = ElasticQueryBuilder.getAll(PERSON_INDEX)
  list.foreach(map => {
    println(Person.toPerson(map).toString)
  })

  System.exit(0)
}
