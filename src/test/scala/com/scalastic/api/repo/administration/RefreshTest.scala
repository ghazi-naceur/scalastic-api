package com.scalastic.api.repo.administration

import com.scalastic.api.repo.ElasticAdministration
import org.elasticsearch.action.admin.indices.refresh.RefreshResponse

/**
  * Created by Ghazi Naceur on 12/04/2019
  * Email: ghazi.ennacer@gmail.com
  */
object RefreshTest extends App {

  private val response1: RefreshResponse = ElasticAdministration.refreshIndices()
  println(response1.getStatus)

  private val response2: RefreshResponse = ElasticAdministration.refreshIndices("persons", "sc_persons")
  println(response2.getStatus)

  System.exit(0)
}
