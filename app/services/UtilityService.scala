package services

import javax.inject._
import play.mvc.Http._
import play.api.libs.ws._
import scala.concurrent._
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global

@Singleton
class UtilityService @Inject()(wsClient: WSClient) {
  def callApi(endpointURL: String): Option[String] = {
    Await.result(wsClient.url(endpointURL)
      .get()
      .map{ response =>
        if (response.status == Status.OK) Some(response.body) else None
      }.recover {
        case ex: Exception => None
      }, Duration(3000, MILLISECONDS))
  }
}
