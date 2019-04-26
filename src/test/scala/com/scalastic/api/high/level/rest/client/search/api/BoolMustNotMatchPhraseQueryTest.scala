package com.scalastic.api.high.level.rest.client.search.api

import com.scalastic.api.config.PropertiesLoader.PERSON_INDEX
import com.scalastic.api.entities.Person

/**
  * Created by Ghazi Naceur on 26/04/2019
  * Email: ghazi.ennacer@gmail.com
  */

object BoolMustNotMatchPhraseQueryTest extends App {
  val map = Map[String, Any]("lastName" -> "Netero", "occupation" -> "Hunter")

  val persons2 = SearchAPIs.searchWithBoolMustNotMatchPhraseQuery(PERSON_INDEX, map)
  persons2.foreach(map => {
    println(Person.toPerson(map).toString)
  })

  System.exit(0)
}
