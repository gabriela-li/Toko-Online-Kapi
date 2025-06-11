import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ItemSelectionService {
    private Scanner scanner;

    public ItemSelectionService(Scanner scanner) {
        this.scanner = scanner;
    }

    public ShoppingCart selectItemsForCheckout(User user, ShoppingCart originalCart) {
        List<CartItem> items = new ArrayList<>(originalCart.getItems());
        ShoppingCart checkoutCart = new ShoppingCart(user);
        boolean hasValidItem = false;

        System.out.print("Pilih item yang ingin Anda checkout (pisahkan dengan koma): ");
        String pilihan = scanner.nextLine();
        String[] indeks = pilihan.split(",");

        for (String idxStr : indeks) {
            try {
                int idx = Integer.parseInt(idxStr.trim()) - 1;
                if (idx >= 0 && idx < items.size()) {
                    CartItem selectedItem = items.get(idx);

                    int checkoutQty = getCheckoutQuantity(selectedItem);

                    if (checkoutQty > 0 && checkoutQty <= selectedItem.getQuantity()) {
                        processValidCheckoutItem(selectedItem, checkoutQty, checkoutCart, originalCart);
                        hasValidItem = true;
                    } else {
                        System.out.println("Jumlah checkout tidak valid.");
                    }
                } else {
                    System.out.println("Pilihan indeks tidak valid: " + idxStr);
                }
            } catch (NumberFormatException e) {
                System.out.println("Input tidak valid: " + idxStr);
            }
        }

        return hasValidItem ? checkoutCart : new ShoppingCart(user);
    }

    private int getCheckoutQuantity(CartItem selectedItem) {
        System.out.printf("Masukkan jumlah untuk checkout '%s' (max %d): ",
                selectedItem.getProduct().getName(), selectedItem.getQuantity());
        int checkoutQty = scanner.nextInt();
        scanner.nextLine(); // consume newline
        return checkoutQty;
    }

    private void processValidCheckoutItem(CartItem selectedItem, int checkoutQty,
                                          ShoppingCart checkoutCart, ShoppingCart originalCart) {
        // Tambahkan ke keranjang checkout
        checkoutCart.addItemForCheckout(selectedItem.getProduct(), checkoutQty);

        // Kurangi quantity di keranjang asli
        originalCart.reduceItemQuantity(selectedItem.getProduct().getId(), checkoutQty);

        System.out.printf("Checkout %d x %s berhasil ditambahkan\n",
                checkoutQty, selectedItem.getProduct().getName());
    }
}