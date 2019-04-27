package com.scalastic.api.high.level.rest.client.miscellaneous.api

import org.elasticsearch.client.xpack.XPackInfoResponse

/**
  * Created by Ghazi Naceur on 27/04/2019
  * Email: ghazi.ennacer@gmail.com
  */

object XPackInfoTest extends App {
  private val response: XPackInfoResponse = MiscellaneousAPIs.xpackInfo()

  println(response.getBuildInfo.getHash)
  println(response.getBuildInfo.getTimestamp)
  response.getFeatureSetsInfo.getFeatureSets.forEach((k,v) => {
    println(k)
    println("Name : "+v.name())
    println("Available : "+v.available())
    println("Description : "+v.description())
    println("Native code native : "+v.nativeCodeInfo())
    println("Enabled : "+v.enabled())
  })
  println("Build info : "+response.getBuildInfo)
  println("Status : "+response.getLicenseInfo.getStatus)
  println("Type : "+response.getLicenseInfo.getType)
  println("Mode : "+response.getLicenseInfo.getMode)
  println("UID : "+response.getLicenseInfo.getUid)
  println("Expiry date : "+response.getLicenseInfo.getExpiryDate)

  System.exit(0)
}
