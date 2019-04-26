package com.scalastic.api.high.level.rest.client.document.api

import org.elasticsearch.index.query.QueryBuilders
import org.elasticsearch.index.reindex.BulkByScrollResponse

object DeleteByQueryTest extends App {

  val query = QueryBuilders.matchQuery("lastName", "Uchiha")
  // query could be null => delete all
  private val response: BulkByScrollResponse = MultiDocumentAPIs.deleteByQuery(query, "people")
  println(response.getDeleted)
  println(response.getNoops)
  println(response.getStatus)
  println(response.getTotal)

  System.exit(0)
}
