public abstract class Product {
    //atribut-atribut yang dimiliki
    protected String id;
    protected String name;
    protected double price;
    protected String category;
    protected int stock;

    //constructor
    public Product (String id, String name, double price, String category, int stock){
        this.id = id;
        this.name = name;
        this.price = price;
        this.category = category;
        this.stock = stock;
    }

    // Getter
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public String getCategory() {
        return category;
    }

    public int getStock() {
        return stock;
    }

    public void reduceStock(int amount) {
        stock -= amount;
    }

    public void addStock(int amount) {
        stock += amount;
    }

    public abstract String getDetails();
    public abstract double getTotalPrice();
    
}
