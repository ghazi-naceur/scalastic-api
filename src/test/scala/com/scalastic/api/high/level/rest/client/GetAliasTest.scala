package com.scalastic.api.high.level.rest.client

import org.elasticsearch.client.GetAliasesResponse

/**
  * Created by Ghazi Naceur on 22/04/2019
  * Email: ghazi.ennacer@gmail.com
  */
object GetAliasTest extends App {

  private val response: GetAliasesResponse = ElasticHighLevelRestClient.getAlias("rollover_index_2")
  println(response.getAliases)
  println(response.status())
  println(response.getError)

  private val response2: GetAliasesResponse = ElasticHighLevelRestClient.getAlias("rollover_index_2", "rollover_alias_1")
  println(response2.getAliases)
  println(response2.status())
  println(response2.getError)

  private val response3: GetAliasesResponse = ElasticHighLevelRestClient.getAlias("rollover_index_2", "rollover_alias_1", "abc")
  println(response3.getAliases)
  println(response3.status())
  println(response3.getError)

  System.exit(0)
}
