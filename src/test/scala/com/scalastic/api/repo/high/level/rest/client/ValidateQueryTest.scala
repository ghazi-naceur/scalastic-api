package com.scalastic.api.repo.high.level.rest.client

import com.scalastic.api.repo.ElasticHighLevelRestClient
import org.elasticsearch.action.admin.indices.validate.query.ValidateQueryResponse
import org.elasticsearch.index.query.QueryBuilders

/**
  * Created by Ghazi Naceur on 22/04/2019
  * Email: ghazi.ennacer@gmail.com
  */
object ValidateQueryTest extends App {

  val builder = QueryBuilders
    .boolQuery()
    .must(QueryBuilders.queryStringQuery("*:*"))
    .filter(QueryBuilders.termQuery("lastName", "Netero"))

  private val response: ValidateQueryResponse = ElasticHighLevelRestClient.validateQuery(builder, "persons")
  println(response.isValid)
  response.getQueryExplanation.forEach(explanation => {
    println(explanation.isValid)
    println(explanation.getIndex)
    println(explanation.getExplanation)
    println(explanation.getShard)
    println(explanation.getError)
  })

  System.exit(0)
}
