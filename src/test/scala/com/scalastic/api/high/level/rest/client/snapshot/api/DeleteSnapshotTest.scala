package com.scalastic.api.high.level.rest.client.snapshot.api

import org.elasticsearch.action.admin.cluster.snapshots.get.GetSnapshotsResponse
import org.elasticsearch.action.support.master.AcknowledgedResponse

import scala.collection.JavaConverters._

/**
  * Created by Ghazi Naceur on 29/04/2019
  * Email: ghazi.ennacer@gmail.com
  */
object DeleteSnapshotTest extends App {

  private val results: GetSnapshotsResponse = SnapshotAPIs.getSnapshots("repo")
  println("snapshot names :")
  for (snapshot <- results.getSnapshots.asScala) {
    println(snapshot.snapshotId().getName)
  }

  private val response: AcknowledgedResponse = SnapshotAPIs.deleteSnapshot("repo", "snapshot1556559170568")
  println(response.isAcknowledged)

  System.exit(0)
}
