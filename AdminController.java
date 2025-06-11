import java.util.List;
import java.util.Scanner;

public class AdminController {
    private ProductCatalogService catalogService;
    private ProductDisplayService displayService;
    private TransactionReportController reportController;
    private Scanner scanner;

    public AdminController() {
        this.catalogService = new ProductCatalogService();
        this.displayService = new ProductDisplayService();
        this.reportController = new TransactionReportController();
        this.scanner = new Scanner(System.in);
    }

    public void handleViewProducts(User admin) {
        // Display category menu
        displayService.displayCategoryMenu();
        int categoryChoice = scanner.nextInt();
        scanner.nextLine(); // consume newline

        // Filter products by category
        List<Product> filteredProducts = catalogService.filterProductsByCategory(categoryChoice);

        if (filteredProducts == null) {
            System.out.println("Pilihan tidak valid.");
            return;
        }

        // Display products (akan menampilkan dengan format admin karena user.getUserType() == "Admin")
        displayService.displayProducts(filteredProducts, admin);
    }

    public void handleProductManagement(User admin) {
        while (true) {
            System.out.println("\n=== MANAJEMEN PRODUK ===");
            System.out.println("1. Lihat Produk");
            System.out.println("2. Tambah Produk");
            System.out.println("3. Hapus Produk");
            System.out.println("4. Update Stok");
            System.out.println("0. Kembali");
            System.out.print("Pilih opsi: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1 -> handleViewProducts(admin);
                case 2 -> handleAddProduct();
                case 3 -> handleDeleteProduct(admin);
                case 4 -> handleUpdateStock(admin);
                case 0 -> {
                    return;
                }
                default -> System.out.println("Pilihan tidak valid.");
            }
        }
    }

    // Method untuk handle transaction reports
    public void handleTransactionReports() {
        reportController.handleTransactionReports();
    }

    private void handleAddProduct() {
        System.out.println("\nTambah Produk Baru:");
        System.out.println("1. Clothing");
        System.out.println("2. Cosmetic");
        System.out.println("3. Food");
        System.out.print("Pilih jenis produk: ");

        int type = scanner.nextInt();
        scanner.nextLine();

        System.out.print("ID Produk: ");
        String id = scanner.nextLine();

        System.out.print("Nama Produk: ");
        String name = scanner.nextLine();

        System.out.print("Harga: ");
        double price = scanner.nextDouble();

        System.out.print("Stok: ");
        int stock = scanner.nextInt();
        scanner.nextLine();

        Product newProduct = null;

        switch (type) {
            case 1 -> {
                System.out.print("Size: ");
                String size = scanner.nextLine();
                System.out.print("Material: ");
                String material = scanner.nextLine();
                newProduct = new Clothing(id, name, price, stock, size, material);
            }
            case 2 -> {
                System.out.print("Brand: ");
                String brand = scanner.nextLine();
                System.out.print("Ingredients: ");
                String ingredients = scanner.nextLine();
                newProduct = new Cosmetic(id, name, price, stock, brand, ingredients);
            }
            case 3 -> {
                System.out.print("Expiration Date: ");
                String expDate = scanner.nextLine();
                System.out.print("Weight: ");
                String weight = scanner.nextLine();
                newProduct = new Food(id, name, price, stock, expDate, weight);
            }
            default -> {
                System.out.println("Jenis produk tidak valid.");
                return;
            }
        }

        ProductDatabase.addProduct(newProduct);
        System.out.println("Produk berhasil ditambahkan!");
    }

    private void handleDeleteProduct(User admin) {
        System.out.println("\nHapus Produk:");

        // Tampilkan semua produk
        List<Product> allProducts = catalogService.filterProductsByCategory(1); // Semua produk
        displayService.displayProducts(allProducts, admin);

        System.out.print("Masukkan ID produk yang akan dihapus: ");
        String productId = scanner.nextLine();

        ProductDatabase.removeProduct(productId);
        System.out.println("Produk berhasil dihapus!");
    }

    private void handleUpdateStock(User admin) {
        System.out.println("\nUpdate Stok Produk:");

        // Tampilkan semua produk
        List<Product> allProducts = catalogService.filterProductsByCategory(1); // Semua produk
        displayService.displayProducts(allProducts, admin);

        System.out.print("Masukkan ID produk: ");
        String productId = scanner.nextLine();

        // Cari produk berdasarkan ID
        Product product = findProductById(productId);
        if (product == null) {
            System.out.println("Produk tidak ditemukan.");
            return;
        }

        System.out.println("Stok saat ini: " + product.getStock());
        System.out.print("Tambah stok: ");
        int additionalStock = scanner.nextInt();
        scanner.nextLine();

        product.addStock(additionalStock);
        System.out.println("Stok berhasil diupdate! Stok baru: " + product.getStock());
    }

    private Product findProductById(String id) {
        List<Product> allProducts = ProductDatabase.getAllProducts();
        for (Product product : allProducts) {
            if (product.getId().equals(id)) {
                return product;
            }
        }
        return null;
    }
}