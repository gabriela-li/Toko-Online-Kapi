import java.util.ArrayList;
import java.util.List;

public class OrderSubject {
    private List<OrderObserver> observers = new ArrayList<>();

    // Register observer
    public void registerObserver(OrderObserver observer) {
        observers.add(observer);
    }

    // Remove observer
    public void removeObserver(OrderObserver observer) {
        observers.remove(observer);
    }

    // Notify semua observer dengan orderId dan status baru
    public void notifyObservers(String orderId, String status) {
        for (OrderObserver observer : observers) {
            observer.update(orderId, status);
        }
    }
}

// Interface observer contoh
interface OrderObserver {
    void update(String orderId, String status);
}
