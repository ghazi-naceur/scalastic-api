package com.scalastic.api.repo.document.api

import com.scalastic.api.client.ElasticClient
import com.scalastic.api.entities.Person
import com.scalastic.api.repo.ElasticQueryBuilder
import org.elasticsearch.client.transport.TransportClient
import org.elasticsearch.index.query.QueryBuilders

/**
  * Created by Ghazi Naceur on 23/04/2019
  * Email: ghazi.ennacer@gmail.com
  */
object MultiSearchTest extends App {

  private val transportClient: TransportClient = ElasticClient.transportClient

  val builder1 = transportClient.prepareSearch("sc_persons").setQuery(QueryBuilders.queryStringQuery("Netero")).setSize(1)
  val builder2 = transportClient.prepareSearch("sc_persons").setQuery(QueryBuilders.matchQuery("lastName", "Netero")).setSize(1)

  private val results: List[Map[String, Any]] = ElasticQueryBuilder.multiSearch(List(builder1, builder2))

  results.foreach(person => {
    println(Person.toPerson(person).toString)
  })

  System.exit(0)
}
