
package clientapp;

import java.util.List;


public class Product
{
     
    private String name;
    private double price;
    private  int count = 0;
    private String category;
    private String guarantee;
        

    
    public String getGuarantee() {
        return guarantee;
    }

    public void setGuarantee(String guarantee) {
        this.guarantee = guarantee;
    }
    
    public Product()
    {

    }

    public Product(String name)
    {
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

   public void setPrice(double price) {
        this.price = price;
    }

    public int getCount() {
        return count;
    }

   public void setCount(int count) {
        this.count = count;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
    public static Product findProductByName(String name)
    {
        Tcp.productsList.size();
        int  i = 0;
        while(i < Tcp.productsList.size() && !name.equals(Tcp.productsList.get(i).getName()))
        {
            i++;
        }
        return Tcp.productsList.get(i);
    }
     
    public static void findProductByCategory(String category, List<Product> list)
    {
         int i = 0 ;
         //Tcp clientTcp = new Tcp();
         while(i < Tcp.productsList.size())
        {
            if(category.equalsIgnoreCase(Tcp.productsList.get(i).getCategory()))
            {
                list.add(Tcp.productsList.get(i));
            }
            i++;
        }
        //return Tcp.productsList.get(i);
    }
}
