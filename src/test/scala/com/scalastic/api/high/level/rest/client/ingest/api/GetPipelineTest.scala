package com.scalastic.api.high.level.rest.client.ingest.api

import org.elasticsearch.action.ingest.GetPipelineResponse

import scala.collection.JavaConverters._

/**
  * Created by Ghazi Naceur on 28/04/2019
  * Email: ghazi.ennacer@gmail.com
  */
object GetPipelineTest extends App {

  private val response: GetPipelineResponse = IngestAPIs.getPipeline("lastname-pipeline-id")
  val pipelines = response.pipelines

  println(response.isFound)
  for (pipeline <- pipelines.asScala) {
    println(pipeline.getConfigAsMap)
  }
  System.exit(0)
}
