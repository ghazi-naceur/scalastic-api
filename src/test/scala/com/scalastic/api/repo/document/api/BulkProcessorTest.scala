package com.scalastic.api.repo.document.api

import com.scalastic.api.entities.Person
import com.scalastic.api.repo.BulkPerformer

/**
  * Created by Ghazi Naceur on 08/04/2019
  * Email: ghazi.ennacer@gmail.com
  */
object BulkProcessorTest extends App {

  private val person1: Map[String, _] = Person("Shisui", "Uchiha", 29, "Shinobi").toMap()
  private val person2: Map[String, _] = Person("Shisui", "Uchiha", 29, "Shinobi").toMap()
  private val person3: Map[String, _] = Person("Shisui", "Uchiha", 29, "Shinobi").toMap()
  private val person4: Map[String, _] = Person("Shisui", "Uchiha", 29, "Shinobi").toMap()
  private val person5: Map[String, _] = Person("Shisui", "Uchiha", 29, "Shinobi").toMap()

  List(person1, person2, person3, person4, person5).foreach(person => {
    BulkPerformer.addToBulk("sc_persons", "sc_person", person)
  })
}
