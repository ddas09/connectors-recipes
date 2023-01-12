package controllers.recipes

import play.api.mvc._
import services._

import javax.inject.Inject

class ValidateEmailController @Inject()(val controllerComponents: ControllerComponents, val emailValidationService: EmailValidationService) extends BaseController {
  def validateEmail(email: String) = Action { implicit request: Request[AnyContent] =>
    val isValidEmail = this.emailValidationService.validateEmailRecipe(email)

    if (isValidEmail.isDefined)
      Ok(s"Validated $email using email validator recipe. Is valid email: ${isValidEmail.get}")
    else
      Ok("Email validation recipe is not working...")
  }
}
