package com.scalastic.api.repo.high.level.rest.client.document.api

import com.scalastic.api.high.level.rest.client.document.api.MultiDocumentAPIs
import com.scalastic.api.high.level.rest.client.tasks.api.TasksAPIs
import org.elasticsearch.action.admin.cluster.node.tasks.list.ListTasksResponse
import org.elasticsearch.tasks.TaskId

object RethrottleTest extends App {

  private val response1: ListTasksResponse = TasksAPIs.listTasks()
  response1.getTasks.forEach(task => {
    println("ID : " + task.getId)
    println("Task ID : " + task.getTaskId)
    println("Status : " + task.getStatus)
  })

  private val response2: ListTasksResponse = MultiDocumentAPIs.rethrottleReindex(new TaskId("il_spXr1SbGwlswusIYRGw:5448"))
  println(response2.getTasks)

  private val response3: ListTasksResponse = MultiDocumentAPIs.rethrottleReindex(new TaskId("il_spXr1SbGwlswusIYRGw:5448"))
  println(response3.getTasks)

  private val response4: ListTasksResponse = MultiDocumentAPIs.rethrottleReindex(new TaskId("il_spXr1SbGwlswusIYRGw:5448"))
  println(response4.getTasks)

  System.exit(0)
}
