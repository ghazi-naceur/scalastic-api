package com.scalastic.api.high.level.rest.client.miscellaneous.api

import com.scalastic.api.client.ElasticClient
import org.elasticsearch.action.main.MainResponse
import org.elasticsearch.client.{RequestOptions, RestHighLevelClient}

/**
  * Created by Ghazi Naceur on 27/04/2019
  * Email: ghazi.ennacer@gmail.com
  */

object MiscellaneousAPIs {

  private val client: RestHighLevelClient = ElasticClient.client

  def info(): MainResponse = {
    client.info(RequestOptions.DEFAULT)
  }
}
