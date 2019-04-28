package com.scalastic.api.high.level.rest.client.snapshot.api

import com.scalastic.api.client.ElasticClient
import org.elasticsearch.action.admin.cluster.repositories.delete.DeleteRepositoryRequest
import org.elasticsearch.action.admin.cluster.repositories.get.{GetRepositoriesRequest, GetRepositoriesResponse}
import org.elasticsearch.action.admin.cluster.repositories.put.PutRepositoryRequest
import org.elasticsearch.action.support.master.AcknowledgedResponse
import org.elasticsearch.client.{RequestOptions, RestHighLevelClient}
import org.elasticsearch.common.settings.Settings
import org.elasticsearch.repositories.fs.FsRepository

/**
  * Created by Ghazi Naceur on 28/04/2019
  * Email: ghazi.ennacer@gmail.com
  */
object SnapshotAPIs {

  private val client: RestHighLevelClient = ElasticClient.client

  def getRepositories: GetRepositoriesResponse = {
    val request = new GetRepositoriesRequest()
    request.local(true)
    client.snapshot().getRepository(request, RequestOptions.DEFAULT)
  }

  //  You need to set the config param 'path.repo' in the config file elasticsearch.yml as follows :
  //  path.repo: folderContainingAllSnapshots
  def createRepository(location: String, repositoryName: String): AcknowledgedResponse = {
    val request = new PutRepositoryRequest()

    val locationKey = FsRepository.LOCATION_SETTING.getKey
    val locationValue = location
    val compressKey = FsRepository.COMPRESS_SETTING.getKey
    val compressValue = true

    val settingsBuilder = Settings.builder
      .put(locationKey, locationValue)
      .put(compressKey, compressValue)
    request.settings(settingsBuilder)

    request.name(repositoryName)
    request.`type`(FsRepository.TYPE)
    request.verify(true)
    client.snapshot().createRepository(request, RequestOptions.DEFAULT)
  }

  def deleteRepository(repositoryName: String): AcknowledgedResponse = {
    val request = new DeleteRepositoryRequest(repositoryName)
    client.snapshot().deleteRepository(request, RequestOptions.DEFAULT)
  }
}
