import java.util.HashMap;
import java.util.Scanner;

public class PaymentMethodService {
    private Scanner scanner;
    private String selectedPaymentMethod;
    // Asumsi ada map untuk CC yang terdaftar
    private static HashMap<String, String> registeredCC = new HashMap<>();

    public PaymentMethodService(Scanner scanner) {
        this.scanner = scanner;
    }

    public PaymentContext selectPaymentMethod(User user) {
        System.out.print("Pilih Jenis Pembayaran (cc / gopay / transfer): ");
        String paymentMethod = scanner.nextLine().toLowerCase();
        this.selectedPaymentMethod = paymentMethod;

        PaymentContext paymentContext = new PaymentContext();

        switch (paymentMethod) {
            case "cc" -> {
                if (registeredCC.containsKey(user.getUsername())) {
                    paymentContext.setPaymentStrategy(new CreditCard(registeredCC.get(user.getUsername())));
                } else {
                    System.out.println("Credit Card Anda tidak terdaftar.");
                    System.out.print("Apakah Anda ingin mendaftarkan credit card sekarang? (y/n): ");
                    String choice = scanner.nextLine().toLowerCase();

                    if (choice.equals("y") || choice.equals("yes")) {
                        System.out.print("Masukkan nomor credit card: ");
                        String ccNumber = scanner.nextLine();
                        registerCreditCard(user.getUsername(), ccNumber);
                        paymentContext.setPaymentStrategy(new CreditCard(ccNumber));
                        System.out.println("Credit card berhasil didaftarkan!");
                    } else {
                        return null;
                    }
                }
            }
            case "gopay" -> {
                GopayAPI gopayApi = new GopayAPI();
                paymentContext.setPaymentStrategy(new GopayAdapter(gopayApi));
            }
            case "transfer" -> {
                System.out.print("Masukkan nomor rekening tujuan: ");
                String accNum = scanner.nextLine();
                paymentContext.setPaymentStrategy(new SwiftTransfer(accNum));
            }
            default -> {
                return null;
            }
        }

        return paymentContext;
    }

    public String getSelectedPaymentMethod() {
        return selectedPaymentMethod;
    }

    // Method untuk register CC (jika diperlukan)
    public static void registerCreditCard(String username, String ccNumber) {
        registeredCC.put(username, ccNumber);
    }
}