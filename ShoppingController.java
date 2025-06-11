import java.util.List;
import java.util.Scanner;

public class ShoppingController {
    private ProductCatalogService catalogService;
    private ProductDisplayService displayService;
    private Scanner scanner;

    public ShoppingController() {
        this.catalogService = new ProductCatalogService();
        this.displayService = new ProductDisplayService();
        this.scanner = new Scanner(System.in);
    }

    public void handleProductBrowsing(User user, ShoppingCart cart) {
        // kategori
        displayService.displayCategoryMenu();
        int categoryChoice = scanner.nextInt();
        scanner.nextLine(); // consume newline

        // Filter produk berdasarkan kategori
        List<Product> filteredProducts = catalogService.filterProductsByCategory(categoryChoice);

        if (filteredProducts == null) {
            System.out.println("Pilihan tidak valid.");
            return;
        }

        if (filteredProducts.isEmpty()) {
            System.out.println("Katalog kosong.");
            return;
        }

        // Display produk
        displayService.displayProducts(filteredProducts, user);

        // Pemilihan produk
        handleProductSelection(filteredProducts, cart);
    }

    private void handleProductSelection(List<Product> products, ShoppingCart cart) {
        System.out.print("Pilih nomor produk untuk lihat detail atau tekan 0 untuk kembali: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        if (choice == 0) {
            return; // kembali
        }

        Product selectedProduct = catalogService.getSelectedProduct(choice, products);

        if (selectedProduct != null) {
            // detail produk
            displayService.displayProductDetails(selectedProduct);

            // tambahkan ke keranjang
            handleAddToCart(selectedProduct, cart);
        } else {
            System.out.println("Pilihan tidak valid.");
        }
    }

    private void handleAddToCart(Product product, ShoppingCart cart) {
        System.out.print("Tambah ke keranjang? (yes/no): ");
        String response = scanner.nextLine();

        if (response.equalsIgnoreCase("yes")) {
            System.out.print("Masukkan jumlah: ");
            int quantity = scanner.nextInt();
            scanner.nextLine(); 

            cart.addItem(product, quantity);
        }
    }
}