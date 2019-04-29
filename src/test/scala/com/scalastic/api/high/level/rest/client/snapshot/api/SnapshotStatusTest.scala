package com.scalastic.api.high.level.rest.client.snapshot.api

import org.elasticsearch.action.admin.cluster.snapshots.status.SnapshotsStatusResponse

import scala.collection.JavaConverters._

/**
  * Created by Ghazi Naceur on 29/04/2019
  * Email: ghazi.ennacer@gmail.com
  */
object SnapshotStatusTest extends App {
  private val response: SnapshotsStatusResponse = SnapshotAPIs.statusSnapshots("repo")
  for (stat <- response.getSnapshots.asScala) {
    println(stat.getState)
    println(stat.getStats)
    println(stat.getSnapshot)
    println(stat.getShardsStats)
    println(stat.getShards)
    println(stat.getIndices)
  }

  System.exit(0)
}
