package com.scalastic.api.repo

import java.util.concurrent.TimeUnit

import com.scalastic.api.client.ElasticClient
import org.elasticsearch.action.admin.cluster.health.ClusterHealthRequest
import org.elasticsearch.action.admin.indices.alias.IndicesAliasesRequest.AliasActions
import org.elasticsearch.action.admin.indices.alias.get.GetAliasesRequest
import org.elasticsearch.action.admin.indices.alias.{Alias, IndicesAliasesRequest}
import org.elasticsearch.action.admin.indices.cache.clear.{ClearIndicesCacheRequest, ClearIndicesCacheResponse}
import org.elasticsearch.action.admin.indices.close.CloseIndexRequest
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest
import org.elasticsearch.action.admin.indices.flush.{FlushRequest, FlushResponse, SyncedFlushRequest}
import org.elasticsearch.action.admin.indices.forcemerge.{ForceMergeRequest, ForceMergeResponse}
import org.elasticsearch.action.admin.indices.get.GetIndexRequest
import org.elasticsearch.action.admin.indices.mapping.get.{GetFieldMappingsRequest, GetFieldMappingsResponse, GetMappingsRequest, GetMappingsResponse}
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequest
import org.elasticsearch.action.admin.indices.open.{OpenIndexRequest, OpenIndexResponse}
import org.elasticsearch.action.admin.indices.refresh.{RefreshRequest, RefreshResponse}
import org.elasticsearch.action.admin.indices.rollover.{RolloverRequest, RolloverResponse}
import org.elasticsearch.action.admin.indices.settings.get.{GetSettingsRequest, GetSettingsResponse}
import org.elasticsearch.action.admin.indices.settings.put.UpdateSettingsRequest
import org.elasticsearch.action.admin.indices.shrink.{ResizeRequest, ResizeResponse}
import org.elasticsearch.action.support.master.AcknowledgedResponse
import org.elasticsearch.client._
import org.elasticsearch.client.transport.TransportClient
import org.elasticsearch.common.settings.Settings
import org.elasticsearch.common.unit.{ByteSizeUnit, ByteSizeValue, TimeValue}
import org.elasticsearch.common.xcontent.XContentType

import scala.collection.JavaConverters._


/**
  * Created by Ghazi Naceur on 18/04/2019
  * Email: ghazi.ennacer@gmail.com
  */
object ElasticHighLevelRestClient {

  private val transportClient: TransportClient = ElasticClient.transportClient
  private val client: RestHighLevelClient = ElasticClient.client
  private val llc: RestClientBuilder = ElasticClient.lowLevelClient

  def getIndicesList: List[String] = {
    val request = new ClusterHealthRequest
    val response = client.cluster.health(request, RequestOptions.DEFAULT)
    response.getIndices.keySet().asScala.toList
  }

  def createIndex(index: String, alias: String, settings: Settings.Builder): Unit = {
    if (indicesExists(index)) {
      println(s"This index $index is already created.")
    }
    else {
      val request = new CreateIndexRequest(index)
      if (settings != null) {
        request.settings(settings)
      }
      if (alias != null) {
        request.alias(new Alias(alias))
      }
      val indices = client.indices
      indices.create(request, RequestOptions.DEFAULT)
    }
  }

  def createIndex(esIndex: String, esType: String, settings: Settings.Builder, mapping: String): Unit = {
    if (!indicesExists(esIndex)) {
      val request = new CreateIndexRequest(esIndex)
      if (settings != null) {
        request.settings(settings)
      }
      val indices = client.indices
      request.mapping(esType, mapping, XContentType.JSON)
      indices.create(request, RequestOptions.DEFAULT)
    } else {
      println(s"This index $esIndex is already created.")
    }
  }

  def deleteIndex(index: String): Unit = {
    val request = new DeleteIndexRequest(index)
    request.timeout(TimeValue.timeValueMinutes(2))
    client.indices().delete(request, RequestOptions.DEFAULT)
  }

  def indicesExists(indices: String*): Boolean = {
    val request = new GetIndexRequest()
    request.indices(indices: _*)
    client.indices().exists(request, RequestOptions.DEFAULT)
  }

  def openIndices(indices: String*): OpenIndexResponse = {
    val request = new OpenIndexRequest(indices: _*)
    client.indices.open(request, RequestOptions.DEFAULT)
  }

  def closeIndices(indices: String*): AcknowledgedResponse = {
    val request = new CloseIndexRequest(indices: _*)
    client.indices.close(request, RequestOptions.DEFAULT)
  }

  def shrinkIndex(targetIndex: String, sourceIndex: String): ResizeResponse = {
    val request = new ResizeRequest(targetIndex, sourceIndex)
    //    request.setCopySettings(true)
    client.indices().shrink(request, RequestOptions.DEFAULT)
  }

