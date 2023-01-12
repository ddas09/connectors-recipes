package services

import javax.inject._
import play.api.libs.json._

import scala.collection.mutable.ListBuffer

@Singleton
class EmailValidationService @Inject()(utilityService: UtilityService) {
  def evaEmailValidator(email: String): Option[Boolean] = {
    val apiResponse = this.utilityService.callApi(s"https://api.eva.pingutil.com/email?email=$email")

    if (apiResponse.isDefined) {
      val jsonValue = Json.parse(apiResponse.get)
      (jsonValue \ "data" \ "valid_syntax").asOpt[Boolean]
    } else None
  }

  def disifyEmailValidator(email: String): Option[Boolean] = {
    val apiResponse = this.utilityService.callApi(s"https://www.disify.com/api/email/$email")

    if (apiResponse.isDefined) {
      val jsonValue = Json.parse(apiResponse.get)
      (jsonValue \ "format").asOpt[Boolean]
    } else None
  }

  def validateEmailRecipe(email: String): Option[Boolean] = {
    val emailValidationResults: ListBuffer[Option[Boolean]] = new ListBuffer[Option[Boolean]]

    emailValidationResults.append(this.evaEmailValidator(email))
    emailValidationResults.append(this.disifyEmailValidator(email))

    var isValidEmail: Option[Boolean] = None
    emailValidationResults.foreach(result => if (result.isDefined) isValidEmail = result)

    isValidEmail
  }
}
