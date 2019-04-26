package com.scalastic.api.high.level.rest.client.document.api

import com.scalastic.api.config.PropertiesLoader.{PERSON_INDEX, PERSON_TYPE}

/**
  * Created by Ghazi Naceur on 07/04/2019
  * Email: ghazi.ennacer@gmail.com
  */
object DeleteTest extends App {
  val response = SingleDocumentAPIs.delete(PERSON_INDEX, PERSON_TYPE, "5cf48d95-9b7c-4bbc-a915-10bedbfcff51")
  println(response.toString)
  println(response.status())

  System.exit(0)
}
