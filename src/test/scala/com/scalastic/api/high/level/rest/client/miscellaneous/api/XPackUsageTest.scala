package com.scalastic.api.high.level.rest.client.miscellaneous.api

import java.util

import org.elasticsearch.client.xpack.XPackUsageResponse

/**
  * Created by Ghazi Naceur on 27/04/2019
  * Email: ghazi.ennacer@gmail.com
  */

object XPackUsageTest extends App {
  private val response: XPackUsageResponse = MiscellaneousAPIs.xpackUsage()
  response.getUsages.forEach((k, v) => {
    println(k)
    println(v)
    println()
  })

  private val usages: util.Map[String, util.Map[String, AnyRef]] = response.getUsages
  private val monitoring: util.Map[String, AnyRef] = usages.get("monitoring")
  println(monitoring.get("available"))
  println(monitoring.get("enabled"))
  println(monitoring.get("collection_enabled"))

  System.exit(0)
}
