public class ClothingFactory extends ProductFactory {
    @Override
    public Product createProduct(String id, String name, double price, int stock, String[] params) {
        String size = params[0];
        String material = params[1];
        return new Clothing(id, name, price, stock, size, material);
    }
}
