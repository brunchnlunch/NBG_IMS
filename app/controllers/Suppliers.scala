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
import models.ProductPartForm
import models.Deal
import models.AddDealForm

class Suppliers extends Controller {
  private val productForm: Form[ProductPartForm] = Form(mapping("ean" -> longNumber)(ProductPartForm.apply)(ProductPartForm.unapply))
  
  private val dealForm: Form[AddDealForm] = Form(mapping("ean" -> longNumber)(AddDealForm.apply)(AddDealForm.unapply))

  def supplierList = Action {
    implicit request =>
      val suppliers = Supplier.findAll
      Ok(views.html.supplierList(suppliers, productForm, dealForm))
  }
  
  def show(id: Long) = Action {
    implicit request =>
      val supplier = Supplier.findById(id).get
      Ok(views.html.supplierDetails(supplier))
  }
  
  def findByProduct = Action { implicit request =>
	val newProductForm = productForm.bindFromRequest()
	newProductForm.fold(
		hasErrors = { form =>
		  println(form.data)
		  val message = "Incorrent EAN number! Please try again."
			Redirect(routes.Suppliers.supplierList()).flashing("error" -> message)
		},
		success = { newProduct =>
			val productSuppliers = Supplier.findByProduct(newProductForm.get.ean)
			val message2 = "It worked!"  //can't display message?
		  Ok(views.html.supplierList(productSuppliers, productForm ,dealForm)).flashing("success" -> message2)
		}
	)
  }
  
  def toggle(id: Long) = Action {
    implicit request =>
      Supplier.toggleAuto(id)
      Redirect(routes.Suppliers.supplierList())
  }
  
  //we take a number to add it to the route, no other reason
  def autoContact(number:Long) = Action {
    implicit request =>
      Deal.autoContact
      Redirect(routes.Deals.dealList)
  }
  
  //-------------------
  
  def addDeal = Action { implicit request =>
	val newDealForm = dealForm.bindFromRequest()
	dealForm.fold(
		hasErrors = { form =>
		  println(form.data)
			val message = "Incorrent EAN number! Please try again."
			Redirect(routes.Suppliers.supplierList()).flashing("error" -> message)
		},
		success = { newDeal =>
		  val message2 = "a"
			Redirect(routes.Products.list).flashing("success" -> message2)
		}
	)
  }
  
}