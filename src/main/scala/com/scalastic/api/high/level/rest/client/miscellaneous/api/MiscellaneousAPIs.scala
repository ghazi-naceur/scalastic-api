package com.scalastic.api.high.level.rest.client.miscellaneous.api

import java.util

import com.scalastic.api.client.ElasticClient
import org.elasticsearch.action.main.MainResponse
import org.elasticsearch.client.xpack.{XPackInfoRequest, XPackInfoResponse}
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

  def ping(): Boolean = {
    client.ping(RequestOptions.DEFAULT)
  }

  def xpackInfo(): XPackInfoResponse = {
    val request = new XPackInfoRequest()
    request.setVerbose(true)
    request.setCategories(util.EnumSet.of(
      XPackInfoRequest.Category.BUILD,
      XPackInfoRequest.Category.LICENSE,
      XPackInfoRequest.Category.FEATURES))
    client.xpack().info(request, RequestOptions.DEFAULT)
  }
}
