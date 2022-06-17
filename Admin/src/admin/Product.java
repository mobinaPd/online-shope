package admin;

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
}