package com.scalastic.api.repo.high.level.rest.client

import com.scalastic.api.repo.ElasticHighLevelRestClient
import org.elasticsearch.action.admin.indices.alias.IndicesAliasesRequest.AliasActions
import org.elasticsearch.action.support.master.AcknowledgedResponse

/**
  * Created by Ghazi Naceur on 21/04/2019
  * Email: ghazi.ennacer@gmail.com
  */
object IndexAliasesTest extends App {

  // 1- Adding a new alias for index
//  ElasticHighLevelRestClient.createIndex("some_new_index", null, null)
//  private val response: AcknowledgedResponse = ElasticHighLevelRestClient.indexAliases("some_new_index", "some_new_alias", AliasActions.Type.ADD)
//  println(response.isAcknowledged)

  // 2- Removing an alias from index
//  private val response2: AcknowledgedResponse = ElasticHighLevelRestClient.indexAliases("some_new_index", "some_new_alias", AliasActions.Type.REMOVE)
//  println(response2.isAcknowledged)

  // 3- Removing index
  private val response3: AcknowledgedResponse = ElasticHighLevelRestClient.indexAliases("some_new_index", "some_new_alias", AliasActions.Type.REMOVE_INDEX)
  println(response3.isAcknowledged)
  System.exit(0)
}
