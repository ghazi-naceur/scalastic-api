package com.scalastic.api.high.level.rest.client.document.api

import com.scalastic.api.config.PropertiesLoader.{PERSON_INDEX, PERSON_TYPE}

/**
  * Created by Ghazi Naceur on 07/04/2019
  * Email: ghazi.ennacer@gmail.com
  */
object UpdateTest extends App {
  val map = Map[String, Any]("lastName" -> "Uchiha", "occupation" -> "Shinobi")
  val response = SingleDocumentAPIs.update(PERSON_INDEX, PERSON_TYPE, "be132f82-81f7-4282-abe5-f18141d70e97", map)
  println(response.toString)
  println(response.status())

  System.exit(0)
}
