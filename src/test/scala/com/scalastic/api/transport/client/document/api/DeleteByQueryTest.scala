package com.scalastic.api.transport.client.document.api

import org.elasticsearch.index.reindex.BulkByScrollResponse

/**
  * Created by Ghazi Naceur on 08/04/2019
  * Email: ghazi.ennacer@gmail.com
  */
object DeleteByQueryTest extends App {
  //  ElasticQueryBuilder.insert("persons", "person", Person("Mamuro", "Takamora", 29, "Boxer").toMap())
  //  ElasticQueryBuilder.insert("persons", "person", Person("Mamuro", "Takamora", 29, "Boxer").toMap())
  //  ElasticQueryBuilder.insert("sc_persons", "sc_person", Person("Mamuro", "Takamora", 29, "Boxer").toMap())
  //  ElasticQueryBuilder.insert("sc_persons", "sc_person", Person("Mamuro", "Takamora", 29, "Boxer").toMap())
  //  ElasticQueryBuilder.insert("sc_persons", "sc_person", Person("Mamuro", "Takamora", 29, "Boxer").toMap())

  val response: BulkByScrollResponse = ElasticQueryBuilder.deleteByQuery(Map("firstName" -> "Mamuro", "occupation" -> "Boxer"), "sc_persons", "persons")
  println(response.getDeleted)

  System.exit(0)
}
