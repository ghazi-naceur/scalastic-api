package com.scalastic.api.high.level.rest.client

import org.elasticsearch.action.support.master.AcknowledgedResponse
import org.elasticsearch.common.settings.Settings

/**
  * Created by Ghazi Naceur on 22/04/2019
  * Email: ghazi.ennacer@gmail.com
  */
object UpdateIndicesSettingsTest extends App {
  //  ElasticHighLevelRestClient.createIndex("new_indice_1", null, null)
  //  ElasticHighLevelRestClient.createIndex("new_indice_2", null, null)

  private val settings: Settings.Builder = Settings.builder()
    //    .put("index.number_of_shards", 3) // This field is non dynamic. Once set, it can't be overridden
    .put("index.number_of_replicas", 2)
    .put("index.blocks.read_only", false)
  private val response: AcknowledgedResponse = ElasticHighLevelRestClient.updateIndicesSettings(settings, "new_indice_1", "new_indice_2")
  println(response.isAcknowledged)

  System.exit(0)
}
