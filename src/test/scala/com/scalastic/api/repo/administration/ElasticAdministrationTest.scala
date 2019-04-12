package com.scalastic.api.repo.administration

import com.carrotsearch.hppc.cursors.ObjectObjectCursor
import com.scalastic.api.repo.ElasticAdministration
import org.elasticsearch.cluster.metadata.IndexMetaData

/**
  * Created by Ghazi Naceur on 12/04/2019
  * Email: ghazi.ennacer@gmail.com
  */
object ElasticAdministrationTest extends App {
  private val indices = ElasticAdministration.getIndices()
  for (indice: ObjectObjectCursor[String, IndexMetaData] <- indices) {
    println(indice.key)
  }
}
