package models

case class Supplier(id: Long, company: String, contactName: String, phoneNumber: String, email: String, products: List[Long]) {
}

object Supplier {
  var Suppliers = Set(Supplier(1, "Ceramics inc", "Jeremy Table", "03069990317", "Ceramicsinc@gmail.com", List(5010255079763L, 5018206244666L, 5018306332812L, 5018306312913L, 5018206244611L)),
      Supplier(2, "Gnome-makers", "Susan Sofa", "03069990876", "Gnome-Makers@gmail.com", List(5018306332812L, 5018306312913L, 5018206244611L, 5018306312914L, 5018306318915L, 5018316318915L, 5018306319915L)),
      Supplier(3, "Garden Land", "Helen window", "03069990799", "Garden-Land@gmail.com", List(5018306318915L, 5018316318915L, 5018306319915L)),
      Supplier(4, "The Gnome factory", "Brian Foot", "03069990146", "Gnomefactory@gmail.com", List(5018316318915L, 5018306319915L, 5010255079763L)))
 
  //make a "supplier-part" object to hold what products each supplier has?

  /**
   * Return the suppliers ordered by id
   */
  def findAll = Suppliers.toList.sortBy(_.id)
  
  /**
   * Return a supplier given it's id
   */
  def findById(id: Long) = Suppliers.find(_.id == id)
  
  /**
   * Return a list of suppliers which stock a give product.
   */
  def findByProduct(ean: Long) : List[Supplier]= {
    var checkedSuppliers = Set.empty[Supplier]
    
    for (supplier <- Suppliers) {
      for (product <- supplier.products) {
        if(product == ean) {
          checkedSuppliers += supplier
        }
      }
    }
    checkedSuppliers.toList
  }
      
      
      
}