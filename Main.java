import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {

    public static final Map<String, String> registeredCC = new HashMap<>();
    static {
        registeredCC.put("titi12", "9944-2211");
        registeredCC.put("andi23", "1234-5678");
        registeredCC.put("dodo", "9876-5432");
    }

    /*
     * username = andi23
     * passw = passw123
     * fullName = Andi Pratama
     * type = customer
     * 
     * username = dodo
     * passw = dodo123
     * full Name = Dodo Ando
     * type = admin
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Config config = new Config();
        System.out.println(config.getStoreName());//tampilkan nama tokonya
        System.out.println("Tanggal " + config.getCurrentDate() + "\n");

        while (true){
            //handle user login
            System.out.print("Masukkan Username Anda : ");
            String username = sc.next();
            //System.out.println();
            System.out.print("Masukkan Password : ");
            String password = sc.next();
            User user = new User(username, password, "Andi Pratama", "Customer");
            
            //branching untuk check apakah user customer atau admin
            String type = user.getUserType();
            if(type.equals("Customer")){
                System.out.println("Hallo " + user.getFullName());
                while(true){
                    //menu-menu yang bisa diakses oleh customer
                    System.out.println("Menu : ");
                    System.out.println("1. Keranjang Anda");
                    System.out.println("2. Tambah Produk ke Keranjang Anda");
                    System.out.println("3. Total Harga");
                    System.out.println("4. Checkout");
                    System.out.print("Masukkan Pilihan Menu : ");
                    int menu = sc.nextInt();
                    
                    
                    //coba masukin dulu ke shop cart clothing
                    ShoppingCart keranjang = new ShoppingCart(user);
                    ProductFactory clothingFactory = new ClothingFactory();
                    String[] clothingParams = {"L", "Cotton"};
                    Product baju = clothingFactory.createProduct("C001", "Kaos Polos Keren", 75000, 10, clothingParams);
                    keranjang.addItem(baju, 2);
        
                    //kalau mau lihat isi keranjang
                    if (menu == 1){
                        System.out.println("Keranjang Anda : ");
                        List<CartItem> isi = keranjang.getItems();
                        for (CartItem temp : isi) {
                            Product p = temp.getProduct();
                            System.out.println(p.getDetails() +
                                " | Qty: " + temp.getQuantity() +
                                " | Harga Satuan: " + formatRupiah(p.getTotalPrice()));
                        }
    
                        System.out.print("Logout (yes/no) : ");
                        String logOut = sc.next();
                        if(logOut.equalsIgnoreCase("yes")){
                            break;
                        }
                    }
                    else if (menu == 2){//tambah product ke keranjang
                        System.out.print("Masukkan ID produk: ");
                        String id = sc.nextLine();
                        System.out.print("Masukkan nama produk: ");
                        String name = sc.nextLine();
                        System.out.print("Masukkan harga: ");
                        double price = sc.nextDouble();
                        System.out.print("Masukkan stok: ");
                        int stock = sc.nextInt();
                        sc.nextLine();
                        System.out.print("Masukkan ukuran: ");
                        String size = sc.nextLine();
                        System.out.print("Masukkan bahan: ");
                        String material = sc.nextLine();
                        Product newProduct = clothingFactory.createProduct(id, name, price, stock, new String[]{size, material});
                        System.out.print("Masukkan jumlah yang ingin ditambahkan: ");
                        int qty = sc.nextInt();
                        keranjang.addItem(newProduct, qty);
                        System.out.println("Produk berhasil ditambahkan ke keranjang!");
    
                        System.out.print("Logout (yes/no) : ");
                        String logOut = sc.next();
                        if(logOut.equalsIgnoreCase("yes")){
                            break;
                        }
                        
                    }
                    else if (menu == 3){//total harga yang ada di keranjang
                        System.out.println("Total Harga : RP." + formatRupiah(keranjang.getTotalPrice()));
                        System.out.print("Logout (yes/no) : ");
                        String logOut = sc.next();
                        if(logOut.equalsIgnoreCase("yes")){
                            break;
                        }
                    }else if (menu == 4){//checkout
                        if(keranjang.isEmpty()){
                            System.out.println("Ups, keranjang anda kosong. Silahkan tambahkan produk baru ke keranjang :)");
                        }else{
                            List<CartItem> items = keranjang.getItems();
                            ShoppingCart selectedCart = new ShoppingCart(user);
                            // System.out.println("Pilih item yang ingin Anda checkout (pisahkan dengan koma, contoh: 1,3):");
                            System.out.println("Keranjang anda : ");
                            for (int i = 0; i < items.size(); i++) {
                                System.out.println("*-------------------------------------------------------*");
                                Product p = items.get(i).getProduct();
                                System.out.println("*" + (i+1) + ". " + p.getName() +
                                    " | Qty: " + items.get(i).getQuantity() +
                                    " | Harga Satuan: " + formatRupiah(p.getTotalPrice()) + "  *");
                                    System.out.println("*-------------------------------------------------------*");
                            }
        
                            System.out.println();
                            System.out.print("Pilih item yang ingin Anda checkout (pisahkan dengan koma) : ");
                            String pilihan = sc.next();
                            System.out.println();
                            String[] indeks = pilihan.split(",");
                            for (String idxStr : indeks) {
                                try {
                                    int idx = Integer.parseInt(idxStr.trim()) - 1;
                                    if (idx >= 0 && idx < items.size()) {
                                        CartItem selected = items.get(idx);
                                        selectedCart.addItem(selected.getProduct(), selected.getQuantity());
                                    }
                                } catch (Exception e) {
                                    System.out.println("Input tidak valid: " + idxStr);
                                }
                            }
        
                            //strategi pembayaran
                            System.out.print("Pilih Jenis Pembayaran (Credit Card (cc) / GOPAY / Transfer Bank) : ");
                            PaymentContext paymentContext = new PaymentContext();
                            String payment = sc.next();
                            if(payment.equalsIgnoreCase("cc")){
                                //check apakah cc user sudah terdaftar pada sistem
                                if(registeredCC.containsKey(user.getUsername())){//case kalau cc udh terdaftar
                                    paymentContext.setPaymentStrategy(new CreditCard(registeredCC.get(user.getUsername())));
                                }else{//cc tidak terdftar
                                    System.out.println("Credit Card anda tidak terdaftar.");
                                    
                                }
                            }else if (payment.equalsIgnoreCase("Gopay")){
                                GopayAPI gopayApi = new GopayAPI();
                                paymentContext.setPaymentStrategy(new GopayAdapter(gopayApi));
                            }else if (payment.equalsIgnoreCase("transfer")){
                                System.out.print("Masukkan nomor rekening tujuan: ");
                                String accNum = sc.nextLine();
                                paymentContext.setPaymentStrategy(new SwiftTransfer(accNum));
                            }else{
                                System.out.println("Maaf, Metode Pembayaran Tidak Tersedia");
                            }
        
                            OrderSubject orderSubject = new OrderSubject();
                            Checkout checkout = new Checkout(paymentContext, orderSubject, config);
        
                            checkout.processCheckout(user, selectedCart, false, false, payment, "Jl. Merdeka 10");
        
                            Order latestOrder = checkout.getLastOrder();
                            System.out.println("\n====== RINGKASAN PESANAN ======");
                            System.out.println(latestOrder.getSummary());
                        }
    
                        System.out.print("Logout (yes/no) : ");
                        String logOut = sc.next();
                        if(logOut.equalsIgnoreCase("yes")){
                            break;
                        }
                    }
                    
                    else {
                        System.out.println("Menu tidak valid.");
                        System.out.print("Logout (yes/no) : ");
                        String logOut = sc.next();
                        if(logOut.equalsIgnoreCase("yes")){
                            break;
                        }
                    }

                }
    
                
            }
        }
    }

    public static String formatRupiah(double amount) {
        return String.format("Rp%,.0f", amount).replace(',', '.');
    }

}
