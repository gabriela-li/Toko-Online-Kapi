public class Food extends Product {
    private String expirationDate;
    private String weight;

    public Food(String id, String name, double price, int stock, String expirationDate, String weight) {
        super(id, name, price,"Food", stock);
        this.expirationDate = expirationDate;
        this.weight = weight;
    }

    @Override
    public String getDetails() {
        return String.format("Food: %s, Expiration: %s, Weight: %s", name, expirationDate, weight);
    }

    public double getTotalPrice(){
        return this.price;
    }
}
