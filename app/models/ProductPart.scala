package models

case class ProductPart(ean: Long, quantity: Long, price: Double) {

  /**
   * return the price per item
   */
  def pricePerItem : Double = {
    val pricePerItem = price / quantity
    pricePerItem
  }
  
}

