package com.scalastic.api.high.level.rest.client.indices.api

import org.elasticsearch.action.admin.indices.settings.get.GetSettingsResponse

/**
  * Created by Ghazi Naceur on 22/04/2019
  * Email: ghazi.ennacer@gmail.com
  */
object GetSettingsTest extends App {
  private val response: GetSettingsResponse = IndicesAPIs.getSettings("persons", "sc_persons")

  println(" >>>  Get default settings :")
  response.getIndexToDefaultSettings.forEach(setting => {
    println(setting.index)
    println(setting.key)
    println(setting.value)
  })

  println(" >>> Get settings :")

  response.getIndexToSettings.forEach(setting => {
    println(setting.index)
    println(setting.key)
    println(setting.value)
  })

  System.exit(0)
}
