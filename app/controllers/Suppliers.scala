package controllers

import play.api._
import play.api.data.Form
import play.api.data.Forms.{mapping, longNumber, nonEmptyText}
import play.api.i18n.Messages
import play.api.i18n.Messages.Implicits._
import play.api.mvc._
import play.api.mvc.{Action, Controller, Flash}
import play.api.Play.current
import models.Supplier

class Suppliers extends Controller {
  
  def supplierList = Action {
    implicit request =>
      val suppliers = Supplier.findAll
      Ok(views.html.supplierList(suppliers))
  }
  
  def show(id: Long) = Action {
    implicit request =>
      val supplier = Supplier.findById(id).get
      Ok(views.html.supplierDetails(supplier))
  }
  
  
  
}