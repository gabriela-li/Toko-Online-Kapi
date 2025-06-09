import java.time.LocalDateTime;

public class Order {
    private String orderId;
    private User user;
    private ShoppingCart cart;
    private double totalPrice;
    private boolean giftWrap;
    private boolean expressShipping;
    private String paymentMethod;
    private String deliveryAddress;
    private LocalDateTime orderDate;
    private String status;

    public Order(String orderId, User user, ShoppingCart cart, double totalPrice, boolean giftWrap, boolean expressShipping,
                 String paymentMethod, String deliveryAddress, LocalDateTime orderDate, String status) {
        this.orderId = orderId;
        this.user = user;
        this.cart = cart;
        this.totalPrice = totalPrice;
        this.giftWrap = giftWrap;
        this.expressShipping = expressShipping;
        this.paymentMethod = paymentMethod;
        this.deliveryAddress = deliveryAddress;
        this.orderDate = orderDate;
        this.status = status;
    }

    public double getTotalPrice() {
        double baseTotal = cart.getTotalPrice();

        // Misal tambahkan biaya gift wrap Rp10.000 jika giftWrap true
        if (giftWrap) {
            baseTotal += 10000;
        }

        // Misal tambahkan biaya express shipping Rp15.000 jika expressShipping true
        if (expressShipping) {
            baseTotal += 15000;
        }

        return baseTotal;
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
        return String.format("Order Summary: %s, Total: %.2f", orderId, totalPrice);
    }

    public void sendNotificationToCustomer() {
        System.out.println("Notification sent to user " + user.getUsername());
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
