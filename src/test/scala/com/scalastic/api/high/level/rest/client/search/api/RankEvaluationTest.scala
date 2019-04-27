package com.scalastic.api.high.level.rest.client.search.api

import org.elasticsearch.index.rankeval.EvalQueryQuality

/**
  * Created by Ghazi Naceur on 27/04/2019
  * Email: ghazi.ennacer@gmail.com
  */

object RankEvaluationTest extends App {
  private val response: Map[String, EvalQueryQuality] = SearchAPIs.rankEvaluation("sc_persons", "114613a2-8403-4a98-8691-fecb3972fadd", "lastname_request", "lastName", "Netero", Array("sc_persons", "persons"))
  response.foreach(res => {
    println(res._1)
    res._2.getHitsAndRatings.forEach(hit => {
      println(hit.getRating)
      println(hit.getSearchHit)
    })
    println(res._2.getMetricDetails.getMetricName)
  })

  System.exit(0)
}
