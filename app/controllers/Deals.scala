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
import models.ProductPart

class Deals extends Controller {
//  private val dealForm: Form[Deal] = Form(
//      mapping(
//          "id" -> longNumber ,
//          "companyId" -> longNumber,
//          "products" -> List (
//              mapping(
//                  "ean" -> longNumber,
//                  "quantity" -> longNumber,
//                  "price" ->  bigDecimal //doesn't recognise the data type??
//              )(ProductPart.apply)(ProductPart.unapply)
//          )
//          )(Deal.apply)(Deal.unapply)
//          )
  
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
  
  //we pass an arbitrary number to add to the address
  def recommend(number : Long) = Action {
    implicit request =>
      Deal.setBestDeals
      Redirect(routes.Deals.dealList())
  }
  
  def toggle(id : Long) = Action {
    implicit request =>
      Deal.toggleAccepted(id)
      Redirect(routes.Deals.dealList())
  }
  
  
//  def purchaseOrder = Action {
//    implicit request =>
//      val PO = Deal.createPO
//      //Redirect...
//  }
  
  
}