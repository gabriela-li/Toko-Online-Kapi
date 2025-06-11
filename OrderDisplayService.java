import java.util.List;
import java.util.Scanner;

public class OrderDisplayService {
    private Scanner scanner; 

    public OrderDisplayService(Scanner scanner) {
        this.scanner = scanner;
    }

    public void displayOrderSummary(Order order) {
        System.out.println("\n====== RINGKASAN PESANAN ======");
        System.out.println(order.getSummary());
        System.out.println("ID Pesanan: " + order.getOrderId());
    }

    public void displayOrderHistory(User user) {
        System.out.println("\n=== RIWAYAT PESANAN ===");
        List<Order> userOrders = OrderDatabase.getOrdersByUser(user.getUsername());

        if (userOrders.isEmpty()) {
            System.out.println("Belum ada pesanan.");
        } else {
            System.out.println("Total pesanan: " + userOrders.size());
            for (Order order : userOrders) {
                displaySingleOrderHistory(order);
            }
        }
    }

    private void displaySingleOrderHistory(Order order) {
        System.out.println("\n--------------------------------");
        System.out.println("ID Pesanan: " + order.getOrderId());
        System.out.println("Tanggal: " + order.getOrderDate());
        System.out.println("Status: " + order.getStatus());
        System.out.println(order.getSummary());
    }
}