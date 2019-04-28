package com.scalastic.api.high.level.rest.client.snapshot.api

import org.elasticsearch.action.admin.cluster.repositories.verify.VerifyRepositoryResponse

import scala.collection.JavaConverters._

/**
  * Created by Ghazi Naceur on 28/04/2019
  * Email: ghazi.ennacer@gmail.com
  */
object VerifyRepositoryTest extends App {
  private val response: VerifyRepositoryResponse = SnapshotAPIs.verifyRepository("repo")
  println(response.getClusterName)
  for (node <- response.getNodes.asScala) {
    println(node.getName)
    println(node.getNodeId)
  }

  System.exit(0)
}
