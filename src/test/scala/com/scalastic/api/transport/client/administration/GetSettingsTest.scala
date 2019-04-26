package com.scalastic.api.transport.client.administration

import org.elasticsearch.action.admin.indices.settings.get.GetSettingsResponse

/**
  * Created by Ghazi Naceur on 13/04/2019
  * Email: ghazi.ennacer@gmail.com
  */
object GetSettingsTest extends App {
  private val response: GetSettingsResponse = ElasticAdministration.getSettings("persons", "sc_persons")
  response.getIndexToSettings.forEach(setting => {
    println(setting.index)
    println(setting.key)
    println(setting.value)
  })

  System.exit(0)
}
