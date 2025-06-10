import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductDatabase {
    private static final Map<String, Product> products = new HashMap<>();

    public static void addProduct(Product product) {
        products.put(product.getId(), product);
    }

    public static List<Product> getAllProducts() {
        return new ArrayList<>(products.values());
    }

    public static void removeProduct(String id) {
        products.remove(id);
    }
}
