package controllers

import models.PurchaseOrder
import models.Deal
import play.api._
import play.api.data.Form
import play.api.data.Forms.{mapping, longNumber, nonEmptyText}
import play.api.i18n.Messages
import play.api.i18n.Messages.Implicits._
import play.api.mvc._
import play.api.mvc.{Action, Controller, Flash}
import play.api.Play.current

class PurchaseOrders extends Controller {
  
  def POList = Action {
    implicit request =>
      val POs = PurchaseOrder.findAll
      Ok(views.html.POList(POs))
  }
  
  def show(id:Long) = Action {
    implicit request =>
      var PO = PurchaseOrder.findById(id).get
      var productList = PurchaseOrder.productList(id).toMap
      Ok(views.html.PODetails(PO, productList))
  }
  
  def addPO(number: Long) = Action {
    implicit request =>
      Deal.createPO
      Redirect(routes.PurchaseOrders.POList)
  }
  
  
}