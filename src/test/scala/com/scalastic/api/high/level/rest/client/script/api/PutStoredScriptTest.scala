package com.scalastic.api.high.level.rest.client.script.api

import org.elasticsearch.action.support.master.AcknowledgedResponse

/**
  * Created by Ghazi Naceur on 30/04/2019
  * Email: ghazi.ennacer@gmail.com
  */
object PutStoredScriptTest extends App {

  val script = "{\n" +
    "\"script\": {\n" +
    "\"lang\": \"painless\",\n" +
    "\"source\": \"if (lastName == 'Netero') {age++;}\"" +
    "}\n" +
    "}\n"
  private val response: AcknowledgedResponse = ScriptAPIs.putStoredScript("changing-content", script)
  println(response.isAcknowledged)

  System.exit(0)
}
