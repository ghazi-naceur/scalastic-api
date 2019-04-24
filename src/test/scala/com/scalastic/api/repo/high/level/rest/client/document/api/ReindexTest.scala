package com.scalastic.api.repo.high.level.rest.client.document.api

import com.scalastic.api.high.level.rest.client.document.api.MultiDocumentAPIs
import org.elasticsearch.index.reindex.BulkByScrollResponse

object ReindexTest extends App {

  // sourceIndices should have the same type
  private val response: BulkByScrollResponse = MultiDocumentAPIs.reindex("scala_index_3-7", "scala_index_3", "scala_index_5")
  println(response.getStatus.getCreated)
  println(response.getStatus.getDeleted)
  println(response.getStatus.getTotal)

  System.exit(0)
}
