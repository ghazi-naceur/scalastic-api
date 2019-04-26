package com.scalastic.api.transport.client.administration

import com.scalastic.api.transport.client.document.api.ElasticQueryBuilder
import org.elasticsearch.index.reindex.BulkByScrollResponse

/**
  * Created by Ghazi Naceur on 09/04/2019
  * Email: ghazi.ennacer@gmail.com
  */
object ReindexTest extends App {
  private val response: BulkByScrollResponse = ElasticQueryBuilder.reindex("sc_persons", "other_persons", Map("firstName" -> "Isaac", "occupation" -> "Hunter"))
  println(response.getCreated)
}
