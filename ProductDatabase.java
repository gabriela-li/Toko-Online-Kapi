import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductDatabase {
    private static ProductDatabase instance;
    private static final Map<String, Product> products = new HashMap<>();

    private ProductDatabase() {
    }

    public static ProductDatabase getInstance() {
        if (instance == null) {
            instance = new ProductDatabase();
        }
        return instance;
    }

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
