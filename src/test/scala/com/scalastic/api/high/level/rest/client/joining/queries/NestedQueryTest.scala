package com.scalastic.api.high.level.rest.client.joining.queries

import com.scalastic.api.high.level.rest.client.ElasticJoiningQueryBuilder

/**
  * Created by Ghazi Naceur on 18/04/2019
  * Email: ghazi.ennacer@gmail.com
  */
object NestedQueryTest extends App {

  //  ElasticHighLevelRestClient.deleteIndex("countries")
  //  val mapping = "{      \"properties\" : {        \"towns\" : {          \"type\" : \"nested\",          \"properties\" : {            \"country\" : {              \"type\" : \"keyword\"            },            \"name\" : {              \"type\" : \"keyword\"            },            \"population\" : {              \"type\" : \"long\"            },            \"prefecture\" : {              \"type\" : \"text\"            }          }        },        \"name\" : {          \"type\" : \"text\"        },        \"population\" : {          \"type\" : \"long\"        }      }    }"
  //  ElasticHighLevelRestClient.createIndex("countries", "country", null, mapping)
  //
  //  val konohaGakure = Town("Konoha", "prefecture 1", "country 1", 5000)
  //  val kiriGakure = Town("Kiri", "prefecture 2", "country 2", 10000)
  //  val kumoGakure = Town("Kumo", "prefecture 3", "country 3", 11000)
  //  val iwaGakure = Town("Iwa", "prefecture 1", "country 4", 6000)
  //  val otoGakure = Town("Oto", "prefecture 1", "country 5", 5000)
  //  val towns = List(konohaGakure, kiriGakure, kumoGakure, iwaGakure, otoGakure)
  //
  //  val nsCountry = Country("ns", towns, 37000)
  //  val hxhCountry = Country("hxh", towns, 37000)
  //
  //  val jsonSource1 = Json.stringify(Json.toJson(nsCountry))
  //  private val response1: IndexResponse = ElasticQueryBuilder.insert("countries", "country", jsonSource1)
  //  println(response1)
  //
  //  val jsonSource2 = Json.stringify(Json.toJson(hxhCountry))
  //  private val response2: IndexResponse = ElasticQueryBuilder.insert("countries", "country", jsonSource2)
  //  println(response2)

  val searchCriteria = Map[String, Any]("prefecture" -> "prefecture 2", "population" -> 10000)

  val countries = ElasticJoiningQueryBuilder.getDocsWithNestedQuery("countries", "towns", searchCriteria)
  countries.foreach(map => {
    println(map)
  })

  System.exit(0)


}
