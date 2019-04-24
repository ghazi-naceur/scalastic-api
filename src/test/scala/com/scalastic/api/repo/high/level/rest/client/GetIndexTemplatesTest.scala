package com.scalastic.api.repo.high.level.rest.client

import com.scalastic.api.high.level.rest.client.ElasticHighLevelRestClient
import org.elasticsearch.action.admin.indices.template.get.GetIndexTemplatesResponse

/**
  * Created by Ghazi Naceur on 23/04/2019
  * Email: ghazi.ennacer@gmail.com
  */
object GetIndexTemplatesTest extends App {
  private val response: GetIndexTemplatesResponse = ElasticHighLevelRestClient.getIndexTemplates()
  response.getIndexTemplates.forEach(template => {
    println(template.getName)
    println(template.getOrder)
    println(template.getMappings)
    println(template.getPatterns)
    println(template.getSettings)
    println(template.getVersion)
    println("-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-")
  })

  System.exit(0)
}
