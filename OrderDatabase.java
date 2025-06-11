import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class OrderDatabase {
    private static OrderDatabase instance;
    private static final List<Order> orders = new ArrayList<>();

    private OrderDatabase() {
    }

    public static OrderDatabase getInstance() {
        if (instance == null) {
            instance = new OrderDatabase();
        }
        return instance;
    }

    public void addOrder(Order order) {
        orders.add(order);
    }

    // Cari order by ID
    public static Order getOrderById(String orderId) {
        return orders.stream()
                .filter(o -> o.getOrderId().equals(orderId))
                .findFirst()
                .orElse(null);
    }

    // Dapatkan order by user
    public static List<Order> getOrdersByUser(String username) {
        return orders.stream()
                .filter(o -> o.getUser().getUsername().equals(username))
                .collect(Collectors.toList());
    }
}
