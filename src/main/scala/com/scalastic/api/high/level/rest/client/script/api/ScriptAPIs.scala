package com.scalastic.api.high.level.rest.client.script.api

import com.scalastic.api.client.ElasticClient
import org.elasticsearch.action.admin.cluster.storedscripts.{GetStoredScriptRequest, GetStoredScriptResponse, PutStoredScriptRequest}
import org.elasticsearch.action.support.master.AcknowledgedResponse
import org.elasticsearch.client.{RequestOptions, RestHighLevelClient}
import org.elasticsearch.common.bytes.BytesArray
import org.elasticsearch.common.unit.TimeValue
import org.elasticsearch.common.xcontent.XContentType

/**
  * Created by Ghazi Naceur on 29/04/2019
  * Email: ghazi.ennacer@gmail.com
  */
object ScriptAPIs {

  private val client: RestHighLevelClient = ElasticClient.client

  def getStoredScript(scriptId: String): GetStoredScriptResponse = {
    val request = new GetStoredScriptRequest(scriptId)
    request.masterNodeTimeout(TimeValue.timeValueSeconds(50))
    client.getScript(request, RequestOptions.DEFAULT)
  }

  def putStoredScript(requestId: String, script: String): AcknowledgedResponse = {
    val request = new PutStoredScriptRequest()
    request.id(requestId)
    request.content(new BytesArray(script), XContentType.JSON)
    request.masterNodeTimeout(TimeValue.timeValueMinutes(1))
    client.putScript(request, RequestOptions.DEFAULT)
  }
}
