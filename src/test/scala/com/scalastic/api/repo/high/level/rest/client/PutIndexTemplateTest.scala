package com.scalastic.api.repo.high.level.rest.client

import com.scalastic.api.repo.ElasticHighLevelRestClient
import org.elasticsearch.action.support.master.AcknowledgedResponse
import org.elasticsearch.common.settings.Settings

/**
  * Created by Ghazi Naceur on 22/04/2019
  * Email: ghazi.ennacer@gmail.com
  */
object PutIndexTemplateTest extends App {
  private val settings: Settings.Builder = Settings.builder()
    .put("index.number_of_shards", 3)
    .put("index.number_of_replicas", 2)
  private val response: AcknowledgedResponse = ElasticHighLevelRestClient.putIndexTemplate("generated_index", "new-pattern", "generated-log-*", 1, settings)
  println(response.isAcknowledged)

  // the templates list : http://localhost:9200/_cat/templates?pretty
  // the generated index : http://localhost:9200/_cat/templates/generated_index?pretty
  System.exit(0)
}
