package com.scalastic.api.repo.high.level.rest.client

import java.util

import com.scalastic.api.repo.ElasticHighLevelRestClient
import org.elasticsearch.client.SyncedFlushResponse

import scala.collection.JavaConverters._

/**
  * Created by Ghazi Naceur on 20/04/2019
  * Email: ghazi.ennacer@gmail.com
  */
object SyncedFlushIndicesTest extends App {
  // SyncedFlush all indices
  private val response: SyncedFlushResponse = ElasticHighLevelRestClient.syncedFlushIndices()
  private val results: util.Map[String, SyncedFlushResponse.IndexResult] = response.getIndexResults
  for ((k, v) <- results.asScala) {
    println(s"$k : ${v.failures()}")
  }
  println("----")
  // SyncedFlush Specific indices
  private val response2: SyncedFlushResponse = ElasticHighLevelRestClient.syncedFlushIndices("persons", "sc_persons")
  private val results2: util.Map[String, SyncedFlushResponse.IndexResult] = response2.getIndexResults
  for ((k, v) <- results2.asScala) {
    println(s"$k : ${v.failures()}")
  }

  System.exit(0)
}
