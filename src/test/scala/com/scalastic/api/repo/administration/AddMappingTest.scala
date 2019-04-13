package com.scalastic.api.repo.administration

import com.scalastic.api.repo.ElasticAdministration
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse

/**
  * Created by Ghazi Naceur on 13/04/2019
  * Email: ghazi.ennacer@gmail.com
  */
object AddMappingTest extends App {
//  "name" -> "text", "age" -> "long", "username" -> "keyword", "birth_date" -> "date"
  private val response: CreateIndexResponse = ElasticAdministration.addMapping("createindex", "createtype" , "birth_date", "date")
  println(response)

  System.exit(0)
}
