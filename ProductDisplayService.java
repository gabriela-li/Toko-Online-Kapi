import java.util.List;

public class ProductDisplayService {

    public void displayProducts(List<Product> products, User user) {
        if (products.isEmpty()) {
            System.out.println("Tidak ada produk.");
            return;
        }

        if (user.getUserType().equals("Admin")) {
            displayProductsForAdmin(products);
        } else {
            displayProductsForCustomer(products);
        }
    }

    private void displayProductsForAdmin(List<Product> products) {
        for (int i = 0; i < products.size(); i++) {
            Product p = products.get(i);
            System.out.printf("%s | %d. %s | %s | Rp%.0f | Stok: %d\n",
                    p.getId(), i + 1, p.getName(), p.getCategory(), p.getPrice(), p.getStock());
        }
    }

    private void displayProductsForCustomer(List<Product> products) {
        for (int i = 0; i < products.size(); i++) {
            Product p = products.get(i);
            System.out.printf("%d. %s | %s | Rp%.0f | Stok: %d\n",
                    i + 1, p.getName(), p.getCategory(), p.getPrice(), p.getStock());
        }
    }

    public void displayProductDetails(Product product) {
        System.out.println("\n" + product.getDetails());
        System.out.println("Harga: " + formatRupiah(product.getPrice()));
        System.out.println("Stok tersedia: " + product.getStock());
    }

    public void displayCategoryMenu() {
        System.out.println("\nPilih kategori produk:");
        System.out.println("1. Semua Produk");
        System.out.println("2. Clothing");
        System.out.println("3. Cosmetic");
        System.out.println("4. Food");
        System.out.print("Masukkan pilihan: ");
    }

    private String formatRupiah(double amount) {
        return String.format("%.0f", amount);
    }
}