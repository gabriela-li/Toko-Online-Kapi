import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ShoppingCart {
    private User user;
    private List<CartItem> items;

    // Constructor: ShoppingCart dimiliki oleh user tertentu
    public ShoppingCart(User user) {
        this.user = user;
        this.items = new ArrayList<>();
    }

    // Method untuk add item ke cart
    public void addItem(Product product, int quantity) {
        if (product.getStock() < quantity) {
            System.out.println("Stok tidak mencukupi!");
            return;
        }

        for (CartItem item : items) {
            if (item.getProduct().getId().equals(product.getId())) {
                item.setQuantity(item.getQuantity() + quantity);
                product.reduceStock(quantity);
                return;
            }
        }
        items.add(new CartItem(product, quantity));
        product.reduceStock(quantity);
        System.out.println("Produk berhasil dimasukkan ke keranjang.");
    }

    public void reduceItemQuantity(String productId, int quantity) {
        Iterator<CartItem> iterator = items.iterator();
        while (iterator.hasNext()) {
            CartItem item = iterator.next();
            if (item.getProduct().getId().equals(productId)) {
                int currentQty = item.getQuantity();
                if (quantity >= currentQty) {
                    // Kalau dikurangi sama atau lebih dari quantity sekarang, hapus item
                    iterator.remove();
                } else {
                    // Kurangi quantity saja
                    item.setQuantity(currentQty - quantity);
                }
                break;
            }
        }
    }

    // Method khusus untuk checkout
    public void addItemForCheckout(Product product, int quantity) {
        for (CartItem item : items) {
            if (item.getProduct().getId().equals(product.getId())) {
                item.setQuantity(item.getQuantity() + quantity);
                return;
            }
        }
        items.add(new CartItem(product, quantity));
    }

    // Hapus item berdasarkan ID produk
    public void removeItem(String productId) {
        Iterator<CartItem> iterator = items.iterator();
        while (iterator.hasNext()) {
            CartItem item = iterator.next();
            if (item.getProduct().getId().equals(productId)) {
                iterator.remove();
                break;
            }
        }
    }

    public List<CartItem> getItems() {
        return items;
    }

    public double getTotalPrice() {
        double total = 0.0;
        for (CartItem item : items) {
            total += item.getTotalPrice();
        }
        return total;
    }

    public void clear() {
        items.clear();
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    //Getter untuk mendapatkan user
    public String getUserName() {
        return user.getFullName() ;
    }

    public String getOwnerInfo() {
        return user.getFullName() + " (" + user.getUsername() + ")";
    }
}
