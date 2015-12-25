package models

case class Deal(id: Long, companyId: Long, products: Set[ProductPart] ) {
}

object Deal {
  var deals = Set.empty[Deal] //where we will record deals
  var dummyDeals = Set(
      Deal(1, 1, Set(new ProductPart(5010255079763L, 3, 17.5))),
      Deal(2, 1, Set(new ProductPart(5018206244666L, 3, 15.5), new ProductPart(5018306332812L, 2, 12.3))))
  
  /**
   * returns deals ordered by id number
   */
  //N.B. currently using dummyDeals, change to deals
  def findAll = dummyDeals.toList.sortBy(_.id)
  
  /**
   * return a deal given it's id
   */
  def findById (id: Long) = dummyDeals.find(_.id == id)
      
  
}