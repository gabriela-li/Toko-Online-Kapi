//checkout dengan konstruktor PaymentContext, OrderSubject, Config
public class Checkout {
    //atribut-atribut yang dimiliki
    private PaymentContext paymentContext;
    private OrderSubject orderSubject;
    private Config config;
    private Order lastOrder; //menyimpan order terakhir

    //constructor
    public Checkout(PaymentContext paymentContext, OrderSubject orderSubject, Config config) {
        this.paymentContext = paymentContext;
        this.orderSubject = orderSubject;
        this.config = config;
    }

    //method untuk proses checkout
    //usernya siapa, keranjang, giftWrap, express shipping kah, payment method nya apa, alamat usernya dimana
    public void processCheckout(User user, ShoppingCart cart, boolean isGiftWrap, boolean isExpressShipping,
                                String paymentMethod, String address) {
        // Hitung subtotal
        double subtotal = cart.getTotalPrice();

        // Hitung biaya tambahan
        if (isGiftWrap) subtotal += 10000;
        if (isExpressShipping) subtotal += 15000;

        // Hitung pajak
        double taxAmount = subtotal * config.getTaxRate();
        double totalPrice = subtotal + taxAmount;

        Order order = new OrderBuilder()
                .setUser(user)
                .setCustomerId(user.getUsername())
                .setCart(cart)
                .setTotalPrice(totalPrice)
                .setGiftWrap(isGiftWrap)
                .setExpressShipping(isExpressShipping)
                .setPaymentMethod(paymentMethod)
                .setDeliveryAddress(address)
                .setOrderDate(java.time.LocalDateTime.now())
                .setStatus("Pending")
                .setTaxAmount(taxAmount)
                .setConfig(config)
                .build();

        // simpan order ke database
        OrderDatabase.addOrder(order);

        this.lastOrder = order;

        TransactionManager.getInstance().addTransaction(order, user, paymentMethod);

        boolean paymentSuccess = paymentContext.pay(order.getTotalPrice(), isExpressShipping);
        if (paymentSuccess) {
            updateOrderStatus(order.getOrderId(), "Paid");
            TransactionManager.getInstance().updateTransactionStatus(order.getOrderId(), "Paid");
        } else {
            updateOrderStatus(order.getOrderId(), "Failed");
            TransactionManager.getInstance().updateTransactionStatus(order.getOrderId(), "Failed");
        }

        order.sendNotificationToCustomer();
        orderSubject.notifyObservers(order.getOrderId(), order.getStatus());
    }

    public void updateOrderStatus(String orderId, String newStatus) {
        System.out.println("Order " + orderId + " updated to: " + newStatus);
        if (lastOrder != null && lastOrder.getOrderId().equals(orderId)) {
            lastOrder.setStatus(newStatus);
        }
    }

    public Order getLastOrder() {
        return lastOrder;
    }

    // Untuk cek status order nanti:
    public void checkOrderStatus(String orderId) {
        Order order = OrderDatabase.getOrderById(orderId);
        if (order != null) {
            System.out.println("Status order: " + order.getStatus());
        } else {
            System.out.println("Order tidak ditemukan");
        }
    }
}
