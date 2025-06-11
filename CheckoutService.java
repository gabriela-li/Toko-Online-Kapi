import java.util.Scanner;

public class CheckoutService {
    private Scanner scanner;
    private Config config;

    public CheckoutService(Scanner scanner, Config config) {
        this.scanner = scanner;
        this.config = config;
    }

    public void processCheckout(User user, ShoppingCart keranjang) {
        // Cek apakah keranjang kosong
        if (keranjang.getItems().isEmpty()) {
            System.out.println("Keranjang Anda kosong.");
            return;
        }

        // Tampilkan isi keranjang dan biarkan user memilih item
        CartDisplayService cartDisplay = new CartDisplayService();
        cartDisplay.displayCart(keranjang);

        // Proses pemilihan item untuk checkout
        ItemSelectionService itemSelection = new ItemSelectionService(scanner);
        ShoppingCart checkoutCart = itemSelection.selectItemsForCheckout(user, keranjang);

        if (checkoutCart.getItems().isEmpty()) {
            System.out.println("Tidak ada item untuk dibayar.");
            return;
        }

        // Pilih metode pembayaran
        PaymentMethodService paymentService = new PaymentMethodService(scanner);
        PaymentContext paymentContext = paymentService.selectPaymentMethod(user);

        if (paymentContext == null) {
            System.out.println("Metode pembayaran tidak valid.");
            return;
        }

        // Pilih opsi pengiriman dan pengemasan
        ShippingService shippingService = new ShippingService(scanner);
        ShippingOptions shippingOptions = shippingService.selectShippingOptions();

        // Input alamat pengiriman
        System.out.print("Masukkan alamat pengiriman: ");
        String deliveryAddress = scanner.nextLine();

        // Proses checkout
        OrderSubject orderSubject = new OrderSubject();
        Checkout checkout = new Checkout(paymentContext, orderSubject, config);
        checkout.processCheckout(user, checkoutCart,
                shippingOptions.isGiftWrap(),
                shippingOptions.isExpressShipping(),
                paymentService.getSelectedPaymentMethod(),
                deliveryAddress);

        // Tampilkan ringkasan pesanan
        Order latestOrder = checkout.getLastOrder();
        OrderDisplayService orderDisplay = new OrderDisplayService(scanner);
        orderDisplay.displayOrderSummary(latestOrder);
    }
}