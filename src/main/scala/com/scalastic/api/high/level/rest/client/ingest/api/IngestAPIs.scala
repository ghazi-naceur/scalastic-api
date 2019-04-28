package com.scalastic.api.high.level.rest.client.ingest.api

import java.nio.charset.StandardCharsets

import com.scalastic.api.client.ElasticClient
import org.elasticsearch.action.ingest._
import org.elasticsearch.action.support.master.AcknowledgedResponse
import org.elasticsearch.client.{RequestOptions, RestHighLevelClient}
import org.elasticsearch.common.bytes.BytesArray
import org.elasticsearch.common.xcontent.XContentType

/**
  * Created by Ghazi Naceur on 28/04/2019
  * Email: ghazi.ennacer@gmail.com
  */
object IngestAPIs {

  private val client: RestHighLevelClient = ElasticClient.client

  def putPipeline(field: String, value: String, pipelineId: String): AcknowledgedResponse = {
    val source = "{\"description\":\"my set of processors\"," +
      "\"processors\":[{\"set\":{\"field\":\"" + field + "\",\"value\":\"" + value + "\"}}]}"
    val request = new PutPipelineRequest(pipelineId, new BytesArray(source.getBytes(StandardCharsets.UTF_8)), XContentType.JSON)
    client.ingest().putPipeline(request, RequestOptions.DEFAULT)
  }

  def getPipeline(pipelineIds: String*): GetPipelineResponse = {
    val request = new GetPipelineRequest(pipelineIds: _*)
    client.ingest().getPipeline(request, RequestOptions.DEFAULT)
  }

  def deletePipeline(pipelineId: String): AcknowledgedResponse = {
    val request = new DeletePipelineRequest(pipelineId)
    client.ingest().deletePipeline(request, RequestOptions.DEFAULT)
  }

  def simulatePipeline(esIndex: String, esType: String, esId: String, field: String, value: String, pipelineId: String): SimulatePipelineResponse = {
    val source =
      "{\"" +
        "pipeline\":{" +
        "\"description\":\"my set of processors\"," +
        "\"processors\":[{\"set\":{\"field\":\"" + field + "\",\"value\":\"" + value + "\"}}]" +
        "}," +
        "\"docs\":[" +
        "{\"_index\":\"" + esIndex + "\",\"_type\":\"" + esType + "\",\"_id\":\"" + esId + "\",\"_source\":{\"" + field + "\":\"" + value + "\"}}" +
        "]" +
        "}"
    val request = new SimulatePipelineRequest(new BytesArray(source.getBytes(StandardCharsets.UTF_8)), XContentType.JSON)
    request.setId(pipelineId)
    request.setVerbose(true)
    client.ingest().simulate(request, RequestOptions.DEFAULT)
  }
}
