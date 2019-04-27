package com.scalastic.api.high.level.rest.client.search.api

import java.util

import org.elasticsearch.action.fieldcaps.FieldCapabilities

/**
  * Created by Ghazi Naceur on 27/04/2019
  * Email: ghazi.ennacer@gmail.com
  */

object FieldCapabilitiesTest extends App {
  private val capabilities: util.Map[String, util.Map[String, FieldCapabilities]] = SearchAPIs.fieldCapabilities("lastName", "sc_persons", "persons")
  capabilities.forEach((k, v) => {
    println(k)
    v.forEach((k,v) => {
      println(k)
      println(v.getName)
      println(v.getType)
      println(v.isSearchable)
      println(v.indices())
    })
  })

  System.exit(0)
}
