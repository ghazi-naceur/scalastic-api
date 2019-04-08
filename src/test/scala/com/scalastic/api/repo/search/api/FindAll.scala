package com.scalastic.api.repo.search.api

import com.scalastic.api.entities.Person
import com.scalastic.api.repo.ElasticQueryBuilder
import com.scalastic.api.config.PropertiesLoader.{PERSON_INDEX}

/**
  * Created by Ghazi Naceur on 07/04/2019
  * Email: ghazi.ennacer@gmail.com
  */
object FindAll extends App {
  // Using scan & scroll
  val list = ElasticQueryBuilder.findAll(PERSON_INDEX)
  list.foreach(map => {
    println(Person.toPerson(map).toString)
  })

  System.exit(0)
}
