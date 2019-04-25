package com.scalastic.api.repo.high.level.rest.client.tasks.api

import com.scalastic.api.high.level.rest.client.tasks.api.TasksAPIs
import org.elasticsearch.action.admin.cluster.node.tasks.list.ListTasksResponse

object ListTasksTest extends App {
  private val response: ListTasksResponse = TasksAPIs.listTasks()
  response.getTasks.forEach(task => {
    println("ID : " + task.getId)
    println("Task ID : " + task.getTaskId)
    println("Status : " + task.getStatus)
  })

  System.exit(0)
}
