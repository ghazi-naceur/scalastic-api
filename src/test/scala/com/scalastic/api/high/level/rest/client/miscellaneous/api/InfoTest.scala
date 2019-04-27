package com.scalastic.api.high.level.rest.client.miscellaneous.api

import org.elasticsearch.action.main.MainResponse

/**
  * Created by Ghazi Naceur on 27/04/2019
  * Email: ghazi.ennacer@gmail.com
  */

object InfoTest extends App {
  private val response: MainResponse = MiscellaneousAPIs.info()

  println(response.getBuild)
  println(response.getClusterName)
  println(response.getClusterUuid)
  println(response.getNodeName)
  println(response.getVersion)
  println(response.isAvailable)

  System.exit(0)
}
