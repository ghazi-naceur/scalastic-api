package com.scalastic.api.high.level.rest.client.search.api

import com.scalastic.api.entities.Person
import com.scalastic.api.high.level.rest.client.document.api.MultiDocumentAPIs
import org.elasticsearch.action.bulk.BulkResponse
import org.elasticsearch.action.index.IndexRequest

import scala.collection.mutable.ListBuffer

/**
  * Created by Ghazi Naceur on 26/04/2019
  * Email: ghazi.ennacer@gmail.com
  */

object FindAllWithScanAndScrollTest extends App {

//  private var requests: ListBuffer[IndexRequest] = ListBuffer()
//  for (i <- 0 until 50000) {
//    requests += new IndexRequest("bulk_persons", "bulk_person", i.toString)
//      .source(Person("Isaac", "Netero", 15, "Hunter").getJsonObject())
//  }
//
//  private val response: BulkResponse = MultiDocumentAPIs.bulk("bulk_persons", "bulk_person", requests.toList)


  private val persons: List[Map[String, Any]] = SearchAPIs.findAllWithScanAndScroll("bulk_persons")
  var i = 0
  persons.foreach(personMap => {
    i += 1
    println(s"$i " + Person.toPerson(personMap).toString)
  })

  System.exit(0)
}
