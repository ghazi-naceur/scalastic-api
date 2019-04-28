package com.scalastic.api.high.level.rest.client.ingest.api

import org.elasticsearch.action.ingest.{SimulateDocumentBaseResult, SimulateDocumentVerboseResult, SimulatePipelineResponse}

import scala.collection.JavaConverters._

/**
  * Created by Ghazi Naceur on 28/04/2019
  * Email: ghazi.ennacer@gmail.com
  */
object SimulatePipelineTest extends App {
  private val response: SimulatePipelineResponse = IngestAPIs.simulatePipeline("sc_persons", "sc_person", "", "lastName", "Netero", "lastname-pipeline-id")
  println(response.getPipelineId)
  println(response.isVerbose)

  for (result <- response.getResults.asScala) {
    if (response.isVerbose) {
      val verboseResult = result.asInstanceOf[SimulateDocumentVerboseResult]
      for (processorResult <- verboseResult.getProcessorResults.asScala) {
        println(processorResult.getIngestDocument)
        println(processorResult.getIngestDocument.getMetadata)
        println(processorResult.getFailure)
      }
    }
    else {
      val baseResult = result.asInstanceOf[SimulateDocumentBaseResult]
      println(baseResult.getIngestDocument)
      println(baseResult.getIngestDocument.getMetadata)
      println(baseResult.getFailure)
    }
  }

  System.exit(0)
}
