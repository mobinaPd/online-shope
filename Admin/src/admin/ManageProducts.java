package admin;

import java.util.LinkedList;
import java.util.List;

public class ManageProducts
{
    final String user = "a", pass = "1234";
    public  static List<Product> productsList = new LinkedList<>();
    
    public  boolean logIn(String user , String pass)
    {
        if(user.equalsIgnoreCase(this.user) && pass.equals(this.pass))
            return true;
        
        return false;
    }

    public void inIt()
    {
        Product product1 = new Product();
        Product product2 = new Product();
        Product product3 = new Product();
        Product product4 = new Product();
        Product product5 = new Product();
        Product product6 = new Product();

        product1.setName("Galaxy Note8");
        product1.setCategory("Mobile");
        product1.setPrice(870);
        product1.setCount(12);
        product1.setGuarantee("Sam Service");
        
        product2.setName("Galaxy S8+");
        product2.setCategory("Mobile");
        product2.setPrice(489.99);
        product2.setCount(12);
        product2.setGuarantee("Sam Service");

        product3.setName("Asus X542 UF");
        product3.setCategory("Laptop");
        product3.setPrice(800);
        product3.setCount(10);
        product3.setGuarantee("Novin Service");

        product4.setName("Be jahanam darmany");
        product4.setCategory("Book");
        product4.setPrice(1.5);
        product4.setCount(14);
        product4.setGuarantee("-----");

        product5.setName("Sony vio ");
        product5.setCategory("Laptop");
        product5.setPrice(500);
        product5.setCount(14);
        product5.setGuarantee("Novin Service");
        
        product6.setName("Kimiyagar");
        product6.setCategory("Book");
        product6.setPrice(2);
        product6.setCount(14);
        product6.setGuarantee("-----");

        productsList.add(product1);
        productsList.add(product2);
        productsList.add(product3);
        productsList.add(product4);
        productsList.add(product5);
        productsList.add(product6);
    }
    
    public void addNewProduct(String name, double price, int count, String category, String guarantee)
    {
        Product product = new Product();
        product.setName(name);
        product.setPrice(price);
        product.setCount(count);
        product.setCategory(category);
        product.setGuarantee(guarantee);
        productsList.add(product);
    }
    
    //edit session
    public void editName(Product product, String name)
    {
        product.setName(name);
    }

    public  void editPrice(Product product, double price)
    {
        product.setPrice(price);
    }

    public  void editCategory(Product product, String category)
    {
        product.setCategory(category);
    }
    
    public  void editGuarantee(Product product, String guarantee)
    {
        product.setGuarantee(guarantee);
    }
  
    public static Product findProductByName(String name)
    {
        int i = 0;
        while(i < productsList.size() && !name.equals(productsList.get(i).getName()))
        {
            i++;
        }
        if(i<productsList.size())
            return productsList.get(i);
        else
            return null;
    }
    
    public  void minusProduct(Product product)
    {
       productsList.remove(findProductByName(product.getName())) ;
    }
}
