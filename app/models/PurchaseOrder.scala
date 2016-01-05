package models

import scala.collection.mutable.Map

//create id by adding together companyId and date?
case class PurchaseOrder (id: Long, companyId: Long, deals : List[Deal], date: Long ) {
  var quantities = createDictionary


  //creates a dictionary that maps deal ids to quantity wanted
  def createDictionary : Map[Long, Long] = {
    var Dic : Map[Long,Long] = Map()
    for (deal <- deals){
      Dic += (deal.id -> 1) //defaults to quantity 1
    }
    Dic
  }
  



}

object PurchaseOrder {
  var purchaseOrders = Set.empty[PurchaseOrder]
  
  def findAll = purchaseOrders.toList.sortBy(_.date)
  
  def findById(id: Long) = purchaseOrders.find(_.id == id)
  
  def findByDealId(id: Long) : PurchaseOrder = {
    var purOrder : PurchaseOrder = null
    for (PO <- purchaseOrders){
      for (deal <- PO.deals) {
        if (deal.id == id){
          purOrder = PO
        }
      }
    }
    purOrder
  }
  
  def addPO (PO : PurchaseOrder) {
    var newPurchaseOrders = Set.empty[PurchaseOrder]
    for (purOrder <- purchaseOrders) {
      newPurchaseOrders += purOrder
    }
    newPurchaseOrders += PO
    purchaseOrders = newPurchaseOrders
  }
  
  //takes deal id and quantity
  def updateQuantity (id: Long, quantity: Long) {
    var PO = findByDealId(id)
    PO.quantities(id) = quantity
  }
  
  def productList (id : Long): Map[Long, List[Double]] = {
    var productList : Map[Long, List[Double]]= Map()
    var PO = findById(id).get
    for(deal <- PO.deals){
      for(product <- deal.products){
        var quantity = product.quantity * PO.quantities(deal.id)
        var pricePerItem = product.pricePerItem
        productList += (product.ean -> List(quantity, pricePerItem))
      }
    }
    productList    
  }
  
  def total (id : Long) : Double = {
    var PO = findById(id).get
    var total : Double = 0
    for(deal <- PO.deals){
      for(product <- deal.products){
        var totalPriceOfProduct = product.price * PO.quantities(deal.id)
        total += totalPriceOfProduct
      }
    }
    total
  }
  
  def dealQuantity (id: Long, quantity: Long) {
    var PO = findByDealId(id)
    PO.quantities(id) = quantity
  }
  
  
  
  
  
  
}