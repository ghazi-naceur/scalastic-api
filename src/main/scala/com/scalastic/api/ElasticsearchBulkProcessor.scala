package com.scalastic.api

import java.util

import org.elasticsearch.action.DocWriteRequest
import org.elasticsearch.action.bulk.BulkProcessor.Listener
import org.elasticsearch.action.bulk.{BulkRequest, BulkResponse}
import scala.collection.JavaConverters._

/**
  * Created by Ghazi Naceur on 08/04/2019
  * Email: ghazi.ennacer@gmail.com
  */
class ElasticsearchBulkProcessor extends Listener {
  override def beforeBulk(executionId: Long, request: BulkRequest): Unit = {}

  override def afterBulk(executionId: Long, request: BulkRequest, response: BulkResponse): Unit = {
    val bulkItemResponses = response.getItems

    if (bulkItemResponses.nonEmpty) {
      for (tempResponse <- bulkItemResponses) {
        val temp = new StringBuilder
        if (tempResponse.isFailed) {
          temp.append("Failed :: (").append(tempResponse.getFailureMessage).append(")").append(tempResponse.getIndex).append(" :: ").append(tempResponse.getType).append(" :: ").append(tempResponse.getId)
        }
        else {
          val index = tempResponse.getIndex
          temp.append("Success :: ").append(index).append(" :: ").append(tempResponse.getType).append(" :: ").append(tempResponse.getId)
        }
      }
    }
  }

  override def afterBulk(executionId: Long, request: BulkRequest, failure: Throwable): Unit = {
    val temp = new StringBuilder
    val bulkItemResponses: util.List[DocWriteRequest[_]] = request.requests
    if (bulkItemResponses != null) {
      // util.List (Java) => List (Scala) ===> asScala (import scala.collection.JavaConverters._)
      for (tempRequest <- bulkItemResponses.asScala) {
        temp.append("Failed single request {}", tempRequest)
      }
    }
    temp.toString()
  }
}
