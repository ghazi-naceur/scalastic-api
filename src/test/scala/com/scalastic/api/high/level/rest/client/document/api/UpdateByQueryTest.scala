package com.scalastic.api.high.level.rest.client.document.api

import org.elasticsearch.index.query.QueryBuilders
import org.elasticsearch.index.reindex.BulkByScrollResponse
import org.elasticsearch.script.{Script, ScriptType}

import scala.collection.JavaConverters._


object UpdateByQueryTest extends App {

  val params: Map[String, AnyRef] = Map()
  val script = new Script(ScriptType.INLINE, "painless",
    "if (ctx._source.lastName == 'Netero') {ctx._source.age++;}", params.asJava)
  val query = QueryBuilders.matchQuery("lastName", "Netero")
  // query could be null => update all
  private val response: BulkByScrollResponse = MultiDocumentAPIs.updateByQuery(script, query, "sc_persons")

  println(response.getCreated)
  println(response.getDeleted)
  println(response.getStatus)
  println(response.getTotal)

  System.exit(0)
}
