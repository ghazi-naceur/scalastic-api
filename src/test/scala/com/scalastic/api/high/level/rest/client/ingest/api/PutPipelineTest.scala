package com.scalastic.api.high.level.rest.client.ingest.api

import org.elasticsearch.action.support.master.AcknowledgedResponse

/**
  * Created by Ghazi Naceur on 28/04/2019
  * Email: ghazi.ennacer@gmail.com
  */
object PutPipelineTest extends App {
  private val response: AcknowledgedResponse = IngestAPIs.putPipeline("lastName", "Netero", "lastname-pipeline-id")
  println(response.isAcknowledged)

  System.exit(0)
}
