package com.scalastic.api.low.level.rest.client

import org.apache.http.util.EntityUtils
import org.elasticsearch.client.Response

/**
  * Created by Ghazi Naceur on 01/05/2019
  * Email: ghazi.ennacer@gmail.com
  */
object PerformRequestTest extends App {
  private val response: Response = Request.performRequest("GET", "/_cat/indices")
  println(response.getEntity)
  println(response.getHeaders)
  println(response.getHost)
  println(response.getRequestLine)
  println(response.getStatusLine)
  println(response.getStatusLine.getStatusCode)
  println(EntityUtils.toString(response.getEntity))

  System.exit(0)
}
