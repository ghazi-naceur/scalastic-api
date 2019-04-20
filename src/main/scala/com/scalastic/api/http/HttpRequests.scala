package com.scalastic.api.http

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.http.scaladsl.unmarshalling.Unmarshal
import akka.stream.ActorMaterializer

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.concurrent.{Await, Future}


/**
  * Created by Ghazi Naceur on 06/04/2019
  * Email: ghazi.ennacer@gmail.com
  */
object HttpRequests {

  implicit val system = ActorSystem("http-client")
  implicit val materializer = ActorMaterializer()

  private def get(uri: String): Future[String] = {
    val request = HttpRequest(HttpMethods.GET, uri)
    for {
      response <- Http().singleRequest(request)
      content <- Unmarshal(response.entity).to[String]
    } yield content
  }

  def getRequest(uri: String): String = {
    Await.result(HttpRequests.get(uri), 60 second)
  }

  def close(): Unit = {
    Http().shutdownAllConnectionPools().foreach(_ => system.terminate)
  }
}
