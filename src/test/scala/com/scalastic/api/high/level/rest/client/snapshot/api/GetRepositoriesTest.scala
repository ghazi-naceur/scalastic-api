package com.scalastic.api.high.level.rest.client.snapshot.api

import java.util

import org.elasticsearch.action.admin.cluster.repositories.get.GetRepositoriesResponse
import org.elasticsearch.cluster.metadata.RepositoryMetaData

import scala.collection.JavaConverters._

/**
  * Created by Ghazi Naceur on 28/04/2019
  * Email: ghazi.ennacer@gmail.com
  */
object GetRepositoriesTest extends App {
  private val response: GetRepositoriesResponse = SnapshotAPIs.getRepositories()
  private val data: util.List[RepositoryMetaData] = response.repositories()
  for (data: RepositoryMetaData <- data.asScala) {
    println(data.name())
    println(data.`type`())
    println(data.settings())
  }

  System.exit(0)
}
