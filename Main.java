import java.util.*;

public class Main {

    public static final Map<String, String> registeredCC = new HashMap<>();
    static {
        registeredCC.put("titi12", "9944-2211");
        registeredCC.put("andi23", "1234-5678");
        registeredCC.put("dodo", "9876-5432");
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        inisialisasiDataDummy();

        Config config = new Config();
        System.out.println(config.getStoreName());
        System.out.println("Tanggal " + config.getCurrentDate() + "\n");

        boolean exitApp = false;

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
                    User user = UserDatabase.loginUser(username, password);

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

                    boolean success = UserDatabase.registerUser(username, password, fullName, userType);
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

        while (!isLoggedOut) {
            System.out.println("\nMenu:");
            System.out.println("1. Keranjang Anda");
            System.out.println("2. Lihat Katalog Produk");
            System.out.println("3. Total Harga");
            System.out.println("4. Checkout");
            System.out.println("5. Log out");
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
                    System.out.println("\nLihat Katalog Produk:");
                    List<Product> all = ProductDatabase.getAllProducts();
                    if (all.isEmpty()) {
                        System.out.println("Katalog kosong.");
                        break;
                    }

                    for (int i = 0; i < all.size(); i++) {
                        System.out.printf("%d. %s | %s | Rp%.0f | Stok: %d\n",
                                i + 1, all.get(i).getName(), all.get(i).getCategory(),
                                all.get(i).getPrice(), all.get(i).getStock());
                    }

                    System.out.print("Pilih nomor produk untuk lihat detail atau tekan 0 untuk kembali: ");
                    int pilihan = sc.nextInt();
                    sc.nextLine();

                    if (pilihan >= 1 && pilihan <= all.size()) {
                        Product selected = all.get(pilihan - 1);
                        System.out.println("\n" + selected.getDetails());
                        System.out.println("Harga: " + Main.formatRupiah(selected.getPrice()));
                        System.out.println("Stok tersedia: " + selected.getStock());

                        System.out.print("Tambah ke keranjang? (yes/no): ");
                        String jawab = sc.nextLine();

                        if (jawab.equalsIgnoreCase("yes")) {
                            System.out.print("Masukkan jumlah: ");
                            int jumlah = sc.nextInt();
                            sc.nextLine();

                            keranjang.addItem(selected, jumlah);
                        }
                    } else if (pilihan == 0){

                    }else {
                        System.out.println("Pilihan tidak valid.");
                    }
                }
                case 3 -> {
                    System.out.println("Total Harga: " + formatRupiah(keranjang.getTotalPrice()));
                }
                case 4 -> {
                    List<CartItem> items = new ArrayList<>(keranjang.getItems());

                    if (items.isEmpty()) {
                        System.out.println("Keranjang Anda kosong.");
                        return;
                    }

                    // Tampilkan isi keranjang dengan nomor urut
                    System.out.println("Isi Keranjang:");
                    for (int i = 0; i < items.size(); i++) {
                        CartItem item = items.get(i);
                        System.out.printf("%d. %s | Qty: %d | Harga per item: Rp%.0f\n",
                                i + 1, item.getProduct().getName(), item.getQuantity(), item.getProduct().getPrice());
                    }

                    // List untuk menyimpan item yang akan dibayar
                    ShoppingCart checkoutCart = new ShoppingCart(user);

                    // Loop untuk input pilihan item dan jumlah checkout
                    System.out.print("Pilih item yang ingin Anda checkout (pisahkan dengan koma): ");
                    String pilihan = sc.nextLine();
                    String[] indeks = pilihan.split(",");

                    for (String idxStr : indeks) {
                        try {
                            int idx = Integer.parseInt(idxStr.trim()) - 1;
                            if (idx >= 0 && idx < items.size()) {
                                CartItem selectedItem = items.get(idx);

                                System.out.printf("Masukkan jumlah untuk checkout '%s' (max %d): ",
                                        selectedItem.getProduct().getName(), selectedItem.getQuantity());
                                int checkoutQty = sc.nextInt();
                                sc.nextLine();

                                if (checkoutQty > 0 && checkoutQty <= selectedItem.getQuantity()) {
                                    double totalHarga = checkoutQty * selectedItem.getProduct().getPrice();
                                    System.out.printf("Checkout %d x %s berhasil, total bayar: Rp%.0f\n",
                                            checkoutQty, selectedItem.getProduct().getName(), totalHarga);

                                    // Tambahkan ke keranjang checkout (yang akan dibayar)
                                    checkoutCart.addItem(selectedItem.getProduct(), checkoutQty);

                                    // Kurangi quantity di keranjang asli
                                    keranjang.reduceItemQuantity(selectedItem.getProduct().getId(), checkoutQty);
                                } else {
                                    System.out.println("Jumlah checkout tidak valid.");
                                }
                            } else {
                                System.out.println("Pilihan indeks tidak valid: " + idxStr);
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Input tidak valid: " + idxStr);
                        }
                    }
                    // --- Pilih metode pembayaran ---
                    if (checkoutCart.getItems().isEmpty()) {
                        System.out.println("Tidak ada item untuk dibayar.");
                        return;
                    }

                    System.out.print("Pilih Jenis Pembayaran (cc / gopay / transfer): ");
                    String paymentMethod = sc.nextLine().toLowerCase();

                    PaymentContext paymentContext = new PaymentContext();

                    switch (paymentMethod) {
                        case "cc" -> {
                            if (registeredCC.containsKey(user.getUsername())) {
                                paymentContext.setPaymentStrategy(new CreditCard(registeredCC.get(user.getUsername())));
                            } else {
                                System.out.println("Credit Card Anda tidak terdaftar.");
                                return;
                            }
                        }
                        case "gopay" -> {
                            GopayAPI gopayApi = new GopayAPI();
                            paymentContext.setPaymentStrategy(new GopayAdapter(gopayApi));
                        }
                        case "transfer" -> {
                            System.out.print("Masukkan nomor rekening tujuan: ");
                            String accNum = sc.nextLine();
                            paymentContext.setPaymentStrategy(new SwiftTransfer(accNum));
                        }
                        default -> {
                            System.out.println("Metode pembayaran tidak valid.");
                            return;
                        }
                    }

                    // Proses checkout / pembayaran
                    OrderSubject orderSubject = new OrderSubject();
                    Checkout checkout = new Checkout(paymentContext, orderSubject, config);
                    checkout.processCheckout(user, checkoutCart, false, false, paymentMethod, "Jl. Merdeka 10");

                    Order latestOrder = checkout.getLastOrder();
                    System.out.println("\n====== RINGKASAN PESANAN ======");
                    System.out.println(latestOrder.getSummary());
                }
                case 5 -> {
                    System.out.print("Logout (yes/no): ");
                    String logOut = sc.next();
                    if (logOut.equalsIgnoreCase("yes")) {
                        isLoggedOut = true;
                    }
                }
                default -> System.out.println("Pilihan tidak valid.");
            }
        }
    }

    public static void handleAdminMenu(User user, Scanner sc, Config config) {
        boolean isLoggedOut = false;

        while (!isLoggedOut) {
            System.out.println("\n=== MENU ADMIN ===");
            System.out.println("1. Lihat Semua Produk");
            System.out.println("2. Tambah Produk Baru");
            System.out.println("3. Hapus Produk");
            System.out.println("4. Lihat Semua Transaksi");
            System.out.println("5. Logout");
            System.out.print("Pilih menu: ");
            int menu = sc.nextInt();
            sc.nextLine();

            switch (menu) {
                case 1 -> {
                    List<Product> products = ProductDatabase.getAllProducts();
                    if (products.isEmpty()) {
                        System.out.println("Belum ada produk dalam katalog.");
                    } else {
                        System.out.println("Daftar Produk:");
                        for (Product p : products) {
                            System.out.println(p.getDetails() +
                                    " | Harga: " + formatRupiah(p.getPrice()) +
                                    " | Stok: " + p.getStock());
                        }
                    }
                }
                case 2 -> {
                    System.out.print("Masukkan ID produk: ");
                    String id = sc.nextLine();
                    System.out.print("Masukkan nama produk: ");
                    String name = sc.nextLine();
                    System.out.print("Masukkan harga produk: ");
                    double price = sc.nextDouble();
                    sc.nextLine();
                    System.out.print("Masukkan stok produk: ");
                    int stock = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Masukkan ukuran (Size): ");
                    String size = sc.nextLine();
                    System.out.print("Masukkan bahan (Material): ");
                    String material = sc.nextLine();

                    Product newProduct = new Clothing(id, name, price, stock, size, material);
                    ProductDatabase.addProduct(newProduct);
                    System.out.println("Produk berhasil ditambahkan!");
                }
                case 3 -> {
                    List<Product> all = ProductDatabase.getAllProducts();
                    if (all.isEmpty()) {
                        System.out.println("Tidak ada produk untuk dihapus.");
                        break;
                    }
                    for (int i = 0; i < all.size(); i++) {
                        System.out.printf("%d. %s\n", i + 1, all.get(i).getName());
                    }
                    System.out.print("Pilih nomor produk yang ingin dihapus: ");
                    int idx = sc.nextInt();
                    sc.nextLine();
                    if (idx >= 1 && idx <= all.size()) {
                        ProductDatabase.removeProduct(all.get(idx - 1).getId());
                        System.out.println("Produk berhasil dihapus.");
                    } else {
                        System.out.println("Pilihan tidak valid.");
                    }
                }
                case 4 -> {
                    List<Order> allOrders = OrderDatabase.getAllOrders();
                    if (allOrders.isEmpty()) {
                        System.out.println("Belum ada transaksi.");
                    } else {
                        System.out.println("Daftar Transaksi:");
                        for (Order o : allOrders) {
                            System.out.println(o.getSummary());
                            System.out.println("--------------------------");
                        }
                    }
                }
                case 5 -> {
                    System.out.print("Logout (yes/no): ");
                    String keluar = sc.next();
                    if (keluar.equalsIgnoreCase("yes")) {
                        isLoggedOut = true;
                    }
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
