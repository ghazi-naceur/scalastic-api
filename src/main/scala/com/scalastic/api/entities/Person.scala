package com.scalastic.api.entities

/**
  * Created by Ghazi Naceur on 06/04/2019
  * Email: ghazi.ennacer@gmail.com
  */
case class Person(firstName: String, lastName: String, age: Int, occupation: String) {
  def toMap(): Map[String, _] = {
    Map[String, Any]("firstName" -> firstName, "lastName" -> lastName, "age" -> age, "occupation" -> occupation)
  }

  override def toString: String = "Person{" +
    "firstName='" + firstName + '\'' +
    ", lastName='" + lastName + '\'' +
    ", age=" + age +
    ", occupation='" + occupation + '\'' +
    '}'
}

object Person {
  def toPerson(map: Map[String, Any]): Person = {
    var mutFirstName: String = ""
    var mutLastName: String = ""
    var mutAge: Int = 0
    var mutOccupation: String = ""
    for ((k, v) <- map) {
      if (k == "firstName") {
        mutFirstName = v.asInstanceOf[String]
      } else if (k == "lastName") {
        mutLastName = v.asInstanceOf[String]
      } else if (k == "age") {
        mutAge = v.asInstanceOf[Int]
      } else if (k == "occupation") {
        mutOccupation = v.asInstanceOf[String]
      }
    }
    Person(mutFirstName, mutLastName, mutAge, mutOccupation)
  }
}
