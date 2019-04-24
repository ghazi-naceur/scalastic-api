package com.scalastic.api.repo.high.level.rest.client

import com.scalastic.api.entities.Person
import com.scalastic.api.high.level.rest.client.ElasticHighLevelRestClient
import com.scalastic.api.high.level.rest.client.document.api.SingleDocumentAPIs
import org.elasticsearch.action.admin.indices.rollover.RolloverResponse

/**
  * Created by Ghazi Naceur on 21/04/2019
  * Email: ghazi.ennacer@gmail.com
  */
object RolloverTest extends App {

  // 1- Create "rollover_index_1" index with "rollover_alias_1" alias
  // 2- Insert 600 entities
  // 3- Rollover to "rollover_index_2" index
  // 4- Insert 100 entities

  // Result :
  // 1- 600 entities in "rollover_index_1"
  // 2- Deletion of "rollover_alias_1" from "rollover_index_1"
  // 3- Creation of "rollover_alias_1" for "rollover_index_2"
  // 4- 100 entities in "rollover_index_2"

  ElasticHighLevelRestClient.createIndex("rollover_index_1", "rollover_alias_1", null)

  val person = Person("Isaac", "Netero", 125, "Hunter")
  for (i <- 0 until 501) {
    SingleDocumentAPIs.index("rollover_alias_1", "rollover", person.toMap())
  }

  private val response: RolloverResponse = ElasticHighLevelRestClient.rollover("rollover_alias_1", "rollover_index_3", 16)
  println(response.getOldIndex)
  println(response.getNewIndex)
  println(response.isDryRun)
  println(response.isRolledOver)
  println(response.getConditionStatus)


  for (i <- 0 until 100) {
    SingleDocumentAPIs.index("rollover_alias_1", "rollover", person.toMap())
  }

  System.exit(0)
}
