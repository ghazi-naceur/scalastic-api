package com.scalastic.api.high.level.rest.client.document.api

import org.elasticsearch.action.get.{MultiGetRequest, MultiGetResponse}

object MultiGetTest extends App {
  private val items = List(new MultiGetRequest.Item("sc_persons", "sc_person", "123"),
    new MultiGetRequest.Item("sc_persons", "sc_person", "b0ecd75a-91db-4ec8-8a82-f644485f16a2"),
    new MultiGetRequest.Item("sc_persons", "sc_person", "be132f82-81f7-4282-abe5-f18141d70e97"))

  private val response: MultiGetResponse = MultiDocumentAPIs.multiGet(items)
  response.forEach(res => {
    println(res.getResponse.getSourceAsString)
  })

  System.exit(0)
}