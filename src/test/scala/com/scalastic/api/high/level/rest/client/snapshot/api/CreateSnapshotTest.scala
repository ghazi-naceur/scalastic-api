package com.scalastic.api.high.level.rest.client.snapshot.api

import java.util.Date

import org.elasticsearch.action.admin.cluster.snapshots.create.CreateSnapshotResponse

/**
  * Created by Ghazi Naceur on 28/04/2019
  * Email: ghazi.ennacer@gmail.com
  */
object CreateSnapshotTest extends App {
  private val response: CreateSnapshotResponse = SnapshotAPIs.createSnapshot("repo", "snapshot" + new Date().getTime)
  println(response.status().getStatus)
  println(response.getSnapshotInfo.basic())
  println(response.getSnapshotInfo.startTime())
  println(response.getSnapshotInfo.endTime())
  println(response.getSnapshotInfo.indices())
  println(response.getSnapshotInfo.reason())
  println(response.getSnapshotInfo.state())

  System.exit(0)
}
