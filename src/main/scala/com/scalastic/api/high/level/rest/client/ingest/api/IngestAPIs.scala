package com.scalastic.api.high.level.rest.client.ingest.api

import java.nio.charset.StandardCharsets

import com.scalastic.api.client.ElasticClient
import org.elasticsearch.action.ingest.{DeletePipelineRequest, GetPipelineRequest, GetPipelineResponse, PutPipelineRequest}
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
}
