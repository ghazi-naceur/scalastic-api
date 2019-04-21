package com.scalastic.api.entities

import play.api.libs.json.{Format, Json}

/**
  * Created by Ghazi Naceur on 21/04/2019
  * Email: ghazi.ennacer@gmail.com
  */
case class Town(name: String, prefecture: String, country: String, population: Int) {
}

object Town {
  implicit val format: Format[Town] = Json.format[Town]
}
