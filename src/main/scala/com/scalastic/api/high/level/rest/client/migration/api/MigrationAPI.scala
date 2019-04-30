package com.scalastic.api.high.level.rest.client.migration.api

import com.scalastic.api.client.ElasticClient
import org.elasticsearch.client.{RequestOptions, RestHighLevelClient}
import org.elasticsearch.protocol.xpack.migration.{IndexUpgradeInfoRequest, IndexUpgradeInfoResponse}

/**
  * Created by Ghazi Naceur on 30/04/2019
  * Email: ghazi.ennacer@gmail.com
  */
object MigrationAPI {

  private val client: RestHighLevelClient = ElasticClient.client

  def indexUpgradeInfo(indices: String*): IndexUpgradeInfoResponse = {
    val request = new IndexUpgradeInfoRequest()
    request.indices(indices: _*)
    client.migration().getAssistance(request, RequestOptions.DEFAULT)
  }
}
