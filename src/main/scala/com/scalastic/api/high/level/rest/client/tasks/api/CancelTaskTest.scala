package com.scalastic.api.high.level.rest.client.tasks.api

import org.elasticsearch.action.admin.cluster.node.tasks.cancel.CancelTasksResponse

import scala.collection.JavaConverters._

/**
  * Created by Ghazi Naceur on 29/04/2019
  * Email: ghazi.ennacer@gmail.com
  */
object CancelTaskTest extends App {
  private val response: CancelTasksResponse = TasksAPIs.cancelTask("il_spXr1SbGwlswusIYRGw:4850")
  for (task <- response.getTasks.asScala) {
    println(task.getId)
    println(task.getAction)
    println(task.getDescription)
    println(task.getStatus)
    println(task.getTaskId)
    println(task.getType)
  }

  System.exit(0)
}