  /** OPEN BUG : https://github.com/elastic/elasticsearch/issues/29652#
    *
    * @param targetIndex  The target index
    * @param sourceIndex  The source index
    * @param shardsNumber : The requested number of primary shards in the target index must be a
    *                     factor of the number of shards in the source index. For example an index
    *                     with 8 primary shards can be shrunk into 4, 2 or 1 primary shards or an
    *                     index with 15 primary shards can be shrunk into 5, 3 or 1.
    * @return ResizeResponse
    */
  //  def splitIndex(targetIndex: String, sourceIndex: String, shardsNumber: Int): ResizeResponse = {
  //    val request = new ResizeRequest(targetIndex, sourceIndex)
  //    request.setResizeType(ResizeType.SPLIT)
  //    request.getTargetIndexRequest.settings(Settings.builder()
  //      .put("index.number_of_shards", shardsNumber))
  //    client.indices().split(request, RequestOptions.DEFAULT)
  //  }

  def refreshIndices(indices: String*): RefreshResponse = {
    val request = new RefreshRequest(indices: _*)
    client.indices().refresh(request, RequestOptions.DEFAULT)
  }

  def flushIndices(indices: String*): FlushResponse = {
    val request = new FlushRequest(indices: _*)
    client.indices().flush(request, RequestOptions.DEFAULT)
  }

  def syncedFlushIndices(indices: String*): SyncedFlushResponse = {
    val request = new SyncedFlushRequest(indices: _*)
    client.indices().flushSynced(request, RequestOptions.DEFAULT)
  }

  // indices should not be "read-only"
  def clearCache(indices: String*): ClearIndicesCacheResponse = {
    val request = new ClearIndicesCacheRequest(indices: _*)
    client.indices().clearCache(request, RequestOptions.DEFAULT)
  }

  // indices should not be "read-only"
  def forceMerge(indices: String*): ForceMergeResponse = {
    val request = new ForceMergeRequest(indices: _*)
    client.indices().forcemerge(request, RequestOptions.DEFAULT)
  }

  // alias should be an actual alias, not an index
  def rollover(alias: String, newIndexName: String, shardsNumber: Int): RolloverResponse = {
    val request = new RolloverRequest(alias, newIndexName)
    request.addMaxIndexAgeCondition(new TimeValue(7, TimeUnit.DAYS))
    request.addMaxIndexDocsCondition(500)
    request.addMaxIndexSizeCondition(new ByteSizeValue(5, ByteSizeUnit.GB))
    request.getCreateIndexRequest.settings(Settings.builder()
      .put("index.number_of_shards", shardsNumber))
    client.indices().rollover(request, RequestOptions.DEFAULT)
  }

  def putMapping(esIndex: String, esType: String, mapping: String): AcknowledgedResponse = {
    val request = new PutMappingRequest(esIndex)
    request.`type`(esType)
    request.source(mapping, XContentType.JSON)
    client.indices().putMapping(request, RequestOptions.DEFAULT)
  }

  def getMappings(esIndex: String, esType: String): GetMappingsResponse = {
    val request = new GetMappingsRequest()
    request.indices(esIndex)
    request.types(esType)
    client.indices().getMapping(request, RequestOptions.DEFAULT)
  }

  def getFieldMapping(esIndex: String, esType: String, fields: String*): GetFieldMappingsResponse = {
    val request = new GetFieldMappingsRequest()
    request.indices(esIndex)
    request.types(esType)
    request.fields(fields: _*)
    client.indices().getFieldMapping(request, RequestOptions.DEFAULT)
  }

  def indexAliases(index: String, alias: String, aliasType: AliasActions.Type): AcknowledgedResponse = {
    val request = new IndicesAliasesRequest()
    val aliasAction = new AliasActions(aliasType)
      .index(index)
    if (aliasType != AliasActions.Type.REMOVE_INDEX) {
      aliasAction.alias(alias)
    }
    request.addAliasAction(aliasAction)
    client.indices().updateAliases(request, RequestOptions.DEFAULT)
  }

  def existsAlias(index: String, aliases: String*): Boolean = {
    val request = new GetAliasesRequest(aliases: _*)
    request.aliases(aliases: _*)
    request.indices(index)
    client.indices().existsAlias(request, RequestOptions.DEFAULT)
  }

  def getAlias(index: String, aliases: String*): GetAliasesResponse = {
    val request = new GetAliasesRequest()
    request.aliases(aliases: _*)
    request.indices(index)
    client.indices().getAlias(request, RequestOptions.DEFAULT)
  }

  def updateIndicesSettings(settings: Settings.Builder, indices: String*): AcknowledgedResponse = {
    val request = new UpdateSettingsRequest(indices: _*)
    request.settings(settings)
    client.indices().putSettings(request, RequestOptions.DEFAULT)
  }

  def getSettings(indices: String*): GetSettingsResponse = {
    val request = new GetSettingsRequest().indices(indices: _*)
    request.includeDefaults(true)
    client.indices().getSettings(request, RequestOptions.DEFAULT)
  }
}
