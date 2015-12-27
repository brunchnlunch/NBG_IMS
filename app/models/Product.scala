package models

case class Product(ean: Long, name:String, quantity: Long, threshold: Long){  
}

object Product { //Each product has a quantity threshhold
  var products = Set(Product(5010255079763L, "Christmas Gnome", 20, 30), 
	  Product(5018206244666L, "Fishing Gnome", 30, 15), 
	  Product(5018306332812L, "Big Gnome", 50, 15), 
	  Product(5018306312913L, "Swimming Gnome", 35, 15), 
	  Product(5018206244611L, "Purple Gnome", 5, 10), 
	  Product(5018306312914L, "Tiny Gnome", 22, 15),
	  Product(5018306318915L, "Robot Gnome", 20, 15),
	  Product(5018316318915L, "Disco Gnome", 10, 15),
	  Product(5018306319915L, "Happy Gnome", 4, 15))
  
  /**
   * Return the products list ordered by ean number.
   */
	def findAll = products.toList.sortBy(_.ean)
	  
	/**
	 * Given the ean of a specific product, returns the unique product with matching ean number
	 */
	def findByEan(ean: Long) = products.find(_.ean == ean)
	
	/**
	 * Checks if a product quantity is under its threshold. Takes a product ean number as an argument and returns true or false
	 */
	def productLow (ean: Long) : Boolean = {
    var product = Product.findByEan(ean).get
    if (product.threshold >= product.quantity ){
      true
    } else {
      false
    }
  }
  
	/**
	 * Checks the quantity against the threshold for each product, if any items need to be ordered returns a list of required products
	 */
	def stockStatus : List[Product] = { //change to recursive
	  var lowProducts = Set.empty[Product]
    for (product <- products) {
      if(Product.productLow(product.ean)) {
        lowProducts += product
      }
    }
	  lowProducts.toList
  }
	
	/**
	 * Gives the difference between the quantity and threshold of a given product
	 */
  def difference (ean: Long) : Long = {
    val product = Product.findByEan(ean).get
    var difference = product.threshold - product.quantity
    difference
  }
  
  
  
  
}