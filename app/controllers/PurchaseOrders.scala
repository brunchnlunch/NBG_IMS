package controllers

import models.PurchaseOrder
import models.Deal
import models.DealPartForm
import models.Product
import play.api._
import play.api.data.Form
import play.api.data.Forms.{mapping, longNumber, nonEmptyText}
import play.api.i18n.Messages
import play.api.i18n.Messages.Implicits._
import play.api.mvc._
import play.api.mvc.{Action, Controller, Flash}
import play.api.Play.current

class PurchaseOrders extends Controller {
  private val dealForm: Form[DealPartForm] = Form(mapping("id" -> longNumber , "quantity" -> longNumber)(DealPartForm.apply)(DealPartForm.unapply))
  
  def POList = Action {
    implicit request =>
      val POs = PurchaseOrder.findAll
      Ok(views.html.POList(POs))
  }
  
  def show(id:Long) = Action {
    implicit request =>
      var PO = PurchaseOrder.findById(id).get
      var productList = PurchaseOrder.productList(id).toMap
      Ok(views.html.PODetails(PO, productList, dealForm))
  }
  
  def addPO(number: Long) = Action {
    implicit request =>
      Deal.createPO
      Redirect(routes.PurchaseOrders.POList)
  }
  
  def dealQuantity = Action {implicit request =>
	val newDealForm = dealForm.bindFromRequest()
	newDealForm.fold(
		hasErrors = { form =>
			Redirect(routes.PurchaseOrders.POList())
		},
		success = { newDealPart =>
		  PurchaseOrder.dealQuantity(newDealPart.id, newDealPart.quantity)		  
		  Redirect(routes.PurchaseOrders.show(PurchaseOrder.findByDealId(newDealPart.id).id))
		}
	)
  }
  
  def confirm (id: Long) = Action {
    implicit request =>
      PurchaseOrder.confirm(id)
      Product.increaseQuantityByPo(id)
      Redirect(routes.PurchaseOrders.show(id))
  }
    
  
  
}