package com.scalastic.api.high.level.rest.client.document.api

import com.scalastic.api.config.PropertiesLoader.{PERSON_INDEX, PERSON_TYPE}

object ExistsApiTest extends App {
  private val exists: Boolean = SingleDocumentAPIs.exists(PERSON_INDEX, PERSON_TYPE, "570f3e69-0f9a-4f64-840a-cbd0efa2d30a")
  println(exists)

  System.exit(0)
}
