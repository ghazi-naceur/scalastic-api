package com.scalastic.api.high.level.rest.client.search.api

import com.scalastic.api.client.ElasticClient
import com.scalastic.api.entities.Person
import org.elasticsearch.action.search.SearchRequest
import org.elasticsearch.client.{RequestOptions, RestHighLevelClient}
import org.elasticsearch.common.unit.TimeValue
import org.elasticsearch.index.query.QueryBuilders
import org.elasticsearch.search.SearchHit
import org.elasticsearch.search.builder.SearchSourceBuilder
import org.elasticsearch.search.sort.{FieldSortBuilder, SortOrder}

import scala.collection.mutable.ListBuffer
import scala.collection.JavaConverters._

/**
  * Created by Ghazi Naceur on 27/04/2019
  * Email: ghazi.ennacer@gmail.com
  */
object ClearScrollTest extends App {

  private val client: RestHighLevelClient = ElasticClient.client


  def findAllWithScanAndScrollTest(index: String): List[Map[String, Any]] = {
    var result = ListBuffer[Map[String, Any]]()
    val request = new SearchRequest(index).scroll(new TimeValue(60000))
    val searchSourceBuilder = new SearchSourceBuilder()
    searchSourceBuilder.query(QueryBuilders.matchAllQuery())
    searchSourceBuilder.sort(FieldSortBuilder.DOC_FIELD_NAME, SortOrder.ASC)
    request.source(searchSourceBuilder)

    var scrollResp = client.search(request, RequestOptions.DEFAULT)
    for (hit: SearchHit <- scrollResp.getHits.getHits) {
      result += hit.getSourceAsMap.asScala.map(kv => (kv._1, kv._2)).toMap
    }
    val scrollId = scrollResp.getScrollId

    val response = SearchAPIs.clearScroll(scrollId)
    println(response.getNumFreed)
    println(response.isSucceeded)
    println(response.status())
    result.toList
  }

  private val persons: List[Map[String, Any]] = findAllWithScanAndScrollTest("bulk_persons")
  var i = 0
  persons.foreach(personMap => {
    i += 1
    println(s"$i " + Person.toPerson(personMap).toString)
  })

  System.exit(0)
}
