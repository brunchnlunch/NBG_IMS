package controllers

import play.api._
import play.api.data.Form
import play.api.data.Forms.{mapping, longNumber, nonEmptyText}
import play.api.i18n.Messages
import play.api.i18n.Messages.Implicits._
import play.api.mvc._
import play.api.mvc.{Action, Controller, Flash}
import play.api.Play.current
import models.Product

class Products extends Controller {
  
  def list = Action {
    implicit request =>
      val products = Product.findAll
      var stockRequired = false
      if (Product.stockStatus.size==0){
        stockRequired = false
      } else {
        stockRequired = true
      }
      Ok(views.html.list(products, stockRequired))
  }
  
  def requiredList = Action {
    implicit request =>
      val products = Product.stockStatus
      Ok(views.html.requiredList(products))
  }
  
}