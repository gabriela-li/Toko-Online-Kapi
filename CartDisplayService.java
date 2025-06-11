import java.util.List;
public class CartDisplayService {

    public void displayCart(ShoppingCart keranjang) {
        List<CartItem> items = keranjang.getItems();

        System.out.println("Isi Keranjang:");
        for (int i = 0; i < items.size(); i++) {
            CartItem item = items.get(i);
            System.out.printf("%d. %s | Qty: %d | Harga per item: Rp%.0f\n",
                    i + 1,
                    item.getProduct().getName(),
                    item.getQuantity(),
                    item.getProduct().getPrice());
        }
    }
}