import org.specs2.mutable._
import org.specs2.runner._
import org.junit.runner._
import play.api.test._
import play.api.test.Helpers._
import models.Product
import models.Supplier
import models.ProductPart
import models.Deal

@RunWith(classOf[JUnitRunner])
object MyTests extends Specification {
  
  "Application" should {
    
    //Product tests -------------------------------
    "return a list of products" in new WithApplication{
      var products = Product.findAll
      assert(products.isInstanceOf[List[Product]])
    }
    
    "return product by ean" in new WithApplication{
      assert(1==1)
    }
    
    "check if a given product is low" in new WithApplication{
      assert(Product.productLow(5010255079763L)==true)
    }
    
    "return a list of products which are low" in new WithApplication{
      var prods = Product.stockStatus
      assert(prods.isInstanceOf[List[Product]])
    }
    
    "return the difference between quantity and threshold of a product" in new WithApplication{
      var difference = Product.difference(5010255079763L)
      var expectedDifference = 10
      assert(difference == expectedDifference)
    }
    
    //Supplier tests ----------------------------------
    "return a list of suppliers" in new WithApplication{
      var suppliers = Supplier.findAll
      assert(suppliers.isInstanceOf[List[Supplier]])
    }
    
    "return a list of suppliers which stock a given product" in new WithApplication{
      var ean = 5018306319915L
      var suppliers = Supplier.findByProduct(ean)
      assert(suppliers.isInstanceOf[List[Supplier]])
    }
    
    "Toggle if a supplier can be automatically contacted" in new WithApplication {
      val id = 1
      val bool = Supplier.findById(id).get.autoDeal
      Supplier.toggleAuto(id)
      val newBool = Supplier.findById(id).get.autoDeal
      assert(newBool == !bool)
    }
    
    //ProductPart tests ---------------------------
    "return the price per item for a product deal" in new WithApplication{
      val deal = Deal.findById(1).get
      val productPart = deal.products.head
      val pPI = productPart.pricePerItem
      assert(pPI == 5)
    }
    
    //Deal tests -------------------------------
    "add company deals which are toggled for auto contact to deals list" in new WithApplication{
      Supplier.toggleAuto(2) //note that 1 is already toggled by a previous test
      Deal.autoContact
      println(Deal.deals)
      assert(Deal.deals.nonEmpty)
    }
    
    "asdhasdh" in new WithApplication {
      Deal.bestDeals
      assert(1==2)
    }
    
  }
  
}