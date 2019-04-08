package com.scalastic.api.repo.document.api

import com.scalastic.api.entities.Person
import com.scalastic.api.repo.ElasticQueryBuilder

/**
  * Created by Ghazi Naceur on 08/04/2019
  * Email: ghazi.ennacer@gmail.com
  */
object BulkApiTest extends App {
  private val person1: Map[String, _] = Person("Shisui", "Uchiha", 29, "Shinobi").toMap()
  private val person2: Map[String, _] = Person("Shisui", "Uchiha", 29, "Shinobi").toMap()
  private val person3: Map[String, _] = Person("Shisui", "Uchiha", 29, "Shinobi").toMap()
  private val person4: Map[String, _] = Person("Shisui", "Uchiha", 29, "Shinobi").toMap()
  private val person5: Map[String, _] = Person("Shisui", "Uchiha", 29, "Shinobi").toMap()
  ElasticQueryBuilder.bulk("persons", "person", List(person1, person2, person3, person4, person5))
}
