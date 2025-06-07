public class Cosmetic extends Product {
    private String brand;
    private String ingredients;

    public Cosmetic(String id, String name, double price, int stock, String brand, String ingredients) {
        super(id, name, price, "Cosmetic", stock);
        this.brand = brand;
        this.ingredients = ingredients;
    }

    @Override
    public String getDetails() {
        return String.format("Cosmetic: %s, Brand: %s, Ingredients: %s", name, brand, ingredients);
    }

    public double getTotalPrice(){
        return this.price;
    }
}
