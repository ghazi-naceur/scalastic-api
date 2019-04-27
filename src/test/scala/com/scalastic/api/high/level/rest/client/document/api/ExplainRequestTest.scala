package com.scalastic.api.high.level.rest.client.document.api

import com.scalastic.api.high.level.rest.client.search.api.SearchAPIs
import org.elasticsearch.action.explain.ExplainResponse

/**
  * Created by Ghazi Naceur on 27/04/2019
  * Email: ghazi.ennacer@gmail.com
  */

object ExplainRequestTest extends App {
  private val response: ExplainResponse = SearchAPIs.explain("sc_persons", "sc_person", "114613a2-8403-4a98-8691-fecb3972fadd", "lastName", "Netero")
  println(response.isMatch)
  println(response.isExists)
  println(response.getExplanation)
  println(response.getGetResult)

  System.exit(0)
}
