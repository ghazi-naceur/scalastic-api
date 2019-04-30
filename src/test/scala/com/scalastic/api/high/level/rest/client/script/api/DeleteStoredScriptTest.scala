package com.scalastic.api.high.level.rest.client.script.api

import org.elasticsearch.action.support.master.AcknowledgedResponse

/**
  * Created by Ghazi Naceur on 30/04/2019
  * Email: ghazi.ennacer@gmail.com
  */
object DeleteStoredScriptTest extends App {
  private val response: AcknowledgedResponse = ScriptAPIs.deleteStoredScript("changing-content")
  println(response.isAcknowledged)

  System.exit(0)
}
