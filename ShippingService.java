import java.util.Scanner;

public class ShippingService {
    private Scanner scanner;

    public ShippingService(Scanner scanner) {
        this.scanner = scanner;
    }

    public ShippingOptions selectShippingOptions() {
        boolean isExpressShipping = selectShippingType();
        boolean isGiftWrap = selectPackagingType();

        return new ShippingOptions(isExpressShipping, isGiftWrap);
    }

    private boolean selectShippingType() {
        System.out.println("\n=== OPSI PENGIRIMAN ===");
        System.out.println("1. Standard Shipping (Gratis)");
        System.out.println("2. Express Shipping (+Rp15.000)");
        System.out.print("Pilih jenis pengiriman (1/2): ");

        int shippingChoice = scanner.nextInt();
        scanner.nextLine(); // consume newline

        return shippingChoice == 2;
    }

    private boolean selectPackagingType() {
        System.out.println("\n=== OPSI PENGEMASAN ===");
        System.out.println("1. Kemasan Biasa (Gratis)");
        System.out.println("2. Gift Wrap (+Rp10.000)");
        System.out.print("Pilih jenis pengemasan (1/2): ");

        int wrapChoice = scanner.nextInt();
        scanner.nextLine(); // consume newline

        return wrapChoice == 2;
    }
}