package com.scalastic.api.entities

/**
  * Created by Ghazi Naceur on 06/04/2019
  * Email: ghazi.ennacer@gmail.com
  */
case class City(name: String, prefecture: String, country: String, population: Int) {
  def toMap(): Map[String, _] = {
    Map[String, Any]("name" -> name, "prefecture" -> prefecture, "country" -> country, "population" -> population)
  }

  override def toString: String = "City{" +
    "name='" + name + '\'' +
    ", prefecture='" + prefecture + '\'' +
    ", country='" + country + '\'' +
    ", population=" + population +
    '}'
}

object City {
  def toCity(map: Map[String, Any]): City = {
    var mutName: String = ""
    var mutPrefecture: String = ""
    var mutCountry: String = ""
    var mutPopulation: Int = 0
    for ((k, v) <- map) {
      if (k == "name") {
        mutName = v.asInstanceOf[String]
      } else if (k == "prefecture") {
        mutPrefecture = v.asInstanceOf[String]
      } else if (k == "country") {
        mutCountry = v.asInstanceOf[String]
      } else if (k == "population") {
        mutPopulation = v.asInstanceOf[Int]
      }
    }
    City(mutName, mutPrefecture, mutCountry, mutPopulation)
  }
}
