package com.scalastic.api.high.level.rest.client.snapshot.api

import java.util.Date

import org.elasticsearch.action.support.master.AcknowledgedResponse

/**
  * Created by Ghazi Naceur on 28/04/2019
  * Email: ghazi.ennacer@gmail.com
  */
object CreateRepositoryTest extends App {
  private val response: AcknowledgedResponse = SnapshotAPIs.createRepository("snapshot" + new Date().getTime, "repo")
  println(response.isAcknowledged)

  System.exit(0)
}
