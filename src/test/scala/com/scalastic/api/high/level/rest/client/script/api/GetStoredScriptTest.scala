package com.scalastic.api.high.level.rest.client.script.api

import org.elasticsearch.action.admin.cluster.storedscripts.GetStoredScriptResponse

/**
  * Created by Ghazi Naceur on 30/04/2019
  * Email: ghazi.ennacer@gmail.com
  */
object GetStoredScriptTest extends App {

  //  Get all stored scripts by invoking : http://localhost:9200/_cluster/state/metadata?filter_path=metadata.stored_scripts&pretty

  private val response: GetStoredScriptResponse = ScriptAPIs.getStoredScript("changing-content")
  println(response.getId)
  println(response.getSource.getLang)
  println(response.getSource.getSource)
  println(response.getSource.getOptions)
  println(response.status())

  System.exit(0)
}
