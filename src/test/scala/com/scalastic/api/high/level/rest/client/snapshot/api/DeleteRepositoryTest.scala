package com.scalastic.api.high.level.rest.client.snapshot.api

import org.elasticsearch.action.support.master.AcknowledgedResponse

/**
  * Created by Ghazi Naceur on 28/04/2019
  * Email: ghazi.ennacer@gmail.com
  */
object DeleteRepositoryTest extends App {
  //  repositoryName is different from location
  private val response: AcknowledgedResponse = SnapshotAPIs.deleteRepository("repo")
  println(response.isAcknowledged)

  System.exit(0)
}
