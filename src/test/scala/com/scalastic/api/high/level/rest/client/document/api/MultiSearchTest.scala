package com.scalastic.api.high.level.rest.client.document.api

import com.scalastic.api.high.level.rest.client.search.api.SearchAPIs

/**
  * Created by Ghazi Naceur on 27/04/2019
  * Email: ghazi.ennacer@gmail.com
  */

object MultiSearchTest extends App {
  val searchCriteria = Map("lastName" -> "Netero", "name" -> "Konoha")
  private val list: List[Map[String, _]] = SearchAPIs.multiSearch(searchCriteria)

  list.foreach(entity => {
    println(entity.toString())
  })

  System.exit(0)
}
