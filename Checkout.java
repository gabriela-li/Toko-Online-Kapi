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
    public void processCheckout(User user, ShoppingCart cart, boolean isGiftWrap, boolean isExpressShipping, String paymentMethod, String address) {
        Order order = new OrderBuilder()
                .setCustomerId(user.getUsername())
                .setCart(cart)
                .setTotalPrice(cart.getTotalPrice())
                .setGiftWrap(isGiftWrap)
                .setExpressShipping(isExpressShipping)
                .setPaymentMethod(paymentMethod)
                .setDeliveryAddress(address)
                .setOrderDate(java.time.LocalDateTime.now())
                .setStatus("Pending")
                .build();
        this.lastOrder = order;
        
        boolean paymentSuccess = paymentContext.pay(order.getTotalPrice(), isExpressShipping);
        if (paymentSuccess) {
            updateOrderStatus(order.getOrderId(), "Paid");
        } else {
            updateOrderStatus(order.getOrderId(), "Failed");
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
}
