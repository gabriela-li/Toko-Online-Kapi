import java.time.LocalDateTime;
import java.util.UUID;
public class OrderBuilder {
    private String customerId;
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
    private User user;

    public OrderBuilder setCustomerId(String customerId) {
        this.customerId = customerId;
        return this;
    }

    public OrderBuilder setUser(User user){
        this.user = user;
        return this;
    }

    public OrderBuilder setTaxAmount(double taxAmount) {
        this.taxAmount = taxAmount;
        return this;
    }

    public OrderBuilder setConfig(Config config) {
        this.config = config;
        return this;
    }

    public OrderBuilder setCart(ShoppingCart cart) {
        this.cart = cart;
        return this;
    }

    public OrderBuilder setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
        return this;
    }

    public OrderBuilder setGiftWrap(boolean giftWrap) {
        this.giftWrap = giftWrap;
        return this;
    }

    public OrderBuilder setExpressShipping(boolean expressShipping) {
        this.expressShipping = expressShipping;
        return this;
    }

    public OrderBuilder setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
        return this;
    }

    public OrderBuilder setDeliveryAddress(String address) {
        this.deliveryAddress = address;
        return this;
    }

    public OrderBuilder setOrderDate(LocalDateTime date) {
        this.orderDate = date;
        return this;
    }

    public OrderBuilder setStatus(String status) {
        this.status = status;
        return this;
    }

    public Order build() {
        return new Order(
                UUID.randomUUID().toString(),
                user,  // Gunakan user yang diset
                cart,
                totalPrice,
                taxAmount,
                giftWrap,
                expressShipping,
                paymentMethod,
                deliveryAddress,
                orderDate,
                status,
                config
        );
    }
}
