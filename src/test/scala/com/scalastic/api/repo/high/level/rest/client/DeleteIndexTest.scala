package com.scalastic.api.repo.high.level.rest.client

import com.scalastic.api.repo.ElasticHighLevelRestClient

/**
  * Created by Ghazi Naceur on 19/04/2019
  * Email: ghazi.ennacer@gmail.com
  */
object DeleteIndexTest extends App {
  ElasticHighLevelRestClient.deleteIndex("scala_index")
}
