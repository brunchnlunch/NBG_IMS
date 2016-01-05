package models

import java.util.Date

case class Deal(id: Long, companyId: Long, products: Set[ProductPart] ) {
var recommended = false
var accepted = false
}

object Deal {
  //the deal in userDeals is for testing... REMOVE!
  var userDeals = Set(new Deal(20, 1, Set(new ProductPart(5010255079763L, 6, 2))))
  var deals = Set.empty[Deal] //where we will record chosen dummy deals
  var dummyDeals = Set(
      Deal(1, 1, Set(new ProductPart(5010255079763L, 3, 15))),
      Deal(2, 1, Set(new ProductPart(5018206244666L, 3, 15.5), new ProductPart(5018306332812L, 2, 12.3))),
      Deal(3, 1, Set(new ProductPart(5018306312913L, 10, 40), new ProductPart(5018206244611L, 10, 40))),
      Deal(4, 2, Set(new ProductPart(5018306332812L, 5, 22), new ProductPart(5018306312913L, 10, 35), new ProductPart(5018206244611L, 10, 20))),
      Deal(5, 2, Set(new ProductPart(5018306312914L, 7, 14.5), new ProductPart(5018306318915L, 2, 10))),
      Deal(6, 2, Set(new ProductPart(5018316318915L, 4, 10))),
      Deal(7, 2, Set(new ProductPart(5018306319915L, 6, 14.5))),
      Deal(8, 3, Set(new ProductPart(5018306318915L, 20, 40), new ProductPart(5018316318915L, 20, 50))),
      Deal(9, 3, Set(new ProductPart(5018306319915L, 10, 12.5))),
      Deal(10, 4, Set(new ProductPart(5018316318915L, 2, 4))),
      Deal(11, 4, Set(new ProductPart(5018306319915L, 4, 7.5), new ProductPart(5010255079763L, 5, 10))))
  
  /**
   * returns a complete list of deals including user and dummy deals. Also orders them by id
   */
  def findAll : List[Deal] = {
    var allDeals = Set.empty[Deal]
    for (deal <- userDeals){
      allDeals += deal
    }
    for (deal <- deals){
      allDeals += deal
    }
    allDeals.toList.sortBy(_.id)
  }
  
  /**
   * return a deal given its id
   */
  def findById (id: Long) = findAll.find(_.id == id)
  
  /**
   * return a deal given its id from dummyDeals
   */
  def findByIdDummy (id: Long) = dummyDeals.find(_.id == id)
  
  /**
   * return a list of deals given a company id from dummyDeals
   */
  //CHANGE TO NORMAL DEALS
  def findByCompanyDummy (id: Long) : List[Deal] = {
    var newDeals = Set.empty[Deal]
    for (deal <- dummyDeals){
      if (deal.companyId == id) {
        newDeals += deal
      }
    }
    newDeals.toList
  }
  
  /**
   * return a list of deals which contain a given product
   */
  def findByProduct (ean: Long) : List[Deal] = {
    var newDeals = Set.empty[Deal]
    for (deal <- findAll) {
      for (product <- deal.products) {
        if(product.ean == ean) {
          newDeals += deal
        }
      }
    }
    newDeals.toList
  }
  
  /**
   * adds dummy deals to the deals list for companys marked as true
   */
  def autoContact {
    var newDeals = Set.empty[Deal]
    for (supplier <- Supplier.Suppliers){
      if(supplier.autoDeal){
        var dealsList = findByCompanyDummy (supplier.id)
        for (deal <- dealsList) {
          newDeals += deal
        }
      }
    }
    deals = newDeals
  }
  
  /**
   * assigns a "score" to a given deal, score is based on price per item, takes average PPI
   */
  def assignScore(id: Long) : Double = {
    var deal = findById(id).get
    var score = 0.0
    for (product <- deal.products) {
      score += product.pricePerItem
    }
    score
  }
  
  /**
   * returns list of deal which it thinks are the best (should also return the number of items ordered?)
   */
  def bestDeals : List[Deal] = {
    var requiredStock = Product.stockStatus
    var stockDone = Set.empty[Product] //products we've found the best deal for
    var bestDeals = Set.empty[Deal]
    for (product <- requiredStock) {
      if (stockDone.contains(product)==false){
        var relevantDeals = findByProduct(product.ean)
        var relevantDealsSorted = relevantDeals.sortWith{ (DealA, DealB) =>
          (assignScore(DealA.id) < assignScore(DealB.id))
        }
        if(relevantDeals.isEmpty){
          stockDone += Product.findByEan(product.ean).get
        } else {
        var bestDeal = relevantDealsSorted.head
        bestDeals += bestDeal
        for (item <- bestDeal.products){
          stockDone += Product.findByEan(item.ean).get
        }
        }
      }
    }
    println("bestDeals completed" + bestDeals)
    bestDeals.toList
  }
  
  /**
   * Sets all deals recommended values to false, and sets recommended ones to true
   */
  def setBestDeals {
    var bestDeal = bestDeals
    for (deal <- findAll){
      deal.recommended = false
      if(bestDeal.contains(deal)){
        deal.recommended = true
      }
    }
  }
  
  def toggleAccepted (id: Long) {
    var deal = findById(id).get
    deal.accepted = !deal.accepted
  }
  
  /**
   * Returns a list of deals which have been toggled as accepted
   */
  def findByAccepted : List[Deal] = {
    var acceptedDeals = Set.empty[Deal]
    for (deal <- findAll){
      if (deal.accepted){
        acceptedDeals += deal
      }
    }
    acceptedDeals.toList
  }
  
  def createPO {
    var acceptedDeals = findByAccepted
    var checkedCompanyIds = Set.empty[Long]
    for (deal <- acceptedDeals){
      if (checkedCompanyIds.contains(deal.companyId)==false){
        checkedCompanyIds += deal.companyId
        var POdeals = Set.empty[Deal]
        POdeals += deal
        for (otherDeal <- acceptedDeals) {
          if (otherDeal.companyId == deal.companyId) {
            POdeals += otherDeal
          }
        }
        var d = new Date().getTime()/1000
        d.toInt
        var id = deal.companyId.toString + d.toString //creates an id by concatenating the company Id and date
        PurchaseOrder.addPO(new PurchaseOrder(id.toLong, deal.companyId, POdeals.toList, d))
      }
    }
    
  }
  
  //INCREASE PRODUCT QUANTITY WHEN ADDING PO????
  
  
  
}