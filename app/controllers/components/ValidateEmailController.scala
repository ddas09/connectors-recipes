package controllers.components

import play.api.mvc._
import services._

import javax.inject._

@Singleton
class ValidateEmailController @Inject()(val controllerComponents: ControllerComponents, val emailValidationService: EmailValidationService) extends BaseController {

  def evaEmailValidator(email: String) = Action { implicit request: Request[AnyContent] =>
    val isValidEmail = this.emailValidationService.evaEmailValidator(email)

    if (isValidEmail.isDefined)
      Ok(s"Validated $email using EVA API. Is valid email: ${isValidEmail.get}")
    else
      Ok("EVA email validation service is not working...")
  }

  def disifyEmailValidator(email: String) = Action { implicit request: Request[AnyContent] =>
    val isValidEmail = this.emailValidationService.disifyEmailValidator(email)

    if (isValidEmail.isDefined)
      Ok(s"Validated $email using Disify API. Is valid email: ${isValidEmail.get}")
    else
      Ok("Disify email validation service is not working...")
  }
}
