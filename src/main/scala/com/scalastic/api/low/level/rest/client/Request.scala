package com.scalastic.api.low.level.rest.client

import com.scalastic.api.client.ElasticClient
import org.elasticsearch.client.{Request, Response, RestClient}

/**
  * Created by Ghazi Naceur on 01/05/2019
  * Email: ghazi.ennacer@gmail.com
  */
object Request {

  private val client: RestClient = ElasticClient.restClient

  def performRequest(httpMethod: String, endpointUrl: String): Response = {
    val request = new Request(httpMethod, endpointUrl)
    request.addParameter("pretty", "true")
    client.performRequest(request)
  }
}
