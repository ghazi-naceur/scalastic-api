package com.scalastic.api.high.level.rest.client.snapshot.api

import org.elasticsearch.action.admin.cluster.snapshots.get.GetSnapshotsResponse

import scala.collection.JavaConverters._

/**
  * Created by Ghazi Naceur on 29/04/2019
  * Email: ghazi.ennacer@gmail.com
  */
object GetSnapshotsTest extends App {
  private val response: GetSnapshotsResponse = SnapshotAPIs.getSnapshots("repo")
  for (snapshot <- response.getSnapshots.asScala) {
    println(snapshot.snapshotId().getName)
    println(snapshot.snapshotId())
    println(snapshot.basic())
    println(snapshot.indices())
    println(snapshot.reason())
    println(snapshot.state())
    println(snapshot.status())
    println(snapshot.startTime())
    println(snapshot.endTime())
  }

  System.exit(0)
}
