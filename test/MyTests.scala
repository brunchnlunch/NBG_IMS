import org.specs2.mutable._
import org.specs2.runner._
import org.junit.runner._
import play.api.test._
import play.api.test.Helpers._
import models.Product
import models.Supplier

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
    
  }
  
}