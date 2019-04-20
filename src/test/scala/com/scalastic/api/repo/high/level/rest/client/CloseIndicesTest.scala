package com.scalastic.api.repo.high.level.rest.client

import com.scalastic.api.repo.ElasticHighLevelRestClient
import org.elasticsearch.action.support.master.AcknowledgedResponse

/**
  * Created by Ghazi Naceur on 20/04/2019
  * Email: ghazi.ennacer@gmail.com
  */
object CloseIndicesTest extends App {
  private val response: AcknowledgedResponse = ElasticHighLevelRestClient.closeIndices("sc_persons", "persons", "cities")
  println(response.isAcknowledged)

  System.exit(0)
}
