package com.scalastic.api.entities

import play.api.libs.json.{Format, Json}

/**
  * Created by Ghazi Naceur on 18/04/2019
  * Email: ghazi.ennacer@gmail.com
  */
case class Country(name: String, towns: List[Town], population: Long) {
}

object Country {
  implicit val format: Format[Country] = Json.format[Country]
}