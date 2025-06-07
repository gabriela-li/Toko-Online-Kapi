public class GopayAPI {

    public boolean makePayment(double amount, boolean isExpressShipping) {
        System.out.println("Processing payment via GoPay");
        System.out.println("Amount: Rp " + amount);
        System.out.println("Express shipping: " + (isExpressShipping ? "Yes" : "No"));

        // Simulasi proses pembayaran: sukses 90%
        if (Math.random() < 0.9) {
            System.out.println("GoPay payment successful.");
            return true;
        } else {
            System.out.println("GoPay payment failed.");
            return false;
        }
    }
}
