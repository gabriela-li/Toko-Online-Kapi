import java.util.*;
public class Main {
    static {
        PaymentMethodService.registerCreditCard("titi12", "9944-2211");
        PaymentMethodService.registerCreditCard("andi23", "1234-5678");
        PaymentMethodService.registerCreditCard("dodo", "9876-5432");
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        inisialisasiDataDummy();

        Config config = new Config();
        System.out.println(config.getStoreName());
        System.out.println("Tanggal " + config.getCurrentDate() + "\n");

        boolean exitApp = false;

        UserDatabase userDB = UserDatabase.getInstance(); 

        while (!exitApp) {
            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.println("3. Exit");
            System.out.print("Pilih menu: ");
            int pilihan = sc.nextInt();
            sc.nextLine();

            switch (pilihan) {
                case 1 -> {
                    System.out.print("Username: ");
                    String username = sc.nextLine();
                    System.out.print("Password: ");
                    String password = sc.nextLine();
                    User user = userDB.loginUser(username, password);

                    if (user == null) {
                        System.out.println("Login gagal! Username atau password salah.\n");
                    } else {
                        System.out.println("\nHalo " + user.getFullName());
                        if (user.getUserType().equalsIgnoreCase("Admin")) {
                            handleAdminMenu(user, sc, config);
                        } else {
                            handleUserMenu(user, sc, config);
                        }
                    }
                }
                case 2 -> {
                    System.out.print("Username baru: ");
                    String username = sc.nextLine();
                    System.out.print("Password: ");
                    String password = sc.nextLine();
                    System.out.print("Nama Lengkap: ");
                    String fullName = sc.nextLine();
                    System.out.print("Tipe user (Customer/Admin): ");
                    String userType = sc.nextLine();

                    boolean success = userDB.registerUser(username, password, fullName, userType);
                    if (success) {
                        System.out.println("Registrasi berhasil!\n");
                    } else {
                        System.out.println("Username sudah digunakan. Coba lagi.\n");
                    }
                }
                case 3 -> {
                    System.out.println("Terima kasih telah menggunakan aplikasi!");
                    exitApp = true;
                }
                default -> System.out.println("Pilihan tidak valid!\n");
            }
        }
    }

    public static void handleUserMenu(User user, Scanner sc, Config config) {
        boolean isLoggedOut = false;
        ShoppingCart keranjang = user.getCart();
        ShoppingController shoppingController = new ShoppingController();
        OrderDisplayService orderDisplay = new OrderDisplayService(sc);

        while (!isLoggedOut) {
            System.out.println("\nMenu:");
            System.out.println("1. Keranjang Anda");
            System.out.println("2. Lihat Katalog Produk");
            System.out.println("3. Total Harga");
            System.out.println("4. Checkout");
            System.out.println("5. Lihat Riwayat Pesanan");
            System.out.println("6. Log out");
            System.out.print("Masukkan Pilihan Menu: ");
            int menu = sc.nextInt();
            sc.nextLine();

            switch (menu) {
                case 1 -> {
                    System.out.println("\nKeranjang Anda:");
                    List<CartItem> isi = keranjang.getItems();

                    if(isi.isEmpty()) {
                        System.out.println("Keranjang Anda kosong.");
                    } else{
                        for (CartItem temp : isi) {
                            Product p = temp.getProduct();
                            System.out.println(p.getDetails() +
                                    " | Qty: " + temp.getQuantity() +
                                    " | Harga Satuan: " + formatRupiah(p.getTotalPrice()));
                        }
                    }
                }
                case 2 -> {
                    shoppingController.handleProductBrowsing(user, keranjang);
                }
                case 3 -> {
                    System.out.println("Total Harga: " + formatRupiah(keranjang.getTotalPrice()));
                }
                case 4 -> {
                    CheckoutService checkoutService = new CheckoutService(sc, config);
                    checkoutService.processCheckout(user, keranjang);
                } case 5 -> {
                    orderDisplay.displayOrderHistory(user);
                }
                case 6 -> {
                    System.out.print("Logout (yes/no): ");
                    String logOut = sc.next();
                    if (logOut.equalsIgnoreCase("yes")) {
                        isLoggedOut = true;
                    }
                    System.out.println();
                }
                default -> System.out.println("Pilihan tidak valid.");
            }
        }
    }

    public static void handleAdminMenu(User user, Scanner sc, Config config) {
        boolean isLoggedOut = false;
        AdminController adminController = new AdminController();

        while (!isLoggedOut) {
            System.out.println("\n=== MENU ADMIN ===");
            System.out.println("1. Lihat Semua Produk");
            System.out.println("2. Manajemen Produk (CRUD)");
            System.out.println("3. Lihat Semua Transaksi");
            System.out.println("4. Logout");
            System.out.print("Pilih menu: ");
            int menu = sc.nextInt();
            sc.nextLine();

            switch (menu) {
                case 1 -> {
                    adminController.handleViewProducts(user);
                }
                case 2 -> {
                    adminController.handleProductManagement(user);
                }
                case 3 -> {
                    adminController.handleTransactionReports();
                }
                case 4 -> {
                    System.out.print("Logout (yes/no): ");
                    String keluar = sc.next();
                    if (keluar.equalsIgnoreCase("yes")) {
                        isLoggedOut = true;
                    }
                    System.out.println();
                }
                default -> System.out.println("Pilihan tidak valid!");
            }
        }
    }

    public static String formatRupiah(double amount) {
        return String.format("Rp%,.0f", amount).replace(',', '.');
    }

    public static void inisialisasiDataDummy() {
        Product p1 = new Clothing("C001", "Kaos Polos Keren", 75000, 10, "L", "Cotton");
        Product p2 = new Clothing("C002", "Jaket Hoodie", 150000, 5, "M", "Fleece");
        Product p3 = new Clothing("C003", "Celana Cargo", 120000, 8, "XL", "Canvas");
        Product c1 = new Cosmetic("CS001", "Lipstick Matte", 50000, 20, "MakeOver", "Rose");
        Product f1 = new Food("F001", "Roti Gandum", 15000, 30, "2025-12-01", "35g");

        ProductDatabase.addProduct(c1);
        ProductDatabase.addProduct(f1);

        ProductDatabase.addProduct(p1);
        ProductDatabase.addProduct(p2);
        ProductDatabase.addProduct(p3);
    }
}
