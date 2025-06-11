import java.time.LocalDateTime;
public class Order {
    private String orderId;
    private User user;
    private ShoppingCart cart;
    private double totalPrice;
    private double taxAmount;
    private boolean giftWrap;
    private boolean expressShipping;
    private String paymentMethod;
    private String deliveryAddress;
    private LocalDateTime orderDate;
    private String status;
    private Config config;

    public Order(String orderId, User user, ShoppingCart cart, double totalPrice, double taxAmount, boolean giftWrap, boolean expressShipping,
                 String paymentMethod, String deliveryAddress, LocalDateTime orderDate, String status, Config config) {
        this.orderId = orderId;
        this.user = user;
        this.cart = cart;
        this.totalPrice = totalPrice;
        this.taxAmount = taxAmount;
        this.giftWrap = giftWrap;
        this.expressShipping = expressShipping;
        this.paymentMethod = paymentMethod;
        this.deliveryAddress = deliveryAddress;
        this.orderDate = orderDate;
        this.status = status;
        this.config = config;
    }

    public User getUser(){
        return user;
    }

    // Tambahkan getter untuk taxAmount
    public double getTaxAmount() {
        return taxAmount;
    }

    public double getTotalPrice() {
        double baseTotal = cart.getTotalPrice();

        // Tambahkan biaya gift wrap jika dipilih
        if (giftWrap) {
            baseTotal += 10000;
        }

        // Tambahkan biaya express shipping jika dipilih
        if (expressShipping) {
            baseTotal += 15000;
        }

        // Hitung pajak
        this.taxAmount = baseTotal * config.getTaxRate();
        return baseTotal + this.taxAmount;
    }

    public LocalDateTime getOrderDate(){
        return orderDate;
    }

    public String getDeliveryAddress(){
        return deliveryAddress;
    }

    public ShoppingCart getCart(){
        return cart;
    }

    public String getOrderId() {
        return orderId;
    }
    
    public String getStatus() {
        return status;
    }

    public String getSummary() {
        double subtotal = cart.getTotalPrice();
        if (giftWrap) subtotal += 10000;
        if (expressShipping) subtotal += 15000;

        return String.format(
                "Order Summary:\n" +
                        "Order ID: %s\n" +
                        "Subtotal: Rp%,.2f\n" +
                        "Gift Wrap: %s\n" +
                        "Express Shipping: %s\n" +
                        "Tax (%.0f%%): Rp%,.2f\n" +
                        "Total: Rp%,.2f\n" +
                        "Alamat Pengiriman: %s",
                orderId,
                subtotal,
                giftWrap ? "Ya" : "Tidak",
                expressShipping ? "Ya" : "Tidak",
                config.getTaxRate() * 100,
                taxAmount,
                getTotalPrice(),
                deliveryAddress
        );
    }

    public void sendNotificationToCustomer() {
        System.out.println("Notification sent to user " + user.getUsername());
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
