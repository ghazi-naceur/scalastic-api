package com.scalastic.api.repo.high.level.rest.client

import com.scalastic.api.repo.ElasticHighLevelRestClient

/**
  * Created by Ghazi Naceur on 19/04/2019
  * Email: ghazi.ennacer@gmail.com
  */
object IndicesExistsTest extends App {
  ElasticHighLevelRestClient.indicesExists("sc_persons", "persons") // true
  ElasticHighLevelRestClient.indicesExists("sc_persons", "persons", "abc") // false
}
