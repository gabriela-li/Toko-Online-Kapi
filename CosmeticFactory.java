public class CosmeticFactory extends ProductFactory {
    @Override
    public Product createProduct(String id, String name, double price, int stock, String[] params) {
        String brand = params[0];
        String ingredients = params[1];
        return new Cosmetic(id, name, price, stock, brand, ingredients);
    }
}
