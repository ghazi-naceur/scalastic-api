package com.scalastic.api.high.level.rest.client.search.api

import com.scalastic.api.entities.{City, Person}

/**
  * Created by Ghazi Naceur on 26/04/2019
  * Email: ghazi.ennacer@gmail.com
  */
object IdsQueryTest extends App {
  val persons = SearchAPIs.searchWithIdsQuery("sc_persons", "7921d79f-c61e-46ad-b4a1-fdf14cfc23da", "d04d8ab9-2b2b-412d-ba67-f3dee957c980", "570f3e69-0f9a-4f64-840a-cbd0efa2d30a")
  persons.foreach(map => {
    println(Person.toPerson(map).toString)
  })
  private val cities: List[Map[String, Any]] = SearchAPIs.searchWithIdsQuery("cities", "a5d4513d-53a7-4535-b2fd-a72bb4ce5be3", "69967626-328b-4d28-a1e1-bd27186c275d")
  cities.foreach(city => {
    println(City.toCity(city).toString)
  })

  System.exit(0)
}
