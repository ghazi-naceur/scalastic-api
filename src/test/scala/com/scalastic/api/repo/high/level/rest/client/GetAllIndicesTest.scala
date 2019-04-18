package com.scalastic.api.repo.high.level.rest.client

import com.scalastic.api.repo.ElasticHighLevelRestClient

/**
  * Created by Ghazi Naceur on 18/04/2019
  * Email: ghazi.ennacer@gmail.com
  */
object GetAllIndicesTest extends App {
  ElasticHighLevelRestClient.getIndicesList.foreach(index => {
    println(index)
  })

  System.exit(0)
}
