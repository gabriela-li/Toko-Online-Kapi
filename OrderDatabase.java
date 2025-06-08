import java.util.ArrayList;
import java.util.List;

public class OrderDatabase {
    private static final List<Order> orders = new ArrayList<>();

    public static void addOrder(Order order) {
        orders.add(order);
    }

    public static List<Order> getAllOrders() {
        return new ArrayList<>(orders);
    }
}
