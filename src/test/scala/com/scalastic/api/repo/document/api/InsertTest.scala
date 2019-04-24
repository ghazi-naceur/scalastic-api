package com.scalastic.api.repo.document.api

import com.scalastic.api.config.PropertiesLoader.{PERSON_INDEX, PERSON_TYPE}
import com.scalastic.api.entities.Person
import com.scalastic.api.high.level.rest.client.document.api.SingleDocumentAPIs
import org.elasticsearch.action.index.IndexResponse
import play.api.libs.json.Json

/**
  * Created by Ghazi Naceur on 07/04/2019
  * Email: ghazi.ennacer@gmail.com
  */
object InsertTest extends App {

  // 1- Source as Map :
  val person = Person("Isaac", "Netero", 125, "Hunter")
  val response = SingleDocumentAPIs.index(PERSON_INDEX, PERSON_TYPE, person.toMap())
  println(response.toString)
  println(response.status())

  // 2- Source as JsonObject :
  private val person2 = Person("Beyond", "Netero", 125, "Hunter").getJsonObject()
  private val response2: IndexResponse = SingleDocumentAPIs.index(PERSON_INDEX, PERSON_TYPE, person2)
  println(response2.toString)
  println(response2.status())

  // 3- Source as JSON :
  private val person3 = Person("Beyond", "Netero", 125, "Hunter")
  val json = Json.stringify(Json.toJson(person3))
  private val response3: IndexResponse = SingleDocumentAPIs.index(PERSON_INDEX, PERSON_TYPE, json)
  println(response3.toString)
  println(response3.status())

  System.exit(0)
}
