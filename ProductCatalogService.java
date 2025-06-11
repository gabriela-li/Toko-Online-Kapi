import java.util.ArrayList;
import java.util.List;

public class ProductCatalogService {

    public List<Product> filterProductsByCategory(int categoryChoice) {
        List<Product> allProducts = ProductDatabase.getAllProducts();
        List<Product> filtered;

        switch (categoryChoice) {
            case 1 -> filtered = allProducts; // Semua Produk
            case 2 -> filtered = filterByType(allProducts, Clothing.class);
            case 3 -> filtered = filterByType(allProducts, Cosmetic.class);
            case 4 -> filtered = filterByType(allProducts, Food.class);
            default -> {
                return null; // Pilihan tidak valid
            }
        }

        return filtered;
    }

    private List<Product> filterByType(List<Product> products, Class<?> productType) {
        List<Product> filtered = new ArrayList<>();
        for (Product product : products) {
            if (productType.isInstance(product)) {
                filtered.add(product);
            }
        }
        return filtered;
    }

    public boolean isValidProductChoice(int choice, List<Product> products) {
        return choice >= 1 && choice <= products.size();
    }

    public Product getSelectedProduct(int choice, List<Product> products) {
        if (isValidProductChoice(choice, products)) {
            return products.get(choice - 1);
        }
        return null;
    }
}