package controllers

import play.api._
import play.api.data.Form
import play.api.data.Forms.{mapping, longNumber, nonEmptyText}
import play.api.i18n.Messages
import play.api.i18n.Messages.Implicits._
import play.api.mvc._
import play.api.mvc.{Action, Controller, Flash}
import play.api.Play.current
import models.Deal

class Deals extends Controller {
  //private val dealForm:  Form[ProductPart] = Form(mapping("ean" -> longNumber , "quantity" -> longNumber, "location" -> nonEmptyText)(ProductPart.apply)(ProductPart.unapply))
  //change productPart to something else
  
  def dealList = Action {
    implicit request =>
      val deals = Deal.findAll
      Ok(views.html.dealList(deals))
  }
  
  def show(id: Long) = Action {
    implicit request =>
      val deal = Deal.findById(id).get
      Ok(views.html.dealDetails(deal))
  }
  
}