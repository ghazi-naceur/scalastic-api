package com.scalastic.api.config

import com.typesafe.config.{Config, ConfigFactory}

/**
  * Created by Ghazi Naceur on 06/04/2019
  * Email: ghazi.ennacer@gmail.com
  */
object PropertiesLoader {

  val CONFIG: Config = ConfigFactory.load()
  val HOST: String = CONFIG.getString("elasticsearch_host")
  val PORT: Int = CONFIG.getInt("elasticsearch_port")
  val HTTP_PORT: Int = CONFIG.getInt("elasticsearch_http_port")
  val PERSON_INDEX: String = CONFIG.getString("person_index")
  val PERSON_TYPE: String = CONFIG.getString("person_type")
  val CITY_INDEX: String = CONFIG.getString("city_index")
  val CITY_TYPE: String = CONFIG.getString("city_type")
}
