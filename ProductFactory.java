public abstract class ProductFactory {
    public abstract Product createProduct(String id, String name, double price, int stock, String[] params);
}
