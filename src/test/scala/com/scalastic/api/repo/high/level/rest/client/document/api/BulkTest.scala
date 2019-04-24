package com.scalastic.api.repo.high.level.rest.client.document.api

import com.scalastic.api.entities.Person
import com.scalastic.api.high.level.rest.client.document.api.MultiDocumentAPIs
import org.elasticsearch.action.bulk.BulkResponse
import org.elasticsearch.action.delete.DeleteRequest
import org.elasticsearch.action.index.IndexRequest
import org.elasticsearch.action.update.UpdateRequest

object BulkTest extends App {

  private val request1: IndexRequest = new IndexRequest("sc_persons", "sc_person", "123")
    .source(Person("Gon", "Freecs", 15, "Hunter").getJsonObject())
  private val request2: UpdateRequest = new UpdateRequest("sc_persons", "sc_person", "123")
    .doc(Person("Gin", "Freecs", 45, "Hunter").getJsonObject())
  private val request3 = new DeleteRequest("sc_persons", "sc_person", "570f3e69-0f9a-4f64-840a-cbd0efa2d30a")

  private val requests = List(request1, request2, request3)

  private val response: BulkResponse = MultiDocumentAPIs.bulk("sc_persons", "sc_person", requests)

  println(response.status())
  println(response.hasFailures)
  println(response.forEach(response => {
    println(response.getResponse)
    println(response.getId)
    println(response.getItemId)
    println(response.getIndex)
  }))

  System.exit(0)
}
