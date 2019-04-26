package com.scalastic.api.transport.client.bulk

import java.util.UUID
import java.util.concurrent.TimeUnit

import com.scalastic.api.client.ElasticClient
import org.elasticsearch.action.bulk.{BackoffPolicy, BulkProcessor}
import org.elasticsearch.action.index.IndexRequest
import org.elasticsearch.client.transport.TransportClient
import org.elasticsearch.common.unit.{ByteSizeUnit, ByteSizeValue, TimeValue}

import scala.collection.JavaConverters._

/**
  * Created by Ghazi Naceur on 08/04/2019
  * Email: ghazi.ennacer@gmail.com
  */
object BulkPerformer {

  var bulkRequest: BulkProcessor = _
  val transportClient: TransportClient = ElasticClient.transportClient

  private def prepareBulk(): Unit = {
    if (bulkRequest == null) {
      bulkRequest = BulkProcessor.builder(transportClient, new ElasticsearchBulkProcessor)
        .setBulkActions(10000).setBulkSize(new ByteSizeValue(5, ByteSizeUnit.MB))
        .setFlushInterval(TimeValue.timeValueSeconds(3))
        .setBackoffPolicy(BackoffPolicy.exponentialBackoff(TimeValue.timeValueMillis(1000), 3))
        .setConcurrentRequests(1).build
    }
  }

  def addToBulk(esIndex: String, esType: String, entity: Map[String, Any]): Unit = {
    prepareBulk()
    try {
      val request = new IndexRequest(esIndex, esType, UUID.randomUUID.toString)
      // Map (Scala) => util.Map (Java) ===> asJava (import scala.collection.JavaConverters._)
      request.source(entity.asJava)
      bulkRequest.add(request)
    } catch {
      case e: Exception =>
        throw new Exception(e)
    }
  }

  def closeBulk(): Unit = {
    if (bulkRequest != null) {
      try {
        bulkRequest.awaitClose(Long.MaxValue, TimeUnit.MILLISECONDS)
      }
      catch {
        case e: Exception =>
          throw new Exception(e)
      }
      bulkRequest.close()
    }
  }
}
