package com.scalastic.api.high.level.rest.client.indices.api

/**
  * Created by Ghazi Naceur on 18/04/2019
  * Email: ghazi.ennacer@gmail.com
  */
object GetAllIndicesTest extends App {
  IndicesAPIs.getIndicesList.foreach(index => {
    println(index)
  })

  System.exit(0)
}
