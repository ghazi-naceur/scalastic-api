package com.scalastic.api.http

/**
  * Created by Ghazi Naceur on 06/04/2019
  * Email: ghazi.ennacer@gmail.com
  */
object BasicElasticHttpRequest {

  def getIndices(esHostIP: String): String = {
    HttpRequests.getRequest(s"http://$esHostIP:9200/_cat/indices?v")
  }

  def countDocs(esHostIP: String, indice: String): String = {
    HttpRequests.getRequest(s"http://$esHostIP:9200/$indice/_count?pretty")
  }

  def showMapping(esHostIP: String, indice: String): String = {
    HttpRequests.getRequest(s"http://$esHostIP:9200/$indice/_mapping?pretty")
  }

  def searchFirstPage(esHostIP: String, indice: String): String = {
    HttpRequests.getRequest(s"http://$esHostIP:9200/$indice/_search?pretty")
  }

  def getEsNodes(esHostIP: String): String = {
    HttpRequests.getRequest(s"http://$esHostIP:9200/_cat/nodes?v")
  }

  def getClusterHealth(esHostIP: String): String = {
    HttpRequests.getRequest(s"http://$esHostIP:9200/_cat/health?v")
  }

  def getAlias(esHostIP: String, indice: String): String = {
    HttpRequests.getRequest(s"http://$esHostIP:9200/$indice/_alias?pretty")
  }

  def getThreadPool(esHostIP: String): String = {
    HttpRequests.getRequest(s"http://$esHostIP:9200/_cat/thread_pool?v")
  }

  def getMasterNode(esHostIP: String): String = {
    HttpRequests.getRequest(s"http://$esHostIP:9200/_cat/master?v")
  }

  def getHardDriveMemoryAllocation(esHostIP: String): String = {
    HttpRequests.getRequest(s"http://$esHostIP:9200/_cat/allocation?v")
  }

  def getPendingTasks(esHostIP: String): String = {
    HttpRequests.getRequest(s"http://$esHostIP:9200/_cat/pending_tasks?v")
  }

  def getSegments(esHostIP: String, indice: String): String ={
    HttpRequests.getRequest(s"http://$esHostIP:9200/_cat/segments/$indice?v")
  }

  def getSegmentsDetails(esHostIP: String, indice: String): String = {
    HttpRequests.getRequest(s"http://$esHostIP:9200/$indice/_segments?pretty")
  }

  def getNodeAttrs(esHostIP: String): String = {
    HttpRequests.getRequest(s"http://$esHostIP:9200/_cat/nodeattrs?v")
  }

  def getTemplates(esHostIP: String): String = {
    HttpRequests.getRequest(s"http://$esHostIP:9200/_cat/templates?v")
  }

  def getTasks(esHostIP: String): String = {
    HttpRequests.getRequest(s"http://$esHostIP:9200/_cat/tasks?v")
  }

  def getShards(esHostIP: String, indice: String): String = {
    HttpRequests.getRequest(s"http://$esHostIP:9200/_cat/shards/$indice?v")
  }

  def getPlugins(esHostIP: String): String = {
    HttpRequests.getRequest(s"http://$esHostIP:9200/_cat/plugins?v")
  }
}
